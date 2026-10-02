// create_network  @ 00015fe8


/* WARNING: Unknown calling convention -- yet parameter storage is locked */

void create_network(void)

{
  int iVar1;
  
  __android_log_print(4,DAT_0001602c + 0x15ff8,DAT_00016030 + 0x15ffa,DAT_00016034 + 0x15ffc);
  iVar1 = socket(2,2,0);
  **(int **)(DAT_00016038 + 0x16012) = iVar1;
  if (-1 < iVar1) {
    return;
  }
  __android_log_print(4,DAT_0001603c + 0x16024,DAT_00016040 + 0x16026,DAT_00016044 + 0x16028);
  return;
}


