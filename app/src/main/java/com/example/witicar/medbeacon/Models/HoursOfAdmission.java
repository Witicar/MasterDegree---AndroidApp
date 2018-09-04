package com.example.witicar.medbeacon.Models;

import java.sql.Time;

public class HoursOfAdmission {

    private long hoursOfAdmissionID;
    private String day;
    private Time startTime;
    private Time endTime;

    public HoursOfAdmission() {
    }

    public long getHoursOfAdmissionID() {
        return hoursOfAdmissionID;
    }

    public void setHoursOfAdmissionID(long hoursOfAdmissionID) {
        this.hoursOfAdmissionID = hoursOfAdmissionID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
