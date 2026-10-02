package com.xiaocong.smarthome.httplib.swipeBack;

import android.view.View;

/* JADX INFO: Access modifiers changed from: private */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SwipeBackLayout$ViewDragCallback extends ViewDragHelper.Callback {
    private boolean mIsScrollOverValid;
    final /* synthetic */ SwipeBackLayout this$0;

    private SwipeBackLayout$ViewDragCallback(SwipeBackLayout swipeBackLayout) {
        this.this$0 = swipeBackLayout;
    }

    @Override // com.xiaocong.smarthome.httplib.swipeBack.ViewDragHelper.Callback
    public boolean tryCaptureView(View view, int i) {
        boolean ret = SwipeBackLayout.access$200(this.this$0).isEdgeTouched(SwipeBackLayout.access$100(this.this$0), i);
        if (ret) {
            if (SwipeBackLayout.access$200(this.this$0).isEdgeTouched(1, i)) {
                SwipeBackLayout.access$302(this.this$0, 1);
            } else if (SwipeBackLayout.access$200(this.this$0).isEdgeTouched(2, i)) {
                SwipeBackLayout.access$302(this.this$0, 2);
            } else if (SwipeBackLayout.access$200(this.this$0).isEdgeTouched(8, i)) {
                SwipeBackLayout.access$302(this.this$0, 8);
            }
            if (SwipeBackLayout.access$400(this.this$0) != null && !SwipeBackLayout.access$400(this.this$0).isEmpty()) {
                for (SwipeBackLayout$SwipeListener listener : SwipeBackLayout.access$400(this.this$0)) {
                    listener.onEdgeTouch(SwipeBackLayout.access$300(this.this$0));
                }
            }
            this.mIsScrollOverValid = true;
        }
        boolean directionCheck = false;
        if (SwipeBackLayout.access$100(this.this$0) == 1 || SwipeBackLayout.access$100(this.this$0) == 2) {
            directionCheck = !SwipeBackLayout.access$200(this.this$0).checkTouchSlop(2, i);
        } else if (SwipeBackLayout.access$100(this.this$0) == 8) {
            directionCheck = !SwipeBackLayout.access$200(this.this$0).checkTouchSlop(1, i);
        } else if (SwipeBackLayout.access$100(this.this$0) == 11) {
            directionCheck = true;
        }
        return ret & directionCheck;
    }

    @Override // com.xiaocong.smarthome.httplib.swipeBack.ViewDragHelper.Callback
    public int getViewHorizontalDragRange(View child) {
        return SwipeBackLayout.access$100(this.this$0) & 3;
    }

    @Override // com.xiaocong.smarthome.httplib.swipeBack.ViewDragHelper.Callback
    public int getViewVerticalDragRange(View child) {
        return SwipeBackLayout.access$100(this.this$0) & 8;
    }

    @Override // com.xiaocong.smarthome.httplib.swipeBack.ViewDragHelper.Callback
    public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
        super.onViewPositionChanged(changedView, left, top, dx, dy);
        if ((SwipeBackLayout.access$300(this.this$0) & 1) != 0) {
            SwipeBackLayout.access$502(this.this$0, Math.abs(left / (SwipeBackLayout.access$600(this.this$0).getWidth() + SwipeBackLayout.access$700(this.this$0).getIntrinsicWidth())));
        } else if ((SwipeBackLayout.access$300(this.this$0) & 2) != 0) {
            SwipeBackLayout.access$502(this.this$0, Math.abs(left / (SwipeBackLayout.access$600(this.this$0).getWidth() + SwipeBackLayout.access$800(this.this$0).getIntrinsicWidth())));
        } else if ((SwipeBackLayout.access$300(this.this$0) & 8) != 0) {
            SwipeBackLayout.access$502(this.this$0, Math.abs(top / (SwipeBackLayout.access$600(this.this$0).getHeight() + SwipeBackLayout.access$900(this.this$0).getIntrinsicHeight())));
        }
        SwipeBackLayout.access$1002(this.this$0, left);
        SwipeBackLayout.access$1102(this.this$0, top);
        this.this$0.invalidate();
        if (SwipeBackLayout.access$500(this.this$0) < SwipeBackLayout.access$1200(this.this$0) && !this.mIsScrollOverValid) {
            this.mIsScrollOverValid = true;
        }
        if (SwipeBackLayout.access$400(this.this$0) != null && !SwipeBackLayout.access$400(this.this$0).isEmpty() && SwipeBackLayout.access$200(this.this$0).getViewDragState() == 1 && SwipeBackLayout.access$500(this.this$0) >= SwipeBackLayout.access$1200(this.this$0) && this.mIsScrollOverValid) {
            this.mIsScrollOverValid = false;
            for (SwipeBackLayout$SwipeListener listener : SwipeBackLayout.access$400(this.this$0)) {
                listener.onScrollOverThreshold();
            }
        }
        if (SwipeBackLayout.access$500(this.this$0) >= 1.0f && !SwipeBackLayout.access$1300(this.this$0).isFinishing()) {
            SwipeBackLayout.access$1300(this.this$0).finish();
            SwipeBackLayout.access$1300(this.this$0).overridePendingTransition(0, 0);
        }
    }

    @Override // com.xiaocong.smarthome.httplib.swipeBack.ViewDragHelper.Callback
    public void onViewReleased(View releasedChild, float xvel, float yvel) {
        int childWidth = releasedChild.getWidth();
        int childHeight = releasedChild.getHeight();
        int left = 0;
        int top = 0;
        if ((SwipeBackLayout.access$300(this.this$0) & 1) != 0) {
            left = (xvel > 0.0f || (xvel == 0.0f && SwipeBackLayout.access$500(this.this$0) > SwipeBackLayout.access$1200(this.this$0))) ? SwipeBackLayout.access$700(this.this$0).getIntrinsicWidth() + childWidth + 10 : 0;
        } else if ((SwipeBackLayout.access$300(this.this$0) & 2) != 0) {
            left = (xvel < 0.0f || (xvel == 0.0f && SwipeBackLayout.access$500(this.this$0) > SwipeBackLayout.access$1200(this.this$0))) ? -(SwipeBackLayout.access$700(this.this$0).getIntrinsicWidth() + childWidth + 10) : 0;
        } else if ((SwipeBackLayout.access$300(this.this$0) & 8) != 0) {
            top = (yvel < 0.0f || (yvel == 0.0f && SwipeBackLayout.access$500(this.this$0) > SwipeBackLayout.access$1200(this.this$0))) ? -(SwipeBackLayout.access$900(this.this$0).getIntrinsicHeight() + childHeight + 10) : 0;
        }
        SwipeBackLayout.access$200(this.this$0).settleCapturedViewAt(left, top);
        this.this$0.invalidate();
    }

    @Override // com.xiaocong.smarthome.httplib.swipeBack.ViewDragHelper.Callback
    public int clampViewPositionHorizontal(View child, int left, int dx) {
        if ((SwipeBackLayout.access$300(this.this$0) & 1) != 0) {
            int ret = Math.min(child.getWidth(), Math.max(left, 0));
            return ret;
        }
        if ((SwipeBackLayout.access$300(this.this$0) & 2) == 0) {
            return 0;
        }
        int ret2 = Math.min(0, Math.max(left, -child.getWidth()));
        return ret2;
    }

    @Override // com.xiaocong.smarthome.httplib.swipeBack.ViewDragHelper.Callback
    public int clampViewPositionVertical(View child, int top, int dy) {
        if ((SwipeBackLayout.access$300(this.this$0) & 8) == 0) {
            return 0;
        }
        int ret = Math.min(0, Math.max(top, -child.getHeight()));
        return ret;
    }

    @Override // com.xiaocong.smarthome.httplib.swipeBack.ViewDragHelper.Callback
    public void onViewDragStateChanged(int state) {
        super.onViewDragStateChanged(state);
        if (SwipeBackLayout.access$400(this.this$0) != null && !SwipeBackLayout.access$400(this.this$0).isEmpty()) {
            for (SwipeBackLayout$SwipeListener listener : SwipeBackLayout.access$400(this.this$0)) {
                listener.onScrollStateChange(state, SwipeBackLayout.access$500(this.this$0));
            }
        }
    }
}
