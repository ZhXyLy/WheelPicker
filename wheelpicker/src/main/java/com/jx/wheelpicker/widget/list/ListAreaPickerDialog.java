package com.jx.wheelpicker.widget.list;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jx.wheelpicker.R;
import com.jx.wheelpicker.widget.lasted.DialogBuilder;
import com.jx.wheelpicker.widget.model.Area;
import com.jx.wheelpicker.widget.model.City;
import com.jx.wheelpicker.widget.model.Province;

/**
 * @author zhaoxl
 * @date 19/5/13
 */
public class ListAreaPickerDialog extends Dialog {

    private ListAreaPicker listAreaPicker;
    private TextView tvTitle;

    private ListAreaBuilder listAreaBuilder;

    private ListAreaPickerDialog(ListAreaBuilder builder) {
        super(builder.context, R.style.WP_Dialog);
        this.listAreaBuilder = builder;
        initializer();
    }

    private void initializer() {
        setContentView(R.layout.list_include_picker_dialog);

        FrameLayout flContent = findViewById(R.id.fl_content);

        listAreaPicker = new ListAreaPicker(getContext());
        flContent.addView(listAreaPicker,
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.6f),
                        Gravity.CENTER));

        tvTitle = findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(listAreaBuilder.title)) {
            tvTitle.setText(listAreaBuilder.title);
        }
        if (listAreaBuilder.titleTextSize > 0) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, listAreaBuilder.titleTextSize);
        }
        if (listAreaBuilder.itemTextSize > 0) {
            listAreaPicker.setItemTextSize(listAreaBuilder.itemTextSize);
        }

        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listAreaBuilder.onPickerAreaListener != null) {
                    listAreaBuilder.onPickerAreaListener.onPickerArea(listAreaPicker,
                            listAreaPicker.getProvince(), listAreaPicker.getCity(), listAreaPicker.getArea());
                }
            }
        });
        listAreaPicker.setOnAreaCheckedListener(new ListAreaPicker.OnAreaCheckedListener() {
            @Override
            public void onAreaChecked(ListAreaPicker listAreaPicker, Province province, City city, Area area) {
                if (listAreaBuilder.onAreaChangedListener != null) {
                    listAreaBuilder.onAreaChangedListener.onAreaChanged(listAreaPicker, province, city, area);
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();

        Window dialogWindow = getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            dialogWindow.setWindowAnimations(R.style.WP_dialogWindowAnimBottom);
            dialogWindow.setGravity(Gravity.BOTTOM);
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            dialogWindow.setAttributes(lp);
        }
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        tvTitle.setText(title);
    }

    public static class ListAreaBuilder implements DialogBuilder {

        private CharSequence title = "请选择地区";
        private float itemTextSize;
        private float titleTextSize;
        private Context context;
        private OnPickerAreaListener onPickerAreaListener;
        private OnAreaChangedListener onAreaChangedListener;

        public ListAreaBuilder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示文字
         */
        @Override
        public ListAreaBuilder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        /**
         * 提示标题的文字大小
         *
         * @param titleTextSize dp
         */
        @Override
        public ListAreaBuilder setTitleTextSize(float titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        /**
         * 滚轮文字大小
         *
         * @param itemTextSize dp
         */
        @Override
        public ListAreaBuilder setItemTextSize(float itemTextSize) {
            this.itemTextSize = itemTextSize;
            return this;
        }

        /**
         * 这是无用的方法
         */
        @Deprecated
        @Override
        public DialogBuilder setItemSpace(int itemSpace) {
            return this;
        }


        /**
         * 滚动监听
         */
        public ListAreaBuilder setOnAreaChangedListener(OnAreaChangedListener listener) {
            this.onAreaChangedListener = listener;
            return this;
        }

        /**
         * 选中监听
         */
        public ListAreaBuilder setOnPickerAreaListener(OnPickerAreaListener listener) {
            this.onPickerAreaListener = listener;
            return this;
        }

        public ListAreaPickerDialog build() {
            return new ListAreaPickerDialog(this);
        }

    }

    /**
     * 根据省市区code确认默认值
     *
     * @param code 省市区code
     */
    public void setDefaultByCode(String code) {
        listAreaPicker.setDefaultByCode(code);
    }

    /**
     * 通过省市区名称确定默认值
     * <p>
     * 首选{@link #setDefaultByCode(String)}}
     *
     * @param provinceName 省
     * @param cityName     市
     * @param areaName     区
     */
    public void setDefaultByName(String provinceName, String cityName, String areaName) {
        listAreaPicker.setDefaultByName(provinceName, cityName, areaName);
    }

    /**
     * 通过省市区名称确定默认值
     * <p>
     * 首选{@link #setDefaultByCode(String)}}
     * 最好用{@link #setDefaultByName(String, String, String)} ()},更准确
     *
     * @param ssqName 省市区全名
     */
    public void setDefaultByAllName(String ssqName) {
        listAreaPicker.setDefaultByAllName(ssqName);
    }

    public interface OnPickerAreaListener {
        /**
         * 选择后确认的回调
         *
         * @param listAreaPicker {@link ListAreaPicker}
         * @param province       {@link Province}
         * @param city           {@link City}
         * @param area           {@link Area}
         */
        void onPickerArea(ListAreaPicker listAreaPicker, Province province, City city, Area area);
    }

    public interface OnAreaChangedListener {
        /**
         * 滚动区域改变回调
         *
         * @param listAreaPicker {@link ListAreaPicker}
         * @param province       {@link Province}
         * @param city           {@link City}
         * @param area           {@link Area}
         */
        void onAreaChanged(ListAreaPicker listAreaPicker, Province province, City city, Area area);
    }
}
