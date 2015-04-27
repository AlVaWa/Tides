package com.waageweb.tides.app.data.dataModel;

/**
 * Created by AleksanderVatleWaage on 27.04.15.
 */
public class Tide {
    LocationData locationdata;

    public LocationData getLocationdata() {
        return locationdata;
    }

    public void setLocationdata(LocationData locationdata) {
        this.locationdata = locationdata;
    }

    @Override
    public String toString() {
        return "Tide{" +
                "locationdata=" + locationdata +
                '}';
    }
}
