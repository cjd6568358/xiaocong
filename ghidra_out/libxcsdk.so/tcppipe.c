// tcppipe  @ 00014ac8


int tcppipe(int *fildes)

{
  int __fd;
  int iVar1;
  int iVar2;
  int iVar3;
  int *piVar4;
  int iVar5;
  int namelen;
  sockaddr_in name;
  
                    /* Unresolved local var: int tcp1@[???]
                       Unresolved local var: int tcp2@[???]
                       Unresolved local var: int tcp@[???] */
  piVar4 = *(int **)(DAT_00014c2c + 0x14af0);
  iVar5 = *piVar4;
  name.__pad[0] = '\0';
  name.__pad[1] = '\0';
  name.__pad[2] = '\0';
  name.__pad[3] = '\0';
  name.__pad[4] = '\0';
  name.__pad[5] = '\0';
  name.__pad[6] = '\0';
  name.__pad[7] = '\0';
  name.sin_addr.s_addr = 0x100007f;
  name.sin_family = 2;
  name.sin_port = 0;
  namelen = 0x10;
  __fd = socket(2,1,0);
  if (__fd == -1) goto LAB_00014bd8;
  iVar1 = bind(__fd,(sockaddr *)&name,namelen);
  if ((((iVar1 == -1) || (iVar1 = listen(__fd,5), iVar1 == -1)) ||
      (iVar1 = getsockname(__fd,(sockaddr *)&name,(socklen_t *)&namelen), iVar1 == -1)) ||
     (iVar1 = socket(2,1,0), iVar1 == -1)) {
    iVar1 = -1;
LAB_00014bf4:
    close(__fd);
    if (iVar1 != -1) goto LAB_00014c04;
  }
  else {
    iVar2 = connect(iVar1,(sockaddr *)&name,namelen);
    if ((iVar2 == -1) || (iVar2 = accept(__fd,(sockaddr *)&name,(socklen_t *)&namelen), iVar2 == -1)
       ) goto LAB_00014bf4;
    iVar3 = close(__fd);
    if (iVar3 != -1) {
      __fd = 0;
      *fildes = iVar1;
      fildes[1] = iVar2;
      goto LAB_00014bd8;
    }
    close(__fd);
    close(iVar2);
LAB_00014c04:
    close(iVar1);
  }
  __fd = -1;
LAB_00014bd8:
  if (iVar5 == *piVar4) {
    return __fd;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


