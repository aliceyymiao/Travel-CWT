package com.example.concur.Entity;
import jakarta.persistence.*;
@Embeddable
public class Geo {
    private String lat;
    private String lng;


    public String toString(){
        return "Lat: "+this.lat+"; "+
                "Lng: "+this.lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
