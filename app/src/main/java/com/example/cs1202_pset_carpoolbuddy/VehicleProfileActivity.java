package com.example.cs1202_pset_carpoolbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class runs the activity to show a Vehicle object's information.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
public class VehicleProfileActivity extends AppCompatActivity {
    private String uid;
    private FirebaseFirestore firestore;
    private TextView price;
    private TextView model;
    private TextView maxCap;
    private TextView owner;
    private TextView seatsLeft;
    private TextView vehicleType;
    private Button closeVehicle;
    private Button reserveSeat;
    private ImageView vehicleImage;
    private String thisOwnerUID;
    private String vID;
    private ArrayList<String> ridersUIDs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicleprofile);
        vID = getIntent().getStringExtra("vehicle");
        closeVehicle = findViewById(R.id.closeVehicle);
        reserveSeat = findViewById(R.id.reserveSeat);
        uid = getIntent().getStringExtra("uid");
        Log.d("THIS UID",uid);
        firestore = FirebaseFirestore.getInstance();
        price = findViewById(R.id.priceText);
        model = findViewById(R.id.modelText);
        maxCap = findViewById(R.id.maxCapText);
        owner = findViewById(R.id.ownerText);
        seatsLeft = findViewById(R.id.seatsLeftText);
        vehicleType = findViewById(R.id.vehicleTypeText);
        vehicleImage = findViewById(R.id.vehicleImage);
        firestore.collection("Vehicles")
                .whereEqualTo("vehicleID", vID)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        String ownerName = documentSnapshot.getString("owner");
                        owner.setText("Owner: "+ ownerName);
                        String modelName = documentSnapshot.getString("model");
                        model.setText("Model: "+ modelName);
                        int thisMaxCap = Math.toIntExact(documentSnapshot.getLong("capacity"));
                        maxCap.setText("Max. Capacity: "+thisMaxCap);
                        double thisPrice = documentSnapshot.getDouble("basePrice");
                        price.setText("Price: "+thisPrice);
                        int seatsRemaining = Math.toIntExact(documentSnapshot.getLong("remainingSeats"));
                        seatsLeft.setText("Seats Left: "+seatsRemaining);
                        thisOwnerUID = documentSnapshot.getString("ownerUI");
                        String thisVehicleType = documentSnapshot.getString("vehicleType");
                        vehicleType.setText("Vehicle Type: "+thisVehicleType);
                        String vehicleImageString = documentSnapshot.getString("vehicleImage");
                        if (vehicleImageString != null) {
                            Uri vehicleImageUri = Uri.parse(vehicleImageString);
                            RequestOptions options = new RequestOptions()
                                    .placeholder(R.drawable.carpoolbuddylogo); // Image to show in case of error loading the image

                            Glide.with(this)
                                    .load(vehicleImageUri)
                                    .apply(options)
                                    .into(vehicleImage);
                        }
                        if(Objects.equals(uid, thisOwnerUID)){
                            Log.d("BUTTONS SWAP", "close");
                            closeVehicle.setVisibility(View.VISIBLE);
                            closeVehicle.setEnabled(true);
                            reserveSeat.setVisibility(View.GONE);
                            reserveSeat.setEnabled(false);
                            if(Boolean.FALSE.equals(documentSnapshot.getBoolean("open"))){
                                closeVehicle.setText("Open Vehicle");
                            }
                            else{
                                closeVehicle.setText("Close Vehicle");
                            }
                        }
                        else {
                            Log.d("BUTTONS SWAP", "reserve");
                            reserveSeat.setVisibility(View.VISIBLE);
                            closeVehicle.setVisibility(View.GONE);
                            closeVehicle.setEnabled(false);
                            if(Boolean.FALSE.equals(documentSnapshot.getBoolean("open"))){
                                reserveSeat.setEnabled(false);
                            }
                            else{
                                reserveSeat.setEnabled(true);
                            }
                        }

                    }
                    Log.d("FIRESTORE", "Firestore data retrieved successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });


    }

    public void goBackVP(View v){
        finish();

    }

    public void closeVehicle(View v){
        firestore.collection("Vehicles")
                .whereEqualTo("vehicleID", vID)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        boolean isOpen = documentSnapshot.getBoolean("open");
                        isOpen = !isOpen;
                        DocumentReference documentRef = firestore.collection("Vehicles").document(vID);
                        documentRef.update("open", isOpen)
                                .addOnSuccessListener(aVoid -> {
                                    // Update successful
                                    Log.d("FIRESTORE", "Successfully closed vehicle.");
                                })
                                .addOnFailureListener(e -> {
                                    // Error occurred
                                    Log.e("FIRESTORE", "Error closing vehicle", e);
                                });

                    }
                    Log.d("FIRESTORE", "Firestore data retrieved successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });

        finish();

    }

    public void reserveSeat(View v){
        firestore.collection("Vehicles")
                .whereEqualTo("vehicleID", vID)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        Object ridersUIDsObj = documentSnapshot.get("ridersUIDs");
                        if (ridersUIDsObj instanceof ArrayList<?>) {
                            ridersUIDs = (ArrayList<String>) ridersUIDsObj;
                        }
                        if(ridersUIDs!=null) {
                            ridersUIDs.add(uid);
                        }
                        else{
                            ridersUIDs = new ArrayList<String>();
                            ridersUIDs.add(uid);
                        }
                        int remainingSeats = Math.toIntExact(documentSnapshot.getLong("remainingSeats"));
                        remainingSeats--;
                        DocumentReference documentRef = firestore.collection("Vehicles").document(vID);
                        documentRef.update("remainingSeats",remainingSeats);
                        documentRef.update("ridersUIDs", ridersUIDs)
                                .addOnSuccessListener(aVoid -> {
                                    // Update successful
                                    Log.d("FIRESTORE", "Successfully added rider.");
                                })
                                .addOnFailureListener(e -> {
                                    // Error occurred
                                    Log.e("FIRESTORE", "Error adding rider", e);
                                });
                    }
                    Log.d("FIRESTORE", "Firestore data retrieved successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });
        finish();
    }


}

