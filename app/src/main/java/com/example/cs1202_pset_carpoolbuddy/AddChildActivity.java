package com.example.cs1202_pset_carpoolbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs1202_pset_carpoolbuddy.Users.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Array;
import java.util.ArrayList;

public class AddChildActivity extends AppCompatActivity {
    private EditText childEmailField;
    private TextView childrenList;
    private String uid;
    private FirebaseFirestore firestore;
    private ArrayList childrenUIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addchild);
        uid = getIntent().getStringExtra("uid");
        childEmailField = findViewById(R.id.childsEmailAddress);
        childrenList = findViewById(R.id.childrenList);
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("Users")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        Object childrenUIDsObj = documentSnapshot.get("childrenUIDs");
                        if (childrenUIDsObj instanceof ArrayList<?>) {
                            childrenUIDs = (ArrayList<String>) childrenUIDsObj;
                        }
                    }
                    if(childrenUIDs!=null){
                        StringBuilder display = new StringBuilder();
                        for (int i = 0; i < childrenUIDs.size(); i++) {
                            display.append(childrenUIDs.get(i));
                            display.append("\n");
                        }
                        childrenList.setText(display.toString());
                    }
                    else{
                        childrenList.setText("");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });

    }

    public void addChild(View v){
        String newChildEmail = childEmailField.getText().toString();
        firestore.collection("Users")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        Object childrenUIDsObj = documentSnapshot.get("childrenUIDs");
                        if (childrenUIDsObj instanceof ArrayList<?>) {
                            childrenUIDs = (ArrayList<String>) childrenUIDsObj;
                        }
                        if(childrenUIDs!=null) {
                            childrenUIDs.add(newChildEmail);
                            StringBuilder display = new StringBuilder();
                            for (int i = 0; i < childrenUIDs.size(); i++) {
                                display.append(childrenUIDs.get(i));
                                display.append("\n");
                            }
                            childrenList.setText(display.toString());
                        }
                        else{
                            childrenUIDs = new ArrayList<String>();
                            childrenUIDs.add(newChildEmail);
                            StringBuilder display = new StringBuilder();
                            for (int i = 0; i < childrenUIDs.size(); i++) {
                                display.append(childrenUIDs.get(i));
                                display.append("\n");
                            }
                            childrenList.setText(display.toString());
                        }
                        DocumentReference documentRef = firestore.collection("Users").document(uid);
                        documentRef.update("childrenUIDs", childrenUIDs)
                                .addOnSuccessListener(aVoid -> {
                                    // Update successful
                                    Log.d("FIRESTORE", "Successfully added child.");
                                })
                                .addOnFailureListener(e -> {
                                    // Error occurred
                                    Log.e("FIRESTORE", "Error adding child", e);
                                });
                    }
                    Log.d("FIRESTORE", "Firestore data retrieved successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });

    }

    public void goBackChild(View v){
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
        finish();
    }

}
