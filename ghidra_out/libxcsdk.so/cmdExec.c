// cmdExec  @ 00018ccc


char * cmdExec(SdkContex *pCtx,CmdType type,char *pCmd)

{
  undefined1 uVar1;
  void *pvVar2;
  int iVar3;
  char *pcVar4;
  int iVar5;
  int iVar6;
  CmdType type_00;
  undefined4 uVar7;
  undefined4 uVar8;
  int iVar9;
  undefined4 *puVar10;
  undefined4 *puVar11;
  int iVar12;
  int *piVar13;
  undefined4 *puVar14;
  void *args;
  scan_para para;
  
                    /* Unresolved local var: int error@[???] */
  iVar12 = DAT_00018f08 + 0x18ce8;
  pvVar2 = (void *)pCtx->workingCmd;
  piVar13 = *(int **)(iVar12 + DAT_00018f0c);
  iVar9 = *piVar13;
  if (pvVar2 == (void *)0x0) {
    args = pvVar2;
    switch(type) {
    case CMD_Scan:
      iVar5 = DAT_00018f14 + 0x18e00;
      __android_log_print(4,iVar5,DAT_00018f18 + 0x18e0c,2,pCmd);
      para.scanType = 0;
      para.productId[0] = '\0';
      para.productId[1] = '\0';
      para.productId[2] = '\0';
      para.productId[3] = '\0';
      para.productId[4] = '\0';
      para.productId[5] = '\0';
      para.productId[6] = '\0';
      para.productId[7] = '\0';
      para.productId[8] = '\0';
      para.productId[9] = '\0';
      para.productId[10] = '\0';
      para.productId[0xb] = '\0';
      iVar3 = getScanPara(pCmd,&para);
      __android_log_print(4,iVar5,DAT_00018f1c + 0x18e40,iVar3);
      if (iVar3 == 0) {
        pCtx->workingCmdArgs = &para;
        pCtx->workingCmd = CMD_Scan;
        iVar3 = cmdforward(pCtx,CMD_Scan,pCmd);
        if ((iVar3 == 0) &&
           (iVar6 = cmdwait(pCtx), iVar5 = DAT_00018f20, iVar3 = DAT_00018f10, iVar6 == 0)) {
          puVar10 = (undefined4 *)(DAT_00018f20 + 0x18e94);
          pCtx->workingCmd = CMD_NoCmd;
          uVar7 = *(undefined4 *)(iVar5 + 0x18e98);
          uVar8 = *(undefined4 *)(iVar5 + 0x18e9c);
          puVar14 = *(undefined4 **)(iVar12 + iVar3);
          uVar1 = *(undefined1 *)(iVar5 + 0x18ea0);
          puVar11 = (undefined4 *)*puVar14;
          *puVar11 = *puVar10;
          puVar11[1] = uVar7;
          pcVar4 = (char *)*puVar14;
          puVar11[2] = uVar8;
          *(undefined1 *)(puVar11 + 3) = uVar1;
          goto LAB_00018d60;
        }
      }
      break;
    case CMD_GetLocalKey:
      para.scanType = 0;
      para.productId[0] = '\0';
      para.productId[1] = '\0';
      para.productId[2] = '\0';
      para.productId[3] = '\0';
      para.productId[4] = '\0';
      para.productId[5] = '\0';
      para.productId[6] = '\0';
      para.productId[7] = '\0';
      para.productId[8] = '\0';
      para.productId[9] = '\0';
      para.productId[10] = '\0';
      para.productId[0xb] = '\0';
      iVar3 = getGenKeyPara(pCmd,(genkey_para *)&para);
      if (iVar3 == 0) {
        type_00 = CMD_GetLocalKey;
LAB_00018da8:
        pCtx->workingCmdArgs = &para;
        pCtx->workingCmd = type_00;
        iVar3 = cmdforward(pCtx,type_00,pCmd);
joined_r0x00018d58:
        if ((iVar3 == 0) && (iVar5 = cmdwait(pCtx), iVar3 = DAT_00018f10, iVar5 == 0)) {
          pCtx->workingCmd = CMD_NoCmd;
          pcVar4 = (char *)**(undefined4 **)(iVar12 + iVar3);
          goto LAB_00018d60;
        }
      }
      break;
    case CMD_Snapshot:
      para.scanType = 0;
      para.productId[0] = '\0';
      para.productId[1] = '\0';
      para.productId[2] = '\0';
      para.productId[3] = '\0';
      para.productId[4] = '\0';
      para.productId[5] = '\0';
      para.productId[6] = '\0';
      para.productId[7] = '\0';
      para.productId[8] = '\0';
      para.productId[9] = '\0';
      para.productId[10] = '\0';
      para.productId[0xb] = '\0';
      iVar3 = getScanPara(pCmd,&para);
      if (iVar3 == 0) {
        type_00 = CMD_Snapshot;
        goto LAB_00018da8;
      }
      break;
    case CMD_Control:
      iVar3 = getScanPara(pCmd,&para);
      if (iVar3 == 0) {
        pCtx->workingCmd = CMD_Control;
        pCtx->workingCmdArgs = &args;
        iVar3 = cmdforward(pCtx,CMD_Control,pCmd);
        goto joined_r0x00018d58;
      }
    }
  }
  pcVar4 = (char *)0x0;
LAB_00018d60:
  if (iVar9 == *piVar13) {
    return pcVar4;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


