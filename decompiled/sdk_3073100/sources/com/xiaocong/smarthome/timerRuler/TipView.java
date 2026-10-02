package com.xiaocong.smarthome.timerRuler;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.xiaocong.smarthome.uilib.R;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TipView extends RelativeLayout {
    private Context context;

    @SuppressLint({"HandlerLeak"})
    private Handler handler;
    private boolean isShowLeftTip;
    private boolean isShowLeftTipLandscape;
    private boolean isShowRightTip;
    private boolean isShowRightTipLandscape;
    private ImageView ivLeft;
    private ImageView ivRight;
    private ImageView ivTipLeft;
    private ImageView ivTipLeftLandscape;
    private ImageView ivTipRight;
    private ImageView ivTipRightLandscape;
    private ObjectAnimator leftAnimation;
    private ObjectAnimator rightAnimation;
    private Timer timer;

    public TipView(Context context) {
        this(context, null);
    }

    public TipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isShowLeftTip = false;
        this.isShowRightTip = false;
        this.isShowLeftTipLandscape = false;
        this.isShowRightTipLandscape = false;
        this.handler = new Handler() { // from class: com.xiaocong.smarthome.timerRuler.TipView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (TipView.this.context != null) {
                    switch (msg.what) {
                        case 446:
                            TipView.this.setShowRightTipLandscape(false);
                            break;
                        case 447:
                            TipView.this.setShowLeftTip(false);
                            break;
                        case 448:
                            TipView.this.setShowLeftTipLandscape(false);
                            break;
                        case 449:
                            TipView.this.setShowRightTip(false);
                            break;
                    }
                }
            }
        };
        this.context = context;
        View inflate = View.inflate(context, R.layout.tip_layout, null);
        addView(inflate);
        this.ivLeft = (ImageView) inflate.findViewById(R.id.iv_left);
        this.ivRight = (ImageView) inflate.findViewById(R.id.iv_right);
        this.ivTipLeft = (ImageView) inflate.findViewById(R.id.iv_tip_left);
        this.ivTipRight = (ImageView) inflate.findViewById(R.id.iv_tip_right);
        this.ivTipLeftLandscape = (ImageView) inflate.findViewById(R.id.iv_tip_left_landscape);
        this.ivTipRightLandscape = (ImageView) inflate.findViewById(R.id.iv_tip_right_landscape);
        this.leftAnimation = ObjectAnimator.ofFloat(this.ivLeft, "Alpha", 0.0f, 1.0f, 0.0f, 1.0f);
        this.rightAnimation = ObjectAnimator.ofFloat(this.ivRight, "Alpha", 0.0f, 1.0f, 0.0f, 1.0f);
        this.leftAnimation.setDuration(3000L);
        this.rightAnimation.setDuration(3000L);
        this.leftAnimation.setRepeatMode(1);
        this.rightAnimation.setRepeatMode(1);
        setShowLeftTip(false);
        setShowRightTip(false);
    }

    public void setShowLeftTip(boolean showLeftTip) {
        this.isShowLeftTip = showLeftTip;
        hideLandscapeTip();
        if (this.isShowLeftTip) {
            this.ivTipLeft.setVisibility(0);
            this.ivLeft.setVisibility(0);
            this.ivTipRight.setVisibility(8);
            this.ivRight.setVisibility(8);
            this.leftAnimation.start();
            if (this.timer != null) {
                this.timer.cancel();
            }
            this.timer = new Timer();
            this.timer.schedule(new TimerTask() { // from class: com.xiaocong.smarthome.timerRuler.TipView.2
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    TipView.this.handler.sendEmptyMessage(447);
                }
            }, 3000L);
            return;
        }
        this.ivTipLeft.setVisibility(8);
        this.ivLeft.setVisibility(8);
        this.leftAnimation.cancel();
    }

    public void setShowRightTip(boolean showRightTip) {
        this.isShowRightTip = showRightTip;
        hideLandscapeTip();
        if (this.isShowRightTip) {
            this.ivRight.setVisibility(0);
            this.ivTipRight.setVisibility(0);
            this.ivTipLeft.setVisibility(8);
            this.ivLeft.setVisibility(8);
            this.rightAnimation.start();
            if (this.timer != null) {
                this.timer.cancel();
            }
            this.timer = new Timer();
            this.timer.schedule(new TimerTask() { // from class: com.xiaocong.smarthome.timerRuler.TipView.3
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    TipView.this.handler.sendEmptyMessage(449);
                }
            }, 3000L);
            return;
        }
        this.ivRight.setVisibility(8);
        this.ivTipRight.setVisibility(8);
        this.rightAnimation.cancel();
    }

    private void hideLandscapeTip() {
        this.ivTipRightLandscape.setVisibility(8);
        this.ivTipLeftLandscape.setVisibility(8);
    }

    private void hideTip() {
        this.ivTipRight.setVisibility(8);
        this.ivTipLeft.setVisibility(8);
    }

    public void setShowLeftTipLandscape(boolean showLeftTipLandscape) {
        this.isShowLeftTipLandscape = showLeftTipLandscape;
        hideTip();
        if (this.isShowLeftTipLandscape) {
            this.ivLeft.setVisibility(0);
            this.ivTipLeftLandscape.setVisibility(0);
            this.ivTipRightLandscape.setVisibility(8);
            this.ivRight.setVisibility(8);
            this.leftAnimation.start();
            if (this.timer != null) {
                this.timer.cancel();
            }
            this.timer = new Timer();
            this.timer.schedule(new TimerTask() { // from class: com.xiaocong.smarthome.timerRuler.TipView.4
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    TipView.this.handler.sendEmptyMessage(448);
                }
            }, 3000L);
            return;
        }
        this.ivLeft.setVisibility(8);
        this.ivTipLeftLandscape.setVisibility(8);
        this.leftAnimation.cancel();
    }

    public void setShowRightTipLandscape(boolean showRightTipLandscape) {
        this.isShowRightTipLandscape = showRightTipLandscape;
        hideTip();
        if (this.isShowRightTipLandscape) {
            this.ivRight.setVisibility(0);
            this.ivTipRightLandscape.setVisibility(0);
            this.ivTipLeftLandscape.setVisibility(8);
            this.ivLeft.setVisibility(8);
            this.rightAnimation.start();
            if (this.timer != null) {
                this.timer.cancel();
            }
            this.timer = new Timer();
            this.timer.schedule(new TimerTask() { // from class: com.xiaocong.smarthome.timerRuler.TipView.5
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    TipView.this.handler.sendEmptyMessage(446);
                }
            }, 3000L);
            return;
        }
        this.ivRight.setVisibility(8);
        this.ivTipRightLandscape.setVisibility(8);
        this.rightAnimation.cancel();
    }
}
