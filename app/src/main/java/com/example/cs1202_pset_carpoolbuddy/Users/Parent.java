package com.example.cs1202_pset_carpoolbuddy.Users;

import java.util.ArrayList;

public class Parent extends User{
    private ArrayList<String> childrenUIDs;

    public Parent(String uid, String name, String email, String userType){
        super(uid, name, email, userType);

    }
}
