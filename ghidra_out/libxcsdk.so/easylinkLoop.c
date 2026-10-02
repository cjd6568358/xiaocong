// easylinkLoop  @ 00019c80


int easylinkLoop(XConfig *pContex)

{
  XConfig *pXVar1;
  int iVar2;
  int extraout_r1;
  int extraout_r1_00;
  int extraout_r1_01;
  int extraout_r1_02;
  int iVar3;
  int iVar4;
  uint uVar5;
  int *piVar6;
  uint uVar7;
  int iVar8;
  bool bVar9;
  uint in_fpscr;
  float fVar10;
  float fVar11;
  char dest [20];
  char message [30];
  
                    /* Unresolved local var: xconfig_para * pArgs@[???]
                       Unresolved local var: float percent@[???]
                       Unresolved local var: int process@[???]
                       Unresolved local var: int ret@[???] */
  piVar6 = *(int **)(DAT_00019fd8 + 0x19c9c);
  bVar9 = pContex->isEasyLinkOprating == 2;
  pXVar1 = pContex;
  if (!bVar9) {
    pXVar1 = (XConfig *)0x1;
  }
  iVar3 = *piVar6;
  if (bVar9) {
    uVar7 = (pContex->args).SendType & 0xf;
    if (uVar7 == 1) {
                    /* Unresolved local var: int i@[???] */
      iVar4 = pContex->muticastIndex;
      snprintf(dest,0x14,(char *)(DAT_00019fdc + 0x19d10),(uint)pContex->payloadMuticast[iVar4][0],
               (uint)pContex->payloadMuticast[iVar4][1],(uint)pContex->payloadMuticast[iVar4][2]);
      snprintf(message,0x1e,(char *)(DAT_00019fe0 + 0x19d3c),iVar4,dest);
      sendpacket(pContex->udpSocket,dest,1);
      iVar2 = pContex->muticastSize;
      __aeabi_idivmod(iVar4 + 1,iVar2);
      pXVar1 = (XConfig *)(pContex->args).bDataInterval;
      fVar11 = (float)VectorSignedToFloat(iVar4 + 1,(byte)(in_fpscr >> 0x16) & 3);
      fVar11 = fVar11 * DAT_00019fd4;
      fVar10 = (float)VectorSignedToFloat(iVar2,(byte)(in_fpscr >> 0x16) & 3);
      pContex->muticastIndex = extraout_r1;
      if ((int)(longlong)(fVar11 / fVar10 + 0.5) == 100) {
        pContex->isEasyLinkOprating = 0;
      }
    }
    else {
      if (uVar7 == 2) {
                    /* Unresolved local var: int bIndex@[???] */
        iVar4 = pContex->broadcastIndex;
        timerReset(&pContex->startTimeStamp);
        iVar2 = timerMsPassed(pContex->startTimeStamp);
        bVar9 = iVar2 == pContex->totalTimeMS;
        if (pContex->totalTimeMS <= iVar2) {
          bVar9 = iVar4 == 4;
        }
        pContex->currentTimeMS = iVar2;
        if (!bVar9) {
          iVar2 = iVar4 + 1;
          snprintf(message,0x14,(char *)(DAT_00019fe4 + 0x19df0),iVar4,
                   (uint)pContex->payloadBroadcast[iVar4]);
          sendpacket(pContex->udpSocket,(char *)(DAT_00019fe8 + 0x19e10),
                     (uint)pContex->payloadBroadcast[iVar4]);
          if (iVar2 == 4) {
            pXVar1 = (XConfig *)(pContex->args).bSyncInterval;
            if (pContex->currentTimeMS < (pContex->args).bSyncTimer) {
              iVar2 = 0;
            }
          }
          else if (pContex->broadcastSize == iVar2) {
            pXVar1 = (XConfig *)(pContex->args).bDataInterval;
            iVar2 = 4;
          }
          else {
            pXVar1 = (XConfig *)0xa;
          }
          pContex->broadcastIndex = iVar2;
          goto LAB_00019cac;
        }
      }
      else if (uVar7 == 3) {
                    /* Unresolved local var: int bIndex@[???] */
        iVar8 = pContex->broadcastIndex;
        iVar2 = timerMsPassed(pContex->startTimeStamp);
        iVar4 = (pContex->args).fullScaleTimer;
        bVar9 = iVar2 == iVar4;
        if (iVar4 <= iVar2) {
          bVar9 = iVar8 == 0;
        }
        pContex->currentTimeMS = iVar2;
        if (!bVar9) {
          if (*(int *)(DAT_00019fec + 0x1a060) == 0) {
            uVar7 = iVar8 + 1;
            snprintf(message,0x14,(char *)(DAT_00019ff8 + 0x19f0c),iVar8,
                     (uint)pContex->payloadBroadcast[iVar8]);
            sendpacket(pContex->udpSocket,(char *)(DAT_00019ffc + 0x19f2c),
                       (uint)pContex->payloadBroadcast[iVar8]);
            if (uVar7 == 4) {
              __aeabi_idivmod(pContex->currentTimeMS,pContex->totalTimeMS);
              pXVar1 = (XConfig *)(pContex->args).bSyncInterval;
              bVar9 = extraout_r1_01 < (pContex->args).bSyncTimer;
              if (bVar9) {
                uVar7 = 0;
              }
              uVar5 = (uint)bVar9;
            }
            else if (pContex->broadcastSize == uVar7) {
              __aeabi_idivmod(pContex->currentTimeMS,pContex->totalTimeMS);
              pXVar1 = (XConfig *)(pContex->args).bDataInterval;
              bVar9 = extraout_r1_02 < (pContex->args).bSyncTimer;
              if (bVar9) {
                uVar7 = 0;
              }
              else {
                uVar7 = 4;
              }
              uVar5 = (uint)bVar9;
            }
            else {
              pXVar1 = (XConfig *)0xa;
              if ((uVar7 & 7) == 0) {
                uVar5 = 1;
              }
              else {
                uVar5 = 0;
              }
            }
            iVar2 = DAT_0001a000;
            pContex->broadcastIndex = uVar7;
            *(uint *)(iVar2 + 0x1a160) = uVar5;
          }
          else {
                    /* Unresolved local var: int gIndex@[???] */
            iVar2 = pContex->muticastIndex;
            *(undefined4 *)(DAT_00019fec + 0x1a060) = 0;
            pXVar1 = (XConfig *)0xa;
            snprintf(dest,0x14,(char *)(DAT_00019ff0 + 0x19e94),
                     (uint)pContex->payloadMuticast[iVar2][0],
                     (uint)pContex->payloadMuticast[iVar2][1],
                     (uint)pContex->payloadMuticast[iVar2][2]);
            snprintf(message,0x1e,(char *)(DAT_00019ff4 + 0x19ec0),iVar2,dest);
            sendpacket(pContex->udpSocket,dest,1);
            __aeabi_idivmod(iVar2 + 1,pContex->muticastSize);
            pContex->muticastIndex = extraout_r1_00;
          }
          goto LAB_00019cac;
        }
      }
      pXVar1 = (XConfig *)0xa;
    }
  }
LAB_00019cac:
  if (iVar3 == *piVar6) {
    return (int)pXVar1;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


