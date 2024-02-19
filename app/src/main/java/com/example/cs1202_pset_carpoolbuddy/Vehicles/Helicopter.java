package com.example.cs1202_pset_carpoolbuddy.Vehicles;

/**
 * This class provides the properties, methods, and constructor for the Helicopter class, which extends the Vehicle class.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
public class Helicopter extends Vehicle{
    private int maxAltitude;
    private int maxAirSpeed;
    public Helicopter(String ownerUI,String owner, String model, int capacity, String vehicleID, String vehicleType, double basePrice) {
        super(ownerUI, owner, model, capacity, vehicleID, vehicleType, basePrice);

    }

    /**
     * Getter method for maxAltitude
     *
     * @return int of the maxAltitude.
     */
    public int getMaxAltitude(){
        return maxAltitude;
    }
    /**
     * Getter method for maxAirSpeed
     *
     * @return int of the maxAirSpeed.
     */
    public int getMaxAirSpeed(){
        return maxAirSpeed;
    }
}
