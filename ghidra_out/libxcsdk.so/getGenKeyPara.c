// getGenKeyPara  @ 00019990


int getGenKeyPara(char *input,genkey_para *args)

{
  JSON_Value *value;
  JSON_Object *object;
  char *__s;
  size_t sVar1;
  
                    /* Unresolved local var: JSON_Value * json@[???]
                       Unresolved local var: JSON_Object * root@[???]
                       Unresolved local var: char * DeviceId@[???] */
  value = json_parse_string(input);
  if (value == (JSON_Value *)0x0) {
    return -1;
  }
  object = json_value_get_object(value);
  if (object != (JSON_Object *)0x0) {
    __s = json_object_get_string(object,(char *)(DAT_00019a1c + 0x199bc));
    if ((__s != (char *)0x0) && (sVar1 = strlen(__s), sVar1 < 0x22)) {
      memcpy(args,__s,sVar1 + 1);
      json_value_free(value);
      return 0;
    }
    json_value_free(value);
    return -4;
  }
  json_value_free(value);
  return -2;
}


