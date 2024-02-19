package com.example.cs1202_pset_carpoolbuddy.Vehicles;

import java.util.ArrayList;

public class Vehicle {
    private String owner;
    private String model;
    private int capacity;
    private String vehicleID;
    private ArrayList<String> ridersUIDs;
    private boolean open;
    private String vehicleType;
    private double basePrice;
    private int remainingSeats;
    private String ownerUI;
    private String vehicleImage;

    public Vehicle() {
    }

    public Vehicle(String ownerUI, String owner, String model, int capacity, String vehicleID, String vehicleType, double basePrice){
        this.owner = owner;
        this.model = model;
        this.capacity = capacity;
        this.vehicleID = vehicleID;
        this.vehicleType = vehicleType;
        this.basePrice = basePrice;
        this.ridersUIDs = new ArrayList<>();
        this.remainingSeats = capacity-1;
        this.ownerUI=ownerUI;
        this.vehicleImage=null;
        open=true;
    }

    public Vehicle(String ownerUI, String owner, String model, int capacity, String vehicleID, String vehicleType, double basePrice, String vehicleImage){
        this.owner = owner;
        this.model = model;
        this.capacity = capacity;
        this.vehicleID = vehicleID;
        this.vehicleType = vehicleType;
        this.basePrice = basePrice;
        this.ridersUIDs = new ArrayList<>();
        this.remainingSeats = capacity-1;
        this.ownerUI=ownerUI;
        this.vehicleImage = vehicleImage;
        open=true;
    }

    public String getOwner(){
        return owner;
    }

    public String getModel(){
        return model;
    }

    public String getVehicleID(){
        return vehicleID;
    }

    public String getVehicleType(){
        return vehicleType;
    }

    public double getBasePrice(){
        return basePrice;
    }
    public ArrayList<String> getRidersUIDs(){
        return ridersUIDs;
    }
    public boolean isOpen(){
        return open;
    }
    public void closeVehicle(){
        this.open=false;
    }
    public int getCapacity(){
        return capacity;
    }
    public int getRemainingSeats(){
        return remainingSeats;
    }

    public String getOwnerUI() {
        return ownerUI;
    }
    public void addRider(String riderUID){
        ridersUIDs.add(riderUID);
    }
    public String getVehicleImage(){
        return vehicleImage;
    }
}
