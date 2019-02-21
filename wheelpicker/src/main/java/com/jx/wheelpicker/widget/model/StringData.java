package com.jx.wheelpicker.widget.model;

/**
 * @author zhaoxl
 * @date 19/1/28
 */
public class StringData implements Data {

    public String name;

    public StringData() {
    }

    public StringData(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return name;
    }

    @Override
    public String getText() {
        return name;
    }
}
