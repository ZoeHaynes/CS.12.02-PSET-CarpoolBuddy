package com.example.cs1202_pset_carpoolbuddy.Users;

import java.util.ArrayList;

public class User {
    private String uid;
    private String name;
    private String email;
    private String userType;
    private double priceMultiplier;
    public ArrayList<String> ownedVehicles;
    private int hairColor;
    private int skinColor;
    private int hairStyle;
    public User(){

    }
    public User(String uid, String name, String email, String userType){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.hairColor = 1;
        this.skinColor = 1;
        this.hairStyle = 1;
    }

    public String getUid(){
        return uid;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getUserType(){
        return userType;
    }

    public double getPriceMultiplier(){
        return priceMultiplier;
    }

    public ArrayList<String> getOwnedVehicles(){
        return ownedVehicles;
    }

    public int getHairColor() {
        return hairColor;
    }

    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }

    public int getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(int skinColor) {
        this.skinColor = skinColor;
    }

    public int getHairStyle() {
        return hairStyle;
    }

    public void setHairStyle(int hairStyle) {
        this.hairStyle = hairStyle;
    }
}
