// generate_broadcast  @ 00014c30


int generate_broadcast(coap_pdu *msg_send,int type,char *productId)

{
  long lVar1;
  size_t payload_len;
  int iVar2;
  int *piVar3;
  char message [42];
  
  piVar3 = *(int **)(DAT_00014d3c + 0x14c50);
  iVar2 = *piVar3;
  coap_init_pdu(msg_send);
  coap_set_version(msg_send,COAP_V1);
  coap_set_type(msg_send,CT_CON);
  coap_set_code(msg_send,CC_GET);
  coap_set_mid(msg_send,1);
  lVar1 = lrand48();
  coap_set_token(msg_send,(longlong)lVar1,'\x02');
  coap_add_option(msg_send,0xb,(uint8_t *)(DAT_00014d40 + 0x14cb8),4);
  if (type == 0) {
    message._0_4_ = *(undefined4 *)(DAT_00014d48 + 0x14d24);
    message._4_4_ = *(undefined4 *)(DAT_00014d48 + 0x14d28);
    message._8_4_ = *(undefined4 *)(DAT_00014d48 + 0x14d2c);
    message._12_4_ = *(undefined4 *)(DAT_00014d48 + 0x14d30);
    message[0x10] = (char)*(undefined4 *)(DAT_00014d48 + 0x14d34);
  }
  else {
    sprintf(message,(char *)(DAT_00014d44 + 0x14cdc),type,productId);
  }
  payload_len = strlen(message);
  coap_set_payload(msg_send,(uint8_t *)message,payload_len);
  if (iVar2 == *piVar3) {
    return 0;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


