package com.example.witicar.medbeacon.models;

import java.util.Date;

public class MedicalTest {

    private long medicalTestID;
    private String name;
    private Date dateOfMedicalTest;
    private String results;

    private Visit medicalTest_visit;

    public MedicalTest() {
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

    //////////////////////////////////////////////////////////////////////


    public Visit getMedicalTest_visit() {
        return medicalTest_visit;
    }

    public void setMedicalTest_visit(Visit medicalTest_visit) {
        this.medicalTest_visit = medicalTest_visit;
    }
}
