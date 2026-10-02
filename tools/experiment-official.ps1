<#
  官方云端旁观实验 —— 不改插座任何配置，看它到底往哪连。

  思路（按用户要求）：
    配网时 domain="" / crt=""（与厂商 App 完全一致 → 插座走它自己的内置云端），
    然后由 tools/lan-mitm.py 在二层 ARP 欺骗网关，把它发往子网外的
    DNS 查询 + TCP/TLS 连接全部截下来 —— 全程不碰插座配置。

  为什么必须 ARP 欺骗：
    Wi-Fi 下看不到别的终端的单播流量（AP 只转发给目标站点），但广播能看到。
    骗插座"网关 MAC 是我"，它所有出子网的流量就会送到本机网卡。
    插座要访问的 DNS（192.168.1.1 / 223.5.5.5）和云端都在子网外 → 全被截获。

  用法（项目根目录，插座已长按 5 秒进入配网模式）：
    & .\tools\experiment-official.ps1
    & .\tools\experiment-official.ps1 -Mode dns -CaptureSec 600
#>
param(
  [string]$PlugSsid = "smart-381785-3a6e7c-b7",
  [string]$HomeSsid = "PDCN_IOT",
  [string]$HomePass = "e3eb773F",
  [int]$CaptureSec  = 300,
  [ValidateSet('relay', 'dns', 'spoof', 'watch')][string]$Mode = 'relay',
  [string]$FakeIp    = '192.168.1.20'
)

$root = Split-Path -Parent $PSScriptRoot
Set-Location $root
$log = Join-Path $root "experiment-official.log"
try { [Console]::OutputEncoding = [System.Text.Encoding]::UTF8 } catch {}
$OutputEncoding = [System.Text.Encoding]::UTF8
"===== 官方云端旁观实验 $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') =====" | Out-File -Encoding utf8 $log
function T($m) { $m | Tee-Object -FilePath $log -Append }

$nodeExe = (Get-Command node -ErrorAction SilentlyContinue).Source
if (-not $nodeExe) { $nodeExe = "C:\Users\caojiecn\.workbuddy-ai\binaries\node\versions\22.22.2-3\node.exe" }
$pyExe = "C:\Users\caojiecn\.workbuddy-ai\binaries\python\envs\xiaocong\Scripts\python.exe"

function Get-ApIp {
  (Get-NetIPAddress -AddressFamily IPv4 -ErrorAction SilentlyContinue |
    Where-Object { $_.IPAddress -like '192.168.4.*' } | Select-Object -First 1).IPAddress
}

function Get-CurSsid {
  $l = (netsh wlan show interfaces) 2>$null | Where-Object { $_ -match '^\s+SSID\s+:' } | Select-Object -First 1
  if ($l) { return ($l -replace '^\s+SSID\s+:\s*', '').Trim() }
  return ''
}

# ---------- 1. 连插座热点 ----------
T "[1] 等插座配网热点 smart-* 出现 ..."

# 先等热点出现（插座配网模式窗口很短，这里主动等，避免抢时间）
$hit = @()
for ($w = 0; $w -lt 40; $w++) {
  $hit = @(& (Join-Path $PSScriptRoot 'wlanapi-scan.ps1') -Filter 'smart-' 2>$null |
           Where-Object { $_ -is [string] -and $_ -match 'smart-' })
  if ($hit.Count -gt 0) { break }
  if ($w -eq 0) { T "    还没看到 smart-* 热点，开始等待（最多 200 秒）…" }
  elseif ($w % 4 -eq 0) { T "    等待中… $($w * 5)s" }
  Start-Sleep -Seconds 5
}
if ($hit.Count -eq 0) {
  T "❌ 200 秒内一直没等到 smart-* 热点。请长按开关键 5 秒进入配网模式后重跑。"
  exit 1
}
T "    热点在：$($hit[0])"

# ★ 用**实际扫到的**热点名，而不是写死的旧名字（插座热点后缀会变）
$scanned = (($hit[0] -split "`t")[1])
if ($scanned) { $PlugSsid = $scanned.Trim() }
T "    使用热点名：$PlugSsid"

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
$tmp = Join-Path $env:TEMP "xc-ap4.xml"
$xml | Out-File -Encoding ASCII $tmp
netsh wlan add profile filename="$tmp" | Out-Null
Remove-Item $tmp -Force

