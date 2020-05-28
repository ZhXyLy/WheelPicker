package com.jx.wheelpicker.widget.lasted;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
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
 * 日期选择Dialog
 *
 * @author zhaoxl
 * @date 2018/6/25
 */
public class DatePickerDialog extends Dialog {
    private static final String TAG = DatePickerDialog.class.getSimpleName();

    private DatePicker datePicker;
    private TextView tvTitle;

    private DateBuilder dateBuilder;

    private DatePickerDialog(DateBuilder builder) {
        super(builder.context, R.style.WP_Dialog);
        this.dateBuilder = builder;
        initializer();
    }

    private void initializer() {
        setContentView(R.layout.wp_include_wheel_picker_dialog);

        FrameLayout flContent = findViewById(R.id.fl_content);

        datePicker = new DatePicker(getContext());
        flContent.addView(datePicker,
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER));

        tvTitle = findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(dateBuilder.title)) {
            tvTitle.setText(dateBuilder.title);
        }
        if (dateBuilder.titleTextSize > 0) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dateBuilder.titleTextSize);
        }
        if (dateBuilder.itemTextSize > 0) {
            datePicker.setItemTextSize(TypedValue.COMPLEX_UNIT_DIP, dateBuilder.itemTextSize);
        }
        if (dateBuilder.itemSpace > 0) {
            datePicker.setItemSpace(dateBuilder.itemSpace);
        }
        if (dateBuilder.minDate != null || dateBuilder.maxDate != null) {
            datePicker.setRangeDate(dateBuilder.minDate, dateBuilder.maxDate);
        }
        if (dateBuilder.defDate != null) {
            datePicker.setDefaultDate(dateBuilder.defDate);
        }
        datePicker.setShowWeek(dateBuilder.showWeek);
        datePicker.setDateMode(dateBuilder.dateMode);
        if (dateBuilder.unit != null) {
            datePicker.setUnit(dateBuilder.unit);
        }
        datePicker.setShowUnit(dateBuilder.showUnit);

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
                if (dateBuilder.onPickerDateListener != null) {
                    dateBuilder.onPickerDateListener.onPickerDate(datePicker,
                            datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), datePicker.getWeek());
                }
            }
        });
        datePicker.setOnDateChangeListener(new DatePicker.OnDateChangeListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day, String week) {
                Log.d(TAG, "onDateChanged: "+dateBuilder.onDateChangedListener);
                if (dateBuilder.onDateChangedListener != null) {
                    dateBuilder.onDateChangedListener.onDateChanged(datePicker,
                            year, month, day, week);
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

    public void setDefaultDate(Date date) {
        datePicker.setDefaultDate(date);
    }

    public interface OnPickerDateListener {
        /**
         * 选择后确认的回调
         *
         * @param datePicker DatePicker
         * @param year       年
         * @param month      月
         * @param day        日
         * @param week       周
         */
        void onPickerDate(DatePicker datePicker, int year, int month, int day, String week);
    }

    public interface OnDateChangedListener {
        /**
         * 滚动日期改变回调
         *
         * @param datePicker DatePicker
         * @param year       年
         * @param month      月
         * @param day        日
         * @param week       周
         */
        void onDateChanged(DatePicker datePicker, int year, int month, int day, String week);
    }

    public static class DateBuilder implements DialogBuilder {

        private CharSequence title = "请选择日期";
        private float itemTextSize;
        private int itemSpace;
        private float titleTextSize;
        private Context context;
        private Date minDate;
        private Date maxDate;
        private Date defDate;
        private int dateMode;
        private OnDateChangedListener onDateChangedListener;
        private OnPickerDateListener onPickerDateListener;
        private boolean showWeek = false;
        private String[] unit;
        private boolean showUnit = true;

        public DateBuilder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示文字
         */
        @Override
        public DateBuilder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        /**
         * 提示标题的文字大小
         *
         * @param titleTextSize dp
         */
        @Override
        public DateBuilder setTitleTextSize(float titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        /**
         * 滚轮文字大小
         *
         * @param itemTextSize dp
         */
        @Override
        public DateBuilder setItemTextSize(float itemTextSize) {
            this.itemTextSize = itemTextSize;
            return this;
        }

        @Override
        public DateBuilder setItemSpace(int itemSpace) {
            this.itemSpace = itemSpace;
            return this;
        }

        /**
         * 设置限制范围（如果只有一个，另一个传null）
         *
         * @param minDate 最小日期
         * @param maxDate 最大日期
         */
        public DateBuilder setRangeDate(Date minDate, Date maxDate) {
            this.minDate = minDate;
            this.maxDate = maxDate;
            return this;
        }

        /**
         * 显示模式，默认{@link com.jx.wheelpicker.widget.lasted.DatePicker.DateMode#ALL}
         */
        public DateBuilder setDateMode(@DatePicker.DateMode int dateMode) {
            this.dateMode = dateMode;
            return this;
        }

        /**
         * 滚动监听
         */
        public DateBuilder setOnDateChangedListener(OnDateChangedListener listener) {
            this.onDateChangedListener = listener;
            return this;
        }

        /**
         * 选中监听
         */
        public DateBuilder setOnPickerDateListener(OnPickerDateListener listener) {
            this.onPickerDateListener = listener;
            return this;
        }

        /**
         * 设置默认选中日期，如果不设置则显示当天
         * （注：若有最大最小限制，则视情况而定）
         */
        public DateBuilder setDefaultDate(Date defDate) {
            this.defDate = defDate;
            return this;
        }

        /**
         * 是否显示周
         */
        public DateBuilder setShowWeek(boolean showWeek) {
            this.showWeek = showWeek;
            return this;
        }

        /**
         * 显示的单位（例如：年，月，日）
         */
        public DateBuilder setUnit(String... unit) {
            this.unit = unit;
            return this;
        }

        /**
         * 是否显示单位（默认显示年、月、日）
         */
        public DateBuilder setShowUnit(boolean showUnit) {
            this.showUnit = showUnit;
            return this;
        }

        public DatePickerDialog build() {
            return new DatePickerDialog(this);
        }

    }
}
