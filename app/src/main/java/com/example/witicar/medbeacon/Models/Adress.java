package com.example.witicar.medbeacon.Models;

public class Adress {

    private long adressID;
    private String country;
    private String voivodeship;
    private String city;
    private String postcode;
    private String houseNumber;

    public Adress() {
    }

    public long getAdressID() {
        return adressID;
    }

    public void setAdressID(long adressID) {
        this.adressID = adressID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
