# 用 wlanapi.dll 强制扫描并枚举可见网络（替代 netsh wlan show networks）
#
# 为什么需要它：
#   `netsh wlan show networks` 返回的列表会**过滤/去重**，实测在插座配网模式下
#   完全看不到 `smart-*` 热点（面板里能看到），导致脚本误判"插座没进配网模式"。
#   本脚本直接 WlanScan + WlanGetAvailableNetworkList，拿到与系统 WiFi 面板同源的全量列表。
#
# 用法：
#   & tools\wlanapi-scan.ps1                        # 打印全部
#   & tools\wlanapi-scan.ps1 -Filter smart-         # 只打印匹配的
#   & tools\wlanapi-scan.ps1 -OutFile C:\tmp\n.txt  # 同时落盘
#
# 输出格式（每行一个网络，制表符分隔）：
#   <信号%>\t<SSID>\t<已保存的profile名>\t<flags>
param(
  [string]$Filter = "",
  [int]$WaitMs = 4500,
  [string]$OutFile = ""
)

$code = @'
using System;
using System.Runtime.InteropServices;
using System.Text;
using System.Collections.Generic;

public static class WlanScanLite {
    [DllImport("wlanapi.dll", SetLastError=true)]
    public static extern int WlanOpenHandle(uint ver, IntPtr res, out uint neg, out IntPtr h);
    [DllImport("wlanapi.dll")]
    public static extern int WlanCloseHandle(IntPtr h, IntPtr res);
    [DllImport("wlanapi.dll")]
    public static extern int WlanEnumInterfaces(IntPtr h, IntPtr res, out IntPtr list);
    [DllImport("wlanapi.dll")]
    public static extern int WlanScan(IntPtr h, ref Guid g, IntPtr ssid, IntPtr ie, IntPtr res);
    [DllImport("wlanapi.dll")]
    public static extern int WlanGetAvailableNetworkList(IntPtr h, ref Guid g, uint flags, IntPtr res, out IntPtr list);
    [DllImport("wlanapi.dll")]
    public static extern void WlanFreeMemory(IntPtr p);

    [StructLayout(LayoutKind.Sequential)]
    public struct WLAN_SSID {
        public uint uSSIDLength;
        [MarshalAs(UnmanagedType.ByValArray, SizeConst = 32)] public byte[] ucSSID;
    }

    [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)]
    public struct WLAN_AVAILABLE_NETWORK {
        [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 256)] public string strProfileName;
        public WLAN_SSID dot11Ssid;
        public uint dot11BssType;
        public uint uNumberOfBssids;
        public int bNetworkConnectable;
        public uint wlanNotConnectableReason;
        public uint uNumberOfPhyTypes;
        [MarshalAs(UnmanagedType.ByValArray, SizeConst = 8)] public uint[] dot11PhyTypes;
        public int bMorePhyTypes;
        public uint wlanSignalQuality;
        public int bSecurityEnabled;
        public uint dot11DefaultAuthAlgorithm;
        public uint dot11DefaultCipherAlgorithm;
        public uint dwFlags;
        public uint dwReserved;
    }

    public static List<string> Scan(int waitMs) {
        var o = new List<string>();
        uint neg; IntPtr h;
        if (WlanOpenHandle(2, IntPtr.Zero, out neg, out h) != 0) { o.Add("ERROR\tWlanOpenHandle failed"); return o; }
        IntPtr ilist;
        if (WlanEnumInterfaces(h, IntPtr.Zero, out ilist) != 0) { WlanCloseHandle(h, IntPtr.Zero); o.Add("ERROR\tWlanEnumInterfaces failed"); return o; }
        int n = Marshal.ReadInt32(ilist);
        int infoSize = 16 + 512 + 4;
        var guids = new Guid[n];
        for (int i = 0; i < n; i++) {
            IntPtr p = (IntPtr)((long)ilist + 8 + i * infoSize);
            byte[] gb = new byte[16];
            Marshal.Copy(p, gb, 0, 16);
            guids[i] = new Guid(gb);
            WlanScan(h, ref guids[i], IntPtr.Zero, IntPtr.Zero, IntPtr.Zero);
        }
        System.Threading.Thread.Sleep(waitMs);
        for (int i = 0; i < n; i++) { WlanScan(h, ref guids[i], IntPtr.Zero, IntPtr.Zero, IntPtr.Zero); }
        System.Threading.Thread.Sleep(2500);

        int netSize = Marshal.SizeOf(typeof(WLAN_AVAILABLE_NETWORK));
        for (int i = 0; i < n; i++) {
            IntPtr alist;
            if (WlanGetAvailableNetworkList(h, ref guids[i], 3, IntPtr.Zero, out alist) != 0) continue;
            int m = Marshal.ReadInt32(alist);
            for (int k = 0; k < m; k++) {
                IntPtr np = (IntPtr)((long)alist + 8 + k * netSize);
                WLAN_AVAILABLE_NETWORK an = (WLAN_AVAILABLE_NETWORK)Marshal.PtrToStructure(np, typeof(WLAN_AVAILABLE_NETWORK));
                int len = (int)an.dot11Ssid.uSSIDLength;
                if (len > 32) len = 32;
                string ssid = "";
                if (len > 0 && an.dot11Ssid.ucSSID != null) ssid = Encoding.UTF8.GetString(an.dot11Ssid.ucSSID, 0, len);
                if (ssid.Length == 0) continue;
                o.Add(string.Format("{0}\t{1}\t{2}\t0x{3:X}", an.wlanSignalQuality, ssid, an.strProfileName, an.dwFlags));
            }
            WlanFreeMemory(alist);
        }
        WlanFreeMemory(ilist);
        WlanCloseHandle(h, IntPtr.Zero);
        return o;
    }
}
'@

Add-Type -TypeDefinition $code -Language CSharp -ErrorAction Stop
$all = [WlanScanLite]::Scan($WaitMs)
$sel = if ($Filter) { $all | Where-Object { $_ -match [regex]::Escape($Filter) } } else { $all }
$sel | ForEach-Object { $_ }
if ($OutFile) { $all | Out-File -Encoding utf8 $OutFile }
