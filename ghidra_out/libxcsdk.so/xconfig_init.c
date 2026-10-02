// xconfig_init  @ 00019c44


/* WARNING: Unknown calling convention -- yet parameter storage is locked */

XConfig * xconfig_init(void)

{
  XConfig *__s;
  
                    /* Unresolved local var: XConfig * pCtx@[???] */
  __s = malloc(0x194);
  if (__s != (XConfig *)0x0) {
    memset(__s,0,0x194);
  }
  return __s;
}


