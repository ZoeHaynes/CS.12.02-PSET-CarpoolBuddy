package com.example.cs1202_pset_carpoolbuddy.Vehicles;

/**
 * This class provides the properties, methods, and constructor for the Segway class, which extends the Vehicle class.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
public class Segway extends Vehicle{
    private int range;
    private int weightCapacity;
    public Segway(String ownerUI, String owner, String model, int capacity, String vehicleID, String vehicleType, double basePrice) {
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
    /**
     * Getter method for weightCapacity
     *
     * @return int of the weightCapacity.
     */
    public int getWeightCapacity(){
        return weightCapacity;
    }



}
