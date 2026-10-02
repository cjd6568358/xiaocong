package com.hzy.tvmao.b;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import com.hzy.tvmao.utils.LogUtil;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: compiled from: BaseControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a {
    private static ExecutorService b = Executors.newFixedThreadPool(5);
    protected static HashMap<String, b> a = new HashMap<>();

    /* JADX INFO: compiled from: BaseControl.java */
    public interface c {
        void a(com.hzy.tvmao.b.a.a aVar);
    }

    /* JADX INFO: renamed from: com.hzy.tvmao.b.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: BaseControl.java */
    public abstract class AbstractAsyncTaskC0021a extends b {
        public AbstractAsyncTaskC0021a(c cVar, String str) {
            super(cVar, str);
        }

        @Override // com.hzy.tvmao.b.a.b
        public void a() {
            if (a.this.a()) {
                super.a();
            } else {
                onPostExecute((com.hzy.tvmao.b.a.a) null);
            }
        }
    }

    /* JADX INFO: compiled from: BaseControl.java */
    public abstract class b extends AsyncTask<Void, Integer, com.hzy.tvmao.b.a.a> {
        public c b;
        public String c;

        protected abstract com.hzy.tvmao.b.a.a b();

        public b(c cVar, String str) {
            if (cVar != null) {
                this.b = cVar;
                this.c = str;
                a.a.put(this.c, this);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final com.hzy.tvmao.b.a.a doInBackground(Void... voidArr) {
            try {
                return b();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @SuppressLint({"NewApi"})
        public void a() {
            LogUtil.i("Running Task ======> " + this.c);
            if (com.hzy.tvmao.y.g >= 11) {
                executeOnExecutor(a.b, new Void[0]);
            } else {
                execute(new Void[0]);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(com.hzy.tvmao.b.a.a aVar) {
            if (aVar == null) {
                aVar = new com.hzy.tvmao.b.a.a(0);
            }
            try {
                aVar.a(this.c);
                if (this.b != null) {
                    this.b.a(aVar);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            a.a.remove(this);
        }

        @Override // android.os.AsyncTask
        protected void onCancelled() {
            LogUtil.i("Task: " + this.c + " Has been cancled");
            super.onCancelled();
        }
    }

    protected boolean a() {
        return com.hzy.tvmao.utils.b.a();
    }
}
