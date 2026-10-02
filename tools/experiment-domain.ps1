<#
  隔离实验：到底是 `domain`、还是 `crt`（或其长度）让设备拒绝配网？

  背景：crt="" domain="" 时配网成功；换成 domain="192.168.1.20" + crt=1338B(真CA) 后，
        设备不再回 {mac,product_id}，反而又发了一个 ch_pubk —— 疑似拒绝 / 重启。

  做法：连上一次插座热点，然后连续跑多组参数，逐个看设备反应。
        每组都会打印设备公钥，用于判断设备是否重启（公钥变化 = 重启）。

  用法（项目根目录）：
    & .\tools\experiment-domain.ps1
#>
param(
  [string]$PlugSsid = "smart-381785-3a6e7c-b7",
  [string]$HomeSsid = "PDCN_IOT",
  [string]$HomePass = "e3eb773F",
  [int]$PerRunSec   = 12,
  [int]$GapSec      = 6
)

$root = Split-Path -Parent $PSScriptRoot
Set-Location $root
$log = Join-Path $root "experiment-domain.log"
try { [Console]::OutputEncoding = [System.Text.Encoding]::UTF8 } catch {}
$OutputEncoding = [System.Text.Encoding]::UTF8
"===== domain/crt 隔离实验 $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') =====" | Out-File -Encoding utf8 $log
function T($m) { $m | Tee-Object -FilePath $log -Append }

$nodeExe = (Get-Command node -ErrorAction SilentlyContinue).Source
if (-not $nodeExe) { $nodeExe = "C:\Users\caojiecn\.workbuddy-ai\binaries\node\versions\22.22.2-3\node.exe" }

# ---------- 连插座热点 ----------
T "[*] 连接插座热点 $PlugSsid ..."
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
$tmp = Join-Path $env:TEMP "xc-ap2.xml"
$xml | Out-File -Encoding ASCII $tmp
netsh wlan add profile filename="$tmp" | Out-Null
Remove-Item $tmp -Force
netsh wlan connect name="$PlugSsid" ssid="$PlugSsid" | Out-Null

$ip = ""
for ($i = 0; $i -lt 25; $i++) {
  Start-Sleep -Seconds 1
  $ip = (Get-NetIPAddress -AddressFamily IPv4 -ErrorAction SilentlyContinue |
    Where-Object { $_.IPAddress -like '192.168.4.*' } | Select-Object -First 1).IPAddress
  if ($ip) { break }
}
if (-not $ip) { T "❌ 未拿到 192.168.4.x 地址，退出"; exit 1 }
T "    本机地址 $ip ✅"

# ---------- 变体 ----------
$variants = @(
  @{ n = 'A  domain=192.168.1.20 , crt=0        '; d = '192.168.1.20'; c = '';             f = '' },
  @{ n = 'B  domain=""           , crt=64B      '; d = '';             c = ('X' * 64);    f = '' },
  @{ n = 'C  domain=""           , crt=256B     '; d = '';             c = ('X' * 256);   f = '' },
  @{ n = 'D  domain=""           , crt=512B     '; d = '';             c = ('X' * 512);   f = '' },
  @{ n = 'E  domain=""           , crt=1024B    '; d = '';             c = ('X' * 1024);  f = '' },
  @{ n = 'F  domain=""           , crt=真CA文件 '; d = '';             c = '';            f = 'server/certs/ca.crt' },
  @{ n = 'G  domain=192.168.1.20 , crt=512B     '; d = '192.168.1.20'; c = ('X' * 512);   f = '' }
)

foreach ($v in $variants) {
  T ""
  T "########## $($v.n) ##########"
  $env:XC_DOMAIN = $v.d
  $env:XC_CRT = $v.c
  $a = @('tools/provision.js', "--ssid=$HomeSsid", "--pass=$HomePass",
         "--timeout=$($PerRunSec * 1000)", '--end-fallback=2500')
  if ($v.f) { $a += "--crt-file=$($v.f)" }
  & $nodeExe @a 2>&1 | Tee-Object -FilePath $log -Append
  $rc = $LASTEXITCODE
  T "    >>> 退出码 = $rc  (0=设备接受并回包 / 2=超时未回包)"
  Start-Sleep -Seconds $GapSec
}

# ---------- 回连 ----------
T ""
T "[*] 回连 $HomeSsid ..."
netsh wlan connect name="$HomeSsid" ssid="$HomeSsid" | Out-Null
T "完成。日志：$log"
