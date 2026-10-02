package com.ixiaocong.smarthome.phone.android.complete.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ScreenUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TitleBarView extends RelativeLayout implements View.OnClickListener {
    boolean backable;
    TitlebarCallback callback;
    private ImageView centerimage;
    private TextView centertv;
    private ImageView leftimg;
    private TextView lefttv;
    private Context mContext;
    private ImageView rightimg;
    private TextView righttv;
    private RelativeLayout titleView;

    public TitleBarView(Context context) {
        super(context);
        this.backable = true;
        this.callback = null;
        this.mContext = context;
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.backable = true;
        this.callback = null;
        this.mContext = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(this.mContext).inflate(R.layout.title_bar_view, this);
        this.titleView = (RelativeLayout) findViewById(R.id.title_view);
        this.leftimg = (ImageView) findViewById(R.id.left_titlebar_image);
        this.rightimg = (ImageView) findViewById(R.id.right_titlebar_image);
        this.centertv = (TextView) findViewById(R.id.centertxt_titlebar);
        this.lefttv = (TextView) findViewById(R.id.left_titlebar_text);
        this.righttv = (TextView) findViewById(R.id.right_titlebar_text);
        int height = ScreenUtils.getStatusHeight(this.mContext);
        findViewById(R.id.title_statusbar_view).setLayoutParams(new RelativeLayout.LayoutParams(-1, height));
        this.centerimage = (ImageView) findViewById(R.id.centerimage_titlebar);
        this.leftimg.setOnClickListener(this);
        this.rightimg.setOnClickListener(this);
        this.lefttv.setOnClickListener(this);
        this.righttv.setOnClickListener(this);
        this.lefttv.setVisibility(8);
        this.righttv.setVisibility(8);
        this.leftimg.setVisibility(8);
        this.rightimg.setVisibility(8);
        TypedArray a = this.mContext.obtainStyledAttributes(attrs, R.styleable.titlebar);
        String titletext = a.getString(1);
        String righttext = a.getString(5);
        String lefttext = a.getString(3);
        Drawable leftd = a.getDrawable(2);
        Drawable rightd = a.getDrawable(4);
        Drawable centertd = a.getDrawable(0);
        int titlBg = a.getColor(6, 0);
        if (titlBg != 0) {
            this.titleView.setBackgroundColor(a.getColor(6, 0));
        }
        if (leftd != null) {
            this.leftimg.setVisibility(0);
            this.leftimg.setImageDrawable(leftd);
        }
        if (rightd != null) {
            this.rightimg.setVisibility(0);
            this.rightimg.setImageDrawable(rightd);
        }
        if (centertd != null) {
            this.centerimage.setVisibility(0);
            this.centerimage.setImageDrawable(rightd);
        }
        if (!TextUtils.isEmpty(titletext)) {
            this.centertv.setVisibility(0);
            this.centertv.setText(titletext);
        }
        if (!TextUtils.isEmpty(lefttext)) {
            this.lefttv.setText(lefttext);
            this.lefttv.setVisibility(0);
        }
        if (!TextUtils.isEmpty(righttext)) {
            this.righttv.setText(righttext);
            this.righttv.setVisibility(0);
        }
        a.recycle();
    }

    public void setTitleBarCall(TitlebarCallback callback) {
        this.callback = callback;
    }

    public void setTitleText(String txtRes) {
        this.centerimage.setVisibility(8);
        this.centertv.setVisibility(0);
        this.centertv.setText(txtRes);
    }

    public ImageView getTitleBarLeftImage() {
        return this.leftimg;
    }

    public ImageView getTitleBarRightImage() {
        return this.rightimg;
    }

    public TextView getTitleBarRightTv() {
        return this.righttv;
    }

    public TextView getTitleBarLeftTv() {
        return this.lefttv;
    }

    public void setLeftText(int id) {
        this.leftimg.setVisibility(8);
        this.lefttv.setVisibility(0);
        this.lefttv.setText(this.mContext.getString(id));
    }

    public void setRightText(int id) {
        this.rightimg.setVisibility(8);
        this.righttv.setVisibility(0);
        this.righttv.setText(this.mContext.getString(id));
    }

    public void setLeftImg(int id) {
        this.lefttv.setVisibility(8);
        this.leftimg.setVisibility(0);
        this.leftimg.setImageResource(id);
    }

    public void setRightImg(int id) {
        this.righttv.setVisibility(8);
        this.rightimg.setVisibility(0);
        this.rightimg.setImageResource(id);
    }

    public void setBackable(boolean b) {
        this.backable = b;
    }

    public void setTitleBg(int bg) {
        this.titleView.setBackgroundResource(bg);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0004. Please report as an issue. */
    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
