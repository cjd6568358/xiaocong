// app_step1  @ 000160d4


/* WARNING: Unknown calling convention -- yet parameter storage is locked */

int app_step1(void)

{
  cJSON *pcVar1;
  cJSON *pcVar2;
  uint8_t *puVar3;
  size_t sVar4;
  uint uVar5;
  ssize_t sVar6;
  uint8_t *iv;
  uint32_t length;
  cJSON *pcVar7;
  int iVar8;
  uint *puVar9;
  int iVar10;
  int iVar11;
  timeval local_21b8;
  fd_set fStack_21b0;
  char acStack_2130 [256];
  uint8_t auStack_2030 [1024];
  uint8_t auStack_1c30 [1024];
  char acStack_1830 [4096];
  uint8_t auStack_830 [2052];
  int iStack_2c;
  
                    /* Unresolved local var: uchar[2048] aesout@[DW_OP_breg13(sp): +6552]
                       Unresolved local var: uchar[4096] baseout@[DW_OP_breg13(sp): +2456]
                       Unresolved local var: uchar[1024] aesde@[DW_OP_breg13(sp): +1432]
                       Unresolved local var: uchar[1024] json@[DW_OP_breg13(sp): +408]
                       Unresolved local var: char[256] buf@[DW_OP_breg13(sp): +152]
                       Unresolved local var: cJSON * send@[???]
                       Unresolved local var: char * sendjson@[???]
                       Unresolved local var: int len@[???]
                       Unresolved local var: fd_set readfds@[???]
                       Unresolved local var: timeval tv@[???]
                       Unresolved local var: int n@[???] */
  iStack_2c = **(int **)(DAT_0001645c + 0x160f0);
  __aeabi_memclr8(auStack_830,0x800);
  __aeabi_memclr8(acStack_1830,0x1000);
  __aeabi_memclr8(auStack_1c30,0x400);
  __aeabi_memclr8(auStack_2030,0x400);
  pcVar1 = cJSON_CreateObject();
  pcVar2 = cJSON_CreateString((char *)(DAT_00016460 + 0x1612c));
  cJSON_AddItemToObject(pcVar1,(char *)(DAT_00016464 + 0x16138),pcVar2);
  pcVar2 = cJSON_CreateString(*(char **)(DAT_00016468 + 0x16140));
  cJSON_AddItemToObject(pcVar1,(char *)(DAT_0001646c + 0x1614e),pcVar2);
  pcVar2 = cJSON_CreateString(*(char **)(DAT_00016470 + 0x16156));
  cJSON_AddItemToObject(pcVar1,(char *)(DAT_00016474 + 0x16164),pcVar2);
  pcVar2 = cJSON_CreateString(*(char **)(DAT_00016478 + 0x1616c));
  cJSON_AddItemToObject(pcVar1,(char *)(DAT_0001647c + 0x1617a),pcVar2);
  pcVar2 = cJSON_CreateString(*(char **)(DAT_00016480 + 0x16182));
  cJSON_AddItemToObject(pcVar1,(char *)(DAT_00016484 + 0x16190),pcVar2);
  pcVar2 = cJSON_CreateString(*(char **)(DAT_00016488 + 0x16198));
  cJSON_AddItemToObject(pcVar1,(char *)(DAT_0001648c + 0x161a6),pcVar2);
  pcVar2 = cJSON_CreateString(*(char **)(DAT_00016490 + 0x161ae));
  cJSON_AddItemToObject(pcVar1,(char *)(DAT_00016494 + 0x161bc),pcVar2);
  puVar3 = (uint8_t *)cJSON_PrintUnformatted(pcVar1);
  __android_log_print(4,DAT_00016498 + 0x161d2,DAT_0001649c + 0x161d4,DAT_000164a0 + 0x161da,puVar3)
  ;
  sVar4 = strlen((char *)puVar3);
  uVar5 = AES128_CBC_encrypt_buffer
                    (auStack_830,puVar3,sVar4,*(uint8_t **)(DAT_000164a4 + 0x161ec),
                     *(uint8_t **)(DAT_000164a8 + 0x161ee));
  base64_encode(auStack_830,uVar5,acStack_1830);
  __aeabi_memclr8(acStack_2130,0x100);
                    /* Unresolved local var: size_t __i@[???] */
  __aeabi_memclr8(&fStack_21b0,0x80);
  puVar9 = *(uint **)(DAT_000164ac + 0x16224);
  uVar5 = *puVar9;
  fStack_21b0.fds_bits[uVar5 >> 5] = 1 << (uVar5 & 0x1f) | fStack_21b0.fds_bits[uVar5 >> 5];
  local_21b8.tv_sec = 0;
  local_21b8.tv_usec = 1;
  select(uVar5 + 1,&fStack_21b0,(fd_set *)0x0,(fd_set *)0x0,&local_21b8);
  uVar5 = *puVar9;
  if (((1 << (uVar5 & 0x1f) & fStack_21b0.fds_bits[uVar5 >> 5]) != 0) &&
     (sVar6 = recvfrom(uVar5,acStack_2130,0x100,0,(sockaddr *)0x0,(socklen_t *)0x0), sVar6 == -1)) {
    __android_log_print(4,DAT_000164b0 + 0x16286,DAT_000164b4 + 0x16288,DAT_000164b8 + 0x1628c,
                        0xffffffff);
    close(**(int **)(DAT_000164bc + 0x16296));
    create_network();
  }
  setskt();
  iVar8 = **(int **)(DAT_000164c0 + 0x162ae);
  sVar4 = strlen(acStack_1830);
  sVar6 = sendto(iVar8,acStack_1830,sVar4,0,*(sockaddr **)(DAT_000164c4 + 0x162c2),0x10);
  if (sVar6 < 0) {
    __android_log_print(4,DAT_000164d4 + 0x16418,DAT_000164d8 + 0x1641a,DAT_000164dc + 0x1641c);
  }
  else {
    if (puVar3 != (uint8_t *)0x0) {
      free(puVar3);
    }
    if (pcVar1 != (cJSON *)0x0) {
      cJSON_Delete(pcVar1);
    }
                    /* Unresolved local var: int x@[???] */
    iVar8 = 0;
                    /* Unresolved local var: int n@[???]
                       Unresolved local var: cJSON * root@[???] */
    puVar9 = *(uint **)(DAT_000164c8 + 0x162fc);
    iVar11 = DAT_000164e4 + 0x16308;
    puVar3 = *(uint8_t **)(DAT_000164cc + 0x1630c);
    iv = *(uint8_t **)(DAT_000164d0 + 0x16302);
    do {
                    /* Unresolved local var: size_t __i@[???] */
      __aeabi_memclr8(&fStack_21b0,0x80);
      uVar5 = *puVar9;
      fStack_21b0.fds_bits[uVar5 >> 5] = 1 << (uVar5 & 0x1f) | fStack_21b0.fds_bits[uVar5 >> 5];
      local_21b8.tv_sec = 0;
      local_21b8.tv_usec = 1000;
      select(uVar5 + 1,&fStack_21b0,(fd_set *)0x0,(fd_set *)0x0,&local_21b8);
      uVar5 = *puVar9;
      if ((1 << (uVar5 & 0x1f) & fStack_21b0.fds_bits[uVar5 >> 5]) != 0) {
        sVar6 = recvfrom(uVar5,acStack_2130,0x100,0,(sockaddr *)0x0,(socklen_t *)0x0);
        if (sVar6 < 0) {
          if (sVar6 == -1) break;
        }
        else {
          iVar10 = DAT_000164ec + 0x1638a;
          __android_log_print(4,iVar11,DAT_000164e8 + 0x16390,iVar10,acStack_2130);
          sVar4 = strlen(acStack_2130);
          length = base64_decode(acStack_2130,sVar4,auStack_1c30);
          AES128_CBC_decrypt_buffer(auStack_2030,auStack_1c30,length,puVar3,iv);
          __android_log_print(4,iVar11,DAT_000164f0 + 0x163c6,iVar10,auStack_2030);
          pcVar1 = cJSON_Parse((char *)auStack_2030);
          if (pcVar1 != (cJSON *)0x0) {
                    /* Unresolved local var: cJSON * mac@[???]
                       Unresolved local var: cJSON * product_id@[???] */
            pcVar2 = cJSON_GetObjectItem(pcVar1,(char *)(DAT_000164f4 + 0x163e4));
            pcVar7 = cJSON_GetObjectItem(pcVar1,(char *)(DAT_000164f8 + 0x163f0));
            if ((pcVar2 != (cJSON *)0x0) && (pcVar7 != (cJSON *)0x0)) {
              app_step2();
              sleep(1);
              softap_callback(pcVar2->valuestring,pcVar7->valuestring);
              cJSON_Delete(pcVar1);
              iVar8 = 2;
              goto LAB_00016420;
            }
          }
        }
      }
      iVar8 = iVar8 + 1;
    } while (iVar8 < 2000);
  }
  iVar8 = 0;
LAB_00016420:
  if (**(int **)(DAT_000164e0 + 0x1642a) == iStack_2c) {
    return iVar8;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


