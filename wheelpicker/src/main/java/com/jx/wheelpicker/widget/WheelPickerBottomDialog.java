package com.jx.wheelpicker.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jx.wheelpicker.R;
import com.jx.wheelpicker.widget.model.IPickerName;

import java.util.ArrayList;
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
    private TextView tvTitle;
    private List mData;

    public WheelPickerBottomDialog(@NonNull Context context) {
        this(context, R.style.WP_Dialog);
    }

    private WheelPickerBottomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        initializer();
    }

    private void initializer() {
        setContentView(R.layout.wp_include_wheel_picker_dialog);

        FrameLayout flContent = findViewById(R.id.fl_content);
        mWheelPicker = new WheelPicker(getContext());
        initWheelPicker(mWheelPicker);
        flContent.addView(mWheelPicker,
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER));

        tvTitle = findViewById(R.id.tv_title);
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
                    int currentItemPosition = mWheelPicker.getCurrentItemPosition();
                    if (currentItemPosition > -1 && getData().size() > currentItemPosition) {
                        mOnWheelPickerListener.onWheelPicker(
                                mWheelPicker,
                                getData().get(currentItemPosition),
                                mWheelPicker.getData().get(currentItemPosition),
                                currentItemPosition
                        );
                    }
                }
            }
        });
        mWheelPicker.setOnWheelScrollChangeListener(new WheelPicker.OnWheelScrollChangeListener() {
            @Override
            public void onWheelScroll(IWheelPicker wheelPicker) {
                if (onWheelScrollChangedListener != null) {
                    int currentItemPosition = wheelPicker.getCurrentItemPosition();
                    if (currentItemPosition > -1 && getData().size() > currentItemPosition) {
                        onWheelScrollChangedListener.onWheelScrollChanged(
                                wheelPicker,
                                getData().get(currentItemPosition),
                                wheelPicker.getData().get(currentItemPosition),
                                currentItemPosition);
                    }
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

    public void setData(List data) {
        mData = data == null ? new ArrayList<>() : data;
        List<String> strings = new ArrayList<>();
        for (Object o : mData) {
            if (o != null) {
                String name;
                if (o instanceof IPickerName) {
                    name = ((IPickerName) o).getPickerName();
                } else if (o instanceof String) {
                    name = (String) o;
                } else {
                    name = o.toString();
                }
                strings.add(name);
            }
        }
        mWheelPicker.setData(strings);
    }

    public List getData() {
        return mData == null ? new ArrayList() : mData;
    }

    public void setSelectPosition(String o) {
        if (!TextUtils.isEmpty(o)) {
            List<String> data = mWheelPicker.getData();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).equals(o)) {
                    mWheelPicker.setSelectedItemPosition(i);
                    return;
                }
            }
        }
    }

    public void setSelectPosition(int position) {
        mWheelPicker.setSelectedItemPosition(position);
    }

    public void setVisibleCount(int count) {
        mWheelPicker.setVisibleItemCount(count);
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        tvTitle.setText(title);
    }

    public void setItemTextSize(float size) {
        mWheelPicker.setItemTextSize(size);
    }

    public void setItemTextSize(int unit, float value) {
        mWheelPicker.setItemTextSize(unit, value);
    }

    public void setItemTextColor(int color) {
        mWheelPicker.setItemTextColor(color);
    }

    public void setSelectedItemColor(int color) {
        mWheelPicker.setSelectedItemTextColor(color);
    }

    public int getVisibleItemCount() {
        return mWheelPicker.getVisibleItemCount();
    }

    public void setItemSpace(int space) {
        mWheelPicker.setItemSpace(space);
    }

    public void setIndicator(boolean hasIndicator) {
        mWheelPicker.setIndicator(hasIndicator);
    }

    public void setIndicatorColor(int color) {
        mWheelPicker.setIndicatorColor(color);
    }

    public void setCurtain(boolean hasCurtain) {
        mWheelPicker.setCurtain(hasCurtain);
    }

    public void setCurtainColor(int color) {
        mWheelPicker.setCurtainColor(color);
    }

    public void setAtmospheric(boolean hasAtmospheric) {
        mWheelPicker.setAtmospheric(hasAtmospheric);
    }

    public void setCurved(boolean isCurved) {
        mWheelPicker.setCurved(isCurved);
    }

    public void setItemAlign(int align) {
        mWheelPicker.setItemAlign(align);
    }

    public void setTypeface(Typeface tf) {
        mWheelPicker.setTypeface(tf);
    }

    //--------------------------------------------------------

    private OnWheelPickerListener mOnWheelPickerListener;

    public interface OnWheelPickerListener {
        /**
         * 选择后确认的回调
         *
         * @param wheelPicker {@link WheelPicker}
         * @param o           选中的数据
         * @param pickerName  选中的文字
         * @param position    选中的位置
         */
        void onWheelPicker(IWheelPicker wheelPicker, Object o, String pickerName, int position);
    }

    public void setOnWheelPickerListener(OnWheelPickerListener listener) {
        mOnWheelPickerListener = listener;
    }

    private OnWheelScrollChangedListener onWheelScrollChangedListener;

    public void setOnWheelScrollChangedListener(OnWheelScrollChangedListener listener) {
        this.onWheelScrollChangedListener = listener;
    }

    public interface OnWheelScrollChangedListener {
        void onWheelScrollChanged(IWheelPicker wheelPicker, Object o, String pickerName, int position);
    }
}
