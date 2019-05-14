package com.jx.wheelpicker.widget.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    private float mItemTextSize = -1;

    public ListAreaAdapter() {
    }

    public void setNewData(List<? extends Data> data) {
        mData = data == null ? new ArrayList<Data>() : data;
        notifyDataSetChanged();
    }

    public void clear() {
        mCheckedId = null;
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
        holder.textView.setChecked(data.getId() != null && data.getId().equals(mCheckedId));
        holder.textView.setSelected(data.getId() != null && data.getId().equals(mCheckedId));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data data = mData.get(holder.getAdapterPosition());
                if (data == null || data.getId().equals(mCheckedId)) {
                    return;
                }
                mCheckedId = data.getId();
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
