// broadcast  @ 000147f8


int broadcast(Network *pCtx,coap_pdu *msg_send)

{
  ssize_t sVar1;
  
                    /* Unresolved local var: int ret@[???] */
  sVar1 = sendto(pCtx->lanfd,msg_send->buf,msg_send->len,0,(sockaddr *)&pCtx->broadcast,0x10);
  if (sVar1 < 0) {
    perror((char *)(DAT_0001484c + 0x14844));
    sVar1 = -1;
  }
  return sVar1;
}


