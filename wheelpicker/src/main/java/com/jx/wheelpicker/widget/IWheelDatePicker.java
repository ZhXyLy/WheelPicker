package com.jx.wheelpicker.widget;

import java.util.Date;

/**
 * @author zhaoxl
 * @date 2018/6/25
 */
public interface IWheelDatePicker {
    /**
     * 获取当前Year
     *
     * @return 返回Year
     */
    int getYear();

    /**
     * 获取当前Month
     *
     * @return 返回Month
     */
    int getMonth();

    /**
     * 获取当前Day
     *
     * @return 返回Day
     */
    int getDay();

    /**
     * 返回字符串日期
     *
     * @param pattern 格式
     * @return String
     */
    String getStringDate(String pattern);

    /**
     * 返回日期
     *
     * @return Date
     */
    Date getDate();

    /**
     * 设置年范围(默认前后50年)
     *
     * @param minYear 最小年
     * @param maxYear 最大年
     */
    void setYearRange(int minYear, int maxYear);

    /**
     * 设置item文字大小
     * @param textSize dp
     */
    void setItemTextSize(int textSize);

    /**
     * 通过Date设置当前年月日位置
     * @param date Date
     */
    void setSelectPositionByDate(Date date);
}
