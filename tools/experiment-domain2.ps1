<#
  方案2 修正版实验 —— 固件到底认不认配网报文里的 `domain` 参数？

  ─────────────────────────────────────────────────────────────
  为什么要重做：
    docs/03 §5.8 那次 domain 实验用的是 **私有 IP 192.168.1.20**，
    而固件有防 DNS 重绑定（拒绝私有网段，见 docs/07 §1.2）。
    所以那次实验**无法区分**：
        (a) domain 被固件忽略（仍走内置 iot.ixiaocong.com）
        (b) domain 被采用，但连接被防重绑定拦下
    方案2 其实**从未被真正验证过**。本次用「公网 IP / 域名」重做。

  判别信号（关键）：
    配网后看插座查什么域名。
      认 domain   → 会查我们编的域名（例：probe2.ixiaocong.test），或直接连 IP 不查 DNS
      不认 domain → 只会查固件内置的 iot.ixiaocong.com
    两者不可能同时出现，一眼可判。

  前置：
    1) fake-cloud.py 正在运行（ARP 欺骗网关 + 伪造 DNS，日志写 fake-cloud.jsonl）
    2) 插座**长按 5 秒**进入配网模式（本脚本会自己等它的 smart-* 热点出现）
    3) 本机无线网卡可用（脚本会自己切到插座热点、再切回家庭网）

  用法（项目根目录）：
    # 变体 1：编一个「公网域名」——最能说明问题（认了就会去查它）
    & .\tools\experiment-domain2.ps1 -Domain "probe2.ixiaocong.test"

    # 变体 2：编一个「公网 IP 字面量」——认了就直接连、且完全不查 DNS
    & .\tools\experiment-domain2.ps1 -Domain "203.0.113.9"

  跑完看输出末尾的「判决」段；也可事后手动复核：
    python tools\dns-since.py fake-cloud.jsonl <配网时刻>
#>
param(
  [string]$Domain          = "probe2.ixiaocong.test",
  [string]$PlugSsidPrefix  = "smart-381785",
  [string]$HomeSsid        = "PDCN_IOT",
  [string]$HomePass        = "e3eb773F",
  [int]   $ApWaitSec       = 240,
  [int]   $PerRunSec       = 18,
  [int]   $ObserveSec      = 150,
  [string]$Jsonl           = "fake-cloud.jsonl"
)

$root = Split-Path -Parent $PSScriptRoot
Set-Location $root
try { [Console]::OutputEncoding = [System.Text.Encoding]::UTF8 } catch {}
$OutputEncoding = [System.Text.Encoding]::UTF8

$log = Join-Path $root "experiment-domain2.log"
function T($m) { $m | Tee-Object -FilePath $log -Append }
"" | Out-File -Encoding utf8 $log
T "===== 方案2 修正版实验  $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') ====="
T "  目标 domain = '$Domain'"

$nodeExe = (Get-Command node -ErrorAction SilentlyContinue).Source
if (-not $nodeExe) { $nodeExe = "C:\Users\caojiecn\.workbuddy-ai\binaries\node\versions\22.22.2-3\node.exe" }
$pyExe = "C:\Users\caojiecn\.workbuddy-ai\binaries\python\versions\3.13.12\python.exe"

# ---------------------------------------------------------------- 0. 自检
T ""
T "[0] 自检"
$fc = Get-Process -Name python -ErrorAction SilentlyContinue
if (-not $fc) { T "    ⚠️ 没看到 python 进程 —— 确认 fake-cloud.py 在跑，否则抓不到 DNS" }
else { T "    有 python 进程在跑 ✅（fake-cloud / 哨兵）" }

# ---------------------------------------------------------------- 1. 等插座热点
T ""
T "[1] 等插座配网热点 ${PlugSsidPrefix}* 出现"
T "    ▶ 现在【长按插座按钮 5 秒】，让它进配网模式。最多等 $ApWaitSec 秒 …"

function Get-PlugSsid {
  $out = & netsh wlan show networks 2>$null
  foreach ($ln in $out) {
    # SSID 行形如： "SSID 3 : smart-381785-3a6e7c-b7"
    if ($ln -match '^\s*SSID\s+\d+\s*:\s*(.+?)\s*$') {
      $s = $Matches[1].Trim()
      if ($s -like "$PlugSsidPrefix*") { return $s }
    }
  }
  return $null
}

$plugSsid = $null
$t0 = Get-Date
$deadline = $t0.AddSeconds($ApWaitSec)
while ((Get-Date) -lt $deadline) {
  $plugSsid = Get-PlugSsid
  if ($plugSsid) { break }
  $left = [int]($deadline - (Get-Date)).TotalSeconds
  Write-Host "`r    等待中 … 还剩 ${left}s   " -NoNewline
  Start-Sleep -Seconds 3
}
Write-Host ""
if (-not $plugSsid) {
  T "    ❌ $ApWaitSec 秒内没等到 ${PlugSsidPrefix}* 热点。插座没进配网模式？"
  T "       长按按钮 5 秒（继电器会响一声），再重跑本脚本。"
  exit 1
}
T "    发现插座热点：$plugSsid ✅"

