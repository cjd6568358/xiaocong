package net.sqlcipher;

import java.text.Collator;
import net.sqlcipher.database.SQLiteProgram;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DatabaseUtils {
    private static final String[] countProjection = {"count(*)"};
    private static final char[] HEX_DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static Collator mColl = null;

    public static void bindObjectToProgram(SQLiteProgram prog, int index, Object value) {
        if (value == null) {
            prog.bindNull(index);
            return;
        }
        if ((value instanceof Double) || (value instanceof Float)) {
            prog.bindDouble(index, ((Number) value).doubleValue());
            return;
        }
        if (value instanceof Number) {
            prog.bindLong(index, ((Number) value).longValue());
            return;
        }
        if (value instanceof Boolean) {
            Boolean bool = (Boolean) value;
            if (bool.booleanValue()) {
                prog.bindLong(index, 1L);
                return;
            } else {
                prog.bindLong(index, 0L);
                return;
            }
        }
        if (value instanceof byte[]) {
            prog.bindBlob(index, (byte[]) value);
        } else {
            prog.bindString(index, value.toString());
        }
    }

    public static void appendEscapedSQLString(StringBuilder sb, String sqlString) {
        sb.append('\'');
        if (sqlString.indexOf(39) != -1) {
            int length = sqlString.length();
            for (int i = 0; i < length; i++) {
                char c = sqlString.charAt(i);
                if (c == '\'') {
                    sb.append('\'');
                }
                sb.append(c);
            }
        } else {
            sb.append(sqlString);
        }
        sb.append('\'');
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0026 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:35:? A[LOOP:0: B:8:0x0020->B:35:?, LOOP_END, SYNTHETIC] */
    public static void cursorFillWindow(Cursor cursor, int position, android.database.CursorWindow window) {
        boolean success;
        if (position >= 0 && position < cursor.getCount()) {
            int oldPos = cursor.getPosition();
            int numColumns = cursor.getColumnCount();
            window.clear();
            window.setStartPosition(position);
            window.setNumColumns(numColumns);
            if (cursor.moveToPosition(position)) {
                while (window.allocRow()) {
                    for (int i = 0; i < numColumns; i++) {
                        int type = cursor.getType(i);
                        switch (type) {
                            case 0:
                                success = window.putNull(position, i);
                                break;
                            case 1:
                                success = window.putLong(cursor.getLong(i), position, i);
                                break;
                            case 2:
                                success = window.putDouble(cursor.getDouble(i), position, i);
                                break;
                            case 3:
                            default:
                                String value = cursor.getString(i);
                                success = value != null ? window.putString(value, position, i) : window.putNull(position, i);
                                break;
                            case 4:
                                byte[] value2 = cursor.getBlob(i);
                                success = value2 != null ? window.putBlob(value2, position, i) : window.putNull(position, i);
                                break;
                        }
                        if (!success) {
                            window.freeLastRow();
                            position++;
                            if (!cursor.moveToNext()) {
                            }
                        }
                    }
                    position++;
                    if (!cursor.moveToNext()) {
                    }
                }
            }
            cursor.moveToPosition(oldPos);
        }
    }
}
