package com.example.cs1202_pset_carpoolbuddy.Vehicles;

public class Helicopter extends Vehicle{
    private int maxAltitude;
    private int maxAirSpeed;
    public Helicopter(String ownerUI,String owner, String model, int capacity, String vehicleID, String vehicleType, double basePrice) {
        super(ownerUI, owner, model, capacity, vehicleID, vehicleType, basePrice);

    }

    public void setMaxAltitude(int maxAltitude) {
        this.maxAltitude = maxAltitude;
    }
    public void setMaxAirSpeed(int maxAirSpeed) {
        this.maxAirSpeed = maxAirSpeed;
    }
    public int getMaxAltitude(){
        return maxAltitude;
    }
    public int getMaxAirSpeed(){
        return maxAirSpeed;
    }
}
