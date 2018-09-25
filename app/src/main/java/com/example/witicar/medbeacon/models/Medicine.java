package com.example.witicar.medbeacon.models;

public class Medicine {

    private long medicinesID;
    private String code;
    private String name;
    private float price;
    private String quantity;

    private Visit medicine_visit;

    public Medicine() {
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


    ///////////////////////////////////////////////////////////////


    public Visit getMedicine_visit() {
        return medicine_visit;
    }

    public void setMedicine_visit(Visit medicine_visit) {
        this.medicine_visit = medicine_visit;
    }
}
