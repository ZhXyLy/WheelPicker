package com.jx.wheelpicker.widget;

import java.util.Date;

/**
 * @author zhaoxl
 * @date 2018/6/25
 */
public interface IWheelTimePicker {
    /**
     * 获取当前小时
     *
     * @return 返回Hour
     */
    int getHour();

    /**
     * 获取当前分钟
     *
     * @return 返回Minute
     */
    int getMinute();

    /**
     * 获取当前秒
     *
     * @return 返回Second
     */
    int getSecond();

    /**
     * 返回字符串日期
     *
     * @return String
     */
    String getStringTime();

    /**
     * 是否显示秒
     * @return 是否显示秒
     */
    boolean isShowSecond();

    /**
     * 设置是否显示秒
     * @param show 设置是否显示秒
     */
    void setShowSecond(boolean show);

    /**
     * 通过date设置当前时间位置
     * @param date Date
     */
    void setSelectPositionByDate(Date date);
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
}
