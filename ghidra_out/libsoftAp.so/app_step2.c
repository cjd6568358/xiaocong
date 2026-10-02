// app_step2  @ 000164fc


/* WARNING: Unknown calling convention -- yet parameter storage is locked */

int app_step2(void)

{
  cJSON *object;
  cJSON *item;
  uint8_t *input;
  size_t sVar1;
  uint uVar2;
  ssize_t sVar3;
  uint *puVar4;
  int __fd;
  timeval local_19b0;
  fd_set fStack_19a8;
  undefined1 auStack_1928 [256];
  char acStack_1828 [4096];
  uint8_t auStack_828 [2048];
  int iStack_28;
  
                    /* Unresolved local var: uchar[2048] aesout@[DW_OP_breg13(sp): +4496]
                       Unresolved local var: uchar[4096] baseout@[DW_OP_breg13(sp): +400]
                       Unresolved local var: char[256] buf@[DW_OP_breg13(sp): +144]
                       Unresolved local var: cJSON * send@[???]
                       Unresolved local var: char * sendjson@[???]
                       Unresolved local var: int len@[???]
                       Unresolved local var: fd_set readfds@[???]
                       Unresolved local var: timeval tv@[???] */
  iStack_28 = **(int **)(DAT_0001668c + 0x16518);
  __aeabi_memclr8(auStack_828,0x800);
  __aeabi_memclr8(acStack_1828,0x1000);
  object = cJSON_CreateObject();
  item = cJSON_CreateString((char *)(DAT_00016690 + 0x1653e));
  cJSON_AddItemToObject(object,(char *)(DAT_00016694 + 0x1654a),item);
  input = (uint8_t *)cJSON_PrintUnformatted(object);
  __android_log_print(4,DAT_00016698 + 0x16560,DAT_0001669c + 0x16562,DAT_000166a0 + 0x16568,input);
  sVar1 = strlen((char *)input);
  uVar2 = AES128_CBC_encrypt_buffer
                    (auStack_828,input,sVar1,*(uint8_t **)(DAT_000166a4 + 0x1657a),
                     *(uint8_t **)(DAT_000166a8 + 0x1657c));
  base64_encode(auStack_828,uVar2,acStack_1828);
  __aeabi_memclr8(auStack_1928,0x100);
                    /* Unresolved local var: size_t __i@[???] */
  __aeabi_memclr8(&fStack_19a8,0x80);
  puVar4 = *(uint **)(DAT_000166ac + 0x165b2);
  uVar2 = *puVar4;
  fStack_19a8.fds_bits[uVar2 >> 5] = 1 << (uVar2 & 0x1f) | fStack_19a8.fds_bits[uVar2 >> 5];
  local_19b0.tv_sec = 0;
  local_19b0.tv_usec = 1;
  select(uVar2 + 1,&fStack_19a8,(fd_set *)0x0,(fd_set *)0x0,&local_19b0);
  uVar2 = *puVar4;
  if (((1 << (uVar2 & 0x1f) & fStack_19a8.fds_bits[uVar2 >> 5]) != 0) &&
     (sVar3 = recvfrom(uVar2,auStack_1928,0x100,0,(sockaddr *)0x0,(socklen_t *)0x0), sVar3 == -1)) {
    create_network();
  }
  setskt();
  __fd = **(int **)(DAT_000166b0 + 0x16616);
  sVar1 = strlen(acStack_1828);
  uVar2 = sendto(__fd,acStack_1828,sVar1,0,*(sockaddr **)(DAT_000166b4 + 0x1662a),0x10);
  if (0x7fffffff < uVar2) {
    __android_log_print(4,DAT_000166b8 + 0x1664a,DAT_000166bc + 0x1664c,DAT_000166c0 + 0x16650,
                        0xffffffff);
  }
  if (input != (uint8_t *)0x0) {
    free(input);
  }
  if (object != (cJSON *)0x0) {
    cJSON_Delete(object);
  }
  if (**(int **)(DAT_000166c4 + 0x16676) != iStack_28) {
                    /* WARNING: Subroutine does not return */
    __stack_chk_fail();
  }
  return 0;
}


