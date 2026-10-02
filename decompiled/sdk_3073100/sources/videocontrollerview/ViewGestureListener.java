package videocontrollerview;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ViewGestureListener extends GestureDetector.SimpleOnGestureListener {
    private Context context;
    private VideoGestureListener listener;

    public ViewGestureListener(Context context, VideoGestureListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent e) {
        this.listener.onSingleTap();
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float deltaX = e1.getRawX() - e2.getRawX();
        float deltaY = e1.getRawY() - e2.getRawY();
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (Math.abs(deltaX) > 60.0f) {
                this.listener.onHorizontalScroll(deltaX < 0.0f);
                return true;
            }
            return true;
        }
        if (Math.abs(deltaY) > 60.0f) {
            Log.i("ViewGestureListener", "deltaY->" + deltaY);
            if (e1.getX() < (((double) getDeviceWidth(this.context)) * 1.0d) / 5.0d) {
                this.listener.onVerticalScroll(deltaY / getDeviceHeight(this.context), 1);
                return true;
            }
            if (e1.getX() > (((double) getDeviceWidth(this.context)) * 4.0d) / 5.0d) {
                this.listener.onVerticalScroll(deltaY / getDeviceHeight(this.context), 2);
                return true;
            }
            return true;
        }
        return true;
    }

    public static int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.widthPixels;
    }

    public static int getDeviceHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.heightPixels;
    }
}
