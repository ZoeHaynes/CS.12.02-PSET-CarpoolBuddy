package com.example.cs1202_pset_carpoolbuddy.Vehicles;

/**
 * This class provides the properties, methods, and constructor for the Bicycle class, which extends the Vehicle class.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
public class Bicycle extends Vehicle{
    private String bicycleType;
    private int weight;
    private int weightCapacity;
    public Bicycle(String ownerUI, String owner, String model, int capacity, String vehicleID, String vehicleType, double basePrice) {
        super(ownerUI, owner, model, capacity, vehicleID, vehicleType, basePrice);

    }
    /**
     * Getter method for bicycleType
     *
     * @return String of the bicycleType.
     */
    public String getBicycleType(){
        return bicycleType;
    }
    /**
     * Getter method for weight
     *
     * @return int of the weight.
     */
    public int getWeight(){
        return weight;
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
