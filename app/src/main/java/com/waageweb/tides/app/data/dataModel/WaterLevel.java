package com.waageweb.tides.app.data.dataModel;

import java.util.Date;

/**
 * Created by AleksanderVatleWaage on 27.04.15.
 */
public class WaterLevel {

    double value;
    String time;
    String flag;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "WaterLevel{" +
                "value=" + value +
                ", time=" + time +
                ", flag='" + flag + '\'' +
                '}';
    }





}
