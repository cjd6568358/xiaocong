// wait_socket  @ 00014a08


int wait_socket(int fd,int msTimeout)

{
  int iVar1;
  __kernel_suseconds_t *p_Var2;
  int *piVar3;
  int iVar4;
  timeval selectTimeOut;
  fd_set readfds;
  
                    /* Unresolved local var: int ret@[???] */
  p_Var2 = &selectTimeOut.tv_usec;
                    /* Unresolved local var: size_t __i@[???] */
  piVar3 = *(int **)(DAT_00014ac4 + 0x14a38);
  selectTimeOut.tv_sec = msTimeout / 1000;
  iVar4 = *piVar3;
  selectTimeOut.tv_usec = (msTimeout % 1000) * 1000;
  do {
    p_Var2 = p_Var2 + 1;
    *p_Var2 = 0;
  } while ((ulong *)p_Var2 != readfds.fds_bits + 0x1f);
  readfds.fds_bits[(uint)fd >> 5] = readfds.fds_bits[(uint)fd >> 5] | 1 << (fd & 0x1fU);
  iVar1 = select(fd + 1,(fd_set *)&readfds,(fd_set *)0x0,(fd_set *)0x0,(timeval *)&selectTimeOut);
  if (iVar4 == *piVar3) {
    return iVar1;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


