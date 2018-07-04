package com.jx.wheelpickerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jx.wheelpicker.widget.IWheelAreaPicker;
import com.jx.wheelpicker.widget.IWheelDatePicker;
import com.jx.wheelpicker.widget.IWheelTimePicker;
import com.jx.wheelpicker.widget.WheelAreaPickerBottomDialog;
import com.jx.wheelpicker.widget.WheelDatePickerBottomDialog;
import com.jx.wheelpicker.widget.WheelPickerBottomDialog;
import com.jx.wheelpicker.widget.WheelTimePickerBottomDialog;

import java.util.Arrays;

/**
 * @author zhaoxl
 */
public class MainActivity extends AppCompatActivity {

    private WheelAreaPickerBottomDialog wheelAreaPickerBottomDialog;
    private WheelDatePickerBottomDialog wheelDatePickerBottomDialog;
    private WheelTimePickerBottomDialog wheelTimePickerBottomDialog;
    private WheelPickerBottomDialog wheelPickerBottomDialog;

    private static final String[] TYPES = {"乘坐交通工具", "现场拜访及办公", "电话拜访", "会议及培训"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_show_area).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAreaDialog();
            }
        });
        findViewById(R.id.btn_show_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        findViewById(R.id.btn_show_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWheelPicker();
            }
        });
        findViewById(R.id.btn_show_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });
    }

    private void showAreaDialog() {
        if (wheelAreaPickerBottomDialog == null) {
            wheelAreaPickerBottomDialog = new WheelAreaPickerBottomDialog(this);
            wheelAreaPickerBottomDialog.setOnPickerAreaListener(new WheelAreaPickerBottomDialog.OnPickerAreaListener() {
                @Override
                public void onPickerArea(IWheelAreaPicker wheelAreaPicker) {
                    String province = wheelAreaPicker.getProvince().getName();
                    String city = wheelAreaPicker.getCity().getName();
                    String area = wheelAreaPicker.getArea().getName();
                    Toast.makeText(MainActivity.this, province + "-" + city + "-" + area, Toast.LENGTH_SHORT).show();
                }
            });
        }
        wheelAreaPickerBottomDialog.show();
    }

    private void showDateDialog() {
        if (wheelDatePickerBottomDialog == null) {
            wheelDatePickerBottomDialog = new WheelDatePickerBottomDialog(this);
            wheelDatePickerBottomDialog.setYearRange(2012, 2022);
            wheelDatePickerBottomDialog.setOnPickerDateListener(new WheelDatePickerBottomDialog.OnPickerDateListener() {

                @Override
                public void onPickerDate(IWheelDatePicker wheelDatePicker) {
                    String stringDate = wheelDatePicker.getStringDate("yyyy年MM月dd日");
                    Toast.makeText(MainActivity.this, stringDate, Toast.LENGTH_SHORT).show();
                }
            });
        }
        wheelDatePickerBottomDialog.show();
    }

    private void showTimeDialog() {
        if (wheelTimePickerBottomDialog == null) {
            wheelTimePickerBottomDialog = new WheelTimePickerBottomDialog(this);
            wheelTimePickerBottomDialog.setShowSecond(true);
            wheelTimePickerBottomDialog.setOnPickerTimeListener(new WheelTimePickerBottomDialog.OnPickerTimeListener() {
                @Override
                public void onPickerTime(IWheelTimePicker wheelTimePicker) {
                    String stringTime = wheelTimePicker.getStringTime();
                    Toast.makeText(MainActivity.this, stringTime, Toast.LENGTH_SHORT).show();
                }
            });
        }
        wheelTimePickerBottomDialog.show();
    }

    private void showWheelPicker() {
        if (wheelPickerBottomDialog == null) {
            wheelPickerBottomDialog = new WheelPickerBottomDialog(this);
            wheelPickerBottomDialog.setOnWheelPickerListener(new WheelPickerBottomDialog.OnWheelPickerListener() {
                @Override
                public void onWheelPicker(Object o) {
                    Toast.makeText(MainActivity.this, o.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            wheelPickerBottomDialog.setData(Arrays.asList(TYPES));
        }
        wheelPickerBottomDialog.show();
    }
}
