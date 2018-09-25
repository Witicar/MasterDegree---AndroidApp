package com.example.witicar.medbeacon.models;

import java.util.ArrayList;
import java.util.List;

public class Visit {

    private long patientVisitID;
    private int visitTime;

    private List<MedicalHistory> visitMedicialHistories = new ArrayList<MedicalHistory>();
    private List<Medicine> visitMedicines = new ArrayList<Medicine>();
    private List<MedicalTest> visitMedicalTests = new ArrayList<MedicalTest>();
    private Patient visit_patient;
    private Doctor visit_doctor;


    public Visit() {
    }

    public long getPatientVisitID() {
        return patientVisitID;
    }

    public void setPatientVisitID(long patientVisitID) {
        this.patientVisitID = patientVisitID;
    }

    public int getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(int visitDay) {
        this.visitTime = visitDay;
    }

    //////////////////////////////////////////////////////////////////////


    public List<MedicalHistory> getVisitMedicialHistories() {
        return visitMedicialHistories;
    }

    public void setVisitMedicialHistories(List<MedicalHistory> visitMedicialHistories) {
        this.visitMedicialHistories = visitMedicialHistories;
    }

    public List<Medicine> getVisitMedicines() {
        return visitMedicines;
    }

    public void setVisitMedicines(List<Medicine> visitMedicines) {
        this.visitMedicines = visitMedicines;
    }

    public List<MedicalTest> getVisitMedicalTests() {
        return visitMedicalTests;
    }

    public void setVisitMedicalTests(List<MedicalTest> visitMedicalTests) {
        this.visitMedicalTests = visitMedicalTests;
    }

    public Patient getVisit_patient() {
        return visit_patient;
    }

    public void setVisit_patient(Patient visit_patient) {
        this.visit_patient = visit_patient;
    }

    public Doctor getVisit_doctor() {
        return visit_doctor;
    }

    public void setVisit_doctor(Doctor visit_doctor) {
        this.visit_doctor = visit_doctor;
    }
}
