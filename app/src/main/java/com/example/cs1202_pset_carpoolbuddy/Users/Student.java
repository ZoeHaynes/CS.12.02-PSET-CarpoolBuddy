package com.example.cs1202_pset_carpoolbuddy.Users;

import java.util.ArrayList;

public class Student extends User{
    private String graduatingYear;
    private ArrayList<String> parentUIDs;

    public Student(String uid, String name, String email, String userType, String graduatingYear){
        super(uid, name, email, userType);
        this.graduatingYear = graduatingYear;
    }
}
