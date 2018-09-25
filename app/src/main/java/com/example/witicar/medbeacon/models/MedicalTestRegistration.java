package com.example.witicar.medbeacon.models;
import java.util.ArrayList;
import java.util.List;

public class MedicalTestRegistration {

    private long medicalTestID;
    private int position;
    private String priority;

    public MedicalTestRegistration() {
    }


    public long getMedicalTestID() {
        return medicalTestID;
    }

    public void setMedicalTestID(long medicalTestID) {
        this.medicalTestID = medicalTestID;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
