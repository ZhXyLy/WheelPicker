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

/**
 * 时间选择Dialog
 *
 * @author zhaoxl
 * @date 2018/6/25
 */
public class WheelTimePickerBottomDialog extends Dialog {

    private WheelTimePicker mWheelTimePicker;

    public WheelTimePickerBottomDialog(@NonNull Context context) {
        this(context, R.style.Dialog);
    }

    private WheelTimePickerBottomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        initializer();
    }

    private void initializer() {
        setContentView(R.layout.include_wheel_picker_dialog);

        FrameLayout flContent = findViewById(R.id.fl_content);
        mWheelTimePicker = new WheelTimePicker(getContext());
        flContent.addView(mWheelTimePicker);

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
                if (mOnPickerTimeListener != null) {
                    mOnPickerTimeListener.onPickerTime(mWheelTimePicker);
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

    public void setShowSecond(boolean show) {
        mWheelTimePicker.setShowSecond(show);
    }

    private OnPickerTimeListener mOnPickerTimeListener;

    public interface OnPickerTimeListener {
        /**
         * 选择后确认的回调
         *
         * @param wheelTimePicker 时间选择器
         */
        void onPickerTime(IWheelTimePicker wheelTimePicker);
    }

    public void setOnPickerTimeListener(OnPickerTimeListener listener) {
        mOnPickerTimeListener = listener;
    }
}
