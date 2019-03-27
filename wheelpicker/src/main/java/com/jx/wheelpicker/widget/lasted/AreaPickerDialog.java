package com.jx.wheelpicker.widget.lasted;

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
import com.jx.wheelpicker.widget.model.Area;
import com.jx.wheelpicker.widget.model.City;
import com.jx.wheelpicker.widget.model.Province;

/**
 * 区域选择Dialog
 *
 * @author zhaoxl
 * @date 2018/6/25
 */
public class AreaPickerDialog extends Dialog {
    private static final String TAG = AreaPickerDialog.class.getSimpleName();

    private AreaPicker areaPicker;
    private TextView tvTitle;

    private AreaBuilder areaBuilder;

    private AreaPickerDialog(AreaBuilder builder) {
        super(builder.context, R.style.WP_Dialog);
        this.areaBuilder = builder;
        initializer();
    }

    private void initializer() {
        setContentView(R.layout.wp_include_wheel_picker_dialog);

        FrameLayout flContent = findViewById(R.id.fl_content);

        areaPicker = new AreaPicker(getContext());
        flContent.addView(areaPicker,
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER));

        tvTitle = findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(areaBuilder.title)) {
            tvTitle.setText(areaBuilder.title);
        }
        if (areaBuilder.titleTextSize > 0) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, areaBuilder.titleTextSize);
        }
        if (areaBuilder.itemTextSize > 0) {
            areaPicker.setItemTextSize(TypedValue.COMPLEX_UNIT_DIP, areaBuilder.itemTextSize);
        }
        areaPicker.setAdjustTextSize(areaBuilder.adjustTextSize);
        areaPicker.setShortText(areaBuilder.shortText);

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
                if (areaBuilder.onPickerAreaListener != null) {
                    areaBuilder.onPickerAreaListener.onPickerArea(areaPicker,
                            areaPicker.getProvince(), areaPicker.getCity(), areaPicker.getArea());
                }
            }
        });
        areaPicker.setOnAreaChangeListener(new AreaPicker.OnAreaChangeListener() {
            @Override
            public void onAreaChanged(AreaPicker areaPicker, Province province, City city, Area area) {
                if (areaBuilder.onAreaChangedListener != null) {
                    areaBuilder.onAreaChangedListener.onAreaChanged(areaPicker,
                            province, city, area);
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

    public void setDefaultByCode(String code) {
        areaPicker.setDefaultByCode(code);
    }

    public void setDefaultByName(String name) {
        areaPicker.setDefaultByName(name);
    }

    public interface OnPickerAreaListener {
        /**
         * 选择后确认的回调
         *
         * @param areaPicker AreaPicker
         * @param province   Province
         * @param city       City
         * @param area       Area
         */
        void onPickerArea(AreaPicker areaPicker, Province province, City city, Area area);
    }

    public interface OnAreaChangedListener {
        /**
         * 滚动区域改变回调
         *
         * @param areaPicker AreaPicker
         * @param province   Province
         * @param city       City
         * @param area       Area
         */
        void onAreaChanged(AreaPicker areaPicker, Province province, City city, Area area);
    }

    public static class AreaBuilder implements DialogBuilder {

        private CharSequence title = "请选择地区";
        private float itemTextSize;
        private float titleTextSize;
        private boolean adjustTextSize = true;
        private boolean shortText;
        private Context context;
        private OnAreaChangedListener onAreaChangedListener;
        private OnPickerAreaListener onPickerAreaListener;

        public AreaBuilder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示文字
         */
        @Override
        public AreaBuilder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        /**
         * 提示标题的文字大小
         *
         * @param titleTextSize dp
         */
        @Override
        public AreaBuilder setTitleTextSize(float titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        /**
         * 滚轮文字大小
         *
         * @param itemTextSize dp
         */
        @Override
        public AreaBuilder setItemTextSize(float itemTextSize) {
            this.itemTextSize = itemTextSize;
            return this;
        }

        /**
         * 滚轮文字大小是否自适应调整大小
         *
         * @param adjustTextSize 是否调整文字大小
         */
        public AreaBuilder setAdjustTextSize(boolean adjustTextSize) {
            this.adjustTextSize = adjustTextSize;
            return this;
        }

        /**
         * @deprecated
         */
        public AreaBuilder setShortText(boolean shortText) {
            this.shortText = shortText;
            return this;
        }

        /**
         * 滚动监听
         */
        public AreaBuilder setOnAreaChangedListener(OnAreaChangedListener listener) {
            this.onAreaChangedListener = listener;
            return this;
        }

        /**
         * 选中监听
         */
        public AreaBuilder setOnPickerAreaListener(OnPickerAreaListener listener) {
            this.onPickerAreaListener = listener;
            return this;
        }

        public AreaPickerDialog build() {
            return new AreaPickerDialog(this);
        }

    }
}
