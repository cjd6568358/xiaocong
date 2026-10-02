<#
  方案 2（domain 参数）真机实验 —— 一次跑完：强扫 → 连插座热点 → 配网 → 回连家庭网

  探针为什么选【公网 IP 139.227.20.142】而不是 allin.dns.army：
    光猫 ixc-go 的劫持域是 [ixiaocong.com allin.dns.army]，allin.dns.army 已在名单里，
    用它当探针无法区分"认了 domain"和"命中劫持"。
    而 203.0.113.9（劫持应答 IP）与 139.227.20.142（WAN IP）在 conntrack 里一眼可分。

  判定：
    插座连向 139.227.20.142:8188 且【没有】iot.ixiaocong.com 的 DNS 查询 → ✅ 认 domain
    插座仍查 iot.ixiaocong.com 并连向 203.0.113.9            → ❌ 忽略 domain
#>
param(
  [string]$PlugSsid = "smart-381785-3a6e7c-b7",
  [string]$HomeSsid = "PDCN_IOT",
  [string]$HomePass = "e3eb773F",
  [string]$Domain   = "139.227.20.142"
)

$root = Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path)
Set-Location $root
$out = Join-Path $root ".scratch\prov-domain.txt"
"" | Out-File -Encoding utf8 $out
function T($m) { $m | Tee-Object -FilePath $out -Append }

T "===== 方案 2 domain 实验 $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') ====="
T "插头热点=$PlugSsid  家庭网=$HomeSsid  探针 domain=$Domain"

# ---------- 0. 强制扫描（netsh 只读缓存，必须 WlanScan 触发真扫描） ----------
$sig = @'
[DllImport("wlanapi.dll", SetLastError=true)]
public static extern int WlanOpenHandle(int v, IntPtr r, out int nv, out IntPtr h);
[DllImport("wlanapi.dll", SetLastError=true)]
public static extern int WlanEnumInterfaces(IntPtr h, IntPtr r, out IntPtr p);
[DllImport("wlanapi.dll", SetLastError=true)]
public static extern int WlanScan(IntPtr h, ref Guid g, IntPtr s, IntPtr ie, IntPtr r);
[DllImport("wlanapi.dll", SetLastError=true)]
public static extern void WlanFreeMemory(IntPtr p);
[DllImport("wlanapi.dll", SetLastError=true)]
public static extern int WlanCloseHandle(IntPtr h, IntPtr r);
'@
Add-Type -MemberDefinition $sig -Name WlanApi -Namespace XcW -ErrorAction SilentlyContinue
$v = 0; $h = [IntPtr]::Zero; $p = [IntPtr]::Zero
if ([XcW.WlanApi]::WlanOpenHandle(2, [IntPtr]::Zero, [ref]$v, [ref]$h) -eq 0) {
  if ([XcW.WlanApi]::WlanEnumInterfaces($h, [IntPtr]::Zero, [ref]$p) -eq 0) {
    $g = [System.Runtime.InteropServices.Marshal]::PtrToStructure([IntPtr]::Add($p, 8), [Type][Guid])
    [XcW.WlanApi]::WlanScan($h, [ref]$g, [IntPtr]::Zero, [IntPtr]::Zero, [IntPtr]::Zero) | Out-Null
    [XcW.WlanApi]::WlanFreeMemory($p)
  }
  [XcW.WlanApi]::WlanCloseHandle($h, [IntPtr]::Zero)
}
Start-Sleep -Seconds 7

$ssids = (netsh wlan show networks) -replace "`r","" |
  Where-Object { $_ -match '^\s*SSID\s+\d+\s*:' } | ForEach-Object { ($_ -split ':',2)[1].Trim() }
T "当前可见 SSID（$($ssids.Count) 个）: $($ssids -join ' | ')"
if ($ssids -notcontains $PlugSsid) {
  T "❌ 没看到 $PlugSsid，插座可能已退出配网模式"
  exit 1
}
T "✅ 命中插座热点 $PlugSsid"

# ---------- 1. 连插座热点 ----------
$xml = @"
<?xml version="1.0"?>
<WLANProfile xmlns="http://www.microsoft.com/networking/WLAN/profile/v1">
  <name>$PlugSsid</name>
  <SSIDConfig><SSID><name>$PlugSsid</name></SSID></SSIDConfig>
  <connectionType>ESS</connectionType>
  <connectionMode>manual</connectionMode>
  <MSM><security><authEncryption><authentication>open</authentication><encryption>none</encryption><useOneX>false</useOneX></authEncryption></security></MSM>
</WLANProfile>
"@
$tmp = Join-Path $env:TEMP "xc-ap.xml"
$xml | Out-File -Encoding ASCII $tmp
netsh wlan add profile filename="$tmp" | Out-Null
Remove-Item $tmp -Force
netsh wlan connect name="$PlugSsid" ssid="$PlugSsid" | Out-Null
T "[*] 已发起连接 $PlugSsid，等待拿到 192.168.4.x ..."

$ip = ""
for ($i = 0; $i -lt 30; $i++) {
  Start-Sleep -Seconds 1
  $ip = (Get-NetIPAddress -AddressFamily IPv4 -ErrorAction SilentlyContinue |
    Where-Object { $_.IPAddress -like '192.168.4.*' } | Select-Object -First 1).IPAddress
  if ($ip) { break }
}
if (-not $ip) { T "❌ 未拿到 192.168.4.x，回连家庭网"; netsh wlan connect name="$HomeSsid" | Out-Null; exit 1 }
T "    本机地址 $ip ✅"

# ---------- 2. 配网 ----------
$node = (Get-Command node -ErrorAction SilentlyContinue).Source
if (-not $node) { $node = "C:/Users/caojiecn/.workbuddy-ai/binaries/node/versions/22.22.2-3/node.exe" }
T ""
T "########## 下发 ch_data : ssid=$HomeSsid  domain=$Domain  crt=(空) ##########"
& $node tools/provision.js "--ssid=$HomeSsid" "--pass=$HomePass" "--domain=$Domain" `
    "--timeout=30000" "--end-fallback=2500" 2>&1 | Tee-Object -FilePath $out -Append
$rc = $LASTEXITCODE
T ""
T "provision.js 退出码 = $rc   (0=成功 2=超时 3=设备NACK)"

# ---------- 3. 回连家庭网 ----------
T "[*] 回连 $HomeSsid ..."
netsh wlan connect name="$HomeSsid" | Out-Null
for ($i = 0; $i -lt 30; $i++) {
  Start-Sleep -Seconds 1
  $back = (Get-NetIPAddress -AddressFamily IPv4 -ErrorAction SilentlyContinue |
    Where-Object { $_.IPAddress -like '192.168.1.*' } | Select-Object -First 1).IPAddress
  if ($back) { T "    已回到 $back ✅"; break }
}
T "===== 结束 $(Get-Date -Format 'HH:mm:ss') ====="
