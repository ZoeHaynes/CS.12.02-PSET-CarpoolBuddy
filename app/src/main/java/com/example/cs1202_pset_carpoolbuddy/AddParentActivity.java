package com.example.cs1202_pset_carpoolbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddParentActivity extends AppCompatActivity {
    private EditText parentEmailField;
    private TextView parentList;
    private String uid;
    private FirebaseFirestore firestore;
    private ArrayList parentUIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addparent);
        uid = getIntent().getStringExtra("uid");
        parentEmailField = findViewById(R.id.parentsEmailAddress);
        parentList = findViewById(R.id.parentsList);
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("Users")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        Object parentUIDsObj = documentSnapshot.get("parentUIDs");
                        if (parentUIDsObj instanceof ArrayList<?>) {
                            parentUIDs = (ArrayList<String>) parentUIDsObj;
                        }
                    }
                    if(parentUIDs!=null){
                        StringBuilder display = new StringBuilder();
                        for (int i = 0; i < parentUIDs.size(); i++) {
                            display.append(parentUIDs.get(i));
                            display.append("\n");
                        }
                        parentList.setText(display.toString());
                    }
                    else{
                        parentList.setText("");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });

    }

    public void addParent(View v){
        String newParentEmail = parentEmailField.getText().toString();
        firestore.collection("Users")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        Object parentUIDsObj = documentSnapshot.get("parentUIDs");
                        if (parentUIDsObj instanceof ArrayList<?>) {
                            parentUIDs = (ArrayList<String>) parentUIDsObj;
                        }
                        if(parentUIDs!=null) {
                            parentUIDs.add(newParentEmail);
                            StringBuilder display = new StringBuilder();
                            for (int i = 0; i < parentUIDs.size(); i++) {
                                display.append(parentUIDs.get(i));
                                display.append("\n");
                            }
                            parentList.setText(display.toString());
                        }
                        else{
                            parentUIDs = new ArrayList<String>();
                            parentUIDs.add(newParentEmail);
                            StringBuilder display = new StringBuilder();
                            for (int i = 0; i < parentUIDs.size(); i++) {
                                display.append(parentUIDs.get(i));
                                display.append("\n");
                            }
                            parentList.setText(display.toString());
                        }
                        DocumentReference documentRef = firestore.collection("Users").document(uid);
                        documentRef.update("parentUIDs", parentUIDs)
                                .addOnSuccessListener(aVoid -> {
                                    // Update successful
                                    Log.d("FIRESTORE", "Successfully added parent.");
                                })
                                .addOnFailureListener(e -> {
                                    // Error occurred
                                    Log.e("FIRESTORE", "Error adding parent", e);
                                });
                    }
                    Log.d("FIRESTORE", "Firestore data retrieved successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });

    }
    public void goBackParent(View v){
        finish();
    }

}
