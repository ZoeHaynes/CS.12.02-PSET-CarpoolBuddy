package com.example.cs1202_pset_carpoolbuddy.Vehicles;

/**
 * This class provides the properties, methods, and constructor for the Car class, which extends the Vehicle class.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
public class Car extends Vehicle{
    private int range;
    public Car(String ownerUI, String owner, String model, int capacity, String vehicleID, String vehicleType, double basePrice) {
        super(ownerUI, owner, model, capacity, vehicleID, vehicleType, basePrice);

    }

    /**
     * Getter method for range
     *
     * @return int of the range.
     */
    public int getRange(){
        return range;
    }
}
