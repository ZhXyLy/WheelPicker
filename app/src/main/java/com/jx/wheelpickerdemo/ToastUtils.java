package com.jx.wheelpickerdemo;

import android.content.Context;
import android.widget.Toast;

/**
 * @author zhaoxl
 * @date 19/2/19
 */
public class ToastUtils {

    private static Toast mToast;

    public static void init(Context appContext) {
        mToast = Toast.makeText(appContext, "", Toast.LENGTH_SHORT);
    }

    public static void show(CharSequence msg) {
        mToast.setText(msg);
        mToast.show();
    }
}
