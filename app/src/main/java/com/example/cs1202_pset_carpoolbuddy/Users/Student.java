package com.example.cs1202_pset_carpoolbuddy.Users;

import java.util.ArrayList;

/**
 * This class provides the properties, methods, and constructor for the Student class, which extends the User class.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
public class Student extends User{
    private String graduatingYear;
    private ArrayList<String> parentUIDs;

    public Student(String uid, String name, String email, String userType, String graduatingYear){
        super(uid, name, email, userType);
        this.graduatingYear = graduatingYear;
    }

    /**
     * Getter method for graduatingYear
     *
     * @return String of the graduatingYear.
     */
    public String getGraduatingYear(){
        return graduatingYear;
    }

    /**
     * Getter method for parentUIDs
     *
     * @return ArrayList of the parentUIDs.
     */
    public ArrayList<String> getParentUIDs(){
        return parentUIDs;
    }
}
