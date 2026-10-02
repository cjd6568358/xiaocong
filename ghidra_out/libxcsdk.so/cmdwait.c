// cmdwait  @ 00018c6c


int cmdwait(SdkContex *pCtx)

{
  int iVar1;
  ssize_t sVar2;
  int *piVar3;
  
                    /* Unresolved local var: int ret@[???]
                       Unresolved local var: int len@[???] */
  iVar1 = wait_socket(pCtx->cmdpipe[0],2000);
  if (-1 < iVar1) {
    piVar3 = *(int **)(DAT_00018cc8 + 0x18c9c);
    sVar2 = recv(pCtx->cmdpipe[0],(void *)*piVar3,piVar3[2],0);
    *(undefined1 *)(*piVar3 + sVar2) = 0;
    piVar3[1] = sVar2;
    return 0;
  }
  return -5;
}


