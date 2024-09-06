package com.camelsoft.scano.client.Requests.Country;

public class CityRequest {

    private String name;
    private String state_code;
    private String latitude;
    private String longitude;
    private String type;


    public CityRequest() {
    }

    public CityRequest(String name, String state_code, String latitude, String longitude, String type) {
        this.name = name;
        this.state_code = state_code;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
