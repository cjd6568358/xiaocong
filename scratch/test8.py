#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""光猫网络栈诊断：判定路径 A 为何失败，以及 DNS 劫持能用什么手段。只读。"""
import os
import paramiko

PW = os.environ["SSH_PW"]
cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=PW, timeout=12,
            look_for_keys=False, allow_agent=False)


def sh(c, t=25):
    _, o, e = cli.exec_command(c, timeout=t)
    return (o.read().decode("utf-8", "replace").strip() +
            ("\n[stderr] " + e.read().decode("utf-8", "replace").strip() if e else ""))


SECTIONS = [
    ("接口一览", "ifconfig -a 2>/dev/null | grep -E '^[a-zA-Z]' "),
    ("路由表(main)", "cat /proc/net/route"),
    ("rp_filter", "for f in all default br0 ppp0 eth0 nbif2 nbif3; do "
                  "printf '%s=' $f; cat /proc/sys/net/ipv4/conf/$f/rp_filter 2>/dev/null || echo NA; done"),
    ("route_localnet / accept_local", "for f in all br0 ppp0; do printf '%s rl=' $f; "
     "cat /proc/sys/net/ipv4/conf/$f/route_localnet 2>/dev/null; printf '%s al=' $f; "
     "cat /proc/sys/net/ipv4/conf/$f/accept_local 2>/dev/null; done"),
    ("ip_forward", "cat /proc/sys/net/ipv4/ip_forward"),
    ("netfilter 表(是否已启用)", "ls -l /proc/net/ip_tables_names /proc/net/nf_tables 2>&1; "
     "echo '--- 内容 ---'; cat /proc/net/ip_tables_names 2>&1"),
    ("netfilter 工具", "for t in iptables iptables-save ip6tables nft ebtables tc ip; do "
     "printf '%s -> ' $t; (command -v $t || echo '(无)'); done"),
    ("/sbin /usr/sbin 里的网络工具", "ls /sbin /usr/sbin /usr/bin 2>/dev/null | grep -iE "
     "'iptable|nft|xt_|netfilter|tc$|^ip$|dnsmasq|odhcp|conntrack' | sort -u"),
    ("UDP 监听(谁占着 53)", "netstat -lnup 2>/dev/null | head -25"),
    ("TCP 监听", "netstat -ltn 2>/dev/null | head -25"),
    ("conntrack 中与 8188 相关", "cat /proc/net/nf_conntrack 2>/dev/null | grep -c 8188; "
     "cat /proc/net/nf_conntrack 2>/dev/null | grep 8188 | head -4"),
    ("内核版本", "uname -a; cat /proc/version 2>/dev/null"),
    ("OpenWrt 版本", "cat /etc/openwrt_release 2>/dev/null; cat /etc/os-release 2>/dev/null | head -5"),
]

for title, cmd in SECTIONS:
    print("=" * 68)
    print("##", title)
    print(sh(cmd))

cli.close()
