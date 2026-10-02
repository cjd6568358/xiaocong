<#
  小葱配网 · WiFi 辅助脚本（Windows / netsh 封装）

  用法：
    powershell -ExecutionPolicy Bypass -File tools/wifi.ps1 scan
    powershell -ExecutionPolicy Bypass -File tools/wifi.ps1 find-plug
    powershell -ExecutionPolicy Bypass -File tools/wifi.ps1 parse -Ssid "smart-1-a1b2c3-4f"
    powershell -ExecutionPolicy Bypass -File tools/wifi.ps1 connect -Ssid "smart-1-a1b2c3-4f"
    powershell -ExecutionPolicy Bypass -File tools/wifi.ps1 status
    powershell -ExecutionPolicy Bypass -File tools/wifi.ps1 disconnect
#>
param(
  [Parameter(Position = 0)][string]$Action = "scan",
  [string]$Ssid = ""
)

function Show-Scan {
  netsh wlan show networks mode=bssid
}

function Find-Plug {
  $out = netsh wlan show networks mode=bssid
  $hit = $out | Select-String -Pattern 'SSID\s+\d+\s*:\s*smart-'
  if (-not $hit) {
    Write-Output "未发现 smart-* 热点。请确认插座已长按 5 秒进入配网模式，且本机无线已开启。"
    return
  }
  $hit | ForEach-Object { Write-Output $_.Line.Trim() }
  Write-Output ""
  Write-Output "用下面的命令连接（把 SSID 换成上面那个）："
  Write-Output "  powershell -ExecutionPolicy Bypass -File tools/wifi.ps1 connect -Ssid `"smart-...`""
}

# SSID 格式：smart-<产品号>-<MAC后6位>-<校验码>
# 校验码 = 去掉末 2 字符后的字节和 & 0xFF，以两位十六进制表示
function Parse-PlugSsid([string]$name) {
  if (-not $name) { Write-Output "需要 -Ssid"; return }
  $m = [regex]::Match($name, '^smart-(.+)-([0-9a-fA-F]{6})-([0-9a-fA-F]{2})$')
  if (-not $m.Success) { Write-Output "SSID 不符合 smart-<pid>-<mac6>-<ck> 格式：$name"; return }
  $prefix = $name.Substring(0, $name.Length - 2)
  $sum = 0
  foreach ($b in [System.Text.Encoding]::ASCII.GetBytes($prefix)) { $sum += $b }
  $expect = ($sum -band 0xFF).ToString('x2')
  Write-Output "产品号   : $($m.Groups[1].Value)"
  Write-Output "MAC 后6位: $($m.Groups[2].Value)"
  Write-Output "校验码   : $($m.Groups[3].Value)"
  Write-Output "校验结果 : $(if ($expect -eq $m.Groups[3].Value.ToLower()) { 'OK ✅' } else { "不匹配 ❌（期望 $expect）" })"
}

function Connect-Ap([string]$name) {
  if (-not $name) { Write-Output "需要 -Ssid"; return }
  $xml = @"
<?xml version="1.0"?>
<WLANProfile xmlns="http://www.microsoft.com/networking/WLAN/profile/v1">
  <name>$name</name>
  <SSIDConfig><SSID><name>$name</name></SSID></SSIDConfig>
  <connectionType>ESS</connectionType>
  <connectionMode>manual</connectionMode>
  <MSM><security>
    <authEncryption>
      <authentication>open</authentication>
      <encryption>none</encryption>
      <useOneX>false</useOneX>
    </authEncryption>
  </security></MSM>
</WLANProfile>
"@
  $tmp = Join-Path $env:TEMP "xc-ap-profile.xml"
  $xml | Out-File -Encoding ASCII $tmp
  netsh wlan add profile filename="$tmp" | Out-Null
  Remove-Item $tmp -Force
  netsh wlan connect name="$name" ssid="$name"
  Start-Sleep -Seconds 4
  netsh wlan show interfaces | Select-String -Pattern 'SSID|状态|State'
}

switch ($Action) {
  "scan"       { Show-Scan }
  "find-plug"  { Find-Plug }
  "parse"      { Parse-PlugSsid $Ssid }
  "connect"    { Connect-Ap $Ssid }
  "status"     { netsh wlan show interfaces }
  "disconnect" { netsh wlan disconnect }
  default      { Write-Output "未知动作：$Action（可用 scan / find-plug / parse / connect / status / disconnect）" }
}
