package com.example.witicar.medbeacon.models;

import java.util.Date;

public class MedicalHistory {

    private long medicalHisotryID;
    private String illness;
    private Date dateOfMedicalHistory;
    private String symptoms;

    private Visit medicalHistory_visit;

    public MedicalHistory() {
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

    ////////////////////////////////////////////////////////////////


    public Visit getMedicalHistory_visit() {
        return medicalHistory_visit;
    }

    public void setMedicalHistory_visit(Visit medicalHistory_visit) {
        this.medicalHistory_visit = medicalHistory_visit;
    }
}
