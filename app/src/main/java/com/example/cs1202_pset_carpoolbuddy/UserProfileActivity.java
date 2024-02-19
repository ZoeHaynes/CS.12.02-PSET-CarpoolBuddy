package com.example.cs1202_pset_carpoolbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This class runs the activity to show the current user's profile information.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
public class UserProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;
    private TextView username;
    private TextView name;
    private TextView emailAddress;
    private TextView userType;
    private TextView last;
    private Button family;
    private String thisUserType;
    private String owner;
    private String uid;
    private int skinColor;
    private int hairColor;
    private int hairStyle;
    private ImageView avatar;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        username = findViewById(R.id.username);
        name = findViewById(R.id.priceText);
        emailAddress = findViewById(R.id.modelText);
        userType = findViewById(R.id.maxCapText);
        avatar = findViewById(R.id.vehicleImage);
        last = findViewById(R.id.ownerText);
        family = findViewById(R.id.reserveSeat);
        email = mUser.getEmail();
        family.setEnabled(false);
        family.setVisibility(View.GONE);
        emailAddress.setText("Email: "+email);
        firestore.collection("Users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        String thisName = documentSnapshot.getString("name");
                        owner = thisName;
                        name.setText("Name: "+ thisName);
                        username.setText(thisName);
                        uid = documentSnapshot.getString("uid");
                        thisUserType = documentSnapshot.getString("userType");
                        userType.setText("User Type: "+thisUserType);
                        skinColor = Math.toIntExact(documentSnapshot.getLong("skinColor"));
                        hairColor = Math.toIntExact(documentSnapshot.getLong("hairColor"));
                        hairStyle = Math.toIntExact(documentSnapshot.getLong("hairStyle"));
                        String imageName = "hair" + hairStyle + "_color" + hairColor + "_skin" + skinColor;
                        int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                        avatar.setImageResource(resourceId);
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
                    }
                    Log.d("FIRESTORE", "Firestore data retrieved successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });



    }

    @Override
    protected void onResume() {
        super.onResume();
        firestore.collection("Users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        skinColor = Math.toIntExact(documentSnapshot.getLong("skinColor"));
                        hairColor = Math.toIntExact(documentSnapshot.getLong("hairColor"));
                        hairStyle = Math.toIntExact(documentSnapshot.getLong("hairStyle"));
                        String imageName = "hair" + hairStyle + "_color" + hairColor + "_skin" + skinColor;
                        int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                        avatar.setImageResource(resourceId);
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
        intent.putExtra("owner", owner);
        intent.putExtra("uid",uid);
        startActivity(intent);

    }

    public void seeFamily(View v){
        if(thisUserType.equals("Student")){
            Intent intent = new Intent(this, AddParentActivity.class);
            intent.putExtra("uid",uid);
            startActivity(intent);
        }
        if(thisUserType.equals("Parent")){
            Intent intent = new Intent(this, AddChildActivity.class);
            intent.putExtra("uid",uid);
            startActivity(intent);

        }

    }

    public void changeAvatar(View v){
        Intent intent = new Intent(this, ChangeProfileActivity.class);
        intent.putExtra("uid",uid);
        startActivity(intent);

    }

}