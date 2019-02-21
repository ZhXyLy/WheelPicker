package com.jx.wheelpicker.widget.model;

/**
 * 日
 *
 * @author zhaoxl
 * @date 19/1/28
 */
public class DayData implements Data {

    public int day;
    public String week = "";
    public boolean showWeek;

    public DayData() {
    }

    public DayData(int day) {
        this.day = day;
    }

    public DayData(int day, String week) {
        this.day = day;
        this.week = week;
    }

    public DayData(int day, String week, boolean showWeek) {
        this.day = day;
        this.week = week;
        this.showWeek = showWeek;
    }

    @Override
    public String getId() {
        return String.valueOf(day);
    }

    @Override
    public String getText() {
        return day < 10 ? "0" + day : String.valueOf(day);
    }

    public String getWeek() {
        return showWeek ? week : "";
    }
}
