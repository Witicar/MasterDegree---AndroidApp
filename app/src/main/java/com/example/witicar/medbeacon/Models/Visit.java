package com.example.witicar.medbeacon.Models;

import java.util.Date;

public class Visit {

    private long patientVisitID;
    private String healthFacility;
    private Date visitDay;

    public Visit() {
    }

    public long getPatientVisitID() {
        return patientVisitID;
    }

    public void setPatientVisitID(long patientVisitID) {
        this.patientVisitID = patientVisitID;
    }

    public String getHealthFacility() {
        return healthFacility;
    }

    public void setHealthFacility(String healthFacility) {
        this.healthFacility = healthFacility;
    }

    public Date getVisitDay() {
        return visitDay;
    }

    public void setVisitDay(Date visitDay) {
        this.visitDay = visitDay;
    }
}
