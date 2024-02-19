package com.example.cs1202_pset_carpoolbuddy.Users;


/**
 * This class provides the properties, methods, and constructor for the Alumni class, which extends the User class.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
public class Alumni extends User{
    private String graduateYear;

    public Alumni(String uid, String name, String email, String userType, String graduateYear){
        super(uid, name, email, userType);
        this.graduateYear = graduateYear;
    }

    /**
     * Getter method for graduateYear.
     *
     * @return String of the Alumni's graduateYear
     */
    public String getGraduateYear(){
        return graduateYear;
    }

}
