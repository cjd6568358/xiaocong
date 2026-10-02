// cmdforward  @ 00018c2c


int cmdforward(SdkContex *pCtx,CmdType type,char *pCmd)

{
  size_t __n;
  ssize_t sVar1;
  
                    /* Unresolved local var: int error@[???]
                       Unresolved local var: int len@[???] */
  __n = strlen(pCmd);
  sVar1 = send(pCtx->cmdpipe[0],pCmd,__n,0);
  if (sVar1 == -1) {
                    /* Unresolved local var: int error@[DW_OP_reg3(r3)]
                       Unresolved local var: int len@[???] */
    __errno();
  }
  return 0;
}


