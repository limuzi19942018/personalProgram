package com.example.demo_activity.test1.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yongli123
 * @since 2020-01-02
 */
@TableName("city_name")
public class CityName extends Model<CityName> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String country;
    private String city;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CityName{" +
        ", id=" + id +
        ", country=" + country +
        ", city=" + city +
        "}";
    }
}
