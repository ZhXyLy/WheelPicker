package com.jx.wheelpicker.widget.model;

/**
 * 年
 *
 * @author zhaoxl
 * @date 19/1/28
 */
public class SecondData implements Data {

    public int second;

    public SecondData() {
    }

    public SecondData(int second) {
        this.second = second;
    }

    @Override
    public String getId() {
        return String.valueOf(second);
    }

    @Override
    public String getText() {
        return second < 10 ? "0" + second : String.valueOf(second);
    }
}
