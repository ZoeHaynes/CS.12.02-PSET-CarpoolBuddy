package com.example.cs1202_pset_carpoolbuddy.Vehicles;

import java.util.ArrayList;

/**
 * This class provides the properties, methods, and constructors for the Vehicle class, which has child classes
 * Bicycle, Car, Helicopter and Segway.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
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

    /**
     * Getter method for owner
     *
     * @return String of the owner.
     */
    public String getOwner(){
        return owner;
    }

    /**
     * Getter method for model
     *
     * @return String of the model.
     */
    public String getModel(){
        return model;
    }

    /**
     * Getter method for vehicleID
     *
     * @return String of the vehicleID.
     */
    public String getVehicleID(){
        return vehicleID;
    }

    /**
     * Getter method for vehicleType
     *
     * @return String of the vehicleType.
     */
    public String getVehicleType(){
        return vehicleType;
    }

    /**
     * Getter method for basePrice
     *
     * @return double of the basePrice.
     */
    public double getBasePrice(){
        return basePrice;
    }

    /**
     * Getter method for ridersUIDs
     *
     * @return ArrayList of type String of the ridersUIDs.
     */
    public ArrayList<String> getRidersUIDs(){
        return ridersUIDs;
    }

    /**
     * Method that checks if vehicle is open.
     *
     * @return boolean of open.
     */
    public boolean isOpen(){
        return open;
    }
    /**
     * Method to close the vehicle if it is open.
     *
     */
    public void closeVehicle(){
        this.open=false;
    }
    /**
     * Getter method for capacity
     *
     * @return int of the capacity.
     */
    public int getCapacity(){
        return capacity;
    }
    /**
     * Getter method for remainingSeats
     *
     * @return int of the remainingSeats.
     */
    public int getRemainingSeats(){
        return remainingSeats;
    }
    /**
     * Getter method for ownerUI
     *
     * @return String of the ownerUI.
     */
    public String getOwnerUI() {
        return ownerUI;
    }

    /**
     * Method to add a rider to the ridersUIDs ArrayList given the UID of the rider.
     *
     * @param riderUID
     */
    public void addRider(String riderUID){
        ridersUIDs.add(riderUID);
    }
    /**
     * Getter method for vehicleImage
     *
     * @return String of the vehicleImage.
     */
    public String getVehicleImage(){
        return vehicleImage;
    }
}
