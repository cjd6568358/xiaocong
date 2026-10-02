// isTimeOut  @ 00019a24


int isTimeOut(uint timestamp,uint timeout)

{
  int iVar1;
  int *piVar2;
  timespec monotime;
  
                    /* Unresolved local var: uint unix_time_value@[???]
                       Unresolved local var: uint ret@[???] */
                    /* Unresolved local var: ulong ret@[???] */
  piVar2 = *(int **)(DAT_00019a98 + 0x19a48);
  iVar1 = *piVar2;
  clock_gettime(4,(timespec *)&monotime);
  if (iVar1 == *piVar2) {
    return (timestamp + monotime.tv_sec * -1000 + timeout) - (uint)monotime.tv_nsec / 1000000 &
           0x80000000;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


