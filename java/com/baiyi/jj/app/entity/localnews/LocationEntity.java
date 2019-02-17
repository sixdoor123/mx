package com.baiyi.jj.app.entity.localnews;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/21 0021.
 */
public class LocationEntity implements Serializable {

    private int areaID;
    private String areashort;
    private String areaName;
    private String countryName;
    private String cityName;
    private double latitude;
    private double longitude;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAreashort() {
        return areashort;
    }

    public void setAreashort(String areashort) {
        this.areashort = areashort;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaID() {
        return areaID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }
}
