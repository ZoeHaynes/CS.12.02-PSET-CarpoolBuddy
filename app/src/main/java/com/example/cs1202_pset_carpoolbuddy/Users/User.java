package com.example.cs1202_pset_carpoolbuddy.Users;

import java.util.ArrayList;

/**
 * This class provides the properties, methods, and constructors for the User class, which has child classes
 * Alumni, Parent, Student and Teacher.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
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


    /**
     * Getter method for uid
     *
     * @return String of the uid.
     */
    public String getUid(){
        return uid;
    }

    /**
     * Getter method for name
     *
     * @return String of the name.
     */
    public String getName(){
        return name;
    }

    /**
     * Getter method for email
     *
     * @return String of the email.
     */
    public String getEmail(){
        return email;
    }

    /**
     * Getter method for userType
     *
     * @return String of the userType.
     */
    public String getUserType(){
        return userType;
    }

    /**
     * Getter method for priceMultiplier
     *
     * @return double of the priceMultiplier.
     */
    public double getPriceMultiplier(){
        return priceMultiplier;
    }

    /**
     * Getter method for ownedVehicles
     *
     * @return ArrayList of type String of the ownedVehicles.
     */
    public ArrayList<String> getOwnedVehicles(){
        return ownedVehicles;
    }

    /**
     * Getter method for hairColor
     *
     * @return int of the hairColor.
     */
    public int getHairColor() {
        return hairColor;
    }

    /**
     * Getter method for skinColor
     *
     * @return int of the skinColor.
     */
    public int getSkinColor() {
        return skinColor;
    }

    /**
     * Getter method for hairStyle
     *
     * @return int of the hairStyle.
     */
    public int getHairStyle() {
        return hairStyle;
    }

}
