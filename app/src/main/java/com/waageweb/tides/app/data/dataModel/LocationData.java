package com.waageweb.tides.app.data.dataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksander Vatle Waage on 27.04.15.
 */

public class LocationData {

    ArrayList<WaterLevel> data;
    Location location;
    String reflevelcode;

    public ArrayList<WaterLevel> getData() {
        return data;
    }

    public void setData(ArrayList<WaterLevel> data) {
        this.data = data;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getReflevelcode() {
        return reflevelcode;
    }

    public void setReflevelcode(String reflevelcode) {
        this.reflevelcode = reflevelcode;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "data=" + data +
                ", location=" + location +
                ", reflevelcode='" + reflevelcode + '\'' +
                '}';
    }
}
