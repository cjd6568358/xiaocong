package com.huawei.hms.core.aidl;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: MessageCodec.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f {
    public IMessageEntity a(Bundle bundle, IMessageEntity iMessageEntity) {
        if (bundle != null) {
            for (Class<?> superclass = iMessageEntity.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
                for (Field field : superclass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(com.huawei.hms.core.aidl.a.a.class)) {
                        try {
                            a(iMessageEntity, field, bundle);
                        } catch (IllegalAccessException | IllegalArgumentException e) {
                            Log.e("MessageCodec", "decode, set value of the field exception, field name:" + field.getName());
                        }
                    }
                }
            }
        }
        return iMessageEntity;
    }

    private void a(IMessageEntity iMessageEntity, Field field, Bundle bundle) throws IllegalAccessException {
        Object objA = a(field, bundle);
        if (objA != null) {
            boolean zIsAccessible = field.isAccessible();
            field.setAccessible(true);
            field.set(iMessageEntity, objA);
            field.setAccessible(zIsAccessible);
        }
    }

    private Object a(Field field, Bundle bundle) {
        String name = field.getName();
        Object objA = bundle.get(name);
        if (objA instanceof Bundle) {
            try {
                Bundle bundle2 = (Bundle) objA;
                int i = bundle2.getInt("_val_type_", -1);
                if (i == 1) {
                    objA = a(field.getGenericType(), bundle2);
                } else if (i == 0) {
                    objA = a((Bundle) objA, (IMessageEntity) field.getType().newInstance());
                }
                return objA;
            } catch (Exception e) {
                Log.e("MessageCodec", "decode, read value of the field exception, field name: " + name);
                return null;
            }
        }
        return objA;
    }

    protected List<Object> a(Type type, Bundle bundle) throws IllegalAccessException, InstantiationException {
        ArrayList arrayList = new ArrayList();
        for (Bundle bundle2 = bundle.getBundle("_next_item_"); bundle2 != null; bundle2 = bundle2.getBundle("_next_item_")) {
            Object obj = bundle2.get("_value_");
            if (obj.getClass().isPrimitive() || (obj instanceof String) || (obj instanceof Serializable)) {
                arrayList.add(obj);
            } else if (obj instanceof Bundle) {
                Bundle bundle3 = (Bundle) obj;
                int i = bundle3.getInt("_val_type_", -1);
                if (i == 1) {
                    throw new InstantiationException("Nested List can not be supported");
                }
                if (i == 0) {
                    arrayList.add(a(bundle3, (IMessageEntity) ((Class) ((ParameterizedType) type).getActualTypeArguments()[0]).newInstance()));
                } else {
                    throw new InstantiationException("Unknown type can not be supported");
                }
            } else {
                continue;
            }
        }
        return arrayList;
    }

    public Bundle a(IMessageEntity iMessageEntity, Bundle bundle) {
        for (Class<?> superclass = iMessageEntity.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            for (Field field : superclass.getDeclaredFields()) {
                if (field.isAnnotationPresent(com.huawei.hms.core.aidl.a.a.class)) {
                    try {
                        b(iMessageEntity, field, bundle);
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        Log.e("MessageCodec", "encode, get value of the field exception, field name: " + field.getName());
                    }
                }
            }
        }
        return bundle;
    }

    private void b(IMessageEntity iMessageEntity, Field field, Bundle bundle) throws IllegalAccessException {
        boolean zIsAccessible = field.isAccessible();
        field.setAccessible(true);
        a(field.getName(), field.get(iMessageEntity), bundle);
        field.setAccessible(zIsAccessible);
    }

    protected void a(String str, Object obj, Bundle bundle) {
        if (obj != null && !b(str, obj, bundle)) {
            if (obj instanceof CharSequence) {
                bundle.putCharSequence(str, (CharSequence) obj);
                return;
            }
            if (obj instanceof Parcelable) {
                bundle.putParcelable(str, (Parcelable) obj);
                return;
            }
            if (obj instanceof byte[]) {
                bundle.putByteArray(str, (byte[]) obj);
                return;
            }
            if (obj instanceof List) {
                a(str, (List) obj, bundle);
                return;
            }
            if (obj instanceof Serializable) {
                bundle.putSerializable(str, (Serializable) obj);
            } else {
                if (obj instanceof IMessageEntity) {
                    Bundle bundleA = a((IMessageEntity) obj, new Bundle());
                    bundleA.putInt("_val_type_", 0);
                    bundle.putBundle(str, bundleA);
                    return;
                }
                Log.e("MessageCodec", "cannot support type, " + str);
            }
        }
    }

    protected void a(String str, List list, Bundle bundle) {
        Bundle bundleA = null;
        for (Object obj : list) {
            if (bundleA == null) {
                bundleA = new Bundle();
                bundle.putBundle(str, bundleA);
                bundleA.putInt("_val_type_", 1);
            }
            bundleA = a("_value_", bundleA, obj);
        }
    }

    private Bundle a(String str, Bundle bundle, Object obj) {
        Bundle bundle2 = new Bundle();
        a(str, obj, bundle2);
        bundle.putBundle("_next_item_", bundle2);
        return bundle2;
    }

    private boolean b(String str, Object obj, Bundle bundle) {
        if (obj instanceof String) {
            bundle.putString(str, (String) obj);
        } else if (obj instanceof Integer) {
            bundle.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Short) {
            bundle.putShort(str, ((Short) obj).shortValue());
        } else if (obj instanceof Long) {
            bundle.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof Float) {
            bundle.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof Double) {
            bundle.putDouble(str, ((Double) obj).doubleValue());
        } else if (obj instanceof Boolean) {
            bundle.putBoolean(str, ((Boolean) obj).booleanValue());
        } else {
            return false;
        }
        return true;
    }
}
