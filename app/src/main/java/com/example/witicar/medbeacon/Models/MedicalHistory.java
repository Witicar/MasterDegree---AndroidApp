package com.example.witicar.medbeacon.Models;

import java.util.Date;

public class MedicalHistory {

    private long medicalHisotryID;
    private String illness;
    private Date dateOfMedicalHistory;
    private String symptoms;

    public MedicalHistory() {
    }

    public long getMedicalHisotryID() {
        return medicalHisotryID;
    }

    public void setMedicalHisotryID(long medicalHisotryID) {
        this.medicalHisotryID = medicalHisotryID;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public Date getDateOfMedicalHistory() {
        return dateOfMedicalHistory;
    }

    public void setDateOfMedicalHistory(Date date) {
        this.dateOfMedicalHistory = date;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
}