# 重试式连接：每 12 秒重发一次 connect，同时报当前 SSID
$ip = ""
for ($i = 0; $i -lt 50; $i++) {
  if ($i % 12 -eq 0) { netsh wlan connect name="$PlugSsid" ssid="$PlugSsid" | Out-Null }
  Start-Sleep -Seconds 1
  $ip = Get-ApIp
  if ($ip) { break }
  if ($i % 10 -eq 9) { T "    第 $($i + 1)s：当前 SSID = '$(Get-CurSsid)'，尚未拿到 192.168.4.x" }
}
if (-not $ip) {
  T "❌ 连不上插座热点（当前 SSID='$(Get-CurSsid)'）—— 插座可能已退出配网模式"
  exit 1
}
T "    本机地址 $ip ✅  (SSID=$(Get-CurSsid))"

# ---------- 2. 用「出厂默认」配网 ----------
T ""
T "[2] 用出厂默认配网（domain='' / crt='' —— 与厂商 App 一致，不改任何默认行为）"
$env:XC_DOMAIN = ""
$env:XC_CRT = ""
& $nodeExe @('tools/provision.js', "--ssid=$HomeSsid", "--pass=$HomePass",
             '--timeout=15000', '--end-fallback=2500') 2>&1 | Tee-Object -FilePath $log -Append
$rc = $LASTEXITCODE
T "    配网退出码 = $rc  ($(if ($rc -eq 0) { '✅ 设备接受' } else { '❌ 未回包' }))"
if ($rc -ne 0) { T "❌ 配网未成功，后续观察无意义，退出"; exit 2 }

# ---------- 3. 回连家庭 WiFi ----------
T ""
T "[3] 回连家庭 WiFi $HomeSsid ..."
netsh wlan connect name="$HomeSsid" ssid="$HomeSsid" | Out-Null
Start-Sleep -Seconds 6

# ---------- 4. 等插座上线 ----------
T ""
T "[4] 等插座出现在局域网（主动 ARP 全网段扫描找 B4:E6:2D）..."
T "    注：不用 arp -a —— 它只记录'最近和本机通信过'的邻居，刚上线的插座不在里面。"
$plugIp = ""
for ($i = 0; $i -lt 30; $i++) {
  $s = & $pyExe 'tools/scan-lan.py' 2>&1
  $hit2 = $s | Select-String '找到插座:' -SimpleMatch | Select-Object -First 1
  if ($hit2) {
    $plugIp = (($hit2.ToString() -replace '.*找到插座:\s*', '') -split '\s+')[0]
    if ($plugIp) { break }
  }
  Start-Sleep -Seconds 3
}
if ($plugIp) { T "    插座已上线：$plugIp ✅" } else { T "    ⚠️ 90 秒内没看到插座上线（仍继续观察）" }

# ---------- 5. ARP 欺骗 + 采集 ----------
T ""
T "[5] 启动 lan-mitm.py（模式=$Mode，持续 ${CaptureSec}s）"
T "    → ARP 欺骗网关，截获插座发往子网外的一切（DNS / TCP / TLS）"
if ($Mode -eq 'dns') { T "    → 并伪造 DNS 应答指向 $FakeIp，把插座引到自建服务器" }
T ""
$pyArgs = @('tools/lan-mitm.py', "--mode=$Mode", "--seconds=$CaptureSec",
            "--fake-ip=$FakeIp", '--log=lan-capture.log', '--json=lan-samples.jsonl')
if ($plugIp) { $pyArgs += "--target=$plugIp" }
& $pyExe @pyArgs 2>&1 | Tee-Object -FilePath $log -Append

T ""
T "[6] 完成。样本："
T "    C:\workspace\xiaocong\lan-samples.jsonl"
T "    C:\workspace\xiaocong\lan-capture.log"
T ""
T "★ 重点看：DNS 查询里出现的『非本机、非已知服务』的域名 —— 那就是插座的内置云端。"
