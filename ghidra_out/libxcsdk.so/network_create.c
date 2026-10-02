// network_create  @ 00014688


Network * network_create(char *host)

{
  Network *pNVar1;
  int __fd;
  int iVar2;
  int iVar3;
  in_addr_t iVar4;
  int iVar5;
  int *piVar6;
  Network *pNVar7;
  int nRecvBuf;
  int broadcastEnable;
  sockaddr_in bindAddr;
  
                    /* Unresolved local var: Network * pCtx@[???]
                       Unresolved local var: int sock@[???] */
  piVar6 = *(int **)(DAT_000147e4 + 0x146a4);
  iVar5 = *piVar6;
  pNVar1 = malloc(0x18);
  bindAddr.sin_family = 0;
  bindAddr.sin_port = 0;
  bindAddr.sin_addr.s_addr = 0;
  bindAddr.__pad[0] = '\0';
  bindAddr.__pad[1] = '\0';
  bindAddr.__pad[2] = '\0';
  bindAddr.__pad[3] = '\0';
  bindAddr.__pad[4] = '\0';
  bindAddr.__pad[5] = '\0';
  bindAddr.__pad[6] = '\0';
  bindAddr.__pad[7] = '\0';
  if (host != (char *)0x0) {
    bindAddr.sin_addr.s_addr = inet_addr(host);
  }
  pNVar7 = (Network *)0x0;
  bindAddr.sin_family = 2;
  bindAddr.sin_port = 0;
  __fd = socket(2,2,0x11);
  nRecvBuf = 0x8000;
  iVar2 = setsockopt(__fd,1,8,&nRecvBuf,4);
  if (iVar2 == 0) {
    broadcastEnable = 1;
    iVar2 = setsockopt(__fd,1,6,&broadcastEnable,4);
    pNVar7 = (Network *)0x0;
    if (iVar2 < 0) {
      puts((char *)(DAT_000147f0 + 0x147c8));
    }
    else {
      iVar3 = bind(__fd,(sockaddr *)&bindAddr,0x10);
      iVar2 = DAT_000147e8;
      if (iVar3 < 0) {
        puts((char *)(DAT_000147f4 + 0x147dc));
      }
      else {
        (pNVar1->broadcast).sin_family = 2;
        (pNVar1->broadcast).sin_port = 0x3316;
        iVar4 = inet_addr((char *)(iVar2 + 0x1477c));
        pNVar1->lanfd = __fd;
        pNVar1->packageId = 0;
        (pNVar1->broadcast).sin_addr.s_addr = iVar4;
        pNVar7 = pNVar1;
      }
    }
  }
  else {
    puts((char *)(DAT_000147ec + 0x147b4));
  }
  if (iVar5 == *piVar6) {
    return pNVar7;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


