package com.example.witicar.medbeacon.Models;

public class Medicine {

    private long medicinesID;
    private String code;
    private String name;
    private float price;
    private String quantity;

    public Medicine() {
    }

    public long getMedicinesID() {
        return medicinesID;
    }

    public void setMedicinesID(long medicinesID) {
        this.medicinesID = medicinesID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
