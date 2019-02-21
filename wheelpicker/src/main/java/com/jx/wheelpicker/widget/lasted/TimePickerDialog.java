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

import java.util.Date;

/**
 * 时间选择Dialog
 *
 * @author zhaoxl
 * @date 2018/6/25
 */
public class TimePickerDialog extends Dialog {

    private TimePicker timePicker;
    private TextView tvTitle;

    private TimeBuilder timeBuilder;

    private TimePickerDialog(TimeBuilder builder) {
        super(builder.context, R.style.Dialog);
        this.timeBuilder = builder;
        initializer();
    }

    private void initializer() {
        setContentView(R.layout.include_wheel_picker_dialog);

        FrameLayout flContent = findViewById(R.id.fl_content);

        timePicker = new TimePicker(getContext());
        flContent.addView(timePicker,
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER));

        tvTitle = findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(timeBuilder.title)) {
            tvTitle.setText(timeBuilder.title);
        }
        if (timeBuilder.titleTextSize > 0) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, timeBuilder.titleTextSize);
        }
        if (timeBuilder.itemTextSize > 0) {
            timePicker.setItemTextSize(TypedValue.COMPLEX_UNIT_DIP, timeBuilder.itemTextSize);
        }
        if (timeBuilder.defDate != null) {
            timePicker.setDefaultTime(timeBuilder.defDate);
        }
        timePicker.setTimeMode(timeBuilder.timeMode);
        if (timeBuilder.unit != null) {
            timePicker.setUnit(timeBuilder.unit);
        }
        timePicker.setShowUnit(timeBuilder.showUnit);

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
                if (timeBuilder.onPickTimeListener != null) {
                    timeBuilder.onPickTimeListener.onTimePicked(timePicker,
                            timePicker.getHour(), timePicker.getMinute(), timePicker.getSecond());
                }
            }
        });
        timePicker.setOnTimeChangeListener(new TimePicker.OnTimeChangeListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute, int second) {
                if (timeBuilder.onTimeChangedListener != null) {
                    timeBuilder.onTimeChangedListener.onTimeChanged(timePicker,
                            hour, minute, second);
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
            dialogWindow.setWindowAnimations(R.style.dialogWindowAnimBottom);
            dialogWindow.setGravity(Gravity.BOTTOM);
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            dialogWindow.setAttributes(lp);
        }
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        tvTitle.setText(title);
    }

    public void setDefaultTime(Date date) {
        timePicker.setDefaultTime(date);
    }

    public interface OnPickTimeListener {
        /**
         * 选择后确认的回调
         *
         * @param timePicker TimePicker
         * @param hour       时
         * @param minute     分
         * @param second     秒
         */
        void onTimePicked(TimePicker timePicker, int hour, int minute, int second);
    }

    public interface OnTimeChangedListener {
        /**
         * 滚动时间改变回调
         *
         * @param timePicker TimePicker
         * @param hour       时
         * @param minute     分
         * @param second     秒
         */
        void onTimeChanged(TimePicker timePicker, int hour, int minute, int second);
    }

    public static class TimeBuilder implements DialogBuilder {

        private CharSequence title = "请选择时间";
        private float itemTextSize;
        private float titleTextSize;
        private Context context;
        private Date defDate;
        private int timeMode;
        private OnTimeChangedListener onTimeChangedListener;
        private OnPickTimeListener onPickTimeListener;
        private String[] unit;
        private boolean showUnit = true;

        public TimeBuilder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示文字
         */
        @Override
        public TimeBuilder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        /**
         * 提示标题的文字大小
         *
         * @param titleTextSize dp
         */
        @Override
        public TimeBuilder setTitleTextSize(float titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        /**
         * 滚轮文字大小
         *
         * @param itemTextSize dp
         */
        @Override
        public TimeBuilder setItemTextSize(float itemTextSize) {
            this.itemTextSize = itemTextSize;
            return this;
        }

        /**
         * 滚动监听
         */
        public TimeBuilder setOnTimeChangedListener(OnTimeChangedListener listener) {
            this.onTimeChangedListener = listener;
            return this;
        }

        /**
         * 选中监听
         */
        public TimeBuilder setOnPickTimeListener(OnPickTimeListener listener) {
            this.onPickTimeListener = listener;
            return this;
        }

        /**
         * 设置默认选中日期，如果不设置则显示当天
         * （注：若有最大最小限制，则视情况而定）
         */
        public TimeBuilder setDefaultDate(Date defDate) {
            this.defDate = defDate;
            return this;
        }

        /**
         * 显示的单位（例如：年，月，日）
         */
        public TimeBuilder setUnit(String... unit) {
            this.unit = unit;
            return this;
        }

        /**
         * 是否显示单位（默认显示年、月、日）
         */
        public TimeBuilder setShowUnit(boolean showUnit) {
            this.showUnit = showUnit;
            return this;
        }

        public TimeBuilder setTimeMode(@TimePicker.TimeMode int timeMode) {
            this.timeMode = timeMode;
            return this;
        }

        public TimePickerDialog build() {
            return new TimePickerDialog(this);
        }

    }
}
