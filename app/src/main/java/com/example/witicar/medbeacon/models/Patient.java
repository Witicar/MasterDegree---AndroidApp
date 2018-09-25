package com.example.witicar.medbeacon.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient {

    private long patientID;
    private String surname;
    private String name;
    private String sex;
    private long pesel;
    private Date birthDate;
    private String placeOfBirth;
    private long phoneNumber;
    private String login;
    private String password;
    private String email;
    private String priority;

    private Address address_patient;
    private List<Visit> patientVisites = new ArrayList<Visit>();
    private MedicalTestRegistration medicalTestRegistration_patient;

    public Patient() {

    }

    public long getPatientID() {
        return patientID;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date date) {
        this.birthDate = date;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    ////////////////////////////////////////////////////////////


    public Address getAddress_patient() {
        return address_patient;
    }

    public void setAddress_patient(Address address_patient) {
        this.address_patient = address_patient;
    }

    public List<Visit> getPatientVisites() {
        return patientVisites;
    }

    public void setPatientVisites(List<Visit> patientVisites) {
        this.patientVisites = patientVisites;
    }

    public MedicalTestRegistration getMedicalTestRegistration_patient() {
        return medicalTestRegistration_patient;
    }

    public void setMedicalTestRegistration_patient(MedicalTestRegistration medicalTestRegistration_patient) {
        this.medicalTestRegistration_patient = medicalTestRegistration_patient;
    }
}
