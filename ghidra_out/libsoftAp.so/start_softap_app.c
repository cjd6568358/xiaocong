// start_softap_app  @ 000166c8


void start_softap_app(char *addr,char *ssid,char *password,char *clientId,char *check,char *domain,
                     char *crt)

{
  int iVar1;
  int iVar2;
  
                    /* Unresolved local var: int s1@[???] */
  strcpy(*(char **)(DAT_000167a4 + 0x166dc),addr);
  strcpy(*(char **)(DAT_000167a8 + 0x166e8),ssid);
  strcpy(*(char **)(DAT_000167ac + 0x166f4),password);
  strcpy(*(char **)(DAT_000167b0 + 0x16700),clientId);
  strcpy(*(char **)(DAT_000167b4 + 0x1670c),check);
  strcpy(*(char **)(DAT_000167b8 + 0x16718),domain);
  strcpy(*(char **)(DAT_000167bc + 0x16724),crt);
  create_ecdh_key();
  create_network();
  setskt();
  usleep(50000);
  iVar1 = app_step0();
  __android_log_print(4,DAT_000167c0 + 0x1674c,DAT_000167c4 + 0x16750,iVar1);
  if (iVar1 == 1) {
                    /* Unresolved local var: int s2@[???] */
    iVar1 = app_step1();
    __android_log_print(4,DAT_000167d0 + 0x1676e,DAT_000167d4 + 0x16772,iVar1);
    if (iVar1 != 0) {
      return;
    }
    usleep(500000);
    iVar1 = DAT_000167d8 + 0x16798;
    iVar2 = DAT_000167dc + 0x1679a;
  }
  else {
    usleep(500000);
    iVar1 = DAT_000167c8 + 0x16788;
    iVar2 = DAT_000167cc + 0x1678a;
  }
                    /* WARNING: Could not recover jumptable at 0x0001bbf0. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  (*(code *)(DAT_0001bbf4 + 0x1bbf8))(iVar1,iVar2);
  return;
}


