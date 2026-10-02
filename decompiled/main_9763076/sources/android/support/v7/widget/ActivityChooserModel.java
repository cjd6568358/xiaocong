package android.support.v7.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ActivityChooserModel extends DataSetObservable {
    private final List<ActivityResolveInfo> mActivities;
    private OnChooseActivityListener mActivityChoserModelPolicy;
    private ActivitySorter mActivitySorter;
    boolean mCanReadHistoricalData;
    final Context mContext;
    private final List<HistoricalRecord> mHistoricalRecords;
    private boolean mHistoricalRecordsChanged;
    final String mHistoryFileName;
    private int mHistoryMaxSize;
    private final Object mInstanceLock;
    private Intent mIntent;
    private boolean mReadShareHistoryCalled;
    private boolean mReloadActivities;
    static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
    private static final Object sRegistryLock = new Object();
    private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();

    public interface ActivitySorter {
        void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
    }

    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
    }

    public int getActivityCount() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mActivities.size();
        }
        return size;
    }

    public ResolveInfo getActivity(int index) {
        ResolveInfo resolveInfo;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            resolveInfo = this.mActivities.get(index).resolveInfo;
        }
        return resolveInfo;
    }

    public int getActivityIndex(ResolveInfo activity) {
        int i;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            List<ActivityResolveInfo> activities = this.mActivities;
            int activityCount = activities.size();
            i = 0;
            while (i < activityCount) {
                ActivityResolveInfo currentActivity = activities.get(i);
                if (currentActivity.resolveInfo != activity) {
                    i++;
                }
            }
            i = -1;
        }
        return i;
    }

    public Intent chooseActivity(int index) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == null) {
                return null;
            }
            ensureConsistentState();
            ActivityResolveInfo chosenActivity = this.mActivities.get(index);
            ComponentName chosenName = new ComponentName(chosenActivity.resolveInfo.activityInfo.packageName, chosenActivity.resolveInfo.activityInfo.name);
            Intent choiceIntent = new Intent(this.mIntent);
            choiceIntent.setComponent(chosenName);
            if (this.mActivityChoserModelPolicy != null) {
                Intent choiceIntentCopy = new Intent(choiceIntent);
                boolean handled = this.mActivityChoserModelPolicy.onChooseActivity(this, choiceIntentCopy);
                if (handled) {
                    return null;
                }
            }
            HistoricalRecord historicalRecord = new HistoricalRecord(chosenName, System.currentTimeMillis(), 1.0f);
            addHistoricalRecord(historicalRecord);
            return choiceIntent;
        }
    }

    public void setOnChooseActivityListener(OnChooseActivityListener listener) {
        synchronized (this.mInstanceLock) {
            this.mActivityChoserModelPolicy = listener;
        }
    }

    public ResolveInfo getDefaultActivity() {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            if (!this.mActivities.isEmpty()) {
                return this.mActivities.get(0).resolveInfo;
            }
            return null;
        }
    }

    public void setDefaultActivity(int index) {
        float weight;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            ActivityResolveInfo newDefaultActivity = this.mActivities.get(index);
            ActivityResolveInfo oldDefaultActivity = this.mActivities.get(0);
            if (oldDefaultActivity != null) {
                weight = (oldDefaultActivity.weight - newDefaultActivity.weight) + 5.0f;
            } else {
                weight = 1.0f;
            }
            ComponentName defaultName = new ComponentName(newDefaultActivity.resolveInfo.activityInfo.packageName, newDefaultActivity.resolveInfo.activityInfo.name);
            HistoricalRecord historicalRecord = new HistoricalRecord(defaultName, System.currentTimeMillis(), weight);
            addHistoricalRecord(historicalRecord);
        }
    }

    private void persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        }
        if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false;
            if (!TextUtils.isEmpty(this.mHistoryFileName)) {
                new PersistHistoryAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new ArrayList(this.mHistoricalRecords), this.mHistoryFileName);
            }
        }
    }

    public int getHistorySize() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mHistoricalRecords.size();
        }
        return size;
    }

    private void ensureConsistentState() {
        boolean stateChanged = loadActivitiesIfNeeded();
        boolean stateChanged2 = stateChanged | readHistoricalDataIfNeeded();
        pruneExcessiveHistoricalRecordsIfNeeded();
        if (stateChanged2) {
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
            return false;
        }
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
        return true;
    }

    private boolean loadActivitiesIfNeeded() {
        if (!this.mReloadActivities || this.mIntent == null) {
            return false;
        }
        this.mReloadActivities = false;
        this.mActivities.clear();
        List<ResolveInfo> resolveInfos = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int resolveInfoCount = resolveInfos.size();
        for (int i = 0; i < resolveInfoCount; i++) {
            ResolveInfo resolveInfo = resolveInfos.get(i);
            this.mActivities.add(new ActivityResolveInfo(resolveInfo));
        }
        return true;
    }

    private boolean readHistoricalDataIfNeeded() {
        if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
            return false;
        }
        this.mCanReadHistoricalData = false;
        this.mReadShareHistoryCalled = true;
        readHistoricalDataImpl();
        return true;
    }

    private boolean addHistoricalRecord(HistoricalRecord historicalRecord) {
        boolean added = this.mHistoricalRecords.add(historicalRecord);
        if (added) {
            this.mHistoricalRecordsChanged = true;
            pruneExcessiveHistoricalRecordsIfNeeded();
            persistHistoricalDataIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
        return added;
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int pruneCount = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (pruneCount > 0) {
            this.mHistoricalRecordsChanged = true;
            for (int i = 0; i < pruneCount; i++) {
                this.mHistoricalRecords.remove(0);
            }
        }
    }

    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(String activityName, long time, float weight) {
            this(ComponentName.unflattenFromString(activityName), time, weight);
        }

        public HistoricalRecord(ComponentName activityName, long time, float weight) {
            this.activity = activityName;
            this.time = time;
            this.weight = weight;
        }

        public int hashCode() {
            int result = (this.activity == null ? 0 : this.activity.hashCode()) + 31;
            return (((result * 31) + ((int) (this.time ^ (this.time >>> 32)))) * 31) + Float.floatToIntBits(this.weight);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                HistoricalRecord other = (HistoricalRecord) obj;
                if (this.activity == null) {
                    if (other.activity != null) {
                        return false;
                    }
                } else if (!this.activity.equals(other.activity)) {
                    return false;
                }
                return this.time == other.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(other.weight);
            }
            return false;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            builder.append("; activity:").append(this.activity);
            builder.append("; time:").append(this.time);
            builder.append("; weight:").append(new BigDecimal(this.weight));
            builder.append("]");
            return builder.toString();
        }
    }

    public static final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo;
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                ActivityResolveInfo other = (ActivityResolveInfo) obj;
                return Float.floatToIntBits(this.weight) == Float.floatToIntBits(other.weight);
            }
            return false;
        }

        @Override // java.lang.Comparable
        public int compareTo(ActivityResolveInfo another) {
            return Float.floatToIntBits(another.weight) - Float.floatToIntBits(this.weight);
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            builder.append("resolveInfo:").append(this.resolveInfo.toString());
            builder.append("; weight:").append(new BigDecimal(this.weight));
            builder.append("]");
            return builder.toString();
        }
    }

    private void readHistoricalDataImpl() {
        FileInputStream fis = null;
        try {
            try {
                fis = this.mContext.openFileInput(this.mHistoryFileName);
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(fis, HTTP.UTF_8);
                    for (int type = 0; type != 1 && type != 2; type = parser.next()) {
                    }
                    if (!"historical-records".equals(parser.getName())) {
                        throw new XmlPullParserException("Share records file does not start with historical-records tag.");
                    }
                    List<HistoricalRecord> historicalRecords = this.mHistoricalRecords;
                    historicalRecords.clear();
                    while (true) {
                        int type2 = parser.next();
                        if (type2 == 1) {
                            if (fis != null) {
                                try {
                                    fis.close();
                                    return;
                                } catch (IOException e) {
                                    return;
                                }
                            }
                            return;
                        }
                        if (type2 != 3 && type2 != 4) {
                            String nodeName = parser.getName();
                            if (!"historical-record".equals(nodeName)) {
                                throw new XmlPullParserException("Share records file not well-formed.");
                            }
                            String activity = parser.getAttributeValue(null, "activity");
                            long time = Long.parseLong(parser.getAttributeValue(null, "time"));
                            float weight = Float.parseFloat(parser.getAttributeValue(null, "weight"));
                            HistoricalRecord readRecord = new HistoricalRecord(activity, time, weight);
                            historicalRecords.add(readRecord);
                        }
                    }
                } catch (IOException ioe) {
                    Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, ioe);
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e2) {
                        }
                    }
                } catch (XmlPullParserException xppe) {
                    Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, xppe);
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e3) {
                        }
                    }
                }
            } catch (FileNotFoundException e4) {
            }
        } catch (Throwable th) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e5) {
                }
            }
            throw th;
        }
    }

    private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
        PersistHistoryAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Object... args) {
            List<HistoricalRecord> historicalRecords = (List) args[0];
            String historyFileName = (String) args[1];
            try {
                FileOutputStream fos = ActivityChooserModel.this.mContext.openFileOutput(historyFileName, 0);
                XmlSerializer serializer = Xml.newSerializer();
                try {
                    try {
                        try {
                            try {
                                serializer.setOutput(fos, null);
                                serializer.startDocument(HTTP.UTF_8, true);
                                serializer.startTag(null, "historical-records");
                                int recordCount = historicalRecords.size();
                                for (int i = 0; i < recordCount; i++) {
                                    HistoricalRecord record = historicalRecords.remove(0);
                                    serializer.startTag(null, "historical-record");
                                    serializer.attribute(null, "activity", record.activity.flattenToString());
                                    serializer.attribute(null, "time", String.valueOf(record.time));
                                    serializer.attribute(null, "weight", String.valueOf(record.weight));
                                    serializer.endTag(null, "historical-record");
                                }
                                serializer.endTag(null, "historical-records");
                                serializer.endDocument();
                                ActivityChooserModel.this.mCanReadHistoricalData = true;
                                if (fos != null) {
                                    try {
                                        fos.close();
                                    } catch (IOException e) {
                                    }
                                }
                            } catch (IllegalArgumentException iae) {
                                Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + ActivityChooserModel.this.mHistoryFileName, iae);
                                ActivityChooserModel.this.mCanReadHistoricalData = true;
                                if (fos != null) {
                                    try {
                                        fos.close();
                                    } catch (IOException e2) {
                                    }
                                }
                            }
                        } catch (IllegalStateException ise) {
                            Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + ActivityChooserModel.this.mHistoryFileName, ise);
                            ActivityChooserModel.this.mCanReadHistoricalData = true;
                            if (fos != null) {
                                try {
                                    fos.close();
                                } catch (IOException e3) {
                                }
                            }
                        }
                    } catch (IOException ioe) {
                        Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + ActivityChooserModel.this.mHistoryFileName, ioe);
                        ActivityChooserModel.this.mCanReadHistoricalData = true;
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e4) {
                            }
                        }
                    }
                    return null;
                } catch (Throwable th) {
                    ActivityChooserModel.this.mCanReadHistoricalData = true;
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e5) {
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException fnfe) {
                Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + historyFileName, fnfe);
                return null;
            }
        }
    }
}
