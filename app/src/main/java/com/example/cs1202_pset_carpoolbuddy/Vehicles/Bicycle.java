package com.example.cs1202_pset_carpoolbuddy.Vehicles;

public class Bicycle extends Vehicle{
    private String bicycleType;
    private int weight;
    private int weightCapacity;
    public Bicycle(String ownerUI, String owner, String model, int capacity, String vehicleID, String vehicleType, double basePrice) {
        super(ownerUI, owner, model, capacity, vehicleID, vehicleType, basePrice);

    }

    public void setBicycleType(String bicycleType) {
        this.bicycleType = bicycleType;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setWeightCapacity(int weightCapacity) {
        this.weightCapacity = weightCapacity;
    }
    public String getBicycleType(){
        return bicycleType;
    }
    public int getWeight(){
        return weight;
    }
    public int getWeightCapacity(){
        return weightCapacity;
    }
}
