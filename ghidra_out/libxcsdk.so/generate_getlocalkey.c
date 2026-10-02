// generate_getlocalkey  @ 00014d4c


int generate_getlocalkey(coap_pdu *msg_send,device_list *pdevice)

{
  int iVar1;
  long lVar2;
  
  iVar1 = DAT_00014df8;
  coap_init_pdu(msg_send);
  coap_set_version(msg_send,COAP_V1);
  coap_set_type(msg_send,CT_CON);
  coap_set_code(msg_send,CC_GET);
  coap_set_mid(msg_send,1);
  lVar2 = lrand48();
  coap_set_token(msg_send,(longlong)lVar2,'\x02');
  coap_add_option(msg_send,0xb,(uint8_t *)(iVar1 + 0x14d70),6);
  coap_add_option(msg_send,0xf,(uint8_t *)(iVar1 + 0x14d70),6);
  coap_set_payload(msg_send,(uint8_t *)(DAT_00014dfc + 0x14dec),0xe);
  return 0;
}


