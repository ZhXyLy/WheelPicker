package com.jx.wheelpicker.widget;

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

}
