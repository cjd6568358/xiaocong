// sdkInit  @ 00018bc4


void sdkInit(SdkContex *pCtx,char *host)

{
  Network *pNVar1;
  XConfig *pXVar2;
  
  memset(pCtx,0,0x44);
  pthread_mutex_init((pthread_mutex_t *)&pCtx->deviceListMutex,(pthread_mutexattr_t *)0x0);
  (pCtx->deviceListHandle).next = &pCtx->deviceListHandle;
  (pCtx->deviceListHandle).prev = &pCtx->deviceListHandle;
  pNVar1 = network_create(host);
  pCtx->pNet = pNVar1;
  pXVar2 = xconfig_init();
  pCtx->scanType = -1;
  pCtx->lanScanInterval = 2000;
  pCtx->pXConfig = pXVar2;
  timerReset(&pCtx->lanTimer);
  tcppipe(pCtx->cmdpipe);
  return;
}


