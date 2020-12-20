package com.example.doctor;

public class Prescription {

    private String name,dosage;
    private int isMorning;
    private int isNoon;
    private int isAfternoon;
    private int isEvening;

    public Prescription() {}

    public Prescription(String name, int isMorning, int isNoon, int isAfternoon, int isEvening, String dosage) {
        this.name = name;
        this.isMorning = isMorning;
        this.isNoon = isNoon;
        this.isAfternoon = isAfternoon;
        this.isEvening = isEvening;
        this.dosage = dosage;
    }

   public String getName() {
        return name;
    }


    public int getIsMorning() {
        return isMorning;
    }

    public int getIsNoon() {
        return isNoon;
    }

    public int getIsAfternoon() {
        return isAfternoon;
    }

    public int  getIsEvening() {
        return isEvening;
    }

    public String getDosage() {
        return dosage;
    }
}
