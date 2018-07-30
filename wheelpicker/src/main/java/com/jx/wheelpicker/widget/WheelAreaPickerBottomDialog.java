package com.jx.wheelpicker.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jx.wheelpicker.R;

/**
 * 省市区选择Dialog
 *
 * @author zhaoxl
 * @date 2018/6/25
 */
public class WheelAreaPickerBottomDialog extends Dialog {

    private WheelAreaPicker wheelAreaPicker;
    private TextView tvTitle;

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
        wheelAreaPicker = new WheelAreaPicker(getContext());
        flContent.addView(wheelAreaPicker,
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER));

        tvTitle = findViewById(R.id.tv_title);
        setTitle(R.string.select_province_city_area);

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
        wheelAreaPicker.setOnWheelScrollChangeListener(new WheelAreaPicker.OnWheelScrollChangeListener() {
            @Override
            public void onWheelScroll(IWheelAreaPicker wheelAreaPicker) {
                if (onWheelScrollChangedListener != null) {
                    onWheelScrollChangedListener.onWheelScrollChanged(wheelAreaPicker);
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
            //窗口动画也可以在R.style.Dialog中设置windowAnimationStyle
            dialogWindow.setWindowAnimations(R.style.dialogWindowAnimBottom);
            dialogWindow.setGravity(Gravity.BOTTOM);
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            dialogWindow.setAttributes(lp);
        }
    }

    public void setSelectPositionByCode(String code) {
        if (!TextUtils.isEmpty(code)) {
            wheelAreaPicker.setSelectPositionByCode(code);
        }
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        tvTitle.setText(title);
    }

    private OnPickerAreaListener mOnPickerAreaListener;

    public interface OnPickerAreaListener {
        /**
         * 选择后确认的回调
         *
         * @param wheelAreaPicker 区域选择器
         */
        void onPickerArea(IWheelAreaPicker wheelAreaPicker);
    }

    public void setOnPickerAreaListener(OnPickerAreaListener listener) {
        mOnPickerAreaListener = listener;
    }

    private OnWheelScrollChangedListener onWheelScrollChangedListener;

    public void setOnWheelScrollChangedListener(OnWheelScrollChangedListener listener) {
        this.onWheelScrollChangedListener = listener;
    }

    public interface OnWheelScrollChangedListener{
        void onWheelScrollChanged(IWheelAreaPicker wheelAreaPicker);
    }
}
