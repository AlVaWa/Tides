package com.waageweb.tides.app.data;

/**
 * Created by AleksanderVatleWaage on 27.04.15.
 */
public class Tides {

    private int id;
    private String name;
    private long created;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getCreated() {
        return created;
    }
}
