<#
  自动守望 + 自动配网：蹲插座热点 smart-* → 一出现就连上去 → 下发 ch_data → 回连家庭网。

  为什么必须自动：插座配网模式有超时，靠人反应 + 我手工敲命令，
  窗口很容易错过（03:13 出现、03:23 就没了，中间我在改别的脚本）。

  探针为什么选【公网 IP 139.227.20.142】而不是 allin.dns.army：
    光猫 ixc-go 的劫持域是 [ixiaocong.com allin.dns.army]，
    allin.dns.army 【已在劫持名单里】，拿它当探针无法区分
    "认了 domain" 和 "命中劫持" —— 两者都会连到 203.0.113.9。
    而 139.227.20.142（光猫 WAN IP，公网、不在名单里）命中要靠 hairpin 规则，
    在 conntrack 里和 203.0.113.9 一眼可分。

  判定（事后看光猫）：
    conntrack 出现 dst=139.227.20.142 dport=8188，且 ixc-go 日志
      【没有】"DNS 劫持 iot.ixiaocong.com ... 来自 192.168.1.23" → ✅ 认 domain
    ixc-go 日志出现 "DNS 劫持 iot.ixiaocong.com ... 来自 192.168.1.23" → ❌ 忽略 domain

  用法：
    powershell -ExecutionPolicy Bypass -File .scratch\watch-and-provision.ps1
    （可加 -Domain "xxx" 换探针；-TotalSec 改守望总时长）
#>
param(
  [string]$HomeSsid = "PDCN_IOT",
  [string]$HomePass = "e3eb773F",
  [string]$Domain   = "139.227.20.142",
  [int]$TotalSec    = 1500,
  [int]$StepSec     = 12
)

$root = Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path)
Set-Location $root
$out = Join-Path $root ".scratch\watch-provision.txt"
"" | Out-File -Encoding utf8 $out
function T($m) { $m | Tee-Object -FilePath $out -Append }

# ---- WlanScan：netsh 只读缓存，连着网时缓存长期不刷新，必须手动触发真扫描 ----
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

function Force-Scan {
  $v = 0; $h = [IntPtr]::Zero; $p = [IntPtr]::Zero
  try {
    if ([XcW.WlanApi]::WlanOpenHandle(2, [IntPtr]::Zero, [ref]$v, [ref]$h) -eq 0) {
      if ([XcW.WlanApi]::WlanEnumInterfaces($h, [IntPtr]::Zero, [ref]$p) -eq 0) {
        $g = [System.Runtime.InteropServices.Marshal]::PtrToStructure([IntPtr]::Add($p, 8), [Type][Guid])
        [XcW.WlanApi]::WlanScan($h, [ref]$g, [IntPtr]::Zero, [IntPtr]::Zero, [IntPtr]::Zero) | Out-Null
        [XcW.WlanApi]::WlanFreeMemory($p)
      }
      [XcW.WlanApi]::WlanCloseHandle($h, [IntPtr]::Zero)
    }
  } catch {}
  Start-Sleep -Seconds 6
}

function Get-Ssids {
  (netsh wlan show networks) -replace "`r","" |
    Where-Object { $_ -match '^\s*SSID\s+\d+\s*:' } |
    ForEach-Object { ($_ -split ':',2)[1].Trim() } | Where-Object { $_ -ne "" }
}

T "===== 守望开始 $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')  总时长 ${TotalSec}s / 每 ${StepSec}s 一轮 ====="
T "家庭网=$HomeSsid  探针 domain=$Domain"
T "（现在请长按插座按钮 5 秒进入配网模式）"

$node = (Get-Command node -ErrorAction SilentlyContinue).Source
if (-not $node) { $node = "C:\Users\caojiecn\.workbuddy-ai\binaries\node\versions\22.22.2-3\node.exe" }

$t = 0
$hit = ""
while ($t -lt $TotalSec) {
  Force-Scan
  $ssids = Get-Ssids
  $hit = ($ssids | Where-Object { $_ -match '^smart-' } | Select-Object -First 1)
  if ($hit) {
    T "[$(Get-Date -Format 'HH:mm:ss')] ★★★ 命中插座热点: $hit（共 $($ssids.Count) 个 SSID）"
    break
  }
  if (($t / $StepSec) % 5 -eq 0) {
    T "[$(Get-Date -Format 'HH:mm:ss')] 还没出现，当前 $($ssids.Count) 个 SSID"
  }
  Start-Sleep -Seconds $StepSec
  $t += $StepSec
}

if (-not $hit) {
  T "[$(Get-Date -Format 'HH:mm:ss')] 超时（${TotalSec}s），没等到插座热点"
  exit 1
}

# ---------- 连插座热点 ----------
$xml = @"
<?xml version="1.0"?>
<WLANProfile xmlns="http://www.microsoft.com/networking/WLAN/profile/v1">
  <name>$hit</name>
  <SSIDConfig><SSID><name>$hit</name></SSID></SSIDConfig>
  <connectionType>ESS</connectionType>
  <connectionMode>manual</connectionMode>
  <MSM><security><authEncryption><authentication>open</authentication><encryption>none</encryption><useOneX>false</useOneX></authEncryption></security></MSM>
</WLANProfile>
"@
$tmp = Join-Path $env:TEMP "xc-ap.xml"
$xml | Out-File -Encoding ASCII $tmp
netsh wlan add profile filename="$tmp" | Out-Null
Remove-Item $tmp -Force
netsh wlan connect name="$hit" ssid="$hit" | Out-Null
T "[$(Get-Date -Format 'HH:mm:ss')] 已发起连接 $hit，等 192.168.4.x ..."

$ip = ""
for ($i = 0; $i -lt 35; $i++) {
  Start-Sleep -Seconds 1
  $ip = (Get-NetIPAddress -AddressFamily IPv4 -ErrorAction SilentlyContinue |
    Where-Object { $_.IPAddress -like '192.168.4.*' } | Select-Object -First 1).IPAddress
  if ($ip) { break }
}
if (-not $ip) {
  T "❌ 没拿到 192.168.4.x，回连家庭网"
  netsh wlan connect name="$HomeSsid" | Out-Null
  exit 1
}
T "    本机地址 $ip ✅  —— 这一步必须拿到 4.x，否则后面全是假结果"

# ---------- 配网 ----------
T ""
T "########## 下发 ch_data : ssid=$HomeSsid  domain=$Domain  crt=(空) ##########"
& $node tools/provision.js "--ssid=$HomeSsid" "--pass=$HomePass" "--domain=$Domain" `
    "--timeout=30000" "--end-fallback=2500" 2>&1 | Tee-Object -FilePath $out -Append
T ""
T "provision.js 退出码 = $LASTEXITCODE  (0=设备接受并回了 mac/product_id  2=超时  3=设备NACK)"

# ---------- 回连家庭网 ----------
T "[$(Get-Date -Format 'HH:mm:ss')] 回连 $HomeSsid ..."
netsh wlan connect name="$HomeSsid" | Out-Null
for ($i = 0; $i -lt 30; $i++) {
  Start-Sleep -Seconds 1
  $back = (Get-NetIPAddress -AddressFamily IPv4 -ErrorAction SilentlyContinue |
    Where-Object { $_.IPAddress -like '192.168.1.*' } | Select-Object -First 1).IPAddress
  if ($back) { T "    已回到 $back ✅"; break }
}
T "===== 结束 $(Get-Date -Format 'HH:mm:ss') ====="
T "下一步：去光猫看  grep 8188 /proc/net/nf_conntrack  和  tail -40 /tmp/ixc-go.log"
