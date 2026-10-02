// setskt  @ 00016048


/* WARNING: Unknown calling convention -- yet parameter storage is locked */

int setskt(void)

{
  undefined4 *puVar1;
  char *__cp;
  undefined4 local_10;
  int local_c;
  
                    /* Unresolved local var: uint opt@[???] */
  local_c = **(int **)(DAT_000160c0 + 0x1605a);
  local_10 = 1;
  setsockopt(**(int **)(DAT_000160c4 + 0x1605c),1,6,&local_10,4);
  usleep(500000);
  puVar1 = *(undefined4 **)(DAT_000160c8 + 0x16088);
  __cp = *(char **)(DAT_000160cc + 0x1608a);
  puVar1[2] = 0;
  puVar1[3] = 0;
  *puVar1 = 0;
  puVar1[1] = 0;
  *puVar1 = 0x1a160002;
  inet_pton(2,__cp,puVar1 + 1);
  if (**(int **)(DAT_000160d0 + 0x160ae) != local_c) {
                    /* WARNING: Subroutine does not return */
    __stack_chk_fail();
  }
  return 0;
}


