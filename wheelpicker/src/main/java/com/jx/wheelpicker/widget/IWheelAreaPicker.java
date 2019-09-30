package com.jx.wheelpicker.widget;

import com.jx.wheelpicker.widget.model.Area;
import com.jx.wheelpicker.widget.model.City;
import com.jx.wheelpicker.widget.model.Province;

/**
 * @author zhaoxl
 * @date 2018/6/25
 */
public interface IWheelAreaPicker {
    /**
     * 获取当前Province
     *
     * @return 返回Province
     */
    Province getProvince();

    /**
     * 获取当前City
     *
     * @return 返回City
     */
    City getCity();

    /**
     * 获取当前Area
     *
     * @return 返回Area
     */
    Area getArea();

    /**
     * 省市区地址
     *
     * @return 省市区地址
     */
    String getAreaString();

    /**
     * 省市区代码（Area的code）
     *
     * @return 省市区代码
     */
    String getAreaCode();

    /**
     * 设置数据项文本尺寸大小
     * <p>
     * Set text size of items
     * Unit in px
     *
     * @param size 设置数据项文本尺寸大小，单位：px
     */
    void setItemTextSize(float size);

    /**
     * 设置数据项文本尺寸大小
     * <p>
     * <p>
     * {@link android.util.TypedValue#COMPLEX_UNIT_PX}
     * {@link android.util.TypedValue#COMPLEX_UNIT_DIP}
     * {@link android.util.TypedValue#COMPLEX_UNIT_SP}
     * {@link android.util.TypedValue#COMPLEX_UNIT_PT}
     * {@link android.util.TypedValue#COMPLEX_UNIT_IN}
     * {@link android.util.TypedValue#COMPLEX_UNIT_MM}
     * </p>
     *
     * @param unit  尺寸单位
     * @param value unit单位下的尺寸大小
     */
    void setItemTextSize(int unit, float value);

    /**
     * 通过传入的省市区代码，确定当前选中的位置
     *
     * @param code 省市区代码
     */
    void setSelectPositionByCode(String code);
}
