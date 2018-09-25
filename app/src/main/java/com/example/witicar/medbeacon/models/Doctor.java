package com.example.witicar.medbeacon.models;

import java.util.ArrayList;
import java.util.List;

public class Doctor {

    private long doctorID;
    private String surname;
    private String name;
    private String specialization;
    private int practiceNumber;
    private long phoneNumber;
    private HoursOfAdmission doctor_hoursOfAdmission;
    private List<Visit> doctorVisits = new ArrayList<Visit>();

    public Doctor() {
    }

    public long getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(long doctorID) {
        this.doctorID = doctorID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getPracticeNumber() {
        return practiceNumber;
    }

    public void setPracticeNumber(int practiceNumber) {
        this.practiceNumber = practiceNumber;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    public HoursOfAdmission getDoctor_hoursOfAdmission() {
        return doctor_hoursOfAdmission;
    }

    public void setDoctor_hoursOfAdmission(HoursOfAdmission doctor_hoursOfAdmission) {
        this.doctor_hoursOfAdmission = doctor_hoursOfAdmission;
    }

    public List<Visit> getDoctorVisits() {
        return doctorVisits;
    }

    public void setDoctorVisits(List<Visit> doctorVisits) {
        this.doctorVisits = doctorVisits;
    }
}
