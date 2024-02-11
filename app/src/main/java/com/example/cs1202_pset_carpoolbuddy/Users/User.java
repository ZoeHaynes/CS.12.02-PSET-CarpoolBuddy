package com.example.cs1202_pset_carpoolbuddy.Users;

import java.util.ArrayList;

public class User {
    private String uid;
    private String name;
    private String email;
    private String userType;
    private double priceMultiplier;
    public ArrayList<String> ownedVehicles;

    public User(){

    }
    public User(String uid, String name, String email, String userType){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.userType = userType;
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

}
