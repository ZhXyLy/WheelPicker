package com.jx.wheelpicker.widget.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.jx.wheelpicker.R;
import com.jx.wheelpicker.widget.model.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxl
 * @date 19/5/13
 */
public class ListAreaAdapter extends RecyclerView.Adapter<ListAreaAdapter.ViewHolder> {

    private List<? extends Data> mData;
    private String mCheckedId;
    private String mCheckedText;//仅用来设置默认值
    private float mItemTextSize = -1;
    private int checkMode;//0:按id设置选中，1：按Text设置选中

    public ListAreaAdapter() {
    }

    public void setNewData(List<? extends Data> data) {
        mData = data == null ? new ArrayList<Data>() : data;
        notifyDataSetChanged();
    }

    public void clear() {
        mCheckedId = null;
        mCheckedText = null;
    }

    public void setCheckedId(String checkedId) {
        this.mCheckedId = checkedId;
        checkMode = 0;
        notifyDataSetChanged();
    }

    public void setCheckedText(String text) {
        this.mCheckedText = text;
        checkMode = 1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_area_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Data data = mData.get(position);
        if (mItemTextSize > 0) {
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mItemTextSize);
        }
        holder.textView.setText(data.getText());
        boolean isChecked;
        if (checkMode == 1) {
            isChecked = data.getText() != null && data.getText().equals(mCheckedText);
        } else {
            isChecked = data.getId() != null && data.getId().equals(mCheckedId);
        }
        Log.d("onBindViewHolder", "onBindViewHolder: "+checkMode+"====="+isChecked);
        holder.textView.setChecked(isChecked);
        holder.textView.setSelected(isChecked);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data data = mData.get(holder.getAdapterPosition());
                if (data == null || data.getId().equals(mCheckedId)) {
                    return;
                }
                mCheckedId = data.getId();
                mCheckedText = data.getText();
                notifyDataSetChanged();
                if (onItemCheckedListener != null) {
                    onItemCheckedListener.onItemChecked(data, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setTextSize(float itemTextSize) {
        this.mItemTextSize = itemTextSize;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckedTextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    private OnItemCheckedListener onItemCheckedListener;

    public void setOnItemCheckedListener(OnItemCheckedListener listener) {
        this.onItemCheckedListener = listener;
    }

    interface OnItemCheckedListener {
        void onItemChecked(Data data, int position);
    }
}
