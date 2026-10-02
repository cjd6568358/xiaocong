package com.facebook.soloader;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ExoSoSource extends UnpackingSoSource {
    public ExoSoSource(Context context, String name) {
        super(context, name);
    }

    @Override // com.facebook.soloader.UnpackingSoSource
    protected UnpackingSoSource.Unpacker makeUnpacker() throws IOException {
        return new ExoUnpacker();
    }

    private final class ExoUnpacker extends UnpackingSoSource.Unpacker {
        private final FileDso[] mDsos;

        /* JADX WARN: Code duplicated, block: B:105:? A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:25:0x00bb A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:33:0x00cc A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:64:0x017b A[Catch: Throwable -> 0x00c1, all -> 0x0168, TRY_LEAVE, TryCatch #4 {Throwable -> 0x00c1, blocks: (B:10:0x0068, B:61:0x016d, B:58:0x0160, B:64:0x017b, B:63:0x0172, B:27:0x00c0), top: B:80:0x0068 }] */
        /* JADX WARN: Code duplicated, block: B:69:0x018f  */
        /* JADX WARN: Code duplicated, block: B:78:0x00bd A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:82:0x00ce A[EXC_TOP_SPLITTER, SYNTHETIC] */
        ExoUnpacker() throws Throwable {
            Throwable th;
            Context context = ExoSoSource.this.mContext;
            File exoDir = new File("/data/local/tmp/exopackage/" + context.getPackageName() + "/native-libs/");
            ArrayList<FileDso> providedLibraries = new ArrayList<>();
            String[] arr$ = SysUtil.getSupportedAbis();
            for (String abi : arr$) {
                File abiDir = new File(exoDir, abi);
                if (abiDir.isDirectory()) {
                    File metadataFileName = new File(abiDir, "metadata.txt");
                    if (metadataFileName.isFile()) {
                        FileReader fr = new FileReader(metadataFileName);
                        Throwable th2 = null;
                        try {
                            try {
                                BufferedReader br = new BufferedReader(fr);
                                Throwable th3 = null;
                                while (true) {
                                    try {
                                        String line = br.readLine();
                                        if (line == null) {
                                            if (br != null) {
                                                if (0 != 0) {
                                                    try {
                                                        br.close();
                                                    } catch (Throwable x2) {
                                                        th3.addSuppressed(x2);
                                                    }
                                                } else {
                                                    br.close();
                                                }
                                            }
                                            if (fr != null) {
                                                if (0 == 0) {
                                                    fr.close();
                                                    break;
                                                }
                                                try {
                                                    fr.close();
                                                    break;
                                                } catch (Throwable x3) {
                                                    th2.addSuppressed(x3);
                                                    break;
                                                }
                                            }
                                            break;
                                        }
                                        if (line.length() != 0) {
                                            int sep = line.indexOf(32);
                                            if (sep == -1) {
                                                throw new RuntimeException("illegal line in exopackage metadata: [" + line + "]");
                                            }
                                            String soName = line.substring(0, sep) + ".so";
                                            int nrAlreadyProvided = providedLibraries.size();
                                            boolean found = false;
                                            for (int i = 0; i < nrAlreadyProvided; i++) {
                                                if (providedLibraries.get(i).name.equals(soName)) {
                                                    found = true;
                                                    break;
                                                }
                                            }
                                            if (!found) {
                                                String backingFileBaseName = line.substring(sep + 1);
                                                providedLibraries.add(new FileDso(soName, backingFileBaseName, new File(abiDir, backingFileBaseName)));
                                            }
                                        }
                                    } catch (Throwable th4) {
                                        th = th4;
                                        if (br != null) {
                                            if (th3 != null) {
                                                br.close();
                                            } else {
                                                br.close();
                                            }
                                        }
                                        throw th;
                                    }
                                }
                            } catch (Throwable th5) {
                                try {
                                    throw th5;
                                } catch (Throwable th6) {
                                    th = th5;
                                    th = th6;
                                    if (fr != null) {
                                        throw th;
                                    }
                                    if (th == null) {
                                        fr.close();
                                        throw th;
                                    }
                                    try {
                                        fr.close();
                                        throw th;
                                    } catch (Throwable x4) {
                                        th.addSuppressed(x4);
                                        throw th;
                                    }
                                }
                            }
                        } catch (Throwable th7) {
                            th = th7;
                            th = null;
                            if (fr != null) {
                                throw th;
                            }
                            if (th == null) {
                                fr.close();
                                throw th;
                            }
                            fr.close();
                            throw th;
                        }
                    } else {
                        continue;
                    }
                }
            }
            this.mDsos = (FileDso[]) providedLibraries.toArray(new FileDso[providedLibraries.size()]);
        }

        @Override // com.facebook.soloader.UnpackingSoSource.Unpacker
        protected UnpackingSoSource.DsoManifest getDsoManifest() throws IOException {
            return new UnpackingSoSource.DsoManifest(this.mDsos);
        }

        @Override // com.facebook.soloader.UnpackingSoSource.Unpacker
        protected UnpackingSoSource.InputDsoIterator openDsoIterator() throws IOException {
            return new FileBackedInputDsoIterator();
        }

        private final class FileBackedInputDsoIterator extends UnpackingSoSource.InputDsoIterator {
            private int mCurrentDso;

            private FileBackedInputDsoIterator() {
            }

            @Override // com.facebook.soloader.UnpackingSoSource.InputDsoIterator
            public boolean hasNext() {
                return this.mCurrentDso < ExoUnpacker.this.mDsos.length;
            }

            @Override // com.facebook.soloader.UnpackingSoSource.InputDsoIterator
            public UnpackingSoSource.InputDso next() throws IOException {
                FileDso[] fileDsoArr = ExoUnpacker.this.mDsos;
                int i = this.mCurrentDso;
                this.mCurrentDso = i + 1;
                FileDso fileDso = fileDsoArr[i];
                FileInputStream dsoFile = new FileInputStream(fileDso.backingFile);
                try {
                    UnpackingSoSource.InputDso ret = new UnpackingSoSource.InputDso(fileDso, dsoFile);
                    dsoFile = null;
                    return ret;
                } finally {
                    if (dsoFile != null) {
                        dsoFile.close();
                    }
                }
            }
        }
    }

    private static final class FileDso extends UnpackingSoSource.Dso {
        final File backingFile;

        FileDso(String name, String hash, File backingFile) {
            super(name, hash);
            this.backingFile = backingFile;
        }
    }
}
