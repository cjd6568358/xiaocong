<#
  等插座上线，然后自动开始「伪造 DNS 把 iot.ixiaocong.com 引到自建服务器」。

  为什么需要：
    插座每 ~11 秒重试一次 DNS，但约 5 分钟就会放弃并离线。
    与其盯着它，不如挂个后台进程：插座一上线就立刻开始伪造，抢在它放弃之前。

  用法：
    & .\tools\forge-when-online.ps1
    & .\tools\forge-when-online.ps1 -WaitMin 40 -CaptureSec 900 -FakeIp 192.168.1.20
#>
param(
  [int]$WaitMin    = 30,
  [int]$CaptureSec = 900,
  [string]$FakeIp  = '192.168.1.20',
  [string]$Domain  = 'iot.ixiaocong.com'
)

$root = Split-Path -Parent $PSScriptRoot
Set-Location $root
$log = Join-Path $root "forge-when-online.log"
try { [Console]::OutputEncoding = [System.Text.Encoding]::UTF8 } catch {}
$OutputEncoding = [System.Text.Encoding]::UTF8
"===== 等插座上线并伪造 DNS $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') =====" |
  Out-File -Encoding utf8 $log
function T($m) { $m | Tee-Object -FilePath $log -Append }

$pyExe = "C:\Users\caojiecn\.workbuddy-ai\binaries\python\envs\xiaocong\Scripts\python.exe"
if (-not (Test-Path $pyExe)) { T "❌ 找不到 Python 环境：$pyExe"; exit 1 }

T "目标：把 $Domain 伪造解析到 $FakeIp"
T "等待插座出现在局域网（每 8 秒查一次 ARP 找 B4:E6:2D，最多 $WaitMin 分钟）…"

$deadline = (Get-Date).AddMinutes($WaitMin)
$plugIp = ""
$round = 0
while ((Get-Date) -lt $deadline) {
  $round++
  $line = arp -a 2>$null | Select-String -Pattern 'b4-e6-2d' -SimpleMatch | Select-Object -First 1
  if ($line) {
    $plugIp = ($line -split '\s+' | Where-Object { $_ -match '^192\.168\.' } | Select-Object -First 1)
    if ($plugIp) { break }
  }
  if ($round % 5 -eq 0) { T "  [$(Get-Date -Format 'HH:mm:ss')] 第 $round 轮：插座还没上线" }
  Start-Sleep -Seconds 8
}

if (-not $plugIp) {
  T "❌ $WaitMin 分钟内没等到插座上线。可能它已放弃重试并离线 —— 断电重插一次再跑本脚本。"
  exit 1
}

T ""
T "★★★ 插座上线：$plugIp —— 立刻开始伪造 DNS"
T "    （插座每 ~11s 重试一次；伪造持续 ${CaptureSec}s）"
T ""
T "    同时请在另一个终端跑多端口监听网，看它连哪个端口："
T "      PORTS=443,8883,1883,8888,8443,9999,10000 node tools/port-watch.js"
T ""

& $pyExe @('tools/lan-mitm.py', '--mode=dns', "--target=$plugIp", "--fake-ip=$FakeIp",
           "--seconds=$CaptureSec", '--log=lan-capture-forge.log', '--json=lan-samples-forge.jsonl') 2>&1 |
  Tee-Object -FilePath $log -Append

T ""
T "===== 伪造结束 $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') ====="
T "样本：lan-samples-forge.jsonl    日志：lan-capture-forge.log"
T ""
T "★ 重点看：插座有没有向 $FakeIp 发起 TCP？连的哪个端口？TLS 握手过了吗？"
