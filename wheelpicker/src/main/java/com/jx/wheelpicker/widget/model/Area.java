package com.jx.wheelpicker.widget.model;

import java.io.Serializable;

/**
 * 区
 *
 * @author zhaoxl
 * @date 2018/6/26
 */
public class Area implements Serializable, Data {
    private String name;
    private String code;

    public String getShortName() {
        return name.length() > 5 ? name.substring(0, 5) + "…" : name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getId() {
        return code;
    }

    @Override
    public String getText() {
        return name;
    }
}