# ---------------------------------------------------------------- 2. 连插座热点
T ""
T "[2] 连接插座热点 $plugSsid"
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
$tmp = Join-Path $env:TEMP "xc-ap-d2.xml"
$xml | Out-File -Encoding ASCII $tmp
& netsh wlan add profile filename="$tmp" | Out-Null
Remove-Item $tmp -Force
& netsh wlan connect name="$plugSsid" ssid="$plugSsid" | Out-Null

$ip = ""
for ($i = 0; $i -lt 30; $i++) {
  Start-Sleep -Seconds 1
  $ip = (Get-NetIPAddress -AddressFamily IPv4 -ErrorAction SilentlyContinue |
    Where-Object { $_.IPAddress -like '192.168.4.*' } | Select-Object -First 1).IPAddress
  if ($ip) { break }
}
if (-not $ip) { T "    ❌ 没拿到 192.168.4.x（没连上插座热点），退出"; exit 1 }
T "    本机地址 $ip ✅"

# ---------------------------------------------------------------- 3. 配网（带 domain）
T ""
T "[3] 用 domain='$Domain' 配网（其余参数与厂商 App 默认一致）"
$env:XC_DOMAIN = $Domain
$env:XC_CRT = ""
$args1 = @('tools/provision.js', "--ssid=$HomeSsid", "--pass=$HomePass",
           "--timeout=$($PerRunSec * 1000)", '--end-fallback=2500')
& $nodeExe @args1 2>&1 | Tee-Object -FilePath $log -Append
$rc = $LASTEXITCODE
T "    >>> provision 退出码 = $rc   (0=设备接受并回包 / 2=超时 / 3=设备主动 NACK)"
if ($rc -ne 0) {
  T "    ⚠️ 没拿到设备的 ch_data 回包 —— 本次配置可能没被接受，结论会不干净。"
}

# ---------------------------------------------------------------- 4. 回连家庭网
T ""
T "[4] 回连家庭网 $HomeSsid"
& netsh wlan connect name="$HomeSsid" ssid="$HomeSsid" | Out-Null
for ($i = 0; $i -lt 25; $i++) {
  Start-Sleep -Seconds 1
  $hip = (Get-NetIPAddress -AddressFamily IPv4 -ErrorAction SilentlyContinue |
    Where-Object { $_.IPAddress -like '192.168.*' -and $_.IPAddress -notlike '192.168.4.*' } |
    Select-Object -First 1).IPAddress
  if ($hip) { break }
}
T "    本机地址 $hip"

$markTime = Get-Date -Format 'yyyy-MM-ddTHH:mm:ss'
T "    ★ 配网完成时刻 = $markTime（后面只看这之后的 DNS）"

# ---------------------------------------------------------------- 5. 观察
T ""
T "[5] 观察 $ObserveSec 秒，看插座查什么域名 …"
Start-Sleep -Seconds $ObserveSec

T ""
T "----- 配网时刻之后的 DNS 查询 -----"
$names = & $pyExe tools/dns-since.py $Jsonl $markTime 2>&1
$names | Tee-Object -FilePath $log -Append

# ---------------------------------------------------------------- 6. 判决
T ""
T "================= 判 决 ================="
$hitOurs = $false
$hitBuiltin = $false
foreach ($ln in $names) {
  if ($ln -match [regex]::Escape($Domain)) { $hitOurs = $true }
  if ($ln -match 'iot\.ixiaocong\.com')    { $hitBuiltin = $true }
}

if ($hitOurs) {
  T "✅✅✅ 固件【认】domain！ 插座去查了我们编的 '$Domain'。"
  T "      → 方案2 成立：配网时把 domain 指向你的服务器，插座就会直连它，"
  T "        不需要 ARP 欺骗 / DNS 劫持，是最干净的永久方案。"
} elseif ($hitBuiltin) {
  T "❌ 固件【不认】domain。 插座仍只查内置的 iot.ixiaocong.com。"
  T "      → 方案2 走死（至少对 hostname 形式如此）；"
  T "        若想再确认，可换 -Domain 203.0.113.9 跑一次（认了会直接连、完全不查 DNS）。"
  T "      → 那就只剩方案1（链路劫持 DNS 应答）这一条免拆机路。"
} else {
  T "⚠️ 两个域名都没看到 —— 插座可能还没重新上线（刚配网完要几十秒），"
  T "   或 fake-cloud.py 没在跑。可手动复核："
  T "     python tools\dns-since.py $Jsonl $markTime"
}

T ""
T "日志：$log"
T "原始样本：$Jsonl"
