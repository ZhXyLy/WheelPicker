package com.jx.wheelpicker.widget.model;

import java.io.Serializable;

/**
 * 区
 *
 * @author zhaoxl
 * @date 2018/6/26
 */
public class Area implements Serializable {
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return name.length() > 5 ? name.substring(0, 5) : name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
