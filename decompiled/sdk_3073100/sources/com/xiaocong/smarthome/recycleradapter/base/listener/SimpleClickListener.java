package com.xiaocong.smarthome.recycleradapter.base.listener;

import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class SimpleClickListener implements RecyclerView.OnItemTouchListener {
    public static String TAG = "SimpleClickListener";
    protected XcBaseRecyclerAdapter baseQuickAdapter;
    private GestureDetectorCompat mGestureDetector;
    private boolean mIsPrepressed = false;
    private boolean mIsShowPress = false;
    private View mPressedView = null;
    private RecyclerView recyclerView;

    public abstract void onItemChildClick(XcBaseRecyclerAdapter xcBaseRecyclerAdapter, View view, int i);

    public abstract void onItemChildLongClick(XcBaseRecyclerAdapter xcBaseRecyclerAdapter, View view, int i);

    public abstract void onItemClick(XcBaseRecyclerAdapter xcBaseRecyclerAdapter, View view, int i);

    public abstract void onItemLongClick(XcBaseRecyclerAdapter xcBaseRecyclerAdapter, View view, int i);

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        BaseRecyclerViewHolder vh;
        if (this.recyclerView == null || this.recyclerView != rv) {
            this.recyclerView = rv;
            this.baseQuickAdapter = (XcBaseRecyclerAdapter) this.recyclerView.getAdapter();
            this.mGestureDetector = new GestureDetectorCompat(this.recyclerView.getContext(), new ItemTouchHelperGestureListener(this.recyclerView));
        }
        if (!this.mGestureDetector.onTouchEvent(e) && e.getActionMasked() == 1 && this.mIsShowPress) {
            if (this.mPressedView != null && ((vh = (BaseRecyclerViewHolder) this.recyclerView.getChildViewHolder(this.mPressedView)) == null || !isHeaderOrFooterView(vh.getItemViewType()))) {
                this.mPressedView.setPressed(false);
            }
            this.mIsShowPress = false;
            this.mIsPrepressed = false;
        }
        return false;
    }

    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        this.mGestureDetector.onTouchEvent(e);
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        private RecyclerView recyclerView;

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent e) {
            SimpleClickListener.this.mIsPrepressed = true;
            SimpleClickListener.this.mPressedView = this.recyclerView.findChildViewUnder(e.getX(), e.getY());
            super.onDown(e);
            return false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onShowPress(MotionEvent e) {
            if (SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                SimpleClickListener.this.mIsShowPress = true;
            }
            super.onShowPress(e);
        }

        ItemTouchHelperGestureListener(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent e) {
            if (SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                if (this.recyclerView.getScrollState() != 0) {
                    return false;
                }
                View pressedView = SimpleClickListener.this.mPressedView;
                BaseRecyclerViewHolder vh = (BaseRecyclerViewHolder) this.recyclerView.getChildViewHolder(pressedView);
                if (SimpleClickListener.this.isHeaderOrFooterPosition(vh.getLayoutPosition())) {
                    return false;
                }
                Set<Integer> childClickViewIds = vh.getChildClickViewIds();
                Set<Integer> nestViewIds = vh.getNestViews();
                if (childClickViewIds == null || childClickViewIds.size() <= 0) {
                    SimpleClickListener.this.setPressViewHotSpot(e, pressedView);
                    SimpleClickListener.this.mPressedView.setPressed(true);
                    if (childClickViewIds != null && childClickViewIds.size() > 0) {
                        for (Integer childClickViewId : childClickViewIds) {
                            View childView = pressedView.findViewById(childClickViewId.intValue());
                            if (childView != null) {
                                childView.setPressed(false);
                            }
                        }
                    }
                    SimpleClickListener.this.onItemClick(SimpleClickListener.this.baseQuickAdapter, pressedView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                } else {
                    for (Integer childClickViewId2 : childClickViewIds) {
                        View childView2 = pressedView.findViewById(childClickViewId2.intValue());
                        if (childView2 != null) {
                            if (SimpleClickListener.this.inRangeOfView(childView2, e) && childView2.isEnabled()) {
                                if (nestViewIds != null && nestViewIds.contains(childClickViewId2)) {
                                    return false;
                                }
                                SimpleClickListener.this.setPressViewHotSpot(e, childView2);
                                childView2.setPressed(true);
                                SimpleClickListener.this.onItemChildClick(SimpleClickListener.this.baseQuickAdapter, childView2, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                                resetPressedView(childView2);
                                return true;
                            }
                            childView2.setPressed(false);
                        }
                    }
                    SimpleClickListener.this.setPressViewHotSpot(e, pressedView);
                    SimpleClickListener.this.mPressedView.setPressed(true);
                    for (Integer childClickViewId3 : childClickViewIds) {
                        View childView3 = pressedView.findViewById(childClickViewId3.intValue());
                        if (childView3 != null) {
                            childView3.setPressed(false);
                        }
                    }
                    SimpleClickListener.this.onItemClick(SimpleClickListener.this.baseQuickAdapter, pressedView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                }
                resetPressedView(pressedView);
            }
            return true;
        }

        private void resetPressedView(final View pressedView) {
            if (pressedView != null) {
                pressedView.post(new Runnable() { // from class: com.xiaocong.smarthome.recycleradapter.base.listener.SimpleClickListener.ItemTouchHelperGestureListener.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (pressedView != null) {
                            pressedView.setPressed(false);
                        }
                    }
                });
            }
            SimpleClickListener.this.mIsPrepressed = false;
            SimpleClickListener.this.mPressedView = null;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent e) {
            boolean isChildLongClick = false;
            if (this.recyclerView.getScrollState() == 0 && SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                SimpleClickListener.this.mPressedView.performHapticFeedback(0);
                BaseRecyclerViewHolder vh = (BaseRecyclerViewHolder) this.recyclerView.getChildViewHolder(SimpleClickListener.this.mPressedView);
                if (!SimpleClickListener.this.isHeaderOrFooterPosition(vh.getLayoutPosition())) {
                    Set<Integer> longClickViewIds = vh.getItemChildLongClickViewIds();
                    Set<Integer> nestViewIds = vh.getNestViews();
                    if (longClickViewIds != null && longClickViewIds.size() > 0) {
                        for (Integer longClickViewId : longClickViewIds) {
                            View childView = SimpleClickListener.this.mPressedView.findViewById(longClickViewId.intValue());
                            if (SimpleClickListener.this.inRangeOfView(childView, e) && childView.isEnabled()) {
                                if (nestViewIds == null || !nestViewIds.contains(longClickViewId)) {
                                    SimpleClickListener.this.setPressViewHotSpot(e, childView);
                                    SimpleClickListener.this.onItemChildLongClick(SimpleClickListener.this.baseQuickAdapter, childView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                                    childView.setPressed(true);
                                    SimpleClickListener.this.mIsShowPress = true;
                                    isChildLongClick = true;
                                    break;
                                }
                                isChildLongClick = true;
                                break;
                            }
                        }
                    }
                    if (!isChildLongClick) {
                        SimpleClickListener.this.onItemLongClick(SimpleClickListener.this.baseQuickAdapter, SimpleClickListener.this.mPressedView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                        SimpleClickListener.this.setPressViewHotSpot(e, SimpleClickListener.this.mPressedView);
                        SimpleClickListener.this.mPressedView.setPressed(true);
                        if (longClickViewIds != null) {
                            Iterator<Integer> it = longClickViewIds.iterator();
                            while (it.hasNext()) {
                                SimpleClickListener.this.mPressedView.findViewById(it.next().intValue()).setPressed(false);
                            }
                        }
                        SimpleClickListener.this.mIsShowPress = true;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPressViewHotSpot(MotionEvent e, View mPressedView) {
        if (Build.VERSION.SDK_INT >= 21 && mPressedView != null && mPressedView.getBackground() != null) {
            mPressedView.getBackground().setHotspot(e.getRawX(), e.getY() - mPressedView.getY());
        }
    }

    public boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        if (view == null || !view.isShown()) {
            return false;
        }
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        return ev.getRawX() >= ((float) x) && ev.getRawX() <= ((float) (view.getWidth() + x)) && ev.getRawY() >= ((float) y) && ev.getRawY() <= ((float) (view.getHeight() + y));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isHeaderOrFooterPosition(int position) {
        if (this.baseQuickAdapter == null) {
            if (this.recyclerView == null) {
                return false;
            }
            this.baseQuickAdapter = (XcBaseRecyclerAdapter) this.recyclerView.getAdapter();
        }
        int type = this.baseQuickAdapter.getItemViewType(position);
        return type == 1365 || type == 273 || type == 819 || type == 546;
    }

    private boolean isHeaderOrFooterView(int type) {
        return type == 1365 || type == 273 || type == 819 || type == 546;
    }
}
