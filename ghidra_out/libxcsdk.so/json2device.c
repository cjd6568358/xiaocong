// json2device  @ 0001973c


int json2device(device_list *device,char *input)

{
  JSON_Value *value;
  JSON_Object *object;
  char *pcVar1;
  size_t sVar2;
  
                    /* Unresolved local var: JSON_Value * json@[???]
                       Unresolved local var: JSON_Object * root@[???]
                       Unresolved local var: char * DeviceId@[???]
                       Unresolved local var: char * mac@[???]
                       Unresolved local var: char * productId@[???]
                       Unresolved local var: char * firmwareVersion@[???]
                       Unresolved local var: char * scriptType@[???]
                       Unresolved local var: char * publicKey@[???] */
  value = json_parse_string(input);
  if (value == (JSON_Value *)0x0) {
    return -1;
  }
  object = json_value_get_object(value);
  if (object != (JSON_Object *)0x0) {
    pcVar1 = json_object_get_string(object,(char *)(DAT_000198c8 + 0x1976c));
    if ((pcVar1 != (char *)0x0) && (sVar2 = strlen(pcVar1), sVar2 < 0x21)) {
      memcpy(device->DeviceId,pcVar1,sVar2 + 1);
      pcVar1 = json_object_get_string(object,(char *)(DAT_000198cc + 0x197a0));
      if ((pcVar1 != (char *)0x0) && (sVar2 = strlen(pcVar1), sVar2 < 0x21)) {
        memcpy(device->mac,pcVar1,sVar2 + 1);
        pcVar1 = json_object_get_string(object,(char *)(DAT_000198d0 + 0x197d4));
        if ((pcVar1 != (char *)0x0) && (sVar2 = strlen(pcVar1), sVar2 < 0xb)) {
          memcpy(device->productId,pcVar1,sVar2 + 1);
          pcVar1 = json_object_get_string(object,(char *)(DAT_000198d4 + 0x19808));
          if ((pcVar1 != (char *)0x0) && (sVar2 = strlen(pcVar1), sVar2 < 0xd)) {
            memcpy(device->firmwareVersion,pcVar1,sVar2 + 1);
            pcVar1 = json_object_get_string(object,(char *)(DAT_000198d8 + 0x1983c));
            if ((pcVar1 != (char *)0x0) && (sVar2 = strlen(pcVar1), sVar2 < 0xd)) {
              memcpy(device->scriptType,pcVar1,sVar2 + 1);
              pcVar1 = json_object_get_string(object,(char *)(DAT_000198dc + 0x19870));
              if ((pcVar1 != (char *)0x0) && (sVar2 = strlen(pcVar1), sVar2 < 0x16)) {
                memcpy(device->publicKey,pcVar1,sVar2 + 1);
                json_value_free(value);
                return 0;
              }
            }
          }
        }
      }
    }
    return -4;
  }
  json_value_free(value);
  return -2;
}


