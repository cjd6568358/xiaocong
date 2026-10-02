<#
  crt 接受边界诊断：固件到底能不能接受非空 crt？

  已知（真机）：
    crt=0     + domain=192.168.1.20 → 设备回 ch_data(mac/product_id)  ✅
    crt=1338B + domain=192.168.1.20 → 设备不回收，重发 ch_pubk      ❌
    crt=627B  + domain=192.168.1.20 → 设备不回收，重发 ch_pubk      ❌

  两种解释：
    (H1) 固件有固定大小的 crt 缓冲区（如 char crt[512]），超长即整包拒绝 → 需要更小的证书
    (H2) 固件只要 crt 非空就拒绝（crt 是厂商签名槽位，不接受任意值）→ 主线 A 基本走死

  本脚本按「从小到大」升序探测，第一个成功的变体即定位边界。
  注意：任一变体一旦成功，设备会立刻退出配网模式 → 后续变体必然失败 → 脚本立即停止。

  用法（项目根目录）：
    & .\tools\experiment-crt.ps1                 # 按升序全跑，命中即停
    & .\tools\experiment-crt.ps1 -Only 1         # 只跑第 1 个变体（插座需重新进配网模式）
    & .\tools\experiment-crt.ps1 -List           # 只列出变体，不连接
#>
param(
  [string]$PlugSsid = "smart-381785-3a6e7c-b7",
  [string]$HomeSsid = "PDCN_IOT",
  [string]$HomePass = "e3eb773F",
  [string]$Domain   = "192.168.1.20",
  [int]$PerRunSec   = 9,
  [int]$GapSec      = 4,
  [int]$Only        = 0,
  [switch]$List
)

$root = Split-Path -Parent $PSScriptRoot
Set-Location $root
$log = Join-Path $root "experiment-crt.log"
try { [Console]::OutputEncoding = [System.Text.Encoding]::UTF8 } catch {}
$OutputEncoding = [System.Text.Encoding]::UTF8

# 变体：c=内联内容（优先），f=文件。按「字符串长度」升序 —— 先便宜后昂贵。
$variants = @(
  @{ n = '1  crt="A"                (1B    非空对照 —— 最关键的一测)'; c = 'A';  f = '' },
  @{ n = '2  crt=64B 填充            (64B)';                            c = ('B' * 64); f = '' },
  @{ n = '3  crt=256B 填充           (256B)';                           c = ('C' * 256); f = '' },
  @{ n = '4  crt=384B 填充           (384B)';                           c = ('D' * 384); f = '' },
  @{ n = '5  crt=448B 填充           (448B)';                           c = ('E' * 448); f = '' },
  @{ n = '6  crt=ec-min.crt          (554B  最小真 EC 证书 PEM)';       c = ''; f = 'server/certs/ec-min.crt' },
  @{ n = '7  crt=ec-ca-b64der.txt    (564B  base64 DER，无 PEM 头)';    c = ''; f = 'server/certs/ec-ca-b64der.txt' },
  @{ n = '8  crt=ec-ca.crt           (627B  EC CA PEM)';                c = ''; f = 'server/certs/ec-ca.crt' },
  @{ n = '9  crt=""                 (0B    已知可成功，作收尾对照)';    c = ''; f = '' }
)

if ($List) {
  "共 $($variants.Count) 个变体："
  $i = 0
  foreach ($v in $variants) { $i++; "  [$i] $($v.n)" }
  exit 0
}

$nodeExe = (Get-Command node -ErrorAction SilentlyContinue).Source
if (-not $nodeExe) { $nodeExe = "C:\Users\caojiecn\.workbuddy-ai\binaries\node\versions\22.22.2-3\node.exe" }

"===== crt 边界诊断 $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') =====" | Out-File -Encoding utf8 $log
function T($m) { $m | Tee-Object -FilePath $log -Append }

function Get-ApIp {
  (Get-NetIPAddress -AddressFamily IPv4 -ErrorAction SilentlyContinue |
    Where-Object { $_.IPAddress -like '192.168.4.*' } | Select-Object -First 1).IPAddress
}

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
$tmp = Join-Path $env:TEMP "xc-ap3.xml"
$xml | Out-File -Encoding ASCII $tmp
netsh wlan add profile filename="$tmp" | Out-Null
Remove-Item $tmp -Force
netsh wlan connect name="$PlugSsid" ssid="$PlugSsid" | Out-Null

$ip = ""
for ($i = 0; $i -lt 25; $i++) {
  Start-Sleep -Seconds 1
  $ip = Get-ApIp
  if ($ip) { break }
}
if (-not $ip) { T "❌ 未拿到 192.168.4.x 地址（插座可能不在配网模式），退出"; exit 1 }
T "    本机地址 $ip ✅"

$list = if ($Only -gt 0) { @($variants[$Only - 1]) } else { $variants }
$hit = $null
$results = @()

foreach ($v in $list) {
  # 每个变体前先确认仍在插座热点上（前一个变体若成功，设备会踢我们下线）
  $cur = Get-ApIp
  if (-not $cur) {
    T ""
    T "⚠️ 已不在 192.168.4.0/24 —— 插座大概率已退出配网模式（说明上一个变体被接受了）。停止。"
    break
  }

  T ""
  T "########## $($v.n) ##########"
  $env:XC_DOMAIN = $Domain
  $env:XC_CRT = $v.c
  $a = @('tools/provision.js', "--ssid=$HomeSsid", "--pass=$HomePass",
         "--timeout=$($PerRunSec * 1000)", '--end-fallback=2500')
  if ($v.f) { $a += "--crt-file=$($v.f)" }
  & $nodeExe @a 2>&1 | Tee-Object -FilePath $log -Append
  $rc = $LASTEXITCODE
  switch ($rc) {
    0 { T "    >>> 退出码 = 0   ✅ 设备接受并回包" }
    3 { T "    >>> 退出码 = 3   ⛔ 设备主动 NACK（收到了 ch_data 但拒绝）" }
    2 { T "    >>> 退出码 = 2   ❔ 超时无回包（丢包 / 没连上热点 / 设备静默）" }
    default { T "    >>> 退出码 = $rc" }
  }

  if ($rc -eq 0) {
    $hit = $v.n
    T ""
    T "★ 命中：$($v.n)"
    T "  设备接受了这个 crt —— 边界就在它之前那个变体之后。"
    break
  }
  $results += [pscustomobject]@{ 变体 = $v.n; 退出码 = $rc }
  Start-Sleep -Seconds $GapSec
}

T ""
if ($hit) {
  T "结论：非空 crt 可被接受，最小可用变体 = $hit"
} else {
  T "结论：本轮所有变体均被拒绝。"
  $nackN = @($results | Where-Object { $_.退出码 -eq 3 }).Count
  $timeoutN = @($results | Where-Object { $_.退出码 -eq 2 }).Count
  T "  主动 NACK(3) = $nackN 次；超时(2) = $timeoutN 次"
  if ($nackN -ge 1) {
    T "  → 设备确实收到了 ch_data 但拒绝 → 强烈指向 (H2)：固件不接受任意 crt 值。"
  } elseif ($timeoutN -ge 1) {
    T "  → 全是超时，没有 NACK → 更像丢包/连接问题，结果不可信，需重跑。"
  }
  if ($results) {
    T ""
    T "  明细："
    foreach ($r in $results) { T "    $($r.变体)  → 退出码 $($r.退出码)" }
  }
}
T "[*] 回连 $HomeSsid ..."
netsh wlan connect name="$HomeSsid" ssid="$HomeSsid" | Out-Null
T "完成。日志：$log"
