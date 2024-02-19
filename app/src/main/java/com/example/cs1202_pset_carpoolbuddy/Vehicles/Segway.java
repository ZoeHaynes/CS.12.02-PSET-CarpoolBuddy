package com.example.cs1202_pset_carpoolbuddy.Vehicles;

public class Segway extends Vehicle{
    private int range;
    private int weightCapacity;
    public Segway(String ownerUI, String owner, String model, int capacity, String vehicleID, String vehicleType, double basePrice) {
        super(ownerUI, owner, model, capacity, vehicleID, vehicleType, basePrice);

    }

    public void setRange(int range){
        this.range=range;
    }

    public void setWeightCapacity(int weightCapacity){
        this.weightCapacity=weightCapacity;
    }
    public int getRange(){
        return range;
    }
    public int getWeightCapacity(){
        return weightCapacity;
    }



}
