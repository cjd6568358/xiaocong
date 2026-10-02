package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import net.sqlcipher.database.SQLiteDatabase;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TaskStackBuilder implements Iterable<Intent> {
    private static final TaskStackBuilderBaseImpl IMPL;
    private final ArrayList<Intent> mIntents = new ArrayList<>();
    private final Context mSourceContext;

    public interface SupportParentable {
        Intent getSupportParentActivityIntent();
    }

    static class TaskStackBuilderBaseImpl {
        TaskStackBuilderBaseImpl() {
        }
    }

    static class TaskStackBuilderApi16Impl extends TaskStackBuilderBaseImpl {
        TaskStackBuilderApi16Impl() {
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 16) {
            IMPL = new TaskStackBuilderApi16Impl();
        } else {
            IMPL = new TaskStackBuilderBaseImpl();
        }
    }

    private TaskStackBuilder(Context a) {
        this.mSourceContext = a;
    }

    public static TaskStackBuilder create(Context context) {
        return new TaskStackBuilder(context);
    }

    public TaskStackBuilder addNextIntent(Intent nextIntent) {
        this.mIntents.add(nextIntent);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TaskStackBuilder addParentStack(Activity activity) {
        Intent parent = null;
        if (activity instanceof SupportParentable) {
            parent = ((SupportParentable) activity).getSupportParentActivityIntent();
        }
        if (parent == null) {
            parent = NavUtils.getParentActivityIntent(activity);
        }
        if (parent != null) {
            ComponentName target = parent.getComponent();
            if (target == null) {
                target = parent.resolveActivity(this.mSourceContext.getPackageManager());
            }
            addParentStack(target);
            addNextIntent(parent);
        }
        return this;
    }

    public TaskStackBuilder addParentStack(ComponentName sourceActivityName) {
        int insertAt = this.mIntents.size();
        try {
            Intent parent = NavUtils.getParentActivityIntent(this.mSourceContext, sourceActivityName);
            while (parent != null) {
                this.mIntents.add(insertAt, parent);
                parent = NavUtils.getParentActivityIntent(this.mSourceContext, parent.getComponent());
            }
            return this;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("TaskStackBuilder", "Bad ComponentName while traversing activity parent metadata");
            throw new IllegalArgumentException(e);
        }
    }

    @Override // java.lang.Iterable
    @Deprecated
    public Iterator<Intent> iterator() {
        return this.mIntents.iterator();
    }

    public void startActivities() {
        startActivities(null);
    }

    public void startActivities(Bundle options) {
        if (this.mIntents.isEmpty()) {
            throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
        }
        Intent[] intents = (Intent[]) this.mIntents.toArray(new Intent[this.mIntents.size()]);
        intents[0] = new Intent(intents[0]).addFlags(268484608);
        if (!ContextCompat.startActivities(this.mSourceContext, intents, options)) {
            Intent topIntent = new Intent(intents[intents.length - 1]);
            topIntent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            this.mSourceContext.startActivity(topIntent);
        }
    }
}
