package net.sqlcipher.database;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class SQLiteClosable {
    private int mReferenceCount = 1;
    private Object mLock = new Object();

    protected abstract void onAllReferencesReleased();

    protected void onAllReferencesReleasedFromContainer() {
    }

    public void acquireReference() {
        synchronized (this.mLock) {
            if (this.mReferenceCount <= 0) {
                throw new IllegalStateException("attempt to re-open an already-closed object: " + getObjInfo());
            }
            this.mReferenceCount++;
        }
    }

    public void releaseReference() {
        synchronized (this.mLock) {
            this.mReferenceCount--;
            if (this.mReferenceCount == 0) {
                onAllReferencesReleased();
            }
        }
    }

    public void releaseReferenceFromContainer() {
        synchronized (this.mLock) {
            this.mReferenceCount--;
            if (this.mReferenceCount == 0) {
                onAllReferencesReleasedFromContainer();
            }
        }
    }

    private String getObjInfo() {
        StringBuilder buff = new StringBuilder();
        buff.append(getClass().getName());
        buff.append(" (");
        if (this instanceof SQLiteDatabase) {
            buff.append("database = ");
            buff.append(((SQLiteDatabase) this).getPath());
        } else if ((this instanceof SQLiteProgram) || (this instanceof SQLiteStatement) || (this instanceof SQLiteQuery)) {
            buff.append("mSql = ");
            buff.append(((SQLiteProgram) this).mSql);
        }
        buff.append(") ");
        return buff.toString();
    }
}
