package com.example.doctor;

public class PatientInfo {
    public String id,name,sex,address;
    public int age;
    public float height,weight;
    public long phoneno;

    public PatientInfo(){}

    public PatientInfo(String id,String name, String sex, String address, int age, float height, float weight, long phoneno){
        this.id=id;
        this.name=name;
        this.sex=sex;
        this.address=address;
        this.age=age;
        this.height=height;
        this.weight=weight;
        this.phoneno=phoneno;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public long getPhoneno() {
        return phoneno;
    }
}
