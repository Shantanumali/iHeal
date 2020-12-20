package com.example.doctor;

public class DoctorClass {
    String id;
    String email;
    String name;
    String qualification;

    public DoctorClass(){

    }
    public DoctorClass(String id, String email, String name, String qualification) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.qualification = qualification;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getQualification() {
        return qualification;
    }
}
