// getScanPara  @ 000198e0


int getScanPara(char *input,scan_para *args)

{
  JSON_Value *value;
  JSON_Object *object;
  undefined4 extraout_r0;
  size_t sVar1;
  undefined4 extraout_r1;
  char *pcVar2;
  
                    /* Unresolved local var: JSON_Value * json@[???]
                       Unresolved local var: JSON_Object * root@[???]
                       Unresolved local var: char * productId@[???] */
  value = json_parse_string(input);
  if (value == (JSON_Value *)0x0) {
    return -1;
  }
  object = json_value_get_object(value);
  if (object != (JSON_Object *)0x0) {
    json_object_get_number(object,(char *)(DAT_00019988 + 0x1990c));
    pcVar2 = (char *)(DAT_0001998c + 0x19924);
    args->scanType = (int)(longlong)(double)CONCAT44(extraout_r1,extraout_r0);
    pcVar2 = json_object_get_string(object,pcVar2);
    if ((pcVar2 != (char *)0x0) && (sVar1 = strlen(pcVar2), sVar1 < 0xb)) {
      memcpy(args->productId,pcVar2,sVar1 + 1);
      json_value_free(value);
      return 0;
    }
    json_value_free(value);
    return -4;
  }
  json_value_free(value);
  return -2;
}


