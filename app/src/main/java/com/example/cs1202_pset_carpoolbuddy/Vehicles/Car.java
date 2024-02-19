package com.example.cs1202_pset_carpoolbuddy.Vehicles;

public class Car extends Vehicle{
    private int range;
    public Car(String ownerUI, String owner, String model, int capacity, String vehicleID, String vehicleType, double basePrice) {
        super(ownerUI, owner, model, capacity, vehicleID, vehicleType, basePrice);

    }
    public void setRange(int range){
        this.range=range;
    }

    public int getRange(){
        return range;
    }
}
