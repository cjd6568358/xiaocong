package org.eclipse.paho.android.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Iterator;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class DatabaseMessageStore implements MessageStore {
    private SQLiteDatabase db = null;
    private MQTTDatabaseHelper mqttDb;
    private MqttTraceHandler traceHandler;

    private static class MQTTDatabaseHelper extends SQLiteOpenHelper {
        private MqttTraceHandler traceHandler;

        public MQTTDatabaseHelper(MqttTraceHandler traceHandler, Context context) {
            super(context, "mqttAndroidService.db", (SQLiteDatabase.CursorFactory) null, 1);
            this.traceHandler = null;
            this.traceHandler = traceHandler;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase database) {
            this.traceHandler.traceDebug("MQTTDatabaseHelper", "onCreate {CREATE TABLE MqttArrivedMessageTable(messageId TEXT PRIMARY KEY, clientHandle TEXT, destinationName TEXT, payload BLOB, qos INTEGER, retained TEXT, duplicate TEXT, mtimestamp INTEGER);}");
            try {
                database.execSQL("CREATE TABLE MqttArrivedMessageTable(messageId TEXT PRIMARY KEY, clientHandle TEXT, destinationName TEXT, payload BLOB, qos INTEGER, retained TEXT, duplicate TEXT, mtimestamp INTEGER);");
                this.traceHandler.traceDebug("MQTTDatabaseHelper", "created the table");
            } catch (SQLException e) {
                this.traceHandler.traceException("MQTTDatabaseHelper", "onCreate", e);
                throw e;
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            this.traceHandler.traceDebug("MQTTDatabaseHelper", "onUpgrade");
            try {
                db.execSQL("DROP TABLE IF EXISTS MqttArrivedMessageTable");
                onCreate(db);
                this.traceHandler.traceDebug("MQTTDatabaseHelper", "onUpgrade complete");
            } catch (SQLException e) {
                this.traceHandler.traceException("MQTTDatabaseHelper", "onUpgrade", e);
                throw e;
            }
        }
    }

    public DatabaseMessageStore(MqttService service, Context context) {
        this.mqttDb = null;
        this.traceHandler = null;
        this.traceHandler = service;
        this.mqttDb = new MQTTDatabaseHelper(this.traceHandler, context);
        this.traceHandler.traceDebug("DatabaseMessageStore", "DatabaseMessageStore<init> complete");
    }

    @Override // org.eclipse.paho.android.service.MessageStore
    public String storeArrived(String clientHandle, String topic, MqttMessage message) {
        this.db = this.mqttDb.getWritableDatabase();
        this.traceHandler.traceDebug("DatabaseMessageStore", "storeArrived{" + clientHandle + "}, {" + message.toString() + "}");
        byte[] payload = message.getPayload();
        int qos = message.getQos();
        boolean retained = message.isRetained();
        boolean duplicate = message.isDuplicate();
        ContentValues values = new ContentValues();
        String id = UUID.randomUUID().toString();
        values.put("messageId", id);
        values.put("clientHandle", clientHandle);
        values.put("destinationName", topic);
        values.put("payload", payload);
        values.put("qos", Integer.valueOf(qos));
        values.put("retained", Boolean.valueOf(retained));
        values.put("duplicate", Boolean.valueOf(duplicate));
        values.put("mtimestamp", Long.valueOf(System.currentTimeMillis()));
        try {
            this.db.insertOrThrow("MqttArrivedMessageTable", null, values);
            int count = getArrivedRowCount(clientHandle);
            this.traceHandler.traceDebug("DatabaseMessageStore", "storeArrived: inserted message with id of {" + id + "} - Number of messages in database for this clientHandle = " + count);
            return id;
        } catch (SQLException e) {
            this.traceHandler.traceException("DatabaseMessageStore", "onUpgrade", e);
            throw e;
        }
    }

    private int getArrivedRowCount(String clientHandle) {
        int count = 0;
        String[] projection = {"messageId"};
        String[] selectionArgs = {clientHandle};
        Cursor c = this.db.query("MqttArrivedMessageTable", projection, "clientHandle=?", selectionArgs, null, null, null);
        if (c.moveToFirst()) {
            count = c.getInt(0);
        }
        c.close();
        return count;
    }

    @Override // org.eclipse.paho.android.service.MessageStore
    public boolean discardArrived(String clientHandle, String id) {
        this.db = this.mqttDb.getWritableDatabase();
        this.traceHandler.traceDebug("DatabaseMessageStore", "discardArrived{" + clientHandle + "}, {" + id + "}");
        String[] selectionArgs = {id, clientHandle};
        try {
            int rows = this.db.delete("MqttArrivedMessageTable", "messageId=? AND clientHandle=?", selectionArgs);
            if (rows != 1) {
                this.traceHandler.traceError("DatabaseMessageStore", "discardArrived - Error deleting message {" + id + "} from database: Rows affected = " + rows);
                return false;
            }
            int count = getArrivedRowCount(clientHandle);
            this.traceHandler.traceDebug("DatabaseMessageStore", "discardArrived - Message deleted successfully. - messages in db for this clientHandle " + count);
            return true;
        } catch (SQLException e) {
            this.traceHandler.traceException("DatabaseMessageStore", "discardArrived", e);
            throw e;
        }
    }

    @Override // org.eclipse.paho.android.service.MessageStore
    public Iterator<MessageStore.StoredMessage> getAllArrivedMessages(final String clientHandle) {
        return new Iterator<MessageStore.StoredMessage>() { // from class: org.eclipse.paho.android.service.DatabaseMessageStore.1
            private Cursor c;
            private boolean hasNext;
            private final String[] selectionArgs;

            {
                this.selectionArgs = new String[]{clientHandle};
                DatabaseMessageStore.this.db = DatabaseMessageStore.this.mqttDb.getWritableDatabase();
                if (clientHandle == null) {
                    this.c = DatabaseMessageStore.this.db.query("MqttArrivedMessageTable", null, null, null, null, null, "mtimestamp ASC");
                } else {
                    this.c = DatabaseMessageStore.this.db.query("MqttArrivedMessageTable", null, "clientHandle=?", this.selectionArgs, null, null, "mtimestamp ASC");
                }
                this.hasNext = this.c.moveToFirst();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (!this.hasNext) {
                    this.c.close();
                }
                return this.hasNext;
            }

            @Override // java.util.Iterator
            public MessageStore.StoredMessage next() {
                String messageId = this.c.getString(this.c.getColumnIndex("messageId"));
                String clientHandle2 = this.c.getString(this.c.getColumnIndex("clientHandle"));
                String topic = this.c.getString(this.c.getColumnIndex("destinationName"));
                byte[] payload = this.c.getBlob(this.c.getColumnIndex("payload"));
                int qos = this.c.getInt(this.c.getColumnIndex("qos"));
                boolean retained = Boolean.parseBoolean(this.c.getString(this.c.getColumnIndex("retained")));
                boolean dup = Boolean.parseBoolean(this.c.getString(this.c.getColumnIndex("duplicate")));
                MqttMessageHack message = DatabaseMessageStore.this.new MqttMessageHack(payload);
                message.setQos(qos);
                message.setRetained(retained);
                message.setDuplicate(dup);
                this.hasNext = this.c.moveToNext();
                return DatabaseMessageStore.this.new DbStoredData(messageId, clientHandle2, topic, message);
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }

            protected void finalize() throws Throwable {
                this.c.close();
                super.finalize();
            }
        };
    }

    @Override // org.eclipse.paho.android.service.MessageStore
    public void clearArrivedMessages(String clientHandle) {
        int rows;
        this.db = this.mqttDb.getWritableDatabase();
        String[] selectionArgs = {clientHandle};
        if (clientHandle == null) {
            this.traceHandler.traceDebug("DatabaseMessageStore", "clearArrivedMessages: clearing the table");
            rows = this.db.delete("MqttArrivedMessageTable", null, null);
        } else {
            this.traceHandler.traceDebug("DatabaseMessageStore", "clearArrivedMessages: clearing the table of " + clientHandle + " messages");
            rows = this.db.delete("MqttArrivedMessageTable", "clientHandle=?", selectionArgs);
        }
        this.traceHandler.traceDebug("DatabaseMessageStore", "clearArrivedMessages: rows affected = " + rows);
    }

    private class DbStoredData implements MessageStore.StoredMessage {
        private MqttMessage message;
        private String messageId;
        private String topic;

        DbStoredData(String messageId, String clientHandle, String topic, MqttMessage message) {
            this.messageId = messageId;
            this.topic = topic;
            this.message = message;
        }

        @Override // org.eclipse.paho.android.service.MessageStore.StoredMessage
        public String getMessageId() {
            return this.messageId;
        }

        @Override // org.eclipse.paho.android.service.MessageStore.StoredMessage
        public String getTopic() {
            return this.topic;
        }

        @Override // org.eclipse.paho.android.service.MessageStore.StoredMessage
        public MqttMessage getMessage() {
            return this.message;
        }
    }

    private class MqttMessageHack extends MqttMessage {
        public MqttMessageHack(byte[] payload) {
            super(payload);
        }

        @Override // org.eclipse.paho.client.mqttv3.MqttMessage
        protected void setDuplicate(boolean dup) {
            super.setDuplicate(dup);
        }
    }

    @Override // org.eclipse.paho.android.service.MessageStore
    public void close() {
        if (this.db != null) {
            this.db.close();
        }
    }
}
