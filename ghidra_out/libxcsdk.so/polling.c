// polling  @ 00018ff0


int polling(SdkContex *pCtx)

{
  list_head *plVar1;
  device_list *pdVar2;
  device_list *pdVar3;
  int iVar4;
  coap_error cVar5;
  list_head *plVar6;
  char *__ptr;
  int iVar7;
  int *piVar8;
  list_head *plVar9;
  int iVar10;
  int *piVar11;
  device_list *pdVar12;
  list_head *plVar13;
  void *__s2;
  coap_payload local_878;
  coap_pdu msg_recv;
  coap_pdu msg_send;
  coap_option opt;
  char str [13];
  undefined3 uStack_83f;
  char peer [20];
  uint8_t msg_recv_buf [1024];
  uint8_t msg_send_buf [1024];
  
  piVar11 = *(int **)(DAT_00019474 + 0x1900c);
  iVar7 = *piVar11;
  if (pCtx->pNet == (network *)0x0) {
    iVar10 = -1;
  }
  else {
    msg_recv.buf = msg_recv_buf;
    msg_recv.max = 0x400;
    iVar10 = 0;
    msg_send.max = 0x400;
    msg_send.buf = msg_send_buf;
    msg_recv.len = 0;
    msg_send.len = 0;
    easylinkLoop(pCtx->pXConfig);
    iVar4 = isTimeOut(pCtx->lanTimer,pCtx->lanScanInterval);
    if ((iVar4 == 0) || (pCtx->scanType == -1)) {
                    /* Unresolved local var: int ret@[???] */
      iVar10 = wait_packet(pCtx->pNet,pCtx->cmdpipe,&msg_recv,peer,1);
      if (iVar10 == 1) {
        cVar5 = coap_validate_pkt(&msg_recv);
        if (cVar5 == CE_NONE) {
                    /* Unresolved local var: coap_payload p@[???]
                       Unresolved local var: device_list * newDevice@[???]
                       Unresolved local var: int error@[???] */
          coap_get_option_by_num(&opt,&msg_recv,CON_LOCATION_PATH,'\0');
          iVar10 = DAT_0001948c;
          coap_get_payload(&local_878,&msg_recv);
          __android_log_print(4,iVar10 + 0x1921c,DAT_00019490 + 0x19234,peer,local_878.len,
                              local_878.val);
          pdVar12 = malloc(0xa4);
          memset(pdVar12,0,0xa4);
          iVar4 = json2device(pdVar12,(char *)local_878.val);
          if (iVar4 == 0) {
                    /* Unresolved local var: device_list * tmp@[???]
                       Unresolved local var: list_head * pos@[???]
                       Unresolved local var: list_head * q@[???] */
            plVar13 = &pCtx->deviceListHandle;
            plVar9 = plVar13->next->next;
            plVar6 = plVar13->next;
            while (plVar1 = plVar9, plVar6 != plVar13) {
              iVar10 = memcmp(plVar6 + 2,pdVar12->DeviceId,0x21);
              if (iVar10 == 0) {
                free(pdVar12);
                __android_log_print(4,DAT_00019494 + 0x192c4,DAT_00019498 + 0x192c8);
                goto LAB_000190a0;
              }
              plVar9 = plVar1->next;
              plVar6 = plVar1;
            }
                    /* Unresolved local var: char * mallocjson@[???] */
            __android_log_print(4,DAT_000194b8 + 0x19430,DAT_000194bc + 0x19434);
            plVar6 = (pCtx->deviceListHandle).next;
            plVar6->prev = &pdVar12->list;
            (pdVar12->list).next = plVar6;
            (pdVar12->list).prev = plVar13;
            (pCtx->deviceListHandle).next = &pdVar12->list;
            __ptr = device2mallocjson(pdVar12);
            (*pCtx->callback)(pCtx->callbackArgs,EVT_DeviceNew,__ptr);
            json_free_serialized_string(__ptr);
          }
          else {
            free(pdVar12);
            __android_log_print(4,iVar10 + 0x1921c,DAT_000194b4 + 0x19410);
          }
        }
        else {
          __android_log_print(4,DAT_000194a8 + 0x193a0,DAT_000194ac + 0x193a8,msg_recv.len);
          hex_dump(msg_recv.buf,msg_recv.len);
        }
      }
      else if (iVar10 == 2) {
        msg_recv.buf[msg_recv.len] = '\0';
        if (pCtx->workingCmd == CMD_Scan) {
                    /* Unresolved local var: scan_para * pArgs@[???] */
                    /* Unresolved local var: device_list * tmp@[???]
                       Unresolved local var: list_head * pos@[???]
                       Unresolved local var: list_head * q@[???] */
          __android_log_print(4,DAT_0001949c + 0x192e4,DAT_000194a0 + 0x192ec,msg_recv.buf);
          piVar8 = pCtx->workingCmdArgs;
          pCtx->scanType = *piVar8;
          iVar10 = piVar8[2];
          iVar4 = piVar8[3];
          *(int *)pCtx->scanProductId = piVar8[1];
          *(int *)(pCtx->scanProductId + 4) = iVar10;
          *(int *)(pCtx->scanProductId + 8) = iVar4;
          pthread_mutex_lock((pthread_mutex_t *)&pCtx->deviceListMutex);
          plVar6 = (pCtx->deviceListHandle).next;
          plVar9 = plVar6->next;
          while (plVar13 = plVar9, &pCtx->deviceListHandle != plVar6) {
            plVar9 = plVar6->prev;
            plVar13->prev = plVar9;
            plVar9->next = plVar13;
            free(plVar6);
            plVar9 = plVar13->next;
            plVar6 = plVar13;
          }
          pthread_mutex_unlock((pthread_mutex_t *)&pCtx->deviceListMutex);
          str._0_4_ = *(undefined4 *)(DAT_000194a4 + 0x1936c);
          str._4_4_ = *(undefined4 *)(DAT_000194a4 + 0x19370);
          str._8_4_ = *(undefined4 *)(DAT_000194a4 + 0x19374);
          stack0xfffff7c0 = CONCAT31(uStack_83f,(char)*(undefined4 *)(DAT_000194a4 + 0x19378));
          send(pCtx->cmdpipe[1],str,0xc,0);
        }
        else if (pCtx->workingCmd == CMD_GetLocalKey) {
                    /* Unresolved local var: genkey_para * pArgs@[???]
                       Unresolved local var: device_list * tmp@[???] */
                    /* Unresolved local var: device_list * tmp@[???]
                       Unresolved local var: list_head * pos@[???]
                       Unresolved local var: list_head * q@[???] */
          __android_log_print(4,DAT_00019480 + 0x19140,DAT_00019484 + 0x19148,msg_recv.buf);
          pdVar12 = (device_list *)(pCtx->deviceListHandle).next;
          __s2 = pCtx->workingCmdArgs;
          pdVar3 = (device_list *)(pdVar12->list).next;
          while (pdVar2 = pdVar3, pdVar12 != (device_list *)&pCtx->deviceListHandle) {
            iVar10 = memcmp(pdVar12->DeviceId,__s2,0x21);
            if (iVar10 == 0) {
              generate_getlocalkey(&msg_send,pdVar12);
              broadcast(pCtx->pNet,&msg_send);
              str._0_4_ = *(undefined4 *)(DAT_00019488 + 0x191b8);
              str._4_4_ = *(undefined4 *)(DAT_00019488 + 0x191bc);
              str._8_4_ = *(undefined4 *)(DAT_00019488 + 0x191c0);
              stack0xfffff7c0 = CONCAT31(uStack_83f,(char)*(undefined4 *)(DAT_00019488 + 0x191c4));
              send(pCtx->cmdpipe[1],str,0xc,0);
              goto LAB_000190a0;
            }
            pdVar3 = (device_list *)(pdVar2->list).next;
            pdVar12 = pdVar2;
          }
          str._0_4_ = *(undefined4 *)(DAT_000194b0 + 0x193c8);
          str._4_4_ = *(undefined4 *)(DAT_000194b0 + 0x193cc);
          str._8_4_ = *(undefined4 *)(DAT_000194b0 + 0x193d0);
          register0x0000002c = *(undefined4 *)(DAT_000194b0 + 0x193d4);
          send(pCtx->cmdpipe[1],str,0x12,0);
        }
      }
LAB_000190a0:
      iVar10 = 0;
    }
    else {
      timerReset(&pCtx->lanTimer);
      __android_log_print(4,DAT_00019478 + 0x190dc,DAT_0001947c + 0x190e0);
      generate_broadcast(&msg_send,pCtx->scanType,pCtx->scanProductId);
      broadcast(pCtx->pNet,&msg_send);
    }
  }
  if (iVar7 == *piVar11) {
    return iVar10;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


