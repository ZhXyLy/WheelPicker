package com.jx.wheelpicker.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jx.wheelpicker.widget.model.Area;
import com.jx.wheelpicker.widget.model.City;
import com.jx.wheelpicker.widget.model.Province;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static Map<String, List<Province>> provinceMap = new HashMap<>();

    public List<Province> getJsonDataFromAssets(AssetManager assetManager) {
        String fileName = "RegionJsonData.json";
        List<Province> provinces = provinceMap.get(fileName);
        if (provinces == null) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                InputStream inputStream = assetManager.open("RegionJsonData.json");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String json = stringBuilder.toString();
                provinces = new Gson().fromJson(json, new TypeToken<List<Province>>() {
                }.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        provinceMap.put(fileName, provinces);
        return provinces;
    }

    public List<Province> getJsonDataFromAssets(AssetManager assetManager, String assetFileName) {
        if (TextUtils.isEmpty(assetFileName)) {
            return getJsonDataFromAssets(assetManager);
        }
        List<Province> provinces = provinceMap.get(assetFileName);
        if (provinces == null) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                InputStream inputStream = assetManager.open(assetFileName);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String json = stringBuilder.toString();
                provinces = new Gson().fromJson(json, new TypeToken<List<Province>>() {
                }.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        provinceMap.put(assetFileName, provinces);
        return provinces;
    }

    public String findAreaByCode(Context context, @NonNull String code) {
        if (code.length() == 6) {
            List<Province> mProvinceList = getJsonDataFromAssets(context.getApplicationContext().getAssets());
            if (mProvinceList == null || mProvinceList.size() == 0) {
                return "";
            }
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

    public String findFullAreaByCode(Context context, @NonNull String code) {
        if (code.length() == 6) {
            List<Province> mProvinceList = getJsonDataFromAssets(context.getApplicationContext().getAssets());
            if (mProvinceList == null || mProvinceList.size() == 0) {
                return "";
            }
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
                            return province.getName()
                                    + city.getName()
                                    + city.getArea().get(0).getName();
                        }
                    }
                    return province.getName()
                            + province.getCity().get(0).getName()
                            + province.getCity().get(0).getArea().get(0).getName();
                }
            }
            return mProvinceList.get(0).getName()
                    + mProvinceList.get(0).getCity().get(0).getName()
                    + mProvinceList.get(0).getCity().get(0).getArea().get(0).getName();
        }
        return "";
    }

    public Ssq findSsqAreaByCode(Context context, @NonNull String code) {
        if (code.length() == 6) {
            List<Province> mProvinceList = getJsonDataFromAssets(context.getApplicationContext().getAssets());
            if (mProvinceList == null || mProvinceList.size() == 0) {
                return null;
            }
            Ssq ssq = new Ssq();
            String proCode = code.replace(code.substring(2, 6), "0000");
            String cityCode = code.replace(code.substring(4, 6), "00");
            for (Province province : mProvinceList) {
                if (proCode.equals(province.getCode())) {
                    for (City city : province.getCity()) {
                        if (cityCode.equals(city.getCode())) {
                            for (Area area : city.getArea()) {
                                if (code.equals(area.getCode())) {
                                    ssq.setProvince(province);
                                    ssq.setCity(city);
                                    ssq.setArea(area);
                                    return ssq;
                                }
                            }
                            ssq.setProvince(province);
                            ssq.setCity(city);
                            ssq.setArea(city.getArea().get(0));
                            return ssq;
                        }
                    }

                    ssq.setProvince(province);
                    ssq.setCity(province.getCity().get(0));
                    ssq.setArea(province.getCity().get(0).getArea().get(0));
                    return ssq;
                }
            }
            ssq.setProvince(mProvinceList.get(0));
            ssq.setCity(mProvinceList.get(0).getCity().get(0));
            ssq.setArea(mProvinceList.get(0).getCity().get(0).getArea().get(0));
            return ssq;
        }
        return null;
    }
}
