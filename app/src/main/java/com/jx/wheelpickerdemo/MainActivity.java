package com.jx.wheelpickerdemo;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jx.wheelpicker.util.AreaUtils;
import com.jx.wheelpicker.util.OnEmptyDataListener;

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

        //http://192.168.15.10:18088/jxWxService/province_json.json
        downloadJson();
    }

    private void downloadJson() {
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        String apkUrl = "http://192.168.15.10:18088/jxWxService/province_json.json";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setDestinationInExternalPublicDir("json", "province_json.json");

// request.setTitle("MeiLiShuo");
// request.setDescription("MeiLiShuo desc");
// request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
// request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
// request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
// request.setMimeType("application/cn.trinea.download.file");
        long downloadId = downloadManager.enqueue(request);

//        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/json/province_json.json";
        String filePath = getFilesDir().getAbsolutePath() + "/province_json.json";
        AreaUtils.getInstance().setFilePath(filePath);
        AreaUtils.getInstance().setOnEmptyDataListener(new OnEmptyDataListener() {
            @Override
            public void onEmptyData() {
                Toast.makeText(MainActivity.this, "数据时空的", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
