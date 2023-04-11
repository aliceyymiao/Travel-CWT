package com.example.concur.Entity;

import jakarta.persistence.*;
@Embeddable
public class Address {

    private String street;

    private String suite;

    private String city;

    private String zipcode;

    @Embedded
    private Geo geo;

    public Address() {
        super();
    }

    public Address(String testStreet, String testSuite, String testCity, String testZipcode) {

    }

    public String toString(){
        return "Street: "+this.street+"; "+
                "Suite: "+this.suite+"; "+
                "City: "+this.city+"; "+
                "Zipcode: "+this.zipcode+"; "+
                "Geo: "+this.geo.toString();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
