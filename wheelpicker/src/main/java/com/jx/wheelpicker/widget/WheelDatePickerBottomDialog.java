package com.jx.wheelpicker.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

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
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER));

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
    }

    @Override
    public void show() {
        Window dialogWindow = getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            dialogWindow.setWindowAnimations(R.style.dialogWindowAnimBottom);
            dialogWindow.setGravity(Gravity.BOTTOM);
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            dialogWindow.setAttributes(lp);
        }

        super.show();
    }

    public void setYearRange(int minYear, int maxYear) {
        wheelDatePicker.setYearRange(minYear, maxYear);
    }

    public void setSelectPositionByDate(Date date) {
        wheelDatePicker.setSelectPositionByDate(date);
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
}
