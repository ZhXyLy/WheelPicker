package com.jx.wheelpickerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jx.wheelpicker.widget.WheelDatePickerBottomDialog;
import com.jx.wheelpicker.widget.lasted.DatePicker;
import com.jx.wheelpicker.widget.lasted.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * @author zhaoxl
 * @date 19/2/19
 */
public class DatePickerDemo extends AppCompatActivity {

    private DatePicker mPicker;
    private Button btnOld, btnLasted;

    private DatePickerDialog mPickerDialog;
    private WheelDatePickerBottomDialog wheelPickerBottomDialog;
    private TextView tvLastedResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        tvLastedResult = findViewById(R.id.tv_lasted_result);
        mPicker = findViewById(R.id.picker);
        btnOld = findViewById(R.id.btn_old);
        btnLasted = findViewById(R.id.btn_lasted);

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
            wheelPickerBottomDialog = new WheelDatePickerBottomDialog(this);
        }
        wheelPickerBottomDialog.show();
    }

    private void showSingleLastedDialog() {
        if (mPickerDialog == null) {
            mPickerDialog = new DatePickerDialog.DateBuilder(this)
                    .setItemSpace(getResources().getDimensionPixelOffset(R.dimen.dp20))
                    .setItemTextSize(20)
                    .setOnDateChangedListener(new DatePickerDialog.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker datePicker, int year, int month, int day, String week) {
                            tvLastedResult.setText(String.format("%s-%s", datePicker.getDateString(), week));
                        }
                    })
                    .setOnPickerDateListener(new DatePickerDialog.OnPickerDateListener() {
                        @Override
                        public void onPickerDate(DatePicker datePicker, int year, int month, int day, String week) {
                            ToastUtils.show(String.format("%s-%s", datePicker.getDateString(), week));
                        }
                    })
                    .build();
        }
        mPickerDialog.show();
    }
}
