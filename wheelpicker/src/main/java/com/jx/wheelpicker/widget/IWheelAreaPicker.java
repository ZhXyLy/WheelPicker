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
    String getSSQ();

    /**
     * 省市区代码（Area的code）
     *
     * @return 省市区代码
     */
    String getSSQCode();

    /**
     * 设置item文字大小
     *
     * @param textSize dp
     */
    void setItemTextSize(int textSize);

    /**
     * 通过传入的省市区代码，确定当前选中的位置
     *
     * @param code 省市区代码
     */
    void setSelectPositionByCode(String code);
}
