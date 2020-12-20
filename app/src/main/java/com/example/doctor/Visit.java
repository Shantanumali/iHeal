package com.example.doctor;

import java.util.Calendar;
import java.util.Locale;

public class Visit {

    private String docId;
    private String patientId;
    private int dayOfTheMonth;
    private String dayOfTheWeek;
    private int month;
    private int year;
    private String reasonOfVisit;
    private long dateOfVisit;
    private String symptoms;
    private int hour;
    private int minute;
    private String[] week = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};


    public Visit() {
    }

    public Visit(String docId, String patientId, String symptoms,String reasonOfVisit) {
        this.docId = docId;
        this.patientId = patientId;
        this.symptoms = symptoms;
        this.reasonOfVisit = reasonOfVisit;

        Calendar rightNow = Calendar.getInstance();
        this.dayOfTheWeek = week[rightNow.get(Calendar.DAY_OF_WEEK)-1];
        this.dayOfTheMonth = rightNow.get(Calendar.DATE);
        this.month = rightNow.get(Calendar.MONTH)+1;
        this.year = rightNow.get(Calendar.YEAR);
        this.hour = rightNow.get(Calendar.HOUR_OF_DAY);
        this.minute = rightNow.get(Calendar.MINUTE);
        String tmp=Integer.toString(year)+Integer.toString(month)+Integer.toString(dayOfTheMonth)
                +Integer.toString(hour)+Integer.toString(minute);
        this.dateOfVisit= Long.parseLong(tmp);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public long getDateOfVisit() {
        return dateOfVisit;
    }

    public String getDocId() {
        return docId;
    }

    public String getPatientId() {
        return patientId;
    }

    public int getDayOfTheMonth() {
        return dayOfTheMonth;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getReasonOfVisit() {
        return reasonOfVisit;
    }
}


