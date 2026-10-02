<#
  小葱 · 一键真机配网（离线执行）

  为什么是一键：连上插座热点后笔记本会暂时断网，这期间无法与 AI 对话，
  所以把「扫描 → 连接 → 等 IP → 配网 → 回连家里 WiFi」串成一条命令，跑完再回来看日志。

  用法（在项目根目录）：
    powershell -ExecutionPolicy Bypass -File tools/live-provision.ps1 `
        -HomeSsid "家里WiFi名" -HomePass "家里WiFi密码"

  可选：
    -PlugSsid "smart-1-a1b2c3-4f"   手动指定插座热点名（系统扫描受限时用；手机 WiFi 列表里能看到）
    -Domain "your.server.com"   填了就把插座指向你的服务器（留空 = 原厂默认）
    -CrtFile "server/certs/ca.crt"   随 domain 一起下发的 CA 证书
    -Check "123456"             固定 checkCode（默认随机 6 位）

  前置：插座已长按 5 秒进入配网模式；本机无线已开、能扫到 smart-* 热点。
#>
param(
  [Parameter(Mandatory = $true)][string]$HomeSsid,
  [Parameter(Mandatory = $true)][string]$HomePass,
  [string]$PlugSsid = "",
  [string]$Domain = "",
  [string]$CrtFile = "",
  [string]$Check = "",
  [int]$WaitIpSec = 20
)

$root = Split-Path -Parent $PSScriptRoot
Set-Location $root
$log = Join-Path $root "provision-live.log"
# 让 node 的中文输出按 UTF-8 解码，否则日志里全是乱码
try { [Console]::OutputEncoding = [System.Text.Encoding]::UTF8 } catch {}
$OutputEncoding = [System.Text.Encoding]::UTF8
"===== 小葱真机配网 $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') =====" | Out-File -Encoding utf8 $log

function T($msg) { $msg | Tee-Object -FilePath $log -Append }

if ($PlugSsid) {
  T "[1] 使用指定的插座热点：$PlugSsid"
} else {
  T "[1] 扫描 smart-* 热点..."
  # ⚠️ 必须用 wlanapi 枚举：`netsh wlan show networks` 会过滤/去重，
  #    实测插座处于配网模式时 netsh 完全看不到 smart-*（系统 WiFi 面板却能看到）。
  $scan = & (Join-Path $PSScriptRoot 'wlanapi-scan.ps1') -Filter 'smart-'
  $hit = @($scan | Where-Object { $_ -is [string] -and $_ -match 'smart-' } | Select-Object -First 1)
  if ($hit.Count -eq 0) {
    T "❌ 未扫描到 smart-* 热点。可能原因："
    T "   - 插座已退出配网模式（SoftAP 一般几分钟后自动关闭），请重新长按 5 秒进入配网模式"
    T "   - 用手机 WiFi 列表确认 smart-... 名字，加 -PlugSsid `"smart-...`" 重跑"
    exit 1
  }
  $f = $hit[0] -split "`t"
  $PlugSsid = $f[1]
  T "    找到：$PlugSsid（信号 $($f[0])%）"
}

T "[2] 连接插座热点（开放网络）..."
$xml = @"
<?xml version="1.0"?>
<WLANProfile xmlns="http://www.microsoft.com/networking/WLAN/profile/v1">
  <name>$plugSsid</name>
  <SSIDConfig><SSID><name>$plugSsid</name></SSID></SSIDConfig>
  <connectionType>ESS</connectionType>
  <connectionMode>manual</connectionMode>
  <MSM><security><authEncryption><authentication>open</authentication><encryption>none</encryption><useOneX>false</useOneX></authEncryption></security></MSM>
</WLANProfile>
"@
$tmp = Join-Path $env:TEMP "xc-ap.xml"
$xml | Out-File -Encoding ASCII $tmp
netsh wlan add profile filename="$tmp" | Out-Null
Remove-Item $tmp -Force
netsh wlan connect name="$plugSsid" ssid="$plugSsid" | Out-Null

T "[3] 等待获取 192.168.4.x 地址..."
$ip = ""
for ($i = 0; $i -lt $WaitIpSec; $i++) {
  Start-Sleep -Seconds 1
  $ip = (Get-NetIPAddress -AddressFamily IPv4 -ErrorAction SilentlyContinue |
    Where-Object { $_.IPAddress -like '192.168.4.*' } | Select-Object -First 1).IPAddress
  if ($ip) { break }
}
if (-not $ip) {
  T "❌ 未拿到 192.168.4.x 地址，当前接口状态："
  T ((netsh wlan show interfaces) -join "`n")
  exit 1
}
T "    本机地址：$ip  ✅"

T "[4] 运行配网客户端（会弹防火墙授权，请选「允许」）..."
$nodeExe = (Get-Command node -ErrorAction SilentlyContinue).Source
if (-not $nodeExe) { $nodeExe = "C:\Users\caojiecn\.workbuddy-ai\binaries\node\versions\22.22.2-3\node.exe" }
$nargs = @("tools/provision.js", "--ssid=$HomeSsid", "--pass=$HomePass")
if ($Domain) { $nargs += "--domain=$Domain" }
if ($CrtFile) { $nargs += "--crt-file=$CrtFile" }
if ($Check) { $nargs += "--check=$Check" }
& $nodeExe @nargs 2>&1 | Tee-Object -FilePath $log -Append
$code = $LASTEXITCODE
T "    配网客户端退出码：$code"

T "[5] 回连家里 WiFi：$HomeSsid"
$homeProf = netsh wlan show profiles | Select-String ":\s*$([regex]::Escape($HomeSsid))\s*$"
if (-not $homeProf) {
  T "    本机没有该 WiFi 的配置，用提供的密码临时创建..."
  $hx = @"
<?xml version="1.0"?>
<WLANProfile xmlns="http://www.microsoft.com/networking/WLAN/profile/v1">
  <name>$HomeSsid</name>
  <SSIDConfig><SSID><name>$HomeSsid</name></SSID></SSIDConfig>
  <connectionType>ESS</connectionType>
  <connectionMode>auto</connectionMode>
  <MSM><security><authEncryption><authentication>WPA2PSK</authentication><encryption>AES</encryption><useOneX>false</useOneX></authEncryption><sharedKey><keyType>passPhrase</keyType><protected>false</protected><keyMaterial>$HomePass</keyMaterial></sharedKey></security></MSM>
</WLANProfile>
"@
  $htmp = Join-Path $env:TEMP "xc-home.xml"
  $hx | Out-File -Encoding ASCII $htmp
  netsh wlan add profile filename="$htmp" | Out-Null
  Remove-Item $htmp -Force
}
netsh wlan connect name="$HomeSsid" ssid="$HomeSsid" | Out-Null

T ""
T "完成。日志已存：$log"
T "回到家里网络后，把上面的输出（或 $log）贴回来。"
