package com.jx.wheelpickerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jx.wheelpicker.widget.WheelPickerBottomDialog;
import com.jx.wheelpicker.widget.lasted.SinglePicker;
import com.jx.wheelpicker.widget.lasted.SinglePickerDialog;
import com.jx.wheelpicker.widget.model.Data;
import com.jx.wheelpicker.widget.model.StringData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhaoxl
 * @date 19/2/19
 */
public class SinglePickerDemo extends AppCompatActivity {
    private static final String TAG = "SinglePickerDemo";

    private SinglePicker mPicker;
    private Button btnOld, btnLasted;

    private SinglePickerDialog singlePickerDialog;
    private WheelPickerBottomDialog wheelPickerBottomDialog;
    private String[] stringArray;
    private List<StringData> stringData;
    private TextView tvLastedResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        tvLastedResult = findViewById(R.id.tv_lasted_result);
        mPicker = findViewById(R.id.picker);
        btnOld = findViewById(R.id.btn_old);
        btnLasted = findViewById(R.id.btn_lasted);

        mPicker.setOnSingleChangeListener(new SinglePicker.OnSingleChangeListener() {
            @Override
            public void onSingleChanged(SinglePicker singlePicker, Data data) {
                Log.d(TAG, "onSingleChanged: " + data);
            }
        });

        stringArray = getResources().getStringArray(R.array.WheelArrayDefault);
        stringData = new ArrayList<>();
        for (String s : stringArray) {
            stringData.add(new StringData(s));
        }
        mPicker.updateData(stringData);

        btnOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleDialog();
            }
        });
        btnLasted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleLastedDialog();
            }
        });
    }

    private void showSingleDialog() {
        if (wheelPickerBottomDialog == null) {
            wheelPickerBottomDialog = new WheelPickerBottomDialog(this);
            wheelPickerBottomDialog.setData(Arrays.asList(stringArray));
        }
        wheelPickerBottomDialog.show();
    }

    private void showSingleLastedDialog() {
        if (singlePickerDialog == null) {
            singlePickerDialog = new SinglePickerDialog.SingleBuilder(this)
                    .setData(stringData)
                    .setOnChangedListener(new SinglePickerDialog.OnChangedListener() {
                        @Override
                        public void onChanged(SinglePicker singlePicker, Data data) {
                            tvLastedResult.setText(data == null ? "没选到东西" : data.getText());
                        }
                    })
                    .setOnPickListener(new SinglePickerDialog.OnPickListener() {
                        @Override
                        public void onPicked(SinglePicker singlePicker, Data data) {
                            ToastUtils.show(data == null ? "没选到东西" : data.getText());
                        }
                    })
                    .build();
        }
        singlePickerDialog.show();
    }
}
