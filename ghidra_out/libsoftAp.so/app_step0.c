// app_step0  @ 00015c38


/* WARNING: Unknown calling convention -- yet parameter storage is locked */

int app_step0(void)

{
  cJSON *pcVar1;
  cJSON *pcVar2;
  char *pcVar3;
  uint uVar4;
  ssize_t sVar5;
  size_t sVar6;
  int iVar7;
  int iVar8;
  uint *puVar9;
  int iVar10;
  int iVar11;
  timeval local_250;
  fd_set fStack_248;
  char acStack_1c8 [256];
  uint8_t local_c8 [64];
  char acStack_88 [96];
  int local_28;
  
                    /* Unresolved local var: char[90] public_base64@[DW_OP_breg13(sp): +464]
                       Unresolved local var: uint8_t[64] public_other@[DW_OP_breg13(sp): +400]
                       Unresolved local var: char[256] buf@[DW_OP_breg13(sp): +144]
                       Unresolved local var: cJSON * send@[???]
                       Unresolved local var: char * pub_base64@[???]
                       Unresolved local var: fd_set readfds@[???]
                       Unresolved local var: timeval tv@[???]
                       Unresolved local var: int n@[???] */
  local_28 = **(int **)(DAT_00015f44 + 0x15c4c);
  __aeabi_memclr8(acStack_88,0x5a);
  __aeabi_memclr8(local_c8,0x40);
  base64_encode(*(uchar **)(DAT_00015f48 + 0x15c68),0x40,acStack_88);
  pcVar1 = cJSON_CreateObject();
  pcVar2 = cJSON_CreateString((char *)(DAT_00015f4c + 0x15c78));
  cJSON_AddItemToObject(pcVar1,(char *)(DAT_00015f50 + 0x15c84),pcVar2);
  pcVar2 = cJSON_CreateString(acStack_88);
  cJSON_AddItemToObject(pcVar1,(char *)(DAT_00015f54 + 0x15c96),pcVar2);
  pcVar3 = cJSON_PrintUnformatted(pcVar1);
  __android_log_print(4,DAT_00015f58 + 0x15cac,DAT_00015f5c + 0x15cae,DAT_00015f60 + 0x15cb4,pcVar3)
  ;
  __aeabi_memclr8(acStack_1c8,0x100);
                    /* Unresolved local var: size_t __i@[???] */
  __aeabi_memclr8(&fStack_248,0x80);
  puVar9 = *(uint **)(DAT_00015f64 + 0x15cd6);
  uVar4 = *puVar9;
  fStack_248.fds_bits[uVar4 >> 5] = 1 << (uVar4 & 0x1f) | fStack_248.fds_bits[uVar4 >> 5];
  local_250.tv_sec = 0;
  local_250.tv_usec = 1;
  select(uVar4 + 1,&fStack_248,(fd_set *)0x0,(fd_set *)0x0,&local_250);
  uVar4 = *puVar9;
  if (((1 << (uVar4 & 0x1f) & fStack_248.fds_bits[uVar4 >> 5]) != 0) &&
     (sVar5 = recvfrom(uVar4,acStack_1c8,0x100,0,(sockaddr *)0x0,(socklen_t *)0x0), sVar5 == -1)) {
    __android_log_print(4,DAT_00015f68 + 0x15d38,DAT_00015f6c + 0x15d3a,DAT_00015f70 + 0x15d3e,
                        0xffffffff);
    close(**(int **)(DAT_00015f74 + 0x15d48));
    create_network();
  }
  setskt();
  iVar8 = **(int **)(DAT_00015f78 + 0x15d5c);
  sVar6 = strlen(pcVar3);
  sVar5 = sendto(iVar8,pcVar3,sVar6,0,*(sockaddr **)(DAT_00015f7c + 0x15d70),0x10);
  if (sVar5 < 0) {
    __android_log_print(4,DAT_00015fc0 + 0x15e7c,DAT_00015fc4 + 0x15e7e,DAT_00015fc8 + 0x15e80);
  }
  else {
    if (pcVar1 != (cJSON *)0x0) {
      cJSON_Delete(pcVar1);
    }
    if (pcVar3 != (char *)0x0) {
      free(pcVar3);
    }
                    /* Unresolved local var: int x@[???] */
    iVar8 = 0;
    puVar9 = *(uint **)(DAT_00015f80 + 0x15daa);
    do {
                    /* Unresolved local var: size_t __i@[???] */
      __aeabi_memclr8(&fStack_248,0x80);
      uVar4 = *puVar9;
      local_250.tv_sec = 0;
      fStack_248.fds_bits[uVar4 >> 5] = 1 << (uVar4 & 0x1f) | fStack_248.fds_bits[uVar4 >> 5];
      local_250.tv_usec = 1000;
      select(uVar4 + 1,&fStack_248,(fd_set *)0x0,(fd_set *)0x0,&local_250);
      uVar4 = *puVar9;
      if ((1 << (uVar4 & 0x1f) & fStack_248.fds_bits[uVar4 >> 5]) != 0) {
        sVar5 = recvfrom(uVar4,acStack_1c8,0x100,0,(sockaddr *)0x0,(socklen_t *)0x0);
        if (sVar5 < 1) {
          if (sVar5 == -1) break;
        }
        else {
                    /* Unresolved local var: cJSON * root@[???] */
          __android_log_print(4,DAT_00015fd0 + 0x15e1e,DAT_00015fd4 + 0x15e20,DAT_00015fd8 + 0x15e24
                              ,acStack_1c8);
          pcVar1 = cJSON_Parse(acStack_1c8);
          if (pcVar1 != (cJSON *)0x0) {
                    /* Unresolved local var: cJSON * type@[???] */
            pcVar2 = cJSON_GetObjectItem(pcVar1,(char *)(DAT_00015fdc + 0x15e3c));
                    /* Unresolved local var: cJSON * pubkey@[???] */
            if (((pcVar2 != (cJSON *)0x0) &&
                (iVar7 = strcmp(pcVar2->valuestring,(char *)(DAT_00015fe0 + 0x15e48)), iVar7 == 0))
               && (pcVar2 = cJSON_GetObjectItem(pcVar1,(char *)(DAT_00015fe4 + 0x15e54)),
                  pcVar2 != (cJSON *)0x0)) {
              pcVar3 = pcVar2->valuestring;
              sVar6 = strlen(pcVar3);
              base64_decode(pcVar3,sVar6,local_c8);
              __android_log_print(4,DAT_00015f84 + 0x15ec0,DAT_00015f88 + 0x15ec2,
                                  DAT_00015f8c + 0x15ec4);
                    /* Unresolved local var: uint i@[???] */
              iVar10 = 0;
              iVar8 = DAT_00015f90 + 0x15ed0;
              iVar7 = DAT_00015f94 + 0x15ed2;
              do {
                __android_log_print(4,iVar8,iVar7,local_c8[iVar10]);
                iVar10 = iVar10 + 1;
              } while (iVar10 != 0x40);
              __android_log_print(4,DAT_00015f98 + 0x15eee,DAT_00015f9c + 0x15ef0);
              uECC_shared_secret(local_c8,*(uint8_t **)(DAT_00015fa4 + 0x15efe),
                                 *(uint8_t **)(DAT_00015fa8 + 0x15f02),
                                 (uECC_Curve)**(undefined4 **)(DAT_00015fa0 + 0x15efa));
                    /* Unresolved local var: uint i@[???] */
              iVar10 = 0;
              iVar11 = DAT_00015fb0 + 0x15f1c;
              iVar8 = *(int *)(DAT_00015fac + 0x15f18);
              iVar7 = DAT_00015fb4 + 0x15f20;
              do {
                __android_log_print(4,iVar11,iVar7,*(undefined1 *)(iVar8 + iVar10));
                iVar10 = iVar10 + 1;
              } while (iVar10 != 0x20);
              __android_log_print(4,DAT_00015fb8 + 0x15f3a,DAT_00015fbc + 0x15f3c);
              iVar8 = 1;
              goto LAB_00015e84;
            }
            cJSON_Delete(pcVar1);
          }
        }
      }
      iVar8 = iVar8 + 1;
    } while (iVar8 < 2000);
  }
  iVar8 = 0;
LAB_00015e84:
  if (**(int **)(DAT_00015fcc + 0x15e8c) == local_28) {
    return iVar8;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


