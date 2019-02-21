package com.jx.wheelpicker.widget.model;

import java.io.Serializable;
import java.util.List;

/**
 * 市
 *
 * @author zhaoxl
 * @date 2018/6/26
 */
public class City implements Serializable,Data {
    private String name;
    private String code;
    private List<Area> areas;

    public String getShortName() {
        return name.length()>5?name.substring(0, 5)+"…":name;
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

    public List<Area> getArea() {
        return areas;
    }

    public void setArea(List<Area> area) {
        this.areas = area;
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
