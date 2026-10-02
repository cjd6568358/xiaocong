// device2mallocjson  @ 0001967c


char * device2mallocjson(device_list *device)

{
  JSON_Value *value;
  JSON_Object *object;
  char *pcVar1;
  
                    /* Unresolved local var: JSON_Value * root_value@[???]
                       Unresolved local var: JSON_Object * root_object@[???]
                       Unresolved local var: char * serialized_string@[???] */
  value = json_value_init_object();
  object = json_value_get_object(value);
  json_object_set_string(object,(char *)(DAT_00019724 + 0x196a0),device->DeviceId);
  json_object_set_string(object,(char *)(DAT_00019728 + 0x196b8),(char *)device->mac);
  json_object_set_string(object,(char *)(DAT_0001972c + 0x196cc),device->productId);
  json_object_set_string(object,(char *)(DAT_00019730 + 0x196e0),device->firmwareVersion);
  json_object_set_string(object,(char *)(DAT_00019734 + 0x196f4),device->scriptType);
  json_object_set_string(object,(char *)(DAT_00019738 + 0x19708),(char *)device->publicKey);
  pcVar1 = json_serialize_to_string(value);
  json_value_free(value);
  return pcVar1;
}


