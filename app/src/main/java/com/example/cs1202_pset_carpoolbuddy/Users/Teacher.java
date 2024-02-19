package com.example.cs1202_pset_carpoolbuddy.Users;

/**
 * This class provides the properties, methods, and constructor for the Teacher class, which extends the User class.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
public class Teacher extends User{
    private String inSchoolTitle;

    public Teacher(String uid, String name, String email, String userType, String inSchoolTitle){
        super(uid, name, email, userType);
        this.inSchoolTitle = inSchoolTitle;
    }

    /**
     * Getter method for inSchoolTitle
     *
     * @return String of the inSchoolTitle.
     */
    public String getInSchoolTitle(){
        return inSchoolTitle;
    }

}
