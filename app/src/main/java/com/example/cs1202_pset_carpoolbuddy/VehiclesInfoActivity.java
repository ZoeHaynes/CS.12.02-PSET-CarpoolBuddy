package com.example.cs1202_pset_carpoolbuddy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cs1202_pset_carpoolbuddy.Vehicles.Vehicle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.common.value.qual.IntRangeFromGTENegativeOne;
import org.w3c.dom.Document;

import java.util.ArrayList;

public class VehiclesInfoActivity extends AppCompatActivity {
    private String owner;
    private String uid;
    RecyclerView recyclerView;
    private ArrayList<Vehicle> vehicleArrayList;
    private VehicleRecyclerViewAdapter myAdapter;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiclesinfo);
        owner = getIntent().getStringExtra("owner");
        uid = getIntent().getStringExtra("uid");
        Log.d("OWNER", owner);

        recyclerView = findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vehicleArrayList = new ArrayList<>();
        myAdapter = new VehicleRecyclerViewAdapter(this, vehicleArrayList,uid);
        recyclerView.setAdapter(myAdapter);

        firestore = FirebaseFirestore.getInstance();
        eventChangeListener();
    }

    private void eventChangeListener() {
        firestore.collection("Vehicles")
                .orderBy("remainingSeats", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("FIRESTORE", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Vehicle vehicle = dc.getDocument().toObject(Vehicle.class);
                                vehicleArrayList.add(vehicle);
                            } else if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                String modifiedVehicleId = dc.getDocument().getId();
                                int modifiedIndex = -1;
                                for (int i = 0; i < vehicleArrayList.size(); i++) {
                                    Vehicle vehicle = vehicleArrayList.get(i);
                                    if (vehicle.getVehicleID().equals(modifiedVehicleId)) {
                                        modifiedIndex = i;
                                        break;
                                    }
                                }
                                // If the vehicle is found, update it with the modified data
                                if (modifiedIndex != -1) {
                                    Vehicle modifiedVehicle = dc.getDocument().toObject(Vehicle.class);
                                    vehicleArrayList.set(modifiedIndex, modifiedVehicle);
                                }
                            } else if (dc.getType() == DocumentChange.Type.REMOVED) {
                                String removedVehicleId = dc.getDocument().getId();
                                int removedIndex = -1;
                                for (int i = 0; i < vehicleArrayList.size(); i++) {
                                    Vehicle vehicle = vehicleArrayList.get(i);
                                    if (vehicle.getVehicleID().equals(removedVehicleId)) {
                                        removedIndex = i;
                                        break;
                                    }
                                }
                                // If the vehicle is found, remove it from the list
                                if (removedIndex != -1) {
                                    vehicleArrayList.remove(removedIndex);
                                }
                            }
                        }
                        myAdapter.notifyDataSetChanged();
                    }

                });
    }

    public void addNewVehicleInfo(View v) {
        Intent intent = new Intent(this, AddVehicleActivity.class);
        intent.putExtra("owner", owner);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void refresh(View v){
        Intent intent = new Intent(this,VehiclesInfoActivity.class);
        intent.putExtra("owner", owner);
        intent.putExtra("uid", uid);
        startActivity(intent);
        finish();
    }

    public void goBackVehicleInfo(View v) {
        finish();
    }
}