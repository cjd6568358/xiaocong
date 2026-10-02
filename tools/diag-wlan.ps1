# 直接调用 wlanapi.dll：强制 WlanScan + 完整枚举可用网络
# 目的：绕开 netsh wlan show networks 的结果过滤，拿到系统 WiFi 面板同源的完整列表
$ErrorActionPreference = 'Continue'
$out = New-Object System.Collections.Generic.List[string]

$out.Add("===== 位置服务（扫描权限）=====")
foreach ($p in @(
  'HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\CapabilityAccessManager\ConsentStore\location',
  'HKCU:\Software\Microsoft\Windows\CurrentVersion\CapabilityAccessManager\ConsentStore\location')) {
  try { $v = (Get-ItemProperty -Path $p -Name Value -ErrorAction Stop).Value; $out.Add("$p => $v") }
  catch { $out.Add("$p => (读取失败)") }
}

$code = @'
using System;
using System.Runtime.InteropServices;
using System.Text;
using System.Collections.Generic;

public static class WlanDiag {
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

    public static List<string> Run() {
        var o = new List<string>();
        uint neg; IntPtr h;
        int r = WlanOpenHandle(2, IntPtr.Zero, out neg, out h);
        o.Add("WlanOpenHandle -> " + r + " (negotiated=" + neg + ")");
        if (r != 0) return o;

        IntPtr ilist;
        r = WlanEnumInterfaces(h, IntPtr.Zero, out ilist);
        o.Add("WlanEnumInterfaces -> " + r);
        if (r != 0) { WlanCloseHandle(h, IntPtr.Zero); return o; }

        int n = Marshal.ReadInt32(ilist);
        o.Add("接口数 = " + n);
        int infoSize = 16 + 512 + 4;

        var guids = new Guid[n];
        for (int i = 0; i < n; i++) {
            IntPtr p = (IntPtr)((long)ilist + 8 + i * infoSize);
            byte[] gb = new byte[16];
            Marshal.Copy(p, gb, 0, 16);
            guids[i] = new Guid(gb);
            string desc = Marshal.PtrToStringUni((IntPtr)((long)p + 16));
            o.Add("接口[" + i + "] " + desc);
            int s1 = WlanScan(h, ref guids[i], IntPtr.Zero, IntPtr.Zero, IntPtr.Zero);
            o.Add("  WlanScan #1 -> " + s1 + (s1 == 5 ? "  ← ERROR_ACCESS_DENIED（扫描被拒！）" : ""));
        }
        System.Threading.Thread.Sleep(2500);
        for (int i = 0; i < n; i++) { WlanScan(h, ref guids[i], IntPtr.Zero, IntPtr.Zero, IntPtr.Zero); }
        System.Threading.Thread.Sleep(3000);

        int netSize = Marshal.SizeOf(typeof(WLAN_AVAILABLE_NETWORK));
        o.Add("WLAN_AVAILABLE_NETWORK 结构大小 = " + netSize);

        for (int i = 0; i < n; i++) {
            IntPtr alist;
            r = WlanGetAvailableNetworkList(h, ref guids[i], 3, IntPtr.Zero, out alist);
            o.Add("WlanGetAvailableNetworkList -> " + r);
            if (r != 0) continue;
            int m = Marshal.ReadInt32(alist);
            o.Add("--- 接口[" + i + "] 可用网络 " + m + " 个 ---");
            for (int k = 0; k < m; k++) {
                IntPtr np = (IntPtr)((long)alist + 8 + k * netSize);
                WLAN_AVAILABLE_NETWORK an = (WLAN_AVAILABLE_NETWORK)Marshal.PtrToStructure(np, typeof(WLAN_AVAILABLE_NETWORK));
                int len = (int)an.dot11Ssid.uSSIDLength;
                if (len > 32) len = 32;
                string ssid = "";
                if (len > 0 && an.dot11Ssid.ucSSID != null) {
                    ssid = Encoding.UTF8.GetString(an.dot11Ssid.ucSSID, 0, len);
                }
                o.Add(string.Format("  [{0,3}%] ssid=\"{1}\"  profile=\"{2}\"  flags=0x{3:X}  connectable={4}",
                    an.wlanSignalQuality, ssid, an.strProfileName, an.dwFlags, an.bNetworkConnectable));
            }
            WlanFreeMemory(alist);
        }
        WlanFreeMemory(ilist);
        WlanCloseHandle(h, IntPtr.Zero);
        return o;
    }
}
'@

try {
  Add-Type -TypeDefinition $code -Language CSharp -ErrorAction Stop
  $out.AddRange([WlanDiag]::Run())
} catch {
  $out.Add("C# 编译/调用失败: " + $_.Exception.Message)
}

$out | Out-File -Encoding utf8 "C:\workspace\xiaocong\_wlan.txt"
