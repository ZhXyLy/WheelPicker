package com.jx.wheelpicker.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
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
public class WheelDatePickerBottomDialog extends Dialog {

    private WheelDatePicker wheelDatePicker;
    private TextView tvTitle;

    public WheelDatePickerBottomDialog(@NonNull Context context) {
        this(context, R.style.Dialog);
    }

    private WheelDatePickerBottomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        initializer();
    }

    private void initializer() {
        setContentView(R.layout.include_wheel_picker_dialog);

        FrameLayout flContent = findViewById(R.id.fl_content);
        wheelDatePicker = new WheelDatePicker(getContext());
        flContent.addView(wheelDatePicker,
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER));

        tvTitle = findViewById(R.id.tv_title);
        setTitle(R.string.select_date);

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
                if (mOnPickerDateListener != null) {
                    mOnPickerDateListener.onPickerDate(wheelDatePicker);
                }
            }
        });
        wheelDatePicker.setOnWheelScrollChangeListener(new WheelDatePicker.OnWheelScrollChangeListener() {
            @Override
            public void onWheelScroll(IWheelDatePicker wheelDatePicker) {
                if (onWheelScrollChangedListener != null) {
                    onWheelScrollChangedListener.onWheelScrollChanged(wheelDatePicker);
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

    public void setYearRange(int minYear, int maxYear) {
        wheelDatePicker.setYearRange(minYear, maxYear);
    }

    public void setSelectPositionByDate(Date date) {
        if (date != null) {
            wheelDatePicker.setSelectPositionByDate(date);
        }
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        tvTitle.setText(title);
    }

    public void setItemTextSize(float size) {
        wheelDatePicker.setItemTextSize(size);
    }

    public void setItemTextSize(int unit, float value) {
        wheelDatePicker.setItemTextSize(unit, value);
    }

    public void setHideDay(boolean hideDay) {
        wheelDatePicker.setHideDay(hideDay);
    }

    private OnPickerDateListener mOnPickerDateListener;

    public interface OnPickerDateListener {
        /**
         * 选择后确认的回调
         *
         * @param wheelDatePicker 日期选择器
         */
        void onPickerDate(IWheelDatePicker wheelDatePicker);
    }

    public void setOnPickerDateListener(OnPickerDateListener listener) {
        mOnPickerDateListener = listener;
    }

    private OnWheelScrollChangedListener onWheelScrollChangedListener;

    public void setOnWheelScrollChangedListener(OnWheelScrollChangedListener listener) {
        this.onWheelScrollChangedListener = listener;
    }

    public interface OnWheelScrollChangedListener {
        void onWheelScrollChanged(IWheelDatePicker wheelDatePicker);
    }
}
