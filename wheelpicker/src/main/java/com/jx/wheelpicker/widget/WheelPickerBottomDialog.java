package com.jx.wheelpicker.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jx.wheelpicker.R;

import java.util.List;

/**
 * 单选Dialog
 *
 * @author zhaoxl
 * @date 2018/6/25
 */
public class WheelPickerBottomDialog extends Dialog {
    private static final float ITEM_TEXT_SIZE = 20;
    private static final float ITEM_SPACE = 10;
    private static final String SELECTED_ITEM_COLOR = "#353535";

    private WheelPicker mWheelPicker;

    public WheelPickerBottomDialog(@NonNull Context context) {
        this(context, R.style.Dialog);
    }

    private WheelPickerBottomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        initializer();
    }

    private void initializer() {
        setContentView(R.layout.include_wheel_picker_dialog);

        FrameLayout flContent = findViewById(R.id.fl_content);
        mWheelPicker = new WheelPicker(getContext());
        initWheelPicker(mWheelPicker);
        flContent.addView(mWheelPicker);

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
                if (mOnWheelPickerListener != null) {
                    mOnWheelPickerListener.onWheelPicker(mWheelPicker.getData().get(mWheelPicker.getCurrentItemPosition()));
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

    private void initWheelPicker(WheelPicker wheelPicker) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);
        wheelPicker.setItemTextSize(dip2px(getContext(), ITEM_TEXT_SIZE));
        wheelPicker.setSelectedItemTextColor(Color.parseColor(SELECTED_ITEM_COLOR));
        wheelPicker.setCurved(true);
        wheelPicker.setVisibleItemCount(7);
        wheelPicker.setAtmospheric(true);
        wheelPicker.setItemSpace(dip2px(getContext(), ITEM_SPACE));
        wheelPicker.setLayoutParams(layoutParams);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private OnWheelPickerListener mOnWheelPickerListener;

    public interface OnWheelPickerListener {
        /**
         * 选择后确认的回调
         *
         * @param o 选中的数据
         */
        void onWheelPicker(Object o);
    }

    public void setOnWheelPickerListener(OnWheelPickerListener listener) {
        mOnWheelPickerListener = listener;
    }

    public void setData(List data) {
        mWheelPicker.setData(data);
    }
}
