package com.ixiaocong.smarthome.phone.android.detail.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.hzy.tvmao.ir.ac.ACConstants;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.AppDetailSettingManager;
import com.ixiaocong.smarthome.phone.android.common.utils.BitmapUtils;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.complete.view.CircleImageView;
import com.ixiaocong.smarthome.phone.android.config.AppConstans;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.network.interfaces.IHttpRequest;
import com.xiaocong.smarthome.sdk.http.XCAsyncHttpClient;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.callback.XCUploadFileCallBack;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UserDataDetailActivity extends XcBaseActivity implements View.OnClickListener {
    private Bitmap bmGraph;
    private CircleImageView mCivHeadImg;
    private String mFilePath;
    private RelativeLayout mHeadLayout;
    private ImageView mIVBack;
    private RelativeLayout mNicknameLayout;
    private PopupWindow mSelectPopup;
    private TextView mTvNickname;
    private TextView mTvUserId;
    private String mUserId;
    private String mUserName;
    IHttpRequest uploadRequest;

    protected int getLayoutId() {
        return R.layout.activity_detail_user_data;
    }

    protected void initView() {
        this.mIVBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvNickname = (TextView) $(R.id.tv_user_data_nickname_value);
        this.mTvUserId = (TextView) $(R.id.tv_user_data_xc_id_value);
        this.mCivHeadImg = (CircleImageView) $(R.id.civ_user_data_head_img);
        this.mHeadLayout = (RelativeLayout) $(R.id.rl_user_data_detail_head);
        this.mNicknameLayout = (RelativeLayout) $(R.id.rl_user_data_detail_nickname);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initData() {
        this.mUserName = getIntent().getStringExtra("userName");
        this.mUserId = getIntent().getStringExtra("userId");
        String userIcon = getIntent().getStringExtra("userImg");
        if (!TextUtils.isEmpty(this.mUserName)) {
            this.mTvNickname.setText(this.mUserName);
        }
        if (!TextUtils.isEmpty(this.mUserId)) {
            this.mTvUserId.setText(this.mUserId);
        }
        Glide.with((FragmentActivity) this).load(userIcon).error(R.mipmap.head_portrait_icon).fallback(R.mipmap.head_portrait_icon).priority(Priority.NORMAL).dontAnimate().into(this.mCivHeadImg);
    }

    public void addListener() {
        super.addListener();
        this.mIVBack.setOnClickListener(this);
        this.mHeadLayout.setOnClickListener(this);
        this.mNicknameLayout.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.photo_outside_pop_view /* 2131296655 */:
                this.mSelectPopup.dismiss();
                break;
            case R.id.rl_user_data_detail_head /* 2131296721 */:
                showSelectPopup();
                break;
            case R.id.rl_user_data_detail_nickname /* 2131296722 */:
                Intent intent = new Intent(this.mActivity, (Class<?>) EditUserDataActivity.class);
                intent.putExtra("userName", this.mTvNickname.getText().toString());
                startActivityForResult(intent, 100);
                break;
            case R.id.tv_select_photo_album /* 2131297061 */:
                BitmapUtils.getoSystemPhoto(this.mActivity, ACConstants.TAG_WIND_SPEED1);
                this.mSelectPopup.dismiss();
                break;
            case R.id.tv_select_photo_graph /* 2131297062 */:
                if (ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.CAMERA") != 0) {
                    ActivityCompat.requestPermissions(this.mActivity, new String[]{"android.permission.CAMERA"}, 100);
                } else {
                    makeCamera();
                }
                break;
        }
    }

    private void makeCamera() {
        String state = Environment.getExternalStorageState();
        this.mFilePath = this.mActivity.getExternalFilesDir(null).getParent();
        this.mFilePath += "/user.png";
        if (state.equals("mounted")) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            Uri uri = Uri.fromFile(new File(this.mFilePath));
            intent.putExtra("output", uri);
            intent.putExtra("autofocus", true);
            intent.putExtra("fullScreen", false);
            intent.putExtra("showActionIcons", false);
            startActivityForResult(intent, 1004);
        } else {
            ToastUtils.showShort(this.mActivity, "请检查手机是否正确安装SD卡");
        }
        this.mSelectPopup.dismiss();
    }

    private void showSelectPopup() {
        View view = LayoutInflater.from(this.mActivity).inflate(R.layout.layout_select_img_pop, (ViewGroup) null);
        TextView photoGraph = (TextView) view.findViewById(R.id.tv_select_photo_graph);
        photoGraph.setText("拍照");
        TextView photoAlbum = (TextView) view.findViewById(R.id.tv_select_photo_album);
        View outsideView = view.findViewById(R.id.photo_outside_pop_view);
        photoAlbum.setText("相册");
        photoGraph.setOnClickListener(this);
        photoAlbum.setOnClickListener(this);
        outsideView.setOnClickListener(this);
        this.mSelectPopup = new PopupWindow(view, -1, -2);
        this.mSelectPopup.setAnimationStyle(R.style.ActionSheetDialogStyle);
        this.mSelectPopup.setBackgroundDrawable(new ColorDrawable(0));
        this.mSelectPopup.setOutsideTouchable(true);
        this.mSelectPopup.showAtLocation(this.mIVBack, 83, 0, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (data != null) {
                    String nickName = data.getStringExtra("userName");
                    this.mTvNickname.setText(nickName);
                    Intent intent = new Intent();
                    intent.putExtra("userName", this.mTvNickname.getText().toString());
                    setResult(100, intent);
                }
                break;
            case 1004:
                if (resultCode != -1) {
                    ToastUtils.showShort(this.mActivity, "您没有拍取照片");
                } else {
                    XcLogger.e("mFilePath", this.mActivity.getExternalFilesDir(null).getParent() + "/user.png");
                    if (TextUtils.isEmpty(this.mFilePath)) {
                        ToastUtils.showShort(this.mActivity, "拍照失败,请重试");
                    } else {
                        BitmapUtils.startPhoto(this.mActivity, Uri.fromFile(new File(this.mFilePath)));
                    }
                }
                break;
            case ACConstants.TAG_WIND_SPEED1 /* 1005 */:
                if (resultCode != -1) {
                    ToastUtils.showShort(this.mActivity, "您没有选择任何图片");
                } else if (data.getData() != null && resultCode != 0) {
                    BitmapUtils.startPhoto(this.mActivity, data.getData());
                } else {
                    ToastUtils.showShort(this.mActivity, "获取相册照片失败,请重试");
                }
                break;
            case ACConstants.TAG_LR_WIND_MODE1 /* 1006 */:
                if (resultCode == -1) {
                    try {
                        Uri uri = Uri.parse(AppConstans.USER_IMG_URL);
                        this.bmGraph = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        if (this.bmGraph != null) {
                            commitPhoto(this.bmGraph);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                break;
        }
    }

    private void commitPhoto(final Bitmap bmGraph) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("user/modifyPortrait");
        HashMap<String, Object> params = new HashMap<>();
        params.put("image", BitmapUtils.saveBitmapToFile(bmGraph, this.mActivity));
        httpSetting.setParamsMapNoSign(params);
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        httpSetting.setCallback(new XCUploadFileCallBack() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.user.UserDataDetailActivity.1
            public void onFailure(int statusCode, Map<String, String> headers, Throwable throwable) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(UserDataDetailActivity.this.mActivity, "头像更新失败,请稍后重试");
            }

            public void onSuccess(int statusCode, Map<String, String> headers) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(UserDataDetailActivity.this.mActivity, "头像更新成功");
                UserDataDetailActivity.this.mCivHeadImg.setImageBitmap(bmGraph);
            }

            public void onProgress(int bytesWritten, int totalSize) {
                XcLogger.e("UserDataDetailActivity", "onProgress onProgress=" + ((int) (((1.0f * bytesWritten) / totalSize) * 100.0f)));
            }

            public void onCancel() {
                super.onCancel();
            }
        });
        this.uploadRequest = XCAsyncHttpClient.uploadFile(this.mActivity, httpSetting);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == 0) {
                makeCamera();
            } else {
                ToastUtils.showShort(this.mActivity, "请授权App访问相机");
                AppDetailSettingManager.getAppDetailSettingIntent(this.mActivity);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onBackPressed() {
        if (this.mSelectPopup != null && this.mSelectPopup.isShowing()) {
            this.mSelectPopup.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
