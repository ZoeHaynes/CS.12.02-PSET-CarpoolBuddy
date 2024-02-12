package com.example.cs1202_pset_carpoolbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;
    private TextView username;
    private TextView name;
    private TextView emailAddress;
    private TextView userType;
    private TextView vehicles;
    private TextView last;
    private Button family;
    private String thisUserType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        username = findViewById(R.id.username);
        name = findViewById(R.id.nameText);
        emailAddress = findViewById(R.id.emailText);
        userType = findViewById(R.id.userTypeText);
        vehicles = findViewById(R.id.vehiclesText);
        last = findViewById(R.id.lastText);
        family = findViewById(R.id.seeFamily);
        String email = mUser.getEmail();
        family.setEnabled(false);
        family.setVisibility(View.GONE);
        emailAddress.setText("Email: "+email);
        firestore.collection("Users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        String thisName = documentSnapshot.getString("name");
                        name.setText("Name: "+ thisName);
                        username.setText(thisName);
                        thisUserType = documentSnapshot.getString("userType");
                        userType.setText("User Type: "+thisUserType);
                        assert thisUserType != null;
                        if(thisUserType.equals("Parent")){
                            last.setVisibility(View.GONE);
                            family.setText("My Children");
                            family.setVisibility(View.VISIBLE);
                            family.setEnabled(true);

                        }
                        else if(thisUserType.equals("Student")){
                            family.setText("My Parents");
                            String thisGradYear = documentSnapshot.getString("graduatingYear");
                            last.setText("Graduating Year: "+thisGradYear);
                            family.setVisibility(View.VISIBLE);
                            family.setEnabled(true);

                        }
                        else{
                            if(thisUserType.equals("Alumni")){
                                String thisYearGrad = documentSnapshot.getString("graduateYear");
                                last.setText("Year Graduated: "+thisYearGrad);

                            }
                            else if(thisUserType.equals("Teacher")){
                                String thisSchoolTitle = documentSnapshot.getString("inSchoolTitle");
                                last.setText("In School Title: "+thisSchoolTitle);
                            }
                        }

                        ArrayList<String> ownedVehicles = documentSnapshot.get("ownedVehicles", ArrayList.class);
                        if(ownedVehicles==null){
                            vehicles.setText("No. of Vehicles: 0");
                        }
                        else{
                            String size = ""+(ownedVehicles.size());
                            vehicles.setText("No. of Vehicles: "+size);
                        }
                    }
                    Log.d("FIRESTORE", "Firestore data retrieved successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });



    }

    public void signOut(View v){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();

    }

    public void seeVehicles(View v){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, VehiclesInfoActivity.class);
        startActivity(intent);
        finish();


    }

    public void seeFamily(View v){
        if(thisUserType.equals("Student")){
            Intent intent = new Intent(this, AddParentActivity.class);
            startActivity(intent);
            finish();
        }
        if(thisUserType.equals("Parent")){
            Intent intent = new Intent(this, AddChildActivity.class);
            startActivity(intent);
            finish();

        }

    }

    public void changeAvatar(View v){
        Intent intent = new Intent(this, ChangeProfileActivity.class);
        startActivity(intent);
        finish();

    }

}