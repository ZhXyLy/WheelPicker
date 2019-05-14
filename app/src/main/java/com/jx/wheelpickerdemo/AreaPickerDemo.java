package com.jx.wheelpickerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jx.wheelpicker.widget.WheelAreaPickerBottomDialog;
import com.jx.wheelpicker.widget.lasted.AreaPicker;
import com.jx.wheelpicker.widget.lasted.AreaPickerDialog;
import com.jx.wheelpicker.widget.list.ListAreaPicker;
import com.jx.wheelpicker.widget.list.ListAreaPickerDialog;
import com.jx.wheelpicker.widget.model.Area;
import com.jx.wheelpicker.widget.model.City;
import com.jx.wheelpicker.widget.model.Province;

/**
 * @author zhaoxl
 * @date 19/2/19
 */
public class AreaPickerDemo extends AppCompatActivity {

    private AreaPicker mPicker;
    private Button btnOld, btnLasted, btnList;

    private AreaPickerDialog mPickerDialog;
    private WheelAreaPickerBottomDialog wheelPickerBottomDialog;
    private TextView tvLastedResult;
    private TextView tvListResult;
    private ListAreaPickerDialog listAreaPickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        tvLastedResult = findViewById(R.id.tv_lasted_result);
        tvListResult = findViewById(R.id.tv_list_result);
        mPicker = findViewById(R.id.picker);
        mPicker.setAdjustTextSize(true);
        mPicker.setDefaultByCode("610328");
//        mPicker.setDefaultByName("陕西省宝鸡市千阳县");
        btnOld = findViewById(R.id.btn_old);
        btnLasted = findViewById(R.id.btn_lasted);
        btnList = findViewById(R.id.btn_list);

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
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListAreaDialog();
            }
        });
    }

    private void showListAreaDialog() {
        if (listAreaPickerDialog == null) {
            listAreaPickerDialog = new ListAreaPickerDialog.ListAreaBuilder(this)
                    .setOnPickerAreaListener(new ListAreaPickerDialog.OnPickerAreaListener() {
                        @Override
                        public void onPickerArea(ListAreaPicker listAreaPicker, Province province, City city, Area area) {
                            ToastUtils.show(listAreaPicker.getAreaString("-"));
                        }
                    })
                    .setOnAreaChangedListener(new ListAreaPickerDialog.OnAreaChangedListener() {
                        @Override
                        public void onAreaChanged(ListAreaPicker listAreaPicker, Province province, City city, Area area) {
                            tvListResult.setText(listAreaPicker.getAreaString("/"));
                        }
                    })
                    .build();
        }
        listAreaPickerDialog.show();
    }

    private void showSingleDialog() {
        if (wheelPickerBottomDialog == null) {
            wheelPickerBottomDialog = new WheelAreaPickerBottomDialog(this);
        }
        wheelPickerBottomDialog.show();
    }

    private void showSingleLastedDialog() {
        if (mPickerDialog == null) {
            mPickerDialog = new AreaPickerDialog.AreaBuilder(this)
                    .setShortText(true)
                    .setAdjustTextSize(false)
                    .setOnAreaChangedListener(new AreaPickerDialog.OnAreaChangedListener() {
                        @Override
                        public void onAreaChanged(AreaPicker areaPicker, Province province, City city, Area area) {
                            tvLastedResult.setText(areaPicker.getAreaString(" "));
                        }
                    })
                    .setOnPickerAreaListener(new AreaPickerDialog.OnPickerAreaListener() {
                        @Override
                        public void onPickerArea(AreaPicker areaPicker, Province province, City city, Area area) {
                            ToastUtils.show(areaPicker.getAreaString("-"));
                        }
                    })
                    .build();
        }
        mPickerDialog.show();
    }
}
