package com.example.witicar.medbeacon.Models;

import java.util.Date;

public class MedicalTest {

    private Visit medicalTest_visit;
    private long medicalTestID;
    private String name;
    private Date dateOfMedicalTest;
    private String results;

    public MedicalTest() {
    }

    public long getMedicalTestID() {
        return medicalTestID;
    }

    public void setMedicalTestID(long medicalTestID) {
        this.medicalTestID = medicalTestID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfMedicalTest() {
        return dateOfMedicalTest;
    }

    public void setDateOfMedicalTest(Date date) {
        this.dateOfMedicalTest = date;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
