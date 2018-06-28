package com.jx.wheelpickerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jx.wheelpicker.widget.WheelAreaPicker;
import com.jx.wheelpicker.widget.WheelAreaPickerBottomDialog;
import com.jx.wheelpicker.widget.WheelDatePicker;
import com.jx.wheelpicker.widget.WheelDatePickerBottomDialog;

/**
 * @author zhaoxl
 */
public class MainActivity extends AppCompatActivity {

    private WheelAreaPickerBottomDialog wheelAreaPickerBottomDialog;
    private WheelDatePickerBottomDialog wheelDatePickerBottomDialog;

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

        WheelDatePicker wheelDatePicker = findViewById(R.id.wheelDatePicker);
        wheelDatePicker.setYearRange(2012, 2022);
    }

    private void showAreaDialog() {
        if (wheelAreaPickerBottomDialog == null) {
            wheelAreaPickerBottomDialog = new WheelAreaPickerBottomDialog(this);
            wheelAreaPickerBottomDialog.setOnPickerAreaListener(new WheelAreaPickerBottomDialog.OnPickerAreaListener() {
                @Override
                public void onPickerArea(WheelAreaPicker wheelAreaPicker) {
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
            wheelDatePickerBottomDialog.setOnPickerDateListener(new WheelDatePickerBottomDialog.OnPickerDateListener() {
                @Override
                public void onPickerDate(WheelDatePicker wheelDatePicker) {
                    String stringDate = wheelDatePicker.getStringDate("yyyy年MM月dd日");
                    Toast.makeText(MainActivity.this, stringDate, Toast.LENGTH_SHORT).show();
                }
            });
        }
        wheelDatePickerBottomDialog.show();
    }
}
