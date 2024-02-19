package com.example.cs1202_pset_carpoolbuddy.Users;

import java.util.ArrayList;

/**
 * This class provides the properties, methods, and constructor for the Parent class, which extends the User class.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
public class Parent extends User{
    private ArrayList<String> childrenUIDs;

    public Parent(String uid, String name, String email, String userType){
        super(uid, name, email, userType);

    }


    /**
     * Getter method for childrenUIDs
     *
     * @return ArrayList of the childrenUIds.
     */
    public ArrayList<String> getChildrenUIDs(){
        return childrenUIDs;
    }


}
