package com.rockstar.swighe.helloworld;

import java.util.HashMap;
import java.util.Map;

public class Place {
    private String name;
    private String user;
    private String latitude;
    private String longitude;

    public Place(String name, String user, String latitude, String longitude) {
        this.name = name;
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Place() {

    }

    public String getName() {
        return this.name;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("user", user);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        return result;
    }

}
