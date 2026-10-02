# 强制触发一次真正的 WiFi 扫描。
#
# 为什么需要这个：
#   `netsh wlan show networks` 读的是 WLAN AutoConfig 的【缓存列表】。
#   PC 已连上 PDCN_IOT（信号 86%）时，Windows 认为"没必要扫描"，
#   于是缓存长期不刷新 —— 表现为永远只报 1 个网络，
#   哪怕插座的热点就在旁边（手机一搜就有）。
#
#   WlanScan() 是唯一能"现在就扫"的官方接口。扫完再读 netsh 才是新鲜数据。
#
# 用法： powershell -ExecutionPolicy Bypass -File .scratch\wifi-scan.ps1

$sig = @'
[DllImport("wlanapi.dll", SetLastError=true)]
public static extern int WlanOpenHandle(int dwClientVersion, IntPtr pReserved, out int pdwNegotiatedVersion, out IntPtr phClientHandle);

[DllImport("wlanapi.dll", SetLastError=true)]
public static extern int WlanCloseHandle(IntPtr hClientHandle, IntPtr pReserved);

[DllImport("wlanapi.dll", SetLastError=true)]
public static extern int WlanEnumInterfaces(IntPtr hClientHandle, IntPtr pReserved, out IntPtr ppInterfaceList);

[DllImport("wlanapi.dll", SetLastError=true)]
public static extern int WlanScan(IntPtr hClientHandle, ref Guid pInterfaceGuid, IntPtr pDot11Ssid, IntPtr pIeData, IntPtr pReserved);

[DllImport("wlanapi.dll", SetLastError=true)]
public static extern void WlanFreeMemory(IntPtr pMemory);
'@

Add-Type -MemberDefinition $sig -Name WlanApi -Namespace XcWifi

$ver = 0
$handle = [IntPtr]::Zero
$rc = [XcWifi.WlanApi]::WlanOpenHandle(2, [IntPtr]::Zero, [ref]$ver, [ref]$handle)
if ($rc -ne 0) { Write-Host "WlanOpenHandle 失败 rc=$rc"; exit 1 }

$ppList = [IntPtr]::Zero
$rc = [XcWifi.WlanApi]::WlanEnumInterfaces($handle, [IntPtr]::Zero, [ref]$ppList)
if ($rc -ne 0) { Write-Host "WlanEnumInterfaces 失败 rc=$rc"; exit 1 }

# WLAN_INTERFACE_INFO_LIST: dwNumberOfItems(4) + dwIndex(4) + WLAN_INTERFACE_INFO[]
# WLAN_INTERFACE_INFO 前 16 字节就是 InterfaceGuid
$n = [System.Runtime.InteropServices.Marshal]::ReadInt32($ppList)
if ($n -lt 1) { Write-Host "没有无线接口"; exit 1 }
$guidPtr = [IntPtr]::Add($ppList, 8)
$guid = [System.Runtime.InteropServices.Marshal]::PtrToStructure($guidPtr, [Type][Guid])

$rc = [XcWifi.WlanApi]::WlanScan($handle, [ref]$guid, [IntPtr]::Zero, [IntPtr]::Zero, [IntPtr]::Zero)
Write-Host "WlanScan rc=$rc  (0 = 扫描已发起，异步进行)"

[XcWifi.WlanApi]::WlanFreeMemory($ppList)
[XcWifi.WlanApi]::WlanCloseHandle($handle, [IntPtr]::Zero)

Start-Sleep -Seconds 7

Write-Host ""
Write-Host "=================== 扫描结果 ==================="
netsh wlan show networks mode=bssid
