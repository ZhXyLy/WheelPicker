package com.jx.wheelpicker.widget.model;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2016/9/14 0014
 */
public class Area implements Serializable {
    public String name;
    public String code;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getShortName() {
        return name.length() > 6 ? name.substring(0, 6) : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
