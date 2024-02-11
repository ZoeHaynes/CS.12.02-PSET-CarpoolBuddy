package com.example.cs1202_pset_carpoolbuddy.Users;

public class Teacher extends User{
    private String inSchoolTitle;

    public Teacher(String uid, String name, String email, String userType, String inSchoolTitle){
        super(uid, name, email, userType);
        this.inSchoolTitle = inSchoolTitle;
    }
}
