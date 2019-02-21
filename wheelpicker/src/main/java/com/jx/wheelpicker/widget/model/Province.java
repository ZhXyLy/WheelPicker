package com.jx.wheelpicker.widget.model;


import java.io.Serializable;
import java.util.List;

/**
 * 省
 *
 * @author zhaoxl
 * @date 2018/6/26
 */
public class Province implements Serializable, Data {
    private String name;
    private String code;
    private List<City> cities;

    public String getName() {
        return name;
    }

    public String getShortName() {
        return name.length()>5?name.substring(0, 5)+"…":name;
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

    public List<City> getCity() {
        return cities;
    }

    public void setCity(List<City> city) {
        this.cities = city;
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
