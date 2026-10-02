package com.ixiaocong.smarthome.phone.android.widget.sharedialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BottomDialog {
    private CustomDialog customDialog;

    public BottomDialog(Context context) {
        this.customDialog = new CustomDialog(context);
    }

    public BottomDialog title(String title) {
        this.customDialog.title(title);
        return this;
    }

    public BottomDialog inflateMenu(int menu, OnItemClickListener onItemClickListener) {
        this.customDialog.inflateMenu(menu, onItemClickListener);
        return this;
    }

    public BottomDialog orientation(int orientation) {
        this.customDialog.orientation(orientation);
        return this;
    }

    public void show() {
        this.customDialog.show();
    }

    private final class CustomDialog extends Dialog {
        private DialogAdapter adapter;
        private LinearLayout background;
        private LinearLayout container;
        private int layout;
        private int leftIcon;
        private int leftPadding;
        private int orientation;
        private int padding;
        private TextView titleView;
        private int topIcon;
        private int topPadding;

        CustomDialog(Context context) {
            super(context, R.style.BottomDialog);
            init();
        }

        private void init() {
            this.padding = getContext().getResources().getDimensionPixelSize(R.dimen.app_normal_margin);
            this.topPadding = getContext().getResources().getDimensionPixelSize(R.dimen.app_tiny_margin);
            this.leftPadding = getContext().getResources().getDimensionPixelSize(R.dimen.app_normal_margin);
            this.topIcon = getContext().getResources().getDimensionPixelSize(R.dimen.bottom_dialog_top_icon);
            this.leftIcon = getContext().getResources().getDimensionPixelSize(R.dimen.bottom_dialog_left_icon);
            setContentView(R.layout.layout_bottom_dialog);
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            getWindow().setGravity(80);
            getWindow().setLayout(-1, -2);
            this.background = (LinearLayout) findViewById(R.id.background);
            this.titleView = (TextView) findViewById(2131296878);
            this.container = (LinearLayout) findViewById(2131296396);
            findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.widget.sharedialog.BottomDialog.CustomDialog.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CustomDialog.this.dismiss();
                }
            });
        }

        void addItems(List<Item> items, OnItemClickListener onItemClickListener) {
            RecyclerView.LayoutManager manager;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -2);
            this.adapter = new DialogAdapter(items, this.layout, this.orientation);
            this.adapter.setItemClick(onItemClickListener);
            if (this.layout != 0 && this.layout == 1) {
                manager = new GridLayoutManager(getContext(), 5, this.orientation, false);
            } else {
                manager = new LinearLayoutManager(getContext(), this.orientation, false);
            }
            RecyclerView recyclerView = new RecyclerView(getContext());
            recyclerView.setLayoutParams(params);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(this.adapter);
            this.container.addView(recyclerView);
        }

        public void title(String title) {
            this.titleView.setText(title);
            this.titleView.setVisibility(0);
        }

        public void orientation(int orientation) {
            this.orientation = orientation;
            if (this.adapter != null) {
                this.adapter.setOrientation(orientation);
            }
        }

        @SuppressLint({"RestrictedApi"})
        void inflateMenu(int menu, OnItemClickListener onItemClickListener) {
            MenuInflater menuInflater = new SupportMenuInflater(getContext());
            MenuBuilder menuBuilder = new MenuBuilder(getContext());
            menuInflater.inflate(menu, menuBuilder);
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < menuBuilder.size(); i++) {
                MenuItem menuItem = menuBuilder.getItem(i);
                items.add(new Item(menuItem.getItemId(), menuItem.getTitle().toString(), menuItem.getIcon()));
            }
            addItems(items, onItemClickListener);
        }

        void setItemClick(OnItemClickListener onItemClickListener) {
            this.adapter.setItemClick(onItemClickListener);
        }

        private class DialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
            private OnItemClickListener itemClickListener;
            private int layout;
            private List<Item> mItems = Collections.emptyList();
            private int orientation;

            DialogAdapter(List<Item> mItems, int layout, int orientation) {
                setList(mItems);
                this.layout = layout;
                this.orientation = orientation;
            }

            private void setList(List<Item> items) {
                if (items == null) {
                    items = new ArrayList<>();
                }
                this.mItems = items;
            }

            void setItemClick(OnItemClickListener onItemClickListener) {
                this.itemClickListener = onItemClickListener;
            }

            public void setOrientation(int orientation) {
                this.orientation = orientation;
                notifyDataSetChanged();
            }

            @Override // android.support.v7.widget.RecyclerView.Adapter
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (this.layout == 1) {
                    return new TopHolder(new LinearLayout(parent.getContext()));
                }
                if (this.orientation == 0) {
                    return new TopHolder(new LinearLayout(parent.getContext()));
                }
                return new LeftHolder(new LinearLayout(parent.getContext()));
            }

            @Override // android.support.v7.widget.RecyclerView.Adapter
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final Item item = this.mItems.get(position);
                if (this.layout == 1) {
                    TopHolder topHolder = (TopHolder) holder;
                    topHolder.item.setText(item.getTitle());
                    topHolder.item.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, topHolder.icon(item.getIcon()), (Drawable) null, (Drawable) null);
                    topHolder.item.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.widget.sharedialog.BottomDialog.CustomDialog.DialogAdapter.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            if (DialogAdapter.this.itemClickListener != null) {
                                DialogAdapter.this.itemClickListener.click(item);
                            }
                        }
                    });
                    return;
                }
                if (this.orientation == 0) {
                    TopHolder topHolder2 = (TopHolder) holder;
                    topHolder2.item.setText(item.getTitle());
                    topHolder2.item.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, topHolder2.icon(item.getIcon()), (Drawable) null, (Drawable) null);
                    topHolder2.item.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.widget.sharedialog.BottomDialog.CustomDialog.DialogAdapter.2
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            if (DialogAdapter.this.itemClickListener != null) {
                                DialogAdapter.this.itemClickListener.click(item);
                                CustomDialog.this.dismiss();
                            }
                        }
                    });
                    return;
                }
                LeftHolder leftHolder = (LeftHolder) holder;
                leftHolder.item.setText(item.getTitle());
                leftHolder.item.setCompoundDrawablesWithIntrinsicBounds(leftHolder.icon(item.getIcon()), (Drawable) null, (Drawable) null, (Drawable) null);
                leftHolder.item.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.widget.sharedialog.BottomDialog.CustomDialog.DialogAdapter.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (DialogAdapter.this.itemClickListener != null) {
                            DialogAdapter.this.itemClickListener.click(item);
                        }
                    }
                });
            }

            @Override // android.support.v7.widget.RecyclerView.Adapter
            public int getItemCount() {
                return this.mItems.size();
            }

            class TopHolder extends RecyclerView.ViewHolder {
                private TextView item;

                TopHolder(View view) {
                    super(view);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -2);
                    params.width = Utils.getScreenWidth(CustomDialog.this.getContext()) / 5;
                    this.item = new TextView(view.getContext());
                    this.item.setLayoutParams(params);
                    this.item.setMaxLines(1);
                    this.item.setEllipsize(TextUtils.TruncateAt.END);
                    this.item.setGravity(17);
                    this.item.setTextColor(ContextCompat.getColor(view.getContext(), R.color.gray_font_dark));
                    this.item.setTextSize(0, CustomDialog.this.getContext().getResources().getDimension(R.dimen.font_small));
                    this.item.setCompoundDrawablePadding(CustomDialog.this.topPadding);
                    this.item.setPadding(0, CustomDialog.this.padding, 0, CustomDialog.this.padding);
                    TypedValue typedValue = new TypedValue();
                    view.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
                    this.item.setBackgroundResource(typedValue.resourceId);
                    ((LinearLayout) view).addView(this.item);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public Drawable icon(Drawable drawable) {
                    if (drawable != null) {
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        Drawable resizeIcon = new BitmapDrawable(CustomDialog.this.getContext().getResources(), Bitmap.createScaledBitmap(bitmap, CustomDialog.this.topIcon, CustomDialog.this.topIcon, true));
                        Drawable.ConstantState state = resizeIcon.getConstantState();
                        if (state != null) {
                            resizeIcon = state.newDrawable().mutate();
                        }
                        return DrawableCompat.wrap(resizeIcon);
                    }
                    return null;
                }
            }

            class LeftHolder extends RecyclerView.ViewHolder {
                private TextView item;

                LeftHolder(View view) {
                    super(view);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -2);
                    view.setLayoutParams(params);
                    this.item = new TextView(view.getContext());
                    this.item.setLayoutParams(params);
                    this.item.setMaxLines(1);
                    this.item.setEllipsize(TextUtils.TruncateAt.END);
                    this.item.setGravity(16);
                    this.item.setTextColor(ContextCompat.getColor(view.getContext(), R.color.black));
                    this.item.setTextSize(0, CustomDialog.this.getContext().getResources().getDimension(R.dimen.font_normal));
                    this.item.setCompoundDrawablePadding(CustomDialog.this.leftPadding);
                    this.item.setPadding(CustomDialog.this.padding, CustomDialog.this.padding, CustomDialog.this.padding, CustomDialog.this.padding);
                    TypedValue typedValue = new TypedValue();
                    view.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
                    this.item.setBackgroundResource(typedValue.resourceId);
                    ((LinearLayout) view).addView(this.item);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public Drawable icon(Drawable drawable) {
                    if (drawable != null) {
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        Drawable resizeIcon = new BitmapDrawable(CustomDialog.this.getContext().getResources(), Bitmap.createScaledBitmap(bitmap, CustomDialog.this.leftIcon, CustomDialog.this.leftIcon, true));
                        Drawable.ConstantState state = resizeIcon.getConstantState();
                        if (state != null) {
                            resizeIcon = state.newDrawable().mutate();
                        }
                        return DrawableCompat.wrap(resizeIcon);
                    }
                    return null;
                }
            }
        }
    }
}
