package com.waageweb.tides.app.data.dataModel;

/**
 * Created by AleksanderVatleWaage on 27.04.15.
 */
public class Location {
    String name;
    String code;
    String latitude;
    String longitude;

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
