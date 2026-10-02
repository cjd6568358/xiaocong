// uECC_compress  @ 00017444


void uECC_compress(uint8_t *public_key,uint8_t *compressed,uECC_Curve curve)

{
  int iVar1;
  int iVar2;
  
                    /* Unresolved local var: wordcount_t i@[???] */
  iVar1 = (int)curve->num_bytes;
  if (0 < iVar1) {
    iVar2 = 0;
    do {
      compressed[iVar2 + 1] = public_key[iVar2];
      iVar1 = (int)curve->num_bytes;
      iVar2 = (int)(char)((char)iVar2 + '\x01');
    } while (iVar2 < iVar1);
  }
  *compressed = public_key[iVar1 * 2 + -1] & 1 | 2;
  return;
}


