// wait_packet  @ 00014850


int wait_packet(Network *pCtx,int *cmdpipe,coap_pdu *msg_recv,char *peer,int msTimeout)

{
  uint uVar1;
  int iVar2;
  uint uVar3;
  size_t sVar4;
  char *__src;
  int iVar5;
  int iVar6;
  int *piVar7;
  uchar *puVar8;
  uint uVar9;
  socklen_t len_from;
  timeval selectTimeOut;
  sockaddr_in peerAddr;
  fd_set readfds;
  
                    /* Unresolved local var: int ret@[???]
                       Unresolved local var: int maxfd@[???] */
  puVar8 = peerAddr.__pad + 4;
  piVar7 = *(int **)(DAT_00014a04 + 0x14888);
                    /* Unresolved local var: size_t __i@[???] */
  iVar5 = *piVar7;
  selectTimeOut.tv_sec = msTimeout / 1000;
  selectTimeOut.tv_usec = (msTimeout % 1000) * 1000;
  do {
    puVar8 = puVar8 + 4;
    puVar8[0] = '\0';
    puVar8[1] = '\0';
    puVar8[2] = '\0';
    puVar8[3] = '\0';
  } while ((ulong *)puVar8 != readfds.fds_bits + 0x1f);
  uVar1 = pCtx->lanfd;
  uVar9 = cmdpipe[1];
  iVar6 = 1;
  uVar3 = uVar1;
  if ((int)uVar1 <= (int)uVar9) {
    uVar3 = uVar9;
  }
  readfds.fds_bits[uVar1 >> 5] = readfds.fds_bits[uVar1 >> 5] | 1 << (uVar1 & 0x1f);
  readfds.fds_bits[uVar9 >> 5] = readfds.fds_bits[uVar9 >> 5] | 1 << (uVar9 & 0x1f);
  iVar2 = select(uVar3 + 1,(fd_set *)&readfds,(fd_set *)0x0,(fd_set *)0x0,(timeval *)&selectTimeOut)
  ;
  if (iVar2 < 0) {
    iVar6 = -1;
  }
  else {
    uVar3 = pCtx->lanfd;
    if ((readfds.fds_bits[uVar3 >> 5] >> (uVar3 & 0x1f) & 1) == 0) {
      uVar3 = cmdpipe[1];
      iVar6 = 0;
      if ((readfds.fds_bits[uVar3 >> 5] >> (uVar3 & 0x1f) & 1) != 0) {
        iVar6 = 2;
        sVar4 = recv(uVar3,msg_recv->buf,msg_recv->max,0);
        msg_recv->len = sVar4;
      }
    }
    else {
                    /* Unresolved local var: char * ip@[???] */
      len_from = 0x10;
      sVar4 = recvfrom(uVar3,msg_recv->buf,msg_recv->max,0,(sockaddr *)&peerAddr,
                       (socklen_t *)&len_from);
      __src = inet_ntoa((in_addr)peerAddr.sin_addr.s_addr);
      msg_recv->len = sVar4;
      strcpy(peer,__src);
    }
  }
  if (iVar5 == *piVar7) {
    return iVar6;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


