// xconfig_start  @ 0001a0c8


/* WARNING: Removing unreachable block (ram,0x0001a334) */
/* WARNING: Removing unreachable block (ram,0x0001a3a0) */
/* WARNING: Removing unreachable block (ram,0x0001a378) */
/* WARNING: Removing unreachable block (ram,0x0001a3b4) */

int xconfig_start(XConfig *pContex,char *ssid,int ssid_len,char *pwd,int pwd_len,char *key,
                 int key_len,xconfig_para *pArgs)

{
  byte bVar1;
  ushort uVar2;
  byte bVar3;
  uint uVar4;
  uint16_t *puVar5;
  uint8_t *key_00;
  int iVar6;
  uint8_t (*pauVar7) [3];
  int extraout_r1;
  uint16_t uVar8;
  uint16_t uVar9;
  int iVar10;
  uint uVar11;
  uint16_t *puVar12;
  int *piVar14;
  int iVar15;
  uint uVar16;
  int iVar17;
  uint uVar18;
  undefined1 *puVar19;
  undefined1 *len;
  uint8_t auVar20 [4];
  byte *p;
  byte *pbVar21;
  uint16_t uVar22;
  int iVar23;
  undefined1 *puVar24;
  undefined1 *puVar25;
  bool bVar26;
  uint8_t aes128key [16];
  uint8_t crcbuffer [4];
  uint8_t local_298 [4];
  uint8_t local_294 [4];
  uint8_t local_290 [4];
  uint8_t payload [96];
  char message [512];
  undefined1 auStack_27 [3];
  uint16_t *puVar13;
  
                    /* Unresolved local var: int i@[???] */
  piVar14 = *(int **)(DAT_0001a79c + 0x1a0dc);
  iVar15 = *piVar14;
  if (pContex != (XConfig *)0x0) {
    if (pArgs == (xconfig_para *)0x0) {
      pArgs = &pContex->args;
      (pContex->args).bSyncInterval = 5;
      (pContex->args).bDataInterval = 10;
      (pContex->args).fullScaleTimer = 40000;
      iVar6 = pContex->udpSocket;
      (pContex->args).SendType = 0x23;
      (pContex->args).bSyncTimer = 2000;
      (pContex->args).bDataTimer = 0x14;
    }
    else {
      iVar6 = pArgs->bSyncInterval;
      iVar10 = pArgs->bSyncTimer;
      iVar23 = pArgs->bDataInterval;
      (pContex->args).SendType = pArgs->SendType;
      (pContex->args).bSyncInterval = iVar6;
      (pContex->args).bSyncTimer = iVar10;
      (pContex->args).bDataInterval = iVar23;
      uVar11 = pArgs->feedbackIp;
      iVar10 = pArgs->fullScaleTimer;
      iVar6 = pContex->udpSocket;
      (pContex->args).bDataTimer = pArgs->bDataTimer;
      (pContex->args).fullScaleTimer = iVar10;
      (pContex->args).feedbackIp = uVar11;
      (pContex->args).feedbackPort = pArgs->feedbackPort;
    }
    if (iVar6 < 1) {
      iVar6 = network_newudp((char *)0x0,0,1,1);
      pContex->udpSocket = iVar6;
      if (iVar6 == -1) goto LAB_0001a710;
    }
    p = payload;
    memset(p,0,0x60);
    if (pwd != (char *)0x0 && -1 < pwd_len) {
      auVar20 = (uint8_t  [4])((uint)ssid_len >> 0x1f);
      if (ssid == (char *)0x0) {
        auVar20[0] = '\x01';
        auVar20[1] = '\0';
        auVar20[2] = '\0';
        auVar20[3] = '\0';
      }
      if (auVar20 == (uint8_t  [4])0x0) {
        payload[2] = (uint8_t)pwd_len;
        payload[1] = payload[2] + (char)ssid_len + '\a';
        memcpy(payload + 3,pwd,pwd_len);
        uVar2 = (pContex->args).feedbackPort;
        *(uint *)(p + pwd_len + 3) = (pContex->args).feedbackIp;
        *(ushort *)(p + pwd_len + 7) = uVar2;
        memcpy(p + pwd_len + 9,ssid,ssid_len);
        memset(message,0,0x200);
        len = (undefined1 *)(pwd_len + 9 + ssid_len);
        dump8((char *)(DAT_0001a7a0 + 0x1a218),message,p,0,(int)len);
        iVar6 = (pContex->args).SendType >> 4;
        if (iVar6 == 2) {
          aes128key._0_4_ = auVar20;
          aes128key._4_4_ = auVar20;
          aes128key._8_4_ = auVar20;
          aes128key._12_4_ = auVar20;
          crcbuffer = auVar20;
          local_298 = auVar20;
          local_294 = auVar20;
          local_290 = auVar20;
          key_00 = memcpy(aes128key,key,key_len);
          uVar11 = device_aes_encrypt(key_00,0x10,crcbuffer,payload + 2,(uint)payload[1],payload + 2
                                      ,payload[1] + 0x10);
          uVar16 = uVar11 & 0xff;
          len = (undefined1 *)(uVar11 + 2);
          payload[1] = (uint8_t)uVar11;
        }
        else {
          if (iVar6 == 1) {
            pContex->isEasyLinkOprating = 0;
            goto LAB_0001a710;
          }
          uVar16 = (uint)payload[1];
        }
                    /* Unresolved local var: uchar crc@[???]
                       Unresolved local var: uchar i@[???] */
        pbVar21 = p;
        uVar11 = 0;
        while (pbVar21 != p + uVar16 + 1) {
          pbVar21 = pbVar21 + 1;
          uVar18 = (uVar11 ^ *pbVar21) >> 1;
          uVar4 = uVar18 ^ 0x8c;
          if (((uVar11 ^ *pbVar21) & 1) == 0) {
            uVar4 = uVar18;
          }
          uVar11 = uVar4 >> 1 ^ 0x8c;
          if ((uVar4 & 1) == 0) {
            uVar11 = uVar4 >> 1;
          }
          uVar18 = uVar11 >> 1;
          if ((uVar11 & 1) != 0) {
            uVar18 = uVar11 >> 1 ^ 0x8c;
          }
          uVar11 = uVar18 >> 1;
          if ((uVar18 & 1) != 0) {
            uVar11 = uVar18 >> 1 ^ 0x8c;
          }
          uVar18 = uVar11 >> 1;
          if ((uVar11 & 1) != 0) {
            uVar18 = uVar11 >> 1 ^ 0x8c;
          }
          uVar11 = uVar18 >> 1;
          if ((uVar18 & 1) != 0) {
            uVar11 = uVar18 >> 1 ^ 0x8c;
          }
          uVar18 = uVar11 >> 1;
          if ((uVar11 & 1) != 0) {
            uVar18 = uVar11 >> 1 ^ 0x8c;
          }
          uVar11 = uVar18 >> 1;
          if ((uVar18 & 1) != 0) {
            uVar11 = uVar18 >> 1 ^ 0x8c;
          }
        }
        payload[0] = (uint8_t)uVar11;
        dump8((char *)(DAT_0001a7a4 + 0x1a318),message,p,0,(int)len);
                    /* Unresolved local var: int i@[???] */
        uVar11 = 1;
        pContex->payloadBroadcast[1] = 2;
        pContex->payloadBroadcast[3] = 4;
                    /* Unresolved local var: int round@[???]
                       Unresolved local var: int j@[???] */
        pContex->payloadBroadcast[0] = 1;
        pContex->payloadBroadcast[2] = 3;
        puVar5 = pContex->payloadBroadcast;
        pContex->payloadBroadcast[5] = 1;
                    /* Unresolved local var: uchar crc@[???]
                       Unresolved local var: uchar i@[???] */
        iVar6 = 6;
        puVar24 = len + 3;
        if (-1 < (int)len) {
          puVar24 = len;
        }
        pContex->payloadBroadcast[4] = 0x106;
        iVar10 = (uint)(((uint)len & 3) != 0) + ((int)puVar24 >> 2);
        if (iVar10 < 1) {
          iVar6 = 6;
        }
        else {
          pbVar21 = (byte *)0x0;
          iVar23 = 1;
          puVar12 = puVar5;
          do {
            uVar8 = (uint16_t)puVar12;
            uVar9 = (uint16_t)iVar6;
            uVar22 = (uint16_t)uVar11;
            bVar1 = *p;
            crcbuffer[0] = '\0';
            crcbuffer[1] = '\0';
            crcbuffer[2] = '\0';
            crcbuffer[3] = '\0';
            pbVar21 = pbVar21 + 1;
            if ((int)len < iVar23) {
              uVar8 = 0;
            }
            else {
              uVar9 = (uint16_t)p[1];
            }
            puVar5[7] = (ushort)bVar1;
            if (iVar23 <= (int)len) {
              uVar8 = uVar9;
            }
            crcbuffer[1] = (uint8_t)uVar8;
            crcbuffer[0] = bVar1;
            crcbuffer[2] = '\0';
            crcbuffer[3] = '\0';
            iVar17 = iVar23 + 2;
            uVar9 = uVar8;
            if (iVar23 + 1 <= (int)len) {
              uVar9 = (uint16_t)p[2];
            }
            puVar5[8] = uVar8;
            if ((int)len < iVar23 + 1) {
              uVar9 = 0;
            }
            crcbuffer[2] = (uint8_t)uVar9;
            crcbuffer[3] = '\0';
            puVar5[9] = uVar9;
            if ((int)len < iVar17) {
              uVar22 = 0;
            }
            else {
              uVar9 = (uint16_t)p[3];
            }
            iVar6 = ((uint)pbVar21 & 0x1f | 0x20) << 3;
            if ((int)len < iVar17) {
              uVar9 = uVar22;
            }
                    /* Unresolved local var: uchar crc@[???]
                       Unresolved local var: uchar i@[???] */
            uVar16 = 0;
            if (iVar17 <= (int)len) {
              uVar22 = uVar9;
            }
            crcbuffer[3] = (uint8_t)uVar9;
            puVar5[10] = uVar22;
            puVar13 = (uint16_t *)crcbuffer;
            do {
              puVar12 = (uint16_t *)((int)puVar13 + 1);
              uVar16 = (byte)*puVar13 ^ uVar16;
              uVar11 = uVar16 >> 1;
              uVar18 = uVar11 ^ 0x8c;
              if ((uVar16 & 1) == 0) {
                uVar18 = uVar11;
              }
              uVar11 = uVar18 >> 1 ^ 0x8c;
              if ((uVar18 & 1) == 0) {
                uVar11 = uVar18 >> 1;
              }
              uVar16 = uVar11 >> 1 ^ 0x8c;
              if ((uVar11 & 1) == 0) {
                uVar16 = uVar11 >> 1;
              }
              uVar11 = uVar16 >> 1 ^ 0x8c;
              if ((uVar16 & 1) == 0) {
                uVar11 = uVar16 >> 1;
              }
              uVar16 = uVar11 >> 1 ^ 0x8c;
              if ((uVar11 & 1) == 0) {
                uVar16 = uVar11 >> 1;
              }
              uVar11 = uVar16 >> 1 ^ 0x8c;
              if ((uVar16 & 1) == 0) {
                uVar11 = uVar16 >> 1;
              }
              uVar18 = uVar11 >> 1 ^ 0x8c;
              if ((uVar11 & 1) == 0) {
                uVar18 = uVar11 >> 1;
              }
              uVar11 = uVar18 >> 1 ^ 0x8c;
              uVar16 = uVar18 >> 1;
              if ((uVar18 & 1) != 0) {
                uVar16 = uVar11;
              }
              puVar13 = puVar12;
            } while (puVar12 != (uint16_t *)local_298);
            iVar23 = iVar23 + 4;
            p = p + 4;
            puVar5[6] = (ushort)uVar16 & 7 | (ushort)iVar6;
            puVar5 = puVar5 + 5;
          } while (iVar23 != iVar10 * 4 + 1);
          iVar6 = iVar10 * 5 + 6;
          p = pbVar21;
        }
                    /* Unresolved local var: int i@[???]
                       Unresolved local var: int j@[???]
                       Unresolved local var: int round@[???]
                       Unresolved local var: uint8_t crc@[???]
                       Unresolved local var: uint8_t[2] load@[???] */
        pContex->broadcastSize = iVar6;
        puVar24 = (undefined1 *)0x0;
        iVar10 = (int)len % 2 + (int)len / 2;
        iVar6 = 4;
        pContex->payloadMuticast[0][0] = '\0';
        pContex->payloadMuticast[1][0] = '\0';
        pContex->payloadMuticast[2][0] = '\0';
        pContex->payloadMuticast[3][0] = '\0';
        pContex->payloadMuticast[0][1] = '\x01';
        pContex->payloadMuticast[0][2] = '\x01';
        pContex->payloadMuticast[1][1] = '\x01';
        pContex->payloadMuticast[2][1] = '\x01';
        pContex->payloadMuticast[3][1] = '\x01';
        pContex->payloadMuticast[1][2] = '\x02';
        pContex->payloadMuticast[2][2] = '\x03';
        pContex->payloadMuticast[3][2] = '\x04';
        if (0 < iVar10) {
          iVar6 = 1;
          pauVar7 = pContex->payloadMuticast;
          do {
            puVar19 = puVar24 + 1;
            bVar26 = len != puVar19;
            puVar25 = puVar24;
            if (bVar26) {
              puVar25 = puVar24 + 2;
            }
            bVar1 = payload[(int)puVar24];
            if (bVar26) {
              puVar19 = &stack0xffffffd8 + (int)puVar19;
            }
            else {
              p = (byte *)0x0;
            }
            bVar3 = (byte)iVar6;
            iVar6 = iVar6 + 1;
            puVar24 = len;
            if (bVar26) {
              p = (byte *)(uint)(byte)puVar19[-0x264];
              puVar24 = puVar25;
            }
            pauVar7[4][1] = bVar1;
            pauVar7[4][2] = (uint8_t)p;
            pauVar7[4][0] = bVar3 & 0x3f | (byte)((((uint)p ^ (uint)bVar1) & 1) << 6);
            pauVar7 = pauVar7 + 1;
          } while (iVar6 != iVar10 + 1);
          iVar6 = iVar10 + 4;
        }
        pContex->muticastSize = iVar6;
        pContex->startTimeStamp = 1;
        timerReset(&pContex->startTimeStamp);
        iVar10 = (pContex->args).fullScaleTimer;
        pContex->totalTimeMS =
             (pContex->muticastSize + -4) * pArgs->bDataInterval * pArgs->bDataTimer +
             pArgs->bSyncTimer;
        __aeabi_idivmod(iVar10);
        iVar6 = 0;
        pContex->isEasyLinkOprating = 2;
        (pContex->args).fullScaleTimer = iVar10 - extraout_r1;
        goto LAB_0001a690;
      }
    }
  }
LAB_0001a710:
  iVar6 = -1;
LAB_0001a690:
  if (iVar15 == *piVar14) {
    return iVar6;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


