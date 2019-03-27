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
import com.jx.wheelpicker.widget.model.Data;

import java.util.List;

/**
 * 单选-选择Dialog
 *
 * @author zhaoxl
 * @date 2018/6/25
 */
public class SinglePickerDialog extends Dialog {
    private static final String TAG = "SinglePickerDialog";

    private SinglePicker singlePicker;
    private TextView tvTitle;

    private SingleBuilder singleBuilder;

    private SinglePickerDialog(SingleBuilder builder) {
        super(builder.context, R.style.WP_Dialog);
        this.singleBuilder = builder;
        initializer();
    }

    private void initializer() {
        setContentView(R.layout.wp_include_wheel_picker_dialog);

        FrameLayout flContent = findViewById(R.id.fl_content);

        singlePicker = new SinglePicker(getContext());
        flContent.addView(singlePicker,
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER));

        tvTitle = findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(singleBuilder.title)) {
            tvTitle.setText(singleBuilder.title);
        }
        if (singleBuilder.titleTextSize > 0) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, singleBuilder.titleTextSize);
        }
        if (singleBuilder.itemTextSize > 0) {
            singlePicker.setItemTextSize(TypedValue.COMPLEX_UNIT_DIP, singleBuilder.itemTextSize);
        }
        if (singleBuilder.defaultText != null) {
            singlePicker.setDefaultByText(singleBuilder.defaultText);
        }
        if (singleBuilder.defaultId != null) {
            singlePicker.setDefaultById(singleBuilder.defaultId);
        }
        if (singleBuilder.unit != null) {
            singlePicker.setUnit(singleBuilder.unit);
        }
        singlePicker.setShowUnit(singleBuilder.showUnit);
        singlePicker.updateData(singleBuilder.data);

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
                if (singleBuilder.onPickListener != null) {
                    singleBuilder.onPickListener.onPicked(singlePicker, singlePicker.getCurrentData());
                }
            }
        });
        singlePicker.setOnSingleChangeListener(new SinglePicker.OnSingleChangeListener() {
            @Override
            public void onSingleChanged(SinglePicker singlePicker, Data data) {
                if (singleBuilder.onChangedListener != null) {
                    singleBuilder.onChangedListener.onChanged(singlePicker, data);
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

    public void setDefaultById(String id) {
        singlePicker.setDefaultById(id);
    }

    public void setDefaultByText(String text) {
        singlePicker.setDefaultByText(text);
    }

    public void updateData(List<? extends Data> data) {
        singlePicker.updateData(data);
    }

    public interface OnPickListener {
        /**
         * 选择后确认的回调
         *
         * @param singlePicker SinglePicker
         * @param data         Data
         */
        void onPicked(SinglePicker singlePicker, Data data);
    }

    public interface OnChangedListener {
        /**
         * 滚动改变回调
         *
         * @param singlePicker SinglePicker
         * @param data         Data
         */
        void onChanged(SinglePicker singlePicker, Data data);
    }

    public static class SingleBuilder implements DialogBuilder {

        private CharSequence title = "";
        private float itemTextSize;
        private float titleTextSize;
        private Context context;
        private String defaultId;
        private String defaultText;
        private OnChangedListener onChangedListener;
        private OnPickListener onPickListener;
        private String unit;
        private boolean showUnit;
        private List<? extends Data> data;

        public SingleBuilder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示文字
         */
        @Override
        public SingleBuilder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        /**
         * 提示标题的文字大小
         *
         * @param titleTextSize dp
         */
        @Override
        public SingleBuilder setTitleTextSize(float titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        /**
         * 滚轮文字大小
         *
         * @param itemTextSize dp
         */
        @Override
        public SingleBuilder setItemTextSize(float itemTextSize) {
            this.itemTextSize = itemTextSize;
            return this;
        }

        /**
         * 滚动监听
         */
        public SingleBuilder setOnChangedListener(OnChangedListener listener) {
            this.onChangedListener = listener;
            return this;
        }

        /**
         * 选中监听
         */
        public SingleBuilder setOnPickListener(OnPickListener listener) {
            this.onPickListener = listener;
            return this;
        }

        /**
         * 设置默认选中
         */
        public SingleBuilder setDefaultById(String id) {
            this.defaultId = id;
            return this;
        }

        public SingleBuilder setDefaultByText(String text) {
            this.defaultText = text;
            return this;
        }

        /**
         * 显示的单位（例如：年，月，日）
         */
        public SingleBuilder setUnit(String unit) {
            this.unit = unit;
            return this;
        }

        /**
         * 是否显示单位（默认显示年、月、日）
         */
        public SingleBuilder setShowUnit(boolean showUnit) {
            this.showUnit = showUnit;
            return this;
        }

        public SingleBuilder setData(List<? extends Data> data) {
            this.data = data;
            return this;
        }

        public SinglePickerDialog build() {
            return new SinglePickerDialog(this);
        }

    }
}
