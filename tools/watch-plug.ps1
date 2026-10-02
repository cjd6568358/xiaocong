<#
  插座守望者：持续轮询，一发现插座回来就立刻记录（可选自动开跑实验）。

  为什么需要它：
    插座在 crt 非空配网尝试后"消失"了（既不在局域网，也不在配网模式）。
    与其反复手工扫描，不如挂一个后台守望进程 —— 一旦它回到配网模式
    （出现 smart-* 热点）或回到局域网（ARP 出现 B4:E6:2D:*），立刻落盘。

  用法：
    & .\tools\watch-plug.ps1 -Minutes 40
    & .\tools\watch-plug.ps1 -Minutes 40 -AutoRun      # 发现配网热点即自动跑 crt 边界诊断
#>
param(
  [int]$Minutes = 40,
  [switch]$AutoRun,
  [string]$HomeSsid = "PDCN_IOT"
)

$root = Split-Path -Parent $PSScriptRoot
Set-Location $root
$log = Join-Path $root "watch-plug.log"
try { [Console]::OutputEncoding = [System.Text.Encoding]::UTF8 } catch {}
$OutputEncoding = [System.Text.Encoding]::UTF8

function T($m) { $m | Tee-Object -FilePath $log -Append }
"===== 守望开始 $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')，持续 $Minutes 分钟 =====" | Out-File -Encoding utf8 $log

$deadline = (Get-Date).AddMinutes($Minutes)
$round = 0
$foundAp = $false
$foundLan = $null

while ((Get-Date) -lt $deadline) {
  $round++
  $ts = Get-Date -Format 'HH:mm:ss'

  # --- 1) 配网热点 ---
  $ap = ""
  try {
    $scan = & (Join-Path $PSScriptRoot 'wlanapi-scan.ps1') -Filter 'smart-' 2>$null
    $hit = @($scan | Where-Object { $_ -is [string] -and $_ -match 'smart-' })
    if ($hit.Count -gt 0) {
      $f = $hit[0] -split "`t"
      $ap = $f[1]
      if (-not $foundAp) {
        $foundAp = $true
        T ""
        T "★★★ [$ts] 插座回到配网模式！ SSID = $ap （信号 $($f[0])%）"
        if ($AutoRun) {
          T "[*] -AutoRun 已开启，自动执行 crt 边界诊断 ..."
          & (Join-Path $PSScriptRoot 'experiment-crt.ps1')
          T "[*] crt 边界诊断已结束。"
        }
      } else {
        T "[$ts] 配网热点仍在：$ap"
      }
    }
  } catch { T "[$ts] 扫描异常：$($_.Exception.Message)" }

  # --- 2) 局域网 ARP（Espressif OUI B4:E6:2D）---
  $macLine = ""
  try {
    $macLine = (arp -a 2>$null | Select-String -Pattern 'b4-e6-2d' -SimpleMatch) | Select-Object -First 1
  } catch {}
  if ($macLine) {
    $ip = ($macLine -split '\s+' | Where-Object { $_ -match '^192\.168\.' } | Select-Object -First 1)
    if ($ip -and $ip -ne $foundLan) {
      $foundLan = $ip
      T ""
      T "★★★ [$ts] 插座回到局域网！ IP = $ip  MAC = B4:E6:2D:*"
    }
  }

  if (-not $ap -and -not $macLine) {
    T "[$ts] 第 $round 轮：未发现插座（配网模式 ✗ / 局域网 ✗）"
  } elseif ($ap) {
    T "[$ts] 第 $round 轮：配网模式 ✓ ($ap)"
  } else {
    T "[$ts] 第 $round 轮：局域网 ✓ ($foundLan)"
  }
}

T ""
T "===== 守望结束 $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')，共 $round 轮 ====="
T "  配网模式：$(if ($foundAp) { '✓ 出现过' } else { '✗ 始终未出现' })"
T "  局域网  ：$(if ($foundLan) { "✓ $foundLan" } else { '✗ 始终未出现' })"
