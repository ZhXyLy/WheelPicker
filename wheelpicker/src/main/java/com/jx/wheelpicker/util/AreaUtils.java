package com.jx.wheelpicker.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jx.wheelpicker.widget.model.Area;
import com.jx.wheelpicker.widget.model.City;
import com.jx.wheelpicker.widget.model.Province;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author zhaoxl
 * @date 2018/10/10
 */
public class AreaUtils {

    private static volatile AreaUtils INSTANCE = null;

    private AreaUtils() {
    }

    public static AreaUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (AreaUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AreaUtils();
                }
            }
        }
        return INSTANCE;
    }

    private static List<Province> provinceList = null;

    public List<Province> getJsonDataFromAssets(AssetManager assetManager) {
        if (provinceList == null) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                InputStream inputStream = assetManager.open("RegionJsonData.txt");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String json = stringBuilder.toString();
                provinceList = new Gson().fromJson(json, new TypeToken<List<Province>>() {
                }.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return provinceList;
    }

    public String findAreaByCode(Context context,@NonNull String code) {
        List<Province> mProvinceList = getJsonDataFromAssets(context.getApplicationContext().getAssets());
        if (code.length() == 6) {
            String proCode = code.replace(code.substring(2, 6), "0000");
            String cityCode = code.replace(code.substring(4, 6), "00");
            for (Province province : mProvinceList) {
                if (proCode.equals(province.getCode())) {
                    for (City city : province.getCity()) {
                        if (cityCode.equals(city.getCode())) {
                            for (Area area : city.getArea()) {
                                if (code.equals(area.getCode())) {
                                    return province.getName() + city.getName() + area.getName();
                                }
                            }
                            return province.getName() + city.getName();
                        }
                    }
                    return province.getName();
                }
            }
        }
        return "";
    }
}
