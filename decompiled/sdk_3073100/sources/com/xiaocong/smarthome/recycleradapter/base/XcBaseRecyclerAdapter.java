package com.xiaocong.smarthome.recycleradapter.base;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.animation.AlphaInAnimation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class XcBaseRecyclerAdapter<T, K extends BaseRecyclerViewHolder> extends RecyclerView.Adapter<K> {
    protected static final String TAG = XcBaseRecyclerAdapter.class.getSimpleName();
    private boolean flag;
    private int mAutoLoadMoreSize;
    protected Context mContext;
    private BaseAnimation mCustomAnimation;
    protected List<T> mData;
    private int mDuration;
    private FrameLayout mEmptyLayout;
    private boolean mFirstOnlyEnable;
    private boolean mFootAndEmptyEnable;
    private LinearLayout mFooterLayout;
    private boolean mHeadAndEmptyEnable;
    private LinearLayout mHeaderLayout;
    private Interpolator mInterpolator;
    private boolean mIsUseEmpty;
    private int mLastPosition;
    protected LayoutInflater mLayoutInflater;
    protected int mLayoutResId;
    private boolean mLoadMoreEnable;
    private LoadMoreView mLoadMoreView;
    private boolean mLoading;
    private boolean mNextLoadEnable;
    private boolean mOpenAnimationEnable;
    private RecyclerView mRecyclerView;
    private RequestLoadMoreListener mRequestLoadMoreListener;
    private BaseAnimation mSelectAnimation;
    private SpanSizeLookup mSpanSizeLookup;

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int i);
    }

    protected abstract void convert(K k, T t);

    public int getLoadMoreViewCount() {
        if (this.mRequestLoadMoreListener == null || !this.mLoadMoreEnable) {
            return 0;
        }
        return ((this.mNextLoadEnable || !this.mLoadMoreView.isLoadEndMoreGone()) && this.mData.size() != 0) ? 1 : 0;
    }

    public XcBaseRecyclerAdapter(int layoutResId, List<T> data) {
        this.mNextLoadEnable = false;
        this.mLoadMoreEnable = false;
        this.mLoading = false;
        this.mLoadMoreView = new SimpleLoadMoreView();
        this.mFirstOnlyEnable = true;
        this.mOpenAnimationEnable = false;
        this.mInterpolator = new LinearInterpolator();
        this.mDuration = 300;
        this.mLastPosition = -1;
        this.mSelectAnimation = new AlphaInAnimation();
        this.mIsUseEmpty = true;
        this.flag = true;
        this.mAutoLoadMoreSize = 1;
        this.mData = data == null ? new ArrayList<>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    public XcBaseRecyclerAdapter(int layoutResId) {
        this(layoutResId, null);
    }

    public void setNewData(List<T> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.mData = data;
        if (this.mRequestLoadMoreListener != null) {
            this.mNextLoadEnable = true;
            this.mLoadMoreEnable = true;
            this.mLoading = false;
            this.mLoadMoreView.setLoadMoreStatus(1);
        }
        this.mLastPosition = -1;
        notifyDataSetChanged();
    }

    public void addData(List<T> newData) {
        this.mData.addAll(newData);
        notifyItemRangeInserted((this.mData.size() - newData.size()) + getHeaderLayoutCount(), newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    private void compatibilityDataSizeChanged(int size) {
        int dataSize = this.mData == null ? 0 : this.mData.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
    }

    public List<T> getData() {
        return this.mData;
    }

    public T getItem(int position) {
        if (position != -1) {
            return this.mData.get(position);
        }
        return null;
    }

    public int getHeaderLayoutCount() {
        return (this.mHeaderLayout == null || this.mHeaderLayout.getChildCount() == 0) ? 0 : 1;
    }

    public int getFooterLayoutCount() {
        return (this.mFooterLayout == null || this.mFooterLayout.getChildCount() == 0) ? 0 : 1;
    }

    public int getEmptyViewCount() {
        return (this.mEmptyLayout == null || this.mEmptyLayout.getChildCount() == 0 || !this.mIsUseEmpty || this.mData.size() != 0) ? 0 : 1;
    }

    public int getItemCount() {
        if (getEmptyViewCount() == 1) {
            int count = 1;
            if (this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                count = 1 + 1;
            }
            if (this.mFootAndEmptyEnable && getFooterLayoutCount() != 0) {
                return count + 1;
            }
            return count;
        }
        return getHeaderLayoutCount() + this.mData.size() + getFooterLayoutCount() + getLoadMoreViewCount();
    }

    public int getItemViewType(int position) {
        if (getEmptyViewCount() == 1) {
            boolean header = this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0;
            switch (position) {
                case 0:
                    return !header ? 1365 : 273;
                case 1:
                    return header ? 1365 : 819;
                case 2:
                    return 819;
                default:
                    return 1365;
            }
        }
        autoLoadMore(position);
        int numHeaders = getHeaderLayoutCount();
        if (position < numHeaders) {
            return 273;
        }
        int adjPosition = position - numHeaders;
        int adapterCount = this.mData.size();
        if (adjPosition < adapterCount) {
            return getDefItemViewType(adjPosition);
        }
        int adjPosition2 = adjPosition - adapterCount;
        int numFooters = getFooterLayoutCount();
        return adjPosition2 < numFooters ? 819 : 546;
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public K onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.mContext = viewGroup.getContext();
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
        switch (i) {
            case 273:
                return (K) createBaseViewHolder(this.mHeaderLayout);
            case 546:
                return (K) getLoadingView(viewGroup);
            case 819:
                return (K) createBaseViewHolder(this.mFooterLayout);
            case 1365:
                return (K) createBaseViewHolder(this.mEmptyLayout);
            default:
                return (K) onCreateDefViewHolder(viewGroup, i);
        }
    }

    private K getLoadingView(ViewGroup viewGroup) {
        K k = (K) createBaseViewHolder(getItemView(this.mLoadMoreView.getLayoutId(), viewGroup));
        ((BaseRecyclerViewHolder) k).itemView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (XcBaseRecyclerAdapter.this.mLoadMoreView.getLoadMoreStatus() == 3) {
                    XcBaseRecyclerAdapter.this.mLoadMoreView.setLoadMoreStatus(1);
                    XcBaseRecyclerAdapter.this.notifyItemChanged(XcBaseRecyclerAdapter.this.getHeaderLayoutCount() + XcBaseRecyclerAdapter.this.mData.size() + XcBaseRecyclerAdapter.this.getFooterLayoutCount());
                }
            }
        });
        return k;
    }

    public void onViewAttachedToWindow(K holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == 1365 || type == 273 || type == 819 || type == 546) {
            setFullSpan(holder);
        } else {
            addAnimation(holder);
        }
    }

    protected void setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        GridLayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = layoutManager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter.2
                public int getSpanSize(int position) {
                    int type = XcBaseRecyclerAdapter.this.getItemViewType(position);
                    if (XcBaseRecyclerAdapter.this.mSpanSizeLookup != null) {
                        return (type == 1365 || type == 273 || type == 819 || type == 546) ? gridManager.getSpanCount() : XcBaseRecyclerAdapter.this.mSpanSizeLookup.getSpanSize(gridManager, position - XcBaseRecyclerAdapter.this.getHeaderLayoutCount());
                    }
                    if (type == 1365 || type == 273 || type == 819 || type == 546) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    public void onBindViewHolder(K holder, int positions) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case 0:
                convert(holder, this.mData.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
                break;
            case 273:
            case 819:
            case 1365:
                break;
            case 546:
                this.mLoadMoreView.convert(holder);
                break;
            default:
                convert(holder, this.mData.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
                break;
        }
    }

    protected K onCreateDefViewHolder(ViewGroup viewGroup, int i) {
        return (K) createBaseViewHolder(viewGroup, this.mLayoutResId);
    }

    protected K createBaseViewHolder(ViewGroup viewGroup, int i) {
        return (K) createBaseViewHolder(getItemView(i, viewGroup));
    }

    protected K createBaseViewHolder(View view) {
        Class instancedGenericKClass = null;
        for (Class<?> superclass = getClass(); instancedGenericKClass == null && superclass != null; superclass = superclass.getSuperclass()) {
            instancedGenericKClass = getInstancedGenericKClass(superclass);
        }
        K k = (K) createGenericKInstance(instancedGenericKClass, view);
        return k != null ? k : (K) new BaseRecyclerViewHolder(view);
    }

    private K createGenericKInstance(Class z, View view) {
        try {
            String buffer = Modifier.toString(z.getModifiers());
            String className = z.getName();
            return (!className.contains("$") || buffer.contains("static")) ? (K) z.getDeclaredConstructor(View.class).newInstance(view) : (K) z.getDeclaredConstructor(getClass(), View.class).newInstance(this, view);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseRecyclerViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                }
            }
        }
        return null;
    }

    public int addHeaderView(View header) {
        return addHeaderView(header, -1);
    }

    public int addHeaderView(View header, int index) {
        return addHeaderView(header, index, 1);
    }

    public int addHeaderView(View header, int index, int orientation) {
        int position;
        if (this.mHeaderLayout == null) {
            this.mHeaderLayout = new LinearLayout(header.getContext());
            if (orientation == 1) {
                this.mHeaderLayout.setOrientation(1);
                this.mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            } else {
                this.mHeaderLayout.setOrientation(0);
                this.mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(-2, -1));
            }
        }
        int childCount = this.mHeaderLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        this.mHeaderLayout.addView(header, index);
        if (this.mHeaderLayout.getChildCount() == 1 && (position = getHeaderViewPosition()) != -1) {
            notifyItemInserted(position);
        }
        return index;
    }

    public int addFooterView(View footer) {
        return addFooterView(footer, -1, 1);
    }

    public int addFooterView(View footer, int index, int orientation) {
        int position;
        if (this.mFooterLayout == null) {
            this.mFooterLayout = new LinearLayout(footer.getContext());
            if (orientation == 1) {
                this.mFooterLayout.setOrientation(1);
                this.mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            } else {
                this.mFooterLayout.setOrientation(0);
                this.mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(-2, -1));
            }
        }
        int childCount = this.mFooterLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        this.mFooterLayout.addView(footer, index);
        if (this.mFooterLayout.getChildCount() == 1 && (position = getFooterViewPosition()) != -1) {
            notifyItemInserted(position);
        }
        return index;
    }

    private int getHeaderViewPosition() {
        return (getEmptyViewCount() != 1 || this.mHeadAndEmptyEnable) ? 0 : -1;
    }

    private int getFooterViewPosition() {
        if (getEmptyViewCount() == 1) {
            int position = 1;
            if (this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                position = 1 + 1;
            }
            if (this.mFootAndEmptyEnable) {
                return position;
            }
            return -1;
        }
        return getHeaderLayoutCount() + this.mData.size();
    }

    private void autoLoadMore(int position) {
        if (getLoadMoreViewCount() != 0 && position >= getItemCount() - this.mAutoLoadMoreSize && this.mLoadMoreView.getLoadMoreStatus() == 1) {
            this.mLoadMoreView.setLoadMoreStatus(2);
            if (!this.mLoading) {
                this.mLoading = true;
                if (this.mRecyclerView != null && this.mRecyclerView.computeVerticalScrollExtent() + this.mRecyclerView.computeVerticalScrollOffset() >= this.mRecyclerView.computeVerticalScrollRange()) {
                    this.mRecyclerView.post(new Runnable() { // from class: com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter.3
                        @Override // java.lang.Runnable
                        public void run() {
                            XcBaseRecyclerAdapter.this.mRequestLoadMoreListener.onLoadMoreRequested();
                        }
                    });
                } else {
                    this.mRequestLoadMoreListener.onLoadMoreRequested();
                }
            }
        }
    }

    private void addAnimation(RecyclerView.ViewHolder holder) {
        BaseAnimation animation;
        if (this.mOpenAnimationEnable) {
            if (!this.mFirstOnlyEnable || holder.getLayoutPosition() > this.mLastPosition) {
                if (this.mCustomAnimation != null) {
                    animation = this.mCustomAnimation;
                } else {
                    animation = this.mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                this.mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    protected void startAnim(Animator anim, int index) {
        anim.setDuration(this.mDuration).start();
        anim.setInterpolator(this.mInterpolator);
    }

    protected View getItemView(int layoutResId, ViewGroup parent) {
        return this.mLayoutInflater.inflate(layoutResId, parent, false);
    }

    public long getItemId(int position) {
        return position;
    }
}
