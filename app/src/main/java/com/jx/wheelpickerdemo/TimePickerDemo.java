package com.jx.wheelpickerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jx.wheelpicker.widget.WheelTimePickerBottomDialog;
import com.jx.wheelpicker.widget.lasted.TimePicker;
import com.jx.wheelpicker.widget.lasted.TimePickerDialog;

/**
 * @author zhaoxl
 * @date 19/2/19
 */
public class TimePickerDemo extends AppCompatActivity {

    private TimePicker mPicker;
    private Button btnOld, btnLasted;

    private TimePickerDialog mPickerDialog;
    private WheelTimePickerBottomDialog wheelPickerBottomDialog;
    private TextView tvLastedResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        tvLastedResult = findViewById(R.id.tv_lasted_result);
        mPicker = findViewById(R.id.picker);
        btnOld = findViewById(R.id.btn_old);
        btnLasted = findViewById(R.id.btn_lasted);

        btnOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOldDialog();
            }
        });
        btnLasted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLastedDialog();
            }
        });
    }

    private void showOldDialog() {
        if (wheelPickerBottomDialog == null) {
            wheelPickerBottomDialog = new WheelTimePickerBottomDialog(this);
        }
        wheelPickerBottomDialog.show();
    }

    private void showLastedDialog() {
        if (mPickerDialog == null) {
            mPickerDialog = new TimePickerDialog.TimeBuilder(this)
                    .setItemSpace(getResources().getDimensionPixelOffset(R.dimen.dp18))
                    .setItemTextSize(18)
                    .setOnTimeChangedListener(new TimePickerDialog.OnTimeChangedListener() {
                        @Override
                        public void onTimeChanged(TimePicker timePicker, int hour, int minute, int second) {
                            tvLastedResult.setText(timePicker.getTimeString());
                        }
                    })
                    .setOnPickTimeListener(new TimePickerDialog.OnPickTimeListener() {
                        @Override
                        public void onTimePicked(TimePicker timePicker, int hour, int minute, int second) {
                            ToastUtils.show(timePicker.getTimeString());
                        }
                    })
                    .build();
        }
        mPickerDialog.show();
    }
}
