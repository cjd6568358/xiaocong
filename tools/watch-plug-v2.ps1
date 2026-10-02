<#
  watch-plug-v2.ps1 —— 双状态监听 + 自动抓包（修正了 v1 的根本缺陷）

  v1（forge-when-online.ps1）的问题：
      它每 8 秒轮询 `arp -a`。而 Windows 的 ARP 表**只记录最近和本机交换过流量的邻居**，
      一台刚上线的插座如果还没跟本机通信，根本不会出现在 arp -a 里 → 永远等不到。
      另外它只盯"插座在局域网"这一种状态，插座若回到**配网模式**它完全无感。

  v2 做两件事：
      1) 每轮用 **主动 ARP 全网段扫描**（sendp 广播 ARP 请求，不依赖缓存）找 B4:E6:2D
      2) 每轮扫 WiFi 找插座配网热点 smart-*（说明插座掉出局域网、回到了 AP 模式）

  找到插座（在局域网）后自动串联：
      a. tls-catch.py         —— 在本机多端口监听，准备接住插座
      b. lan-mitm.py --mode dns —— ARP 欺骗 + 伪造 DNS，把 iot.ixiaocong.com 引到本机
  这样插座"以为在连官方云端"，实际连到我们这里，我们能看清它的 TLS 握手。

  用法：
    & .\tools\watch-plug-v2.ps1
    & .\tools\watch-plug-v2.ps1 -WaitMin 60 -CaptureSec 900
#>
param(
  [int]$WaitMin    = 60,
  [int]$CaptureSec = 900,
  [string]$FakeIp  = '192.168.1.20',
  [string]$Domain  = 'iot.ixiaocong.com',
  [int]$RoundSec   = 10,
  [string]$PyExe   = "C:\Users\caojiecn\.workbuddy-ai\binaries\python\envs\xiaocong\Scripts\python.exe"
)

$root = Split-Path -Parent $PSScriptRoot
Set-Location $root
$log = Join-Path $root "watch-plug-v2.log"
try { [Console]::OutputEncoding = [System.Text.Encoding]::UTF8 } catch {}
$OutputEncoding = [System.Text.Encoding]::UTF8
"===== watch-plug-v2 $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') =====" | Out-File -Encoding utf8 $log
function T($m) { $m | Tee-Object -FilePath $log -Append }

if (-not (Test-Path $PyExe)) { T "❌ 找不到 Python：$PyExe"; exit 1 }

T "目标：把 $Domain 伪造解析到 $FakeIp，并接住插座的 TLS 握手"
T "监听两种状态：① 插座在局域网（主动 ARP 扫描）  ② 插座回到配网热点（smart-*）"
T "每 $RoundSec 秒一轮，最多 $WaitMin 分钟。"
T ""

$deadline = (Get-Date).AddMinutes($WaitMin)
$round = 0
$plugIp = ""
$sawPairing = $false

while ((Get-Date) -lt $deadline) {
  $round++
  $ts = Get-Date -Format 'HH:mm:ss'

  # --- ① 主动 ARP 扫描找插座 ---
  $scanOut = & $PyExe "tools/scan-lan.py" --our-ip $FakeIp 2>&1
  $found = $scanOut | Select-String -Pattern '✅ 找到插座:' -SimpleMatch | Select-Object -First 1
  $hostCount = ($scanOut | Select-String -Pattern '主动 ARP 扫描到 (\d+) 台设备').Matches.Groups[1].Value

  # --- ② 扫插座配网热点 ---
  # 注意：不能只按 SSID 找 smart-* —— 若插座热点是**隐藏 SSID**，
  #       按 SSID 扫会漏掉。ESP8266/ESP32 的 AP BSSID 一定是 b4:e6:2d 开头，
  #       所以**同时按 BSSID 的 OUI 搜**才是完备的。
  $hot = @()
  try { $hot = @(& "$PSScriptRoot\wlanapi-scan.ps1" -Filter 'smart-' -WaitMs 2000 2>$null) } catch {}
  $esp = $null
  try { $esp = (netsh wlan show networks mode=bssid 2>$null | Select-String 'b4:e6:2d' | Select-Object -First 1) } catch {}
  $curSsid = ((netsh wlan show interfaces 2>$null | Select-String '^\s+SSID\s+:') -replace '.*:\s*','')

  if ($found) {
    $plugIp = ($found.ToString() -replace '.*找到插座:\s*','').Split()[0]
    T "[$ts] 第 $round 轮 ★★★ 插座上线：$plugIp ★★★"
    break
  }

  if ($hot.Count -gt 0) {
    $sawPairing = $true
    T "[$ts] 第 $round 轮 ⚠️ 插座在【配网模式】：$($hot -join ' | ')  —— 它掉出局域网了，需要重新配网"
  } elseif ($esp) {
    $sawPairing = $true
    T "[$ts] 第 $round 轮 ⚠️ 发现 Espressif(插座) BSSID（可能是隐藏 SSID 的热点）：$esp"
  } else {
    if ($round % 3 -eq 0) {
      T "[$ts] 第 $round 轮：插座未上线（局域网 $hostCount 台设备，无 smart-* 也无 Espressif BSSID，本机WiFi=$curSsid）"
    }
  }
  Start-Sleep -Seconds $RoundSec
}

if (-not $plugIp) {
  T ""
  if ($sawPairing) {
    T "⛔ $WaitMin 分钟内插座一直在【配网模式】—— 它的 WiFi 凭据可能已丢失。"
    T "   需要重新配网：& .\tools\experiment-official.ps1 -Mode relay"
  } else {
    T "⛔ $WaitMin 分钟内既没等到插座上线，也没看到配网热点。"
    T "   插座可能：没通电 / 没连上 PDCN_IOT / 已彻底离线。请断电重插后再跑本脚本。"
  }
  exit 1
}

T ""
T "★★★ 插座在 $plugIp —— 立刻开始伪造 DNS"
T ""
T "    注：TLS 捕获服务（tls-catch.py）应已独立常驻监听 443/80/8883/1883/8443/8080。"
T "        若没在跑，另开一个终端执行："
T "          & $PyExe tools/tls-catch.py --seconds 7200"
T ""

# ARP 欺骗 + 伪造 DNS
T "--- 启动 ARP 欺骗 + 伪造 DNS（把 $Domain → $FakeIp）---"
& $PyExe @('tools/lan-mitm.py', '--mode=dns', "--target=$plugIp", "--fake-ip=$FakeIp",
           "--seconds=$CaptureSec", '--log=lan-capture-forge.log',
           '--json=lan-samples-forge.jsonl') 2>&1 | Tee-Object -FilePath $log -Append

T ""
T "===== 结束 $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') ====="
T "样本：lan-samples-forge.jsonl   抓包日志：lan-capture-forge.log   TLS：tls-catch.jsonl / tls-catch.log"
T ""
T "★ 重点看：插座有没有向 $FakeIp 发起 TCP？连哪个端口？TLS 握手成功还是失败？"
