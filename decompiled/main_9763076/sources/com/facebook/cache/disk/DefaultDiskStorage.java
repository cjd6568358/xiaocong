package com.facebook.cache.disk;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.common.WriterCallback;
import com.facebook.common.file.FileTree;
import com.facebook.common.file.FileTreeVisitor;
import com.facebook.common.file.FileUtils;
import com.facebook.common.internal.CountingOutputStream;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.time.Clock;
import com.facebook.common.time.SystemClock;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultDiskStorage implements DiskStorage {
    private static final Class<?> TAG = DefaultDiskStorage.class;
    static final long TEMP_FILE_LIFETIME_MS = TimeUnit.MINUTES.toMillis(30);
    private final CacheErrorLogger mCacheErrorLogger;
    private final Clock mClock;
    private final File mRootDirectory;
    private final File mVersionDirectory;

    public DefaultDiskStorage(File rootDirectory, int version, CacheErrorLogger cacheErrorLogger) {
        Preconditions.checkNotNull(rootDirectory);
        this.mRootDirectory = rootDirectory;
        this.mVersionDirectory = new File(this.mRootDirectory, getVersionSubdirectoryName(version));
        this.mCacheErrorLogger = cacheErrorLogger;
        recreateDirectoryIfVersionChanges();
        this.mClock = SystemClock.get();
    }

    static String getVersionSubdirectoryName(int version) {
        return String.format((Locale) null, "%s.ols%d.%d", "v2", 100, Integer.valueOf(version));
    }

    private void recreateDirectoryIfVersionChanges() {
        boolean recreateBase = false;
        if (!this.mRootDirectory.exists()) {
            recreateBase = true;
        } else if (!this.mVersionDirectory.exists()) {
            recreateBase = true;
            FileTree.deleteRecursively(this.mRootDirectory);
        }
        if (recreateBase) {
            try {
                FileUtils.mkdirs(this.mVersionDirectory);
            } catch (FileUtils.CreateDirectoryException e) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_CREATE_DIR, TAG, "version directory could not be created: " + this.mVersionDirectory, null);
            }
        }
    }

    private static class IncompleteFileException extends IOException {
        public final long actual;
        public final long expected;

        public IncompleteFileException(long expected, long actual) {
            super("File was not written completely. Expected: " + expected + ", found: " + actual);
            this.expected = expected;
            this.actual = actual;
        }
    }

    File getContentFileFor(String resourceId) {
        return new File(getFilename(resourceId));
    }

    private String getSubdirectoryPath(String resourceId) {
        String subdirectory = String.valueOf(Math.abs(resourceId.hashCode() % 100));
        return this.mVersionDirectory + File.separator + subdirectory;
    }

    private File getSubdirectory(String resourceId) {
        return new File(getSubdirectoryPath(resourceId));
    }

    private class EntriesCollector implements FileTreeVisitor {
        private final List<DiskStorage.Entry> result;

        private EntriesCollector() {
            this.result = new ArrayList();
        }

        @Override // com.facebook.common.file.FileTreeVisitor
        public void preVisitDirectory(File directory) {
        }

        @Override // com.facebook.common.file.FileTreeVisitor
        public void visitFile(File file) {
            FileInfo info = DefaultDiskStorage.this.getShardFileInfo(file);
            if (info != null && info.type == FileType.CONTENT) {
                this.result.add(new EntryImpl(info.resourceId, file));
            }
        }

        @Override // com.facebook.common.file.FileTreeVisitor
        public void postVisitDirectory(File directory) {
        }

        public List<DiskStorage.Entry> getEntries() {
            return Collections.unmodifiableList(this.result);
        }
    }

    private class PurgingVisitor implements FileTreeVisitor {
        private boolean insideBaseDirectory;

        private PurgingVisitor() {
        }

        @Override // com.facebook.common.file.FileTreeVisitor
        public void preVisitDirectory(File directory) {
            if (!this.insideBaseDirectory && directory.equals(DefaultDiskStorage.this.mVersionDirectory)) {
                this.insideBaseDirectory = true;
            }
        }

        @Override // com.facebook.common.file.FileTreeVisitor
        public void visitFile(File file) {
            if (!this.insideBaseDirectory || !isExpectedFile(file)) {
                file.delete();
            }
        }

        @Override // com.facebook.common.file.FileTreeVisitor
        public void postVisitDirectory(File directory) {
            if (!DefaultDiskStorage.this.mRootDirectory.equals(directory) && !this.insideBaseDirectory) {
                directory.delete();
            }
            if (this.insideBaseDirectory && directory.equals(DefaultDiskStorage.this.mVersionDirectory)) {
                this.insideBaseDirectory = false;
            }
        }

        private boolean isExpectedFile(File file) {
            FileInfo info = DefaultDiskStorage.this.getShardFileInfo(file);
            if (info == null) {
                return false;
            }
            if (info.type == FileType.TEMP) {
                return isRecentFile(file);
            }
            Preconditions.checkState(info.type == FileType.CONTENT);
            return true;
        }

        private boolean isRecentFile(File file) {
            return file.lastModified() > DefaultDiskStorage.this.mClock.now() - DefaultDiskStorage.TEMP_FILE_LIFETIME_MS;
        }
    }

    @Override // com.facebook.cache.disk.DiskStorage
    public void purgeUnexpectedResources() {
        FileTree.walkFileTree(this.mRootDirectory, new PurgingVisitor());
    }

    private void mkdirs(File directory, String message) throws IOException {
        try {
            FileUtils.mkdirs(directory);
        } catch (FileUtils.CreateDirectoryException cde) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_CREATE_DIR, TAG, message, cde);
            throw cde;
        }
    }

    @Override // com.facebook.cache.disk.DiskStorage
    public DiskStorage.Inserter insert(String resourceId, Object debugInfo) throws IOException {
        FileInfo info = new FileInfo(FileType.TEMP, resourceId);
        File parent = getSubdirectory(info.resourceId);
        if (!parent.exists()) {
            mkdirs(parent, "insert");
        }
        try {
            File file = info.createTempFile(parent);
            return new InserterImpl(resourceId, file);
        } catch (IOException ioe) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_CREATE_TEMPFILE, TAG, "insert", ioe);
            throw ioe;
        }
    }

    @Override // com.facebook.cache.disk.DiskStorage
    public BinaryResource getResource(String resourceId, Object debugInfo) {
        File file = getContentFileFor(resourceId);
        if (!file.exists()) {
            return null;
        }
        file.setLastModified(this.mClock.now());
        return FileBinaryResource.createOrNull(file);
    }

    private String getFilename(String resourceId) {
        FileInfo fileInfo = new FileInfo(FileType.CONTENT, resourceId);
        String path = getSubdirectoryPath(fileInfo.resourceId);
        return fileInfo.toPath(path);
    }

    @Override // com.facebook.cache.disk.DiskStorage
    public boolean contains(String resourceId, Object debugInfo) {
        return query(resourceId, false);
    }

    private boolean query(String resourceId, boolean touch) {
        File contentFile = getContentFileFor(resourceId);
        boolean exists = contentFile.exists();
        if (touch && exists) {
            contentFile.setLastModified(this.mClock.now());
        }
        return exists;
    }

    @Override // com.facebook.cache.disk.DiskStorage
    public long remove(DiskStorage.Entry entry) {
        EntryImpl entryImpl = (EntryImpl) entry;
        FileBinaryResource resource = entryImpl.getResource();
        return doRemove(resource.getFile());
    }

    private long doRemove(File contentFile) {
        if (!contentFile.exists()) {
            return 0L;
        }
        long length = contentFile.length();
        if (!contentFile.delete()) {
            return -1L;
        }
        return length;
    }

    @Override // com.facebook.cache.disk.DiskStorage
    public void clearAll() {
        FileTree.deleteContents(this.mRootDirectory);
    }

    @Override // com.facebook.cache.disk.DiskStorage
    public List<DiskStorage.Entry> getEntries() throws IOException {
        EntriesCollector collector = new EntriesCollector();
        FileTree.walkFileTree(this.mVersionDirectory, collector);
        return collector.getEntries();
    }

    static class EntryImpl implements DiskStorage.Entry {
        private final String id;
        private final FileBinaryResource resource;
        private long size;
        private long timestamp;

        private EntryImpl(String id, File cachedFile) {
            Preconditions.checkNotNull(cachedFile);
            this.id = (String) Preconditions.checkNotNull(id);
            this.resource = FileBinaryResource.createOrNull(cachedFile);
            this.size = -1L;
            this.timestamp = -1L;
        }

        @Override // com.facebook.cache.disk.DiskStorage.Entry
        public String getId() {
            return this.id;
        }

        @Override // com.facebook.cache.disk.DiskStorage.Entry
        public long getTimestamp() {
            if (this.timestamp < 0) {
                File cachedFile = this.resource.getFile();
                this.timestamp = cachedFile.lastModified();
            }
            return this.timestamp;
        }

        public FileBinaryResource getResource() {
            return this.resource;
        }

        @Override // com.facebook.cache.disk.DiskStorage.Entry
        public long getSize() {
            if (this.size < 0) {
                this.size = this.resource.size();
            }
            return this.size;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FileInfo getShardFileInfo(File file) {
        FileInfo info = FileInfo.fromFile(file);
        if (info == null) {
            return null;
        }
        File expectedDirectory = getSubdirectory(info.resourceId);
        boolean isCorrect = expectedDirectory.equals(file.getParentFile());
        if (!isCorrect) {
            info = null;
        }
        return info;
    }

    private enum FileType {
        CONTENT(".cnt"),
        TEMP(".tmp");

        public final String extension;

        FileType(String extension) {
            this.extension = extension;
        }

        public static FileType fromExtension(String extension) {
            if (".cnt".equals(extension)) {
                return CONTENT;
            }
            if (".tmp".equals(extension)) {
                return TEMP;
            }
            return null;
        }
    }

    private static class FileInfo {
        public final String resourceId;
        public final FileType type;

        private FileInfo(FileType type, String resourceId) {
            this.type = type;
            this.resourceId = resourceId;
        }

        public String toString() {
            return this.type + "(" + this.resourceId + ")";
        }

        public String toPath(String parentPath) {
            return parentPath + File.separator + this.resourceId + this.type.extension;
        }

        public File createTempFile(File parent) throws IOException {
            File f = File.createTempFile(this.resourceId + ".", ".tmp", parent);
            return f;
        }

        public static FileInfo fromFile(File file) {
            String name = file.getName();
            int pos = name.lastIndexOf(46);
            if (pos <= 0) {
                return null;
            }
            String ext = name.substring(pos);
            FileType type = FileType.fromExtension(ext);
            if (type == null) {
                return null;
            }
            String resourceId = name.substring(0, pos);
            if (type.equals(FileType.TEMP)) {
                int numPos = resourceId.lastIndexOf(46);
                if (numPos <= 0) {
                    return null;
                }
                resourceId = resourceId.substring(0, numPos);
            }
            return new FileInfo(type, resourceId);
        }
    }

    class InserterImpl implements DiskStorage.Inserter {
        private final String mResourceId;
        final File mTemporaryFile;

        public InserterImpl(String resourceId, File temporaryFile) {
            this.mResourceId = resourceId;
            this.mTemporaryFile = temporaryFile;
        }

        @Override // com.facebook.cache.disk.DiskStorage.Inserter
        public void writeData(WriterCallback callback, Object debugInfo) throws IOException {
            try {
                FileOutputStream fileStream = new FileOutputStream(this.mTemporaryFile);
                try {
                    CountingOutputStream countingStream = new CountingOutputStream(fileStream);
                    callback.write(countingStream);
                    countingStream.flush();
                    long length = countingStream.getCount();
                    fileStream.close();
                    if (this.mTemporaryFile.length() != length) {
                        throw new IncompleteFileException(length, this.mTemporaryFile.length());
                    }
                } catch (Throwable th) {
                    fileStream.close();
                    throw th;
                }
            } catch (FileNotFoundException fne) {
                DefaultDiskStorage.this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_UPDATE_FILE_NOT_FOUND, DefaultDiskStorage.TAG, "updateResource", fne);
                throw fne;
            }
        }

        @Override // com.facebook.cache.disk.DiskStorage.Inserter
        public BinaryResource commit(Object debugInfo) throws IOException {
            CacheErrorLogger.CacheErrorCategory category;
            File targetFile = DefaultDiskStorage.this.getContentFileFor(this.mResourceId);
            try {
                FileUtils.rename(this.mTemporaryFile, targetFile);
                if (targetFile.exists()) {
                    targetFile.setLastModified(DefaultDiskStorage.this.mClock.now());
                }
                return FileBinaryResource.createOrNull(targetFile);
            } catch (FileUtils.RenameException re) {
                Throwable cause = re.getCause();
                if (cause == null) {
                    category = CacheErrorLogger.CacheErrorCategory.WRITE_RENAME_FILE_OTHER;
                } else if (cause instanceof FileUtils.ParentDirNotFoundException) {
                    category = CacheErrorLogger.CacheErrorCategory.WRITE_RENAME_FILE_TEMPFILE_PARENT_NOT_FOUND;
                } else if (cause instanceof FileNotFoundException) {
                    category = CacheErrorLogger.CacheErrorCategory.WRITE_RENAME_FILE_TEMPFILE_NOT_FOUND;
                } else {
                    category = CacheErrorLogger.CacheErrorCategory.WRITE_RENAME_FILE_OTHER;
                }
                DefaultDiskStorage.this.mCacheErrorLogger.logError(category, DefaultDiskStorage.TAG, "commit", re);
                throw re;
            }
        }

        @Override // com.facebook.cache.disk.DiskStorage.Inserter
        public boolean cleanUp() {
            return !this.mTemporaryFile.exists() || this.mTemporaryFile.delete();
        }
    }
}
