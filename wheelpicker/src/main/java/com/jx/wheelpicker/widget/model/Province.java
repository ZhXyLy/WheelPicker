package com.jx.wheelpicker.widget.model;


import java.io.Serializable;
import java.util.List;

/**
 * 省
 *
 * @author zhaoxl
 * @date 2018/6/26
 */
public class Province implements Serializable {
    private String name;
    private String code;
    private List<City> cities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
//        return smartSubstring();
        return name.length() > 5 ? name.substring(0, 5) : name;
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

    /**
     * 智能截取省份
     *
     * @return 省名
     */
    private String smartSubstring() {
        //省份只有内蒙古和黑龙江是三个字，其余两个字
        if (name != null
                && name.length() >= 3
                && (name.contains("内蒙古") || name.contains("黑龙江"))) {
            return name.substring(0, 3);
        } else if (name != null
                && name.length() >= 2) {
            return name.substring(0, 2);
        } else {
            return name;
        }
    }
}
