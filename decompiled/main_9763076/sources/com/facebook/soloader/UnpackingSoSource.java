package com.facebook.soloader;

import android.content.Context;
import android.os.Parcel;
import android.util.Log;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class UnpackingSoSource extends DirectorySoSource {
    protected final Context mContext;

    protected abstract Unpacker makeUnpacker() throws IOException;

    protected UnpackingSoSource(Context context, String name) {
        super(getSoStorePath(context, name), 1);
        this.mContext = context;
    }

    public static File getSoStorePath(Context context, String name) {
        return new File(context.getApplicationInfo().dataDir + "/" + name);
    }

    public static class Dso {
        public final String hash;
        public final String name;

        public Dso(String name, String hash) {
            this.name = name;
            this.hash = hash;
        }
    }

    public static final class DsoManifest {
        public final Dso[] dsos;

        public DsoManifest(Dso[] dsos) {
            this.dsos = dsos;
        }

        static final DsoManifest read(DataInput xdi) throws IOException {
            int version = xdi.readByte();
            if (version != 1) {
                throw new RuntimeException("wrong dso manifest version");
            }
            int nrDso = xdi.readInt();
            if (nrDso < 0) {
                throw new RuntimeException("illegal number of shared libraries");
            }
            Dso[] dsos = new Dso[nrDso];
            for (int i = 0; i < nrDso; i++) {
                dsos[i] = new Dso(xdi.readUTF(), xdi.readUTF());
            }
            return new DsoManifest(dsos);
        }

        public final void write(DataOutput xdo) throws IOException {
            xdo.writeByte(1);
            xdo.writeInt(this.dsos.length);
            for (int i = 0; i < this.dsos.length; i++) {
                xdo.writeUTF(this.dsos[i].name);
                xdo.writeUTF(this.dsos[i].hash);
            }
        }
    }

    protected static final class InputDso implements Closeable {
        public final InputStream content;
        public final Dso dso;

        public InputDso(Dso dso, InputStream content) {
            this.dso = dso;
            this.content = content;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.content.close();
        }
    }

    protected static abstract class InputDsoIterator implements Closeable {
        public abstract boolean hasNext();

        public abstract InputDso next() throws IOException;

        protected InputDsoIterator() {
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
        }
    }

    protected static abstract class Unpacker implements Closeable {
        protected abstract DsoManifest getDsoManifest() throws IOException;

        protected abstract InputDsoIterator openDsoIterator() throws IOException;

        protected Unpacker() {
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:16:0x0037 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:21:0x0042  */
    /* JADX WARN: Code duplicated, block: B:26:0x0039 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static void writeState(File stateFileName, byte state) throws Throwable {
        RandomAccessFile stateFile = new RandomAccessFile(stateFileName, "rw");
        Throwable th = null;
        try {
            stateFile.seek(0L);
            stateFile.write(state);
            stateFile.setLength(stateFile.getFilePointer());
            stateFile.getFD().sync();
            if (stateFile != null) {
                if (0 != 0) {
                    try {
                        stateFile.close();
                        return;
                    } catch (Throwable x2) {
                        th.addSuppressed(x2);
                        return;
                    }
                }
                stateFile.close();
            }
        } catch (Throwable th2) {
            th = th2;
            if (stateFile != null) {
                if (th != null) {
                    stateFile.close();
                } else {
                    stateFile.close();
                }
            }
            throw th;
        }
    }

    private void deleteUnmentionedFiles(Dso[] dsos) throws IOException {
        String[] existingFiles = this.soDirectory.list();
        if (existingFiles == null) {
            throw new IOException("unable to list directory " + this.soDirectory);
        }
        for (String fileName : existingFiles) {
            if (!fileName.equals("dso_state") && !fileName.equals("dso_lock") && !fileName.equals("dso_deps") && !fileName.equals("dso_manifest")) {
                boolean found = false;
                for (int j = 0; !found && j < dsos.length; j++) {
                    if (dsos[j].name.equals(fileName)) {
                        found = true;
                    }
                }
                if (!found) {
                    File fileNameToDelete = new File(this.soDirectory, fileName);
                    Log.v("fb-UnpackingSoSource", "deleting unaccounted-for file " + fileNameToDelete);
                    SysUtil.dumbDeleteRecursive(fileNameToDelete);
                }
            }
        }
    }

    private void extractDso(InputDso iDso, byte[] ioBuffer) throws IOException {
        RandomAccessFile dsoFile;
        Log.i("fb-UnpackingSoSource", "extracting DSO " + iDso.dso.name);
        File dsoFileName = new File(this.soDirectory, iDso.dso.name);
        try {
            dsoFile = new RandomAccessFile(dsoFileName, "rw");
        } catch (IOException ex) {
            Log.w("fb-UnpackingSoSource", "error overwriting " + dsoFileName + " trying to delete and start over", ex);
            dsoFileName.delete();
            dsoFile = new RandomAccessFile(dsoFileName, "rw");
        }
        try {
            InputStream dsoContent = iDso.content;
            int sizeHint = dsoContent.available();
            if (sizeHint > 1) {
                SysUtil.fallocateIfSupported(dsoFile.getFD(), sizeHint);
            }
            SysUtil.copyBytes(dsoFile, iDso.content, Integer.MAX_VALUE, ioBuffer);
            dsoFile.setLength(dsoFile.getFilePointer());
            if (!dsoFileName.setExecutable(true, false)) {
                throw new IOException("cannot make file executable: " + dsoFileName);
            }
            dsoFile.close();
        } catch (Throwable th) {
            dsoFile.close();
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:101:0x0098 A[EDGE_INSN: B:101:0x0098->B:25:0x0098 BREAK  A[LOOP:1: B:13:0x0063->B:21:0x008b], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:11:0x005c A[Catch: Throwable -> 0x00ac, all -> 0x00be, TRY_LEAVE, TryCatch #2 {, blocks: (B:4:0x0039, B:8:0x0048, B:9:0x0056, B:11:0x005c, B:42:0x00ba, B:32:0x00a8, B:56:0x00d4, B:55:0x00d0, B:53:0x00ce, B:23:0x008f), top: B:73:0x0039 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x006a A[Catch: Throwable -> 0x00c1, all -> 0x00f5, TryCatch #12 {all -> 0x00f5, Throwable -> 0x00c1, blocks: (B:14:0x0065, B:16:0x006a, B:18:0x007a, B:26:0x009a), top: B:90:0x0065 }] */
    /* JADX WARN: Code duplicated, block: B:26:0x009a A[Catch: Throwable -> 0x00c1, all -> 0x00f5, TRY_ENTER, TRY_LEAVE, TryCatch #12 {all -> 0x00f5, Throwable -> 0x00c1, blocks: (B:14:0x0065, B:16:0x006a, B:18:0x007a, B:26:0x009a), top: B:90:0x0065 }] */
    /* JADX WARN: Code duplicated, block: B:51:0x00c9 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:56:0x00d4 A[Catch: Throwable -> 0x00ac, all -> 0x00be, TRY_LEAVE, TryCatch #2 {, blocks: (B:4:0x0039, B:8:0x0048, B:9:0x0056, B:11:0x005c, B:42:0x00ba, B:32:0x00a8, B:56:0x00d4, B:55:0x00d0, B:53:0x00ce, B:23:0x008f), top: B:73:0x0039 }] */
    /* JADX WARN: Code duplicated, block: B:59:0x00da A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:64:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:72:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:78:0x00cb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x00dc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:82:0x0040 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:0x00a3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:90:0x0065 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:93:0x00a1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:94:0x00ba A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:96:0x0056 A[SYNTHETIC] */
    private void regenerate(byte state, DsoManifest desiredManifest, InputDsoIterator dsoIterator) throws Throwable {
        DsoManifest existingManifest;
        Throwable th;
        DsoManifest existingManifest2;
        byte[] ioBuffer;
        InputDso iDso;
        boolean obsolete;
        int i;
        Throwable th2;
        Log.v("fb-UnpackingSoSource", "regenerating DSO store " + getClass().getName());
        File manifestFileName = new File(this.soDirectory, "dso_manifest");
        RandomAccessFile manifestFile = new RandomAccessFile(manifestFileName, "rw");
        Throwable th3 = null;
        if (state != 1) {
            existingManifest = null;
            if (existingManifest == null) {
                existingManifest2 = new DsoManifest(new Dso[0]);
            } else {
                existingManifest2 = existingManifest;
            }
            deleteUnmentionedFiles(desiredManifest.dsos);
            ioBuffer = new byte[WXMediaMessage.THUMB_LENGTH_LIMIT];
            while (dsoIterator.hasNext()) {
                iDso = dsoIterator.next();
                Throwable th4 = null;
                obsolete = true;
                i = 0;
                while (obsolete) {
                    if (i < existingManifest2.dsos.length) {
                        break;
                        break;
                    } else {
                        if (!existingManifest2.dsos[i].name.equals(iDso.dso.name)) {
                        }
                        i++;
                    }
                }
                if (obsolete) {
                    extractDso(iDso, ioBuffer);
                }
                if (iDso == null) {
                    if (0 != 0) {
                        iDso.close();
                    } else {
                        iDso.close();
                    }
                }
            }
            if (manifestFile != null) {
                if (0 == 0) {
                    manifestFile.close();
                    return;
                } else {
                    manifestFile.close();
                    return;
                }
            }
            return;
        }
        try {
            try {
                DsoManifest existingManifest3 = DsoManifest.read(manifestFile);
                existingManifest = existingManifest3;
            } catch (Exception ex) {
                Log.i("fb-UnpackingSoSource", "error reading existing DSO manifest", ex);
                existingManifest = null;
            }
            if (existingManifest == null) {
                try {
                    existingManifest2 = new DsoManifest(new Dso[0]);
                } catch (Throwable th5) {
                    Throwable th6 = th5;
                    try {
                        throw th6;
                    } catch (Throwable th7) {
                        th = th6;
                        th = th7;
                    }
                }
            } else {
                existingManifest2 = existingManifest;
            }
            deleteUnmentionedFiles(desiredManifest.dsos);
            ioBuffer = new byte[WXMediaMessage.THUMB_LENGTH_LIMIT];
            while (dsoIterator.hasNext()) {
                iDso = dsoIterator.next();
                Throwable th8 = null;
                obsolete = true;
                i = 0;
                while (obsolete) {
                    try {
                        if (i < existingManifest2.dsos.length) {
                            break;
                        }
                        if (!existingManifest2.dsos[i].name.equals(iDso.dso.name) && existingManifest2.dsos[i].hash.equals(iDso.dso.hash)) {
                            obsolete = false;
                        }
                        i++;
                    } catch (Throwable th9) {
                        try {
                            throw th9;
                        } catch (Throwable th10) {
                            th8 = th9;
                            th2 = th10;
                            if (iDso != null) {
                                if (th8 != null) {
                                    iDso.close();
                                } else {
                                    iDso.close();
                                }
                            }
                            throw th2;
                        }
                    }
                }
                if (obsolete) {
                    extractDso(iDso, ioBuffer);
                }
                if (iDso == null) {
                    if (0 != 0) {
                        try {
                            iDso.close();
                        } catch (Throwable x2) {
                            th8.addSuppressed(x2);
                        }
                    } else {
                        iDso.close();
                    }
                }
            }
            if (manifestFile != null) {
                if (0 == 0) {
                    manifestFile.close();
                    return;
                }
                try {
                    manifestFile.close();
                    return;
                } catch (Throwable x3) {
                    th3.addSuppressed(x3);
                    return;
                }
            }
            return;
        } catch (Throwable th11) {
            th = th11;
            th = null;
        }
        if (manifestFile == null) {
            throw th;
        }
        if (th == null) {
            manifestFile.close();
            throw th;
        }
        try {
            manifestFile.close();
            throw th;
        } catch (Throwable x4) {
            th.addSuppressed(x4);
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:108:0x0113 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:113:0x00e1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:115:0x0103 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:136:? A[Catch: Throwable -> 0x0107, all -> 0x013c, SYNTHETIC, TRY_ENTER, TRY_LEAVE, TryCatch #8 {Throwable -> 0x0107, blocks: (B:11:0x0061, B:13:0x0071, B:14:0x007a, B:16:0x0082, B:18:0x008c, B:89:0x0140, B:86:0x0135, B:92:0x014c, B:91:0x0146, B:61:0x0106), top: B:117:0x0061 }] */
    /* JADX WARN: Code duplicated, block: B:139:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x00df A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:50:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:59:0x0101 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:67:0x0111 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:92:0x014c A[Catch: Throwable -> 0x0107, all -> 0x013c, TRY_LEAVE, TryCatch #8 {Throwable -> 0x0107, blocks: (B:11:0x0061, B:13:0x0071, B:14:0x007a, B:16:0x0082, B:18:0x008c, B:89:0x0140, B:86:0x0135, B:92:0x014c, B:91:0x0146, B:61:0x0106), top: B:117:0x0061 }] */
    /* JADX WARN: Code duplicated, block: B:99:0x0166  */
    private boolean refreshLocked(final FileLocker lock, int flags, final byte[] deps) throws Throwable {
        byte state;
        Throwable th;
        Throwable th2;
        Throwable th3;
        final File stateFileName = new File(this.soDirectory, "dso_state");
        RandomAccessFile stateFile = new RandomAccessFile(stateFileName, "rw");
        Throwable th4 = null;
        try {
            state = stateFile.readByte();
            if (state != 1) {
                Log.v("fb-UnpackingSoSource", "dso store " + this.soDirectory + " regeneration interrupted: wiping clean");
                state = 0;
            }
        } catch (EOFException e) {
            state = 0;
        } catch (Throwable th5) {
            th = th5;
            if (stateFile != null) {
                if (th4 != null) {
                    stateFile.close();
                } else {
                    stateFile.close();
                }
            }
            throw th;
        }
        if (stateFile != null) {
            if (0 != 0) {
                try {
                    stateFile.close();
                } catch (Throwable x2) {
                    th4.addSuppressed(x2);
                }
            } else {
                stateFile.close();
            }
        }
        final File depsFileName = new File(this.soDirectory, "dso_deps");
        DsoManifest desiredManifest = null;
        RandomAccessFile depsFile = new RandomAccessFile(depsFileName, "rw");
        Throwable th6 = null;
        try {
            try {
                byte[] existingDeps = new byte[(int) depsFile.length()];
                if (depsFile.read(existingDeps) != existingDeps.length) {
                    Log.v("fb-UnpackingSoSource", "short read of so store deps file: marking unclean");
                    state = 0;
                }
                if (!Arrays.equals(existingDeps, deps)) {
                    Log.v("fb-UnpackingSoSource", "deps mismatch on deps store: regenerating");
                    state = 0;
                }
                if (state == 0) {
                    Log.v("fb-UnpackingSoSource", "so store dirty: regenerating");
                    writeState(stateFileName, (byte) 0);
                    Unpacker u = makeUnpacker();
                    Throwable th7 = null;
                    try {
                        try {
                            desiredManifest = u.getDsoManifest();
                            InputDsoIterator idi = u.openDsoIterator();
                            Throwable th8 = null;
                            try {
                                regenerate(state, desiredManifest, idi);
                                if (idi != null) {
                                    if (0 != 0) {
                                        try {
                                            idi.close();
                                        } catch (Throwable x3) {
                                            th8.addSuppressed(x3);
                                        }
                                    } else {
                                        idi.close();
                                    }
                                }
                                if (u != null) {
                                    if (0 != 0) {
                                        try {
                                            u.close();
                                        } catch (Throwable x4) {
                                            th7.addSuppressed(x4);
                                        }
                                    } else {
                                        u.close();
                                    }
                                }
                            } catch (Throwable th9) {
                                if (idi != null) {
                                    if (0 != 0) {
                                        try {
                                            idi.close();
                                        } catch (Throwable x5) {
                                            th8.addSuppressed(x5);
                                        }
                                    } else {
                                        idi.close();
                                    }
                                }
                                throw th9;
                            }
                        } catch (Throwable th10) {
                            th3 = th10;
                            th2 = null;
                            if (u != null) {
                                throw th3;
                            }
                            if (th2 == null) {
                                u.close();
                                throw th3;
                            }
                            try {
                                u.close();
                                throw th3;
                            } catch (Throwable x6) {
                                th2.addSuppressed(x6);
                                throw th3;
                            }
                        }
                    } catch (Throwable th11) {
                        try {
                            throw th11;
                        } catch (Throwable th12) {
                            th2 = th11;
                            th3 = th12;
                            if (u != null) {
                                throw th3;
                            }
                            if (th2 == null) {
                                u.close();
                                throw th3;
                            }
                            u.close();
                            throw th3;
                        }
                    }
                }
                if (depsFile != null) {
                    if (0 != 0) {
                        try {
                            depsFile.close();
                        } catch (Throwable x7) {
                            th6.addSuppressed(x7);
                        }
                    } else {
                        depsFile.close();
                    }
                }
                if (desiredManifest == null) {
                    return false;
                }
                final DsoManifest manifest = desiredManifest;
                Runnable syncer = new Runnable() { // from class: com.facebook.soloader.UnpackingSoSource.1
                    /* JADX WARN: Code duplicated, block: B:34:0x00bc A[DONT_INVERT] */
                    /* JADX WARN: Code duplicated, block: B:39:0x00c7 A[Catch: all -> 0x007f, TryCatch #11 {all -> 0x007f, blocks: (B:3:0x0001, B:8:0x0023, B:27:0x00af, B:19:0x007b, B:9:0x0026, B:14:0x0042, B:42:0x00d1, B:41:0x00cc, B:15:0x0045, B:50:0x00e0, B:54:0x00e9, B:53:0x00e5, B:51:0x00e3, B:35:0x00be, B:39:0x00c7, B:38:0x00c3, B:36:0x00c1), top: B:64:0x0001, outer: #2, inners: #0, #1, #5, #6 }] */
                    /* JADX WARN: Code duplicated, block: B:49:0x00de A[DONT_INVERT] */
                    /* JADX WARN: Code duplicated, block: B:54:0x00e9 A[Catch: all -> 0x007f, TRY_LEAVE, TryCatch #11 {all -> 0x007f, blocks: (B:3:0x0001, B:8:0x0023, B:27:0x00af, B:19:0x007b, B:9:0x0026, B:14:0x0042, B:42:0x00d1, B:41:0x00cc, B:15:0x0045, B:50:0x00e0, B:54:0x00e9, B:53:0x00e5, B:51:0x00e3, B:35:0x00be, B:39:0x00c7, B:38:0x00c3, B:36:0x00c1), top: B:64:0x0001, outer: #2, inners: #0, #1, #5, #6 }] */
                    /* JADX WARN: Code duplicated, block: B:69:0x00be A[EXC_TOP_SPLITTER, SYNTHETIC] */
                    /* JADX WARN: Code duplicated, block: B:71:0x00e0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                    @Override // java.lang.Runnable
                    public void run() {
                        Throwable th13;
                        Throwable th14 = null;
                        try {
                            try {
                                Log.v("fb-UnpackingSoSource", "starting syncer worker");
                                RandomAccessFile depsFile2 = new RandomAccessFile(depsFileName, "rw");
                                Throwable th15 = null;
                                try {
                                    depsFile2.write(deps);
                                    depsFile2.setLength(depsFile2.getFilePointer());
                                    if (depsFile2 != null) {
                                        if (0 != 0) {
                                            try {
                                                depsFile2.close();
                                            } catch (Throwable x8) {
                                                th15.addSuppressed(x8);
                                            }
                                        } else {
                                            depsFile2.close();
                                        }
                                    }
                                    File manifestFileName = new File(UnpackingSoSource.this.soDirectory, "dso_manifest");
                                    RandomAccessFile manifestFile = new RandomAccessFile(manifestFileName, "rw");
                                    Throwable th16 = null;
                                    try {
                                        manifest.write(manifestFile);
                                        if (manifestFile != null) {
                                            if (0 != 0) {
                                                try {
                                                    manifestFile.close();
                                                } catch (Throwable x9) {
                                                    th16.addSuppressed(x9);
                                                }
                                            } else {
                                                manifestFile.close();
                                            }
                                        }
                                        SysUtil.fsyncRecursive(UnpackingSoSource.this.soDirectory);
                                        UnpackingSoSource.writeState(stateFileName, (byte) 1);
                                        Log.v("fb-UnpackingSoSource", "releasing dso store lock for " + UnpackingSoSource.this.soDirectory + " (from syncer thread)");
                                        lock.close();
                                    } catch (Throwable th17) {
                                        try {
                                            throw th17;
                                        } catch (Throwable th18) {
                                            th14 = th17;
                                            th13 = th18;
                                            if (manifestFile != null) {
                                                if (th14 != null) {
                                                    manifestFile.close();
                                                } else {
                                                    manifestFile.close();
                                                }
                                            }
                                            throw th13;
                                        }
                                    }
                                } catch (Throwable th19) {
                                    th = th19;
                                    if (depsFile2 != null) {
                                        if (th14 != null) {
                                            depsFile2.close();
                                        } else {
                                            depsFile2.close();
                                        }
                                    }
                                    throw th;
                                }
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } catch (Throwable th20) {
                            Log.v("fb-UnpackingSoSource", "releasing dso store lock for " + UnpackingSoSource.this.soDirectory + " (from syncer thread)");
                            lock.close();
                            throw th20;
                        }
                    }
                };
                if ((flags & 1) != 0) {
                    new Thread(syncer, "SoSync:" + this.soDirectory.getName()).start();
                } else {
                    syncer.run();
                }
                return true;
            } catch (Throwable th13) {
                try {
                    throw th13;
                } catch (Throwable th14) {
                    th = th13;
                    th = th14;
                    if (depsFile != null) {
                        throw th;
                    }
                    if (th == null) {
                        depsFile.close();
                        throw th;
                    }
                    try {
                        depsFile.close();
                        throw th;
                    } catch (Throwable x8) {
                        th.addSuppressed(x8);
                        throw th;
                    }
                }
            }
        } catch (Throwable th15) {
            th = th15;
            th = null;
            if (depsFile != null) {
                throw th;
            }
            if (th == null) {
                depsFile.close();
                throw th;
            }
            depsFile.close();
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:26:0x0057  */
    /* JADX WARN: Code duplicated, block: B:33:0x004e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    protected byte[] getDepsBlock() throws Throwable {
        Throwable th;
        Parcel parcel = Parcel.obtain();
        Unpacker u = makeUnpacker();
        Throwable th2 = null;
        try {
            Dso[] dsos = u.getDsoManifest().dsos;
            parcel.writeByte((byte) 1);
            parcel.writeInt(dsos.length);
            for (int i = 0; i < dsos.length; i++) {
                parcel.writeString(dsos[i].name);
                parcel.writeString(dsos[i].hash);
            }
            if (u != null) {
                if (0 != 0) {
                    try {
                        u.close();
                    } catch (Throwable x2) {
                        th2.addSuppressed(x2);
                    }
                } else {
                    u.close();
                }
            }
            byte[] depsBlock = parcel.marshall();
            parcel.recycle();
            return depsBlock;
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                th2 = th3;
                th = th4;
                if (u != null) {
                    if (th2 != null) {
                        u.close();
                    } else {
                        u.close();
                    }
                }
                throw th;
            }
        }
    }

    @Override // com.facebook.soloader.SoSource
    protected void prepare(int flags) throws IOException {
        SysUtil.mkdirOrThrow(this.soDirectory);
        File lockFileName = new File(this.soDirectory, "dso_lock");
        FileLocker lock = FileLocker.lock(lockFileName);
        try {
            Log.v("fb-UnpackingSoSource", "locked dso store " + this.soDirectory);
            if (refreshLocked(lock, flags, getDepsBlock())) {
                lock = null;
            } else {
                Log.i("fb-UnpackingSoSource", "dso store is up-to-date: " + this.soDirectory);
            }
        } finally {
            if (lock != null) {
                Log.v("fb-UnpackingSoSource", "releasing dso store lock for " + this.soDirectory);
                lock.close();
            } else {
                Log.v("fb-UnpackingSoSource", "not releasing dso store lock for " + this.soDirectory + " (syncer thread started)");
            }
        }
    }
}
