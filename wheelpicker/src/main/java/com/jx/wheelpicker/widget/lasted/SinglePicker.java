package com.jx.wheelpicker.widget.lasted;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jx.wheelpicker.R;
import com.jx.wheelpicker.widget.model.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选-选择器
 *
 * @author zhaoxl
 * @date 19/2/15
 */
public class SinglePicker extends FrameLayout {
    private static final String TAG = SinglePicker.class.getSimpleName();

    private final BasePicker mSinglePicker;

    private final TextView mSingleUnit;

    private boolean mShowUnit = true;

    private String mStringUnit;

    private List<? extends Data> mSingleData;

    private Data mCurrentData;

    private int mCurrentPosition = -1;

    public SinglePicker(Context context) {
        this(context, null);
    }

    public SinglePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SinglePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.wp_single_picker_layout, this, true);
        mSinglePicker = findViewById(R.id.wp_single);
        mSingleUnit = findViewById(R.id.tv_single_unit);

        BasePicker.OnWheelScrollChangeListener scrollChangeListener = new BasePicker.OnWheelScrollChangeListener() {
            @Override
            public void onWheelScroll(BasePicker wheelPicker, Data data) {
                if (wheelPicker == mSinglePicker) {
                    if (data != null) {
                        if (mCurrentData == null || !data.getId().equals(mCurrentData.getId())) {
                            setCurrentData(data);
                            notifyDateChanged();
                        }
                    }
                }
            }
        };

        addScrollListener(scrollChangeListener);

        updateData(null);
    }

    private void addScrollListener(BasePicker.OnWheelScrollChangeListener scrollChangeListener) {
        mSinglePicker.setOnWheelScrollChangeListener(scrollChangeListener);
    }

    private void setCurrentData(Data data) {
        mCurrentData = data;
    }

    public Data getCurrentData() {
        return mCurrentData;
    }

    public void updateData(List<? extends Data> data) {
        mSingleData = data == null ? new ArrayList<Data>() : data;
        mSinglePicker.setData(mSingleData);
        //更新数据后的数据比之前选择的位置还少，则默认选中为最后一个
        mCurrentPosition = Math.min(mCurrentPosition, mSingleData.size() - 1);
        if (mCurrentPosition >= 0) {
            setCurrentData(mSingleData.get(mCurrentPosition));
        }
        if (defaultId != null || defaultText != null) {
            if (defaultId != null) {
                setDefaultById(defaultId);
            } else {
                setDefaultByText(defaultText);
            }
            scrollToCurrentDate();
            notifyDateChanged();
        }
    }

    /**
     * 通知改变，即监听的回调
     */
    private void notifyDateChanged() {
        if (onSingleChangeListener != null) {
            onSingleChangeListener.onSingleChanged(this, mCurrentData);
        }
    }

    public void setShowUnit(boolean showUnit) {
        this.mShowUnit = showUnit;
        ViewGroup.LayoutParams layoutParams = mSinglePicker.getLayoutParams();
        if (mShowUnit) {
            mSingleUnit.setVisibility(VISIBLE);
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            mSingleUnit.setVisibility(GONE);
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        mSinglePicker.setLayoutParams(layoutParams);
    }

    public void setUnit(String unit) {
        this.mStringUnit = unit;
        mSingleUnit.setText(mStringUnit);
    }

    private String defaultId;
    private String defaultText;

    public void setDefaultById(String id) {
        defaultId = id == null ? "" : id;
        if (!TextUtils.isEmpty(id)) {
            if (mSingleData != null && mSingleData.size() > 0) {
                for (int i = 0; i < mSingleData.size(); i++) {
                    if (id != null && id.equals(mSingleData.get(i).getId())) {
                        setCurrentData(mSingleData.get(i));
                        mCurrentPosition = i;
                        scrollToCurrentDate();
                        notifyDateChanged();
                        return;
                    }
                }
                defaultId = null;
            }
        } else if (mSingleData.size() > 0) {
            setCurrentData(mSingleData.get(0));
            mCurrentPosition = 0;
            scrollToCurrentDate();
            notifyDateChanged();
        }
    }

    public void setDefaultByText(String text) {
        defaultText = text == null ? "" : text;
        if (!TextUtils.isEmpty(text)) {
            if (mSingleData != null && mSingleData.size() > 0) {
                for (int i = 0; i < mSingleData.size(); i++) {
                    if (text != null && text.equals(mSingleData.get(i).getText())) {
                        setCurrentData(mSingleData.get(i));
                        mCurrentPosition = i;
                        scrollToCurrentDate();
                        notifyDateChanged();
                        return;
                    }
                }
                defaultText = null;
            }
        } else if (mSingleData.size() > 0) {
            setCurrentData(mSingleData.get(0));
            mCurrentPosition = 0;
            scrollToCurrentDate();
            notifyDateChanged();
        }
    }

    private void scrollToCurrentDate() {
        mSinglePicker.setSelectedItemPosition(mCurrentPosition);
    }

    public void setItemTextSize(float size) {
        mSinglePicker.setItemTextSize(size);
    }

    public void setItemTextSize(int unit, float value) {
        mSinglePicker.setItemTextSize(unit, value);
    }

    public void setItemSpace(int itemSpace) {
        mSinglePicker.setItemSpace(itemSpace);
    }

    public interface OnSingleChangeListener {
        /**
         * 停止滚动即回调
         *
         * @param singlePicker SinglePicker
         * @param data         Data
         */
        void onSingleChanged(SinglePicker singlePicker, Data data);
    }

    private OnSingleChangeListener onSingleChangeListener;

    public void setOnSingleChangeListener(OnSingleChangeListener listener) {
        this.onSingleChangeListener = listener;
    }
}
