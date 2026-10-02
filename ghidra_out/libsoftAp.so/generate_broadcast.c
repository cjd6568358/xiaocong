// generate_broadcast  @ 00015a68


int generate_broadcast(coap_pdu *msg_send,int type,char *productId,char *mac)

{
  long lVar1;
  size_t payload_len;
  undefined4 local_140;
  undefined4 uStack_13c;
  undefined4 uStack_138;
  undefined4 uStack_134;
  undefined1 local_130;
  int local_1c;
  
                    /* Unresolved local var: char[287] message@[DW_OP_breg13(sp): +8] */
  local_1c = **(int **)(DAT_00015b18 + 0x15a7c);
  coap_init_pdu(msg_send);
  coap_set_version(msg_send,COAP_V1);
  coap_set_type(msg_send,CT_CON);
  coap_set_code(msg_send,CC_GET);
  coap_set_mid(msg_send,1);
  lVar1 = lrand48();
  coap_set_token(msg_send,(longlong)lVar1,'\x02');
  coap_add_option(msg_send,0xb,(uint8_t *)(DAT_00015b1c + 0x15ac8),4);
  if (type == 0) {
    local_140 = *(undefined4 *)(DAT_00015b24 + 0x15ae6);
    uStack_13c = *(undefined4 *)(DAT_00015b24 + 0x15aea);
    uStack_138 = *(undefined4 *)(DAT_00015b24 + 0x15aee);
    uStack_134 = *(undefined4 *)(DAT_00015b24 + 0x15af2);
    local_130 = *(undefined1 *)(DAT_00015b24 + 0x15af6);
  }
  else {
    sprintf((char *)&local_140,(char *)(DAT_00015b20 + 0x15ad8),type,productId,mac);
  }
  payload_len = strlen((char *)&local_140);
  coap_set_payload(msg_send,(uint8_t *)&local_140,payload_len);
  if (**(int **)(DAT_00015b28 + 0x15b06) != local_1c) {
                    /* WARNING: Subroutine does not return */
    __stack_chk_fail();
  }
  return 0;
}


