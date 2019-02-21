package com.jx.wheelpickerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jx.wheelpicker.util.AreaUtils;
import com.jx.wheelpicker.widget.IWheelAreaPicker;
import com.jx.wheelpicker.widget.IWheelDatePicker;
import com.jx.wheelpicker.widget.IWheelPicker;
import com.jx.wheelpicker.widget.IWheelTimePicker;
import com.jx.wheelpicker.widget.WheelAreaPickerBottomDialog;
import com.jx.wheelpicker.widget.WheelDatePickerBottomDialog;
import com.jx.wheelpicker.widget.WheelPickerBottomDialog;
import com.jx.wheelpicker.widget.WheelTimePickerBottomDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author zhaoxl
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToastUtils.init(getApplicationContext());

        findViewById(R.id.btn_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SinglePickerDemo.class));
            }
        });
        findViewById(R.id.btn_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DatePickerDemo.class));
            }
        });
        findViewById(R.id.btn_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TimePickerDemo.class));
            }
        });
        findViewById(R.id.btn_area).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AreaPickerDemo.class));
            }
        });
    }
}
