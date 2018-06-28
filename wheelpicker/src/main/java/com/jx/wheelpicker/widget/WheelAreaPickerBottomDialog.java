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
 * @author zhaoxl
 * @date 2018/6/25
 */
public class WheelAreaPickerBottomDialog extends Dialog {


    public WheelAreaPickerBottomDialog(@NonNull Context context) {
        this(context, R.style.Dialog);
    }

    private WheelAreaPickerBottomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        initializer();
    }

    private void initializer() {
        setContentView(R.layout.include_wheel_picker_dialog);

        FrameLayout flContent = findViewById(R.id.fl_content);
        final WheelAreaPicker wheelAreaPicker = new WheelAreaPicker(getContext());
        flContent.addView(wheelAreaPicker);

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
                if (mOnPickerAreaListener != null) {
                    mOnPickerAreaListener.onPickerArea(wheelAreaPicker);
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

    private OnPickerAreaListener mOnPickerAreaListener;

    public interface OnPickerAreaListener {
        /**
         * 选择后确认的回调
         *
         * @param wheelAreaPicker 区域选择器
         */
        void onPickerArea(WheelAreaPicker wheelAreaPicker);
    }

    public void setOnPickerAreaListener(OnPickerAreaListener listener) {
        mOnPickerAreaListener = listener;
    }
}
