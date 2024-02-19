package com.example.cs1202_pset_carpoolbuddy;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cs1202_pset_carpoolbuddy.Users.Alumni;
import com.example.cs1202_pset_carpoolbuddy.Users.Parent;
import com.example.cs1202_pset_carpoolbuddy.Users.Student;
import com.example.cs1202_pset_carpoolbuddy.Users.Teacher;
import com.example.cs1202_pset_carpoolbuddy.Vehicles.Bicycle;
import com.example.cs1202_pset_carpoolbuddy.Vehicles.Car;
import com.example.cs1202_pset_carpoolbuddy.Vehicles.Helicopter;
import com.example.cs1202_pset_carpoolbuddy.Vehicles.Segway;
import com.example.cs1202_pset_carpoolbuddy.Vehicles.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class AddVehicleActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText maxCapacityField;
    private EditText modelField;
    private EditText basePriceField;
    private ImageView imagePreview;
    private ActivityResultLauncher<Intent> imageActivityResultLauncher;
    private String vehicleType;
    private String owner;
    private String uid;
    private Uri vehicleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvehicle);
        Spinner spinner = findViewById(R.id.spinnerVehicleType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.vehicle_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        firestore = FirebaseFirestore.getInstance();
        maxCapacityField = findViewById(R.id.editMaxCap);
        modelField = findViewById(R.id.editModel);
        basePriceField = findViewById(R.id.editBasePrice);
        imagePreview = findViewById(R.id.imagePreviewView);
        imagePreview.setVisibility(View.GONE);
        imageActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        vehicleImage = selectedImageUri;
                        imagePreview.setImageURI(selectedImageUri);
                        imagePreview.setVisibility(View.VISIBLE);

                    }
                }
        );
        owner = getIntent().getStringExtra("owner");
        uid = getIntent().getStringExtra("uid");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                vehicleType = selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    public void selectImage(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageActivityResultLauncher.launch(intent);
    }

    public void addVehicle(View v){
        Log.d("ADD VEHICLE","Adding new vehicle...");
        String vehicleID = UUID.randomUUID().toString();
        String maxCapacityString = maxCapacityField.getText().toString();
        String model = modelField.getText().toString();
        String basePriceString = basePriceField.getText().toString();
        if(TextUtils.isEmpty(model) || TextUtils.isEmpty(maxCapacityString) || TextUtils.isEmpty(basePriceString)) {
            Toast.makeText(AddVehicleActivity.this, "Make sure all fields are filled in correctly.",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            int maxCapacity = Integer.parseInt(maxCapacityString);
            double basePrice = Double.parseDouble(basePriceString);
            if(vehicleImage!=null) {
                String vehicleImageString = vehicleImage.toString();
                Vehicle newVehicle = new Vehicle(uid, owner, model, maxCapacity, vehicleID, vehicleType, basePrice,vehicleImageString);
                String addUid = uid;
                newVehicle.addRider(addUid);
                firestore.collection("Vehicles").document(vehicleID).set(newVehicle)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Document successfully written
                                Log.d("ADD VEHICLE", "Vehicle data saved successfully.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // An error occurred while writing the document
                                Log.w("ADD VEHICLE", "Error saving vehicle data", e);
                            }
                        });

                finish();
            }
            else{
                Vehicle newVehicle = new Vehicle(uid, owner, model, maxCapacity, vehicleID, vehicleType, basePrice);
                String addUid = uid;
                newVehicle.addRider(addUid);
                firestore.collection("Vehicles").document(vehicleID).set(newVehicle)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Document successfully written
                                Log.d("ADD VEHICLE", "Vehicle data saved successfully.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // An error occurred while writing the document
                                Log.w("ADD VEHICLE", "Error saving vehicle data", e);
                            }
                        });

                finish();
            }

        }
    }

    public void goBackAddV(View v){
        finish();
    }



}