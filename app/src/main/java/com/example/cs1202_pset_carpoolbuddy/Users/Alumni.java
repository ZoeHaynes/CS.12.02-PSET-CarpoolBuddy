package com.example.cs1202_pset_carpoolbuddy.Users;

public class Alumni extends User{
    private String graduateYear;

    public Alumni(String uid, String name, String email, String userType, String graduateYear){
        super(uid, name, email, userType);
        this.graduateYear = graduateYear;
    }
}
