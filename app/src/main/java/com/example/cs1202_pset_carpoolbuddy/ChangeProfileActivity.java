package com.example.cs1202_pset_carpoolbuddy;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

/**
 * This class runs the activity to change the profile avatar of the current user.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
public class ChangeProfileActivity extends AppCompatActivity {
    private int step;
    private Button skinColorButton;
    private Button hairColorButton;
    private Button hairStyleButton;
    private Button nextButton;
    private TextView changeText;
    private ImageView avatar;
    private FirebaseFirestore firestore;
    private String uid;
    private int hairColor;
    private int skinColor;
    private int hairStyle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeavatar);
        step = 1;
        uid = getIntent().getStringExtra("uid");
        firestore = FirebaseFirestore.getInstance();
        skinColorButton = findViewById(R.id.changeSkin);
        hairColorButton = findViewById(R.id.changeHairColor);
        hairStyleButton = findViewById(R.id.changeHairStyle);
        nextButton = findViewById(R.id.nextCH);
        changeText = findViewById(R.id.textChange);
        avatar = findViewById(R.id.avatar);
        changeAvatar(uid);
        skinColorButton.setEnabled(true);
        skinColorButton.setVisibility(View.VISIBLE);
        hairColorButton.setEnabled(false);
        hairColorButton.setVisibility(View.GONE);
        hairStyleButton.setEnabled(false);
        hairStyleButton.setVisibility(View.GONE);

    }

    public void changeAvatar(String uid) {
        firestore.collection("Users")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        skinColor = Math.toIntExact(documentSnapshot.getLong("skinColor"));
                        hairColor = Math.toIntExact(documentSnapshot.getLong("hairColor"));
                        hairStyle = Math.toIntExact(documentSnapshot.getLong("hairStyle"));

                    }
                    Log.d("FIRESTORE", "Firestore data retrieved successfully");
                    String imageName = "hair" + hairStyle + "_color" + hairColor + "_skin" + skinColor;
                    int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                    avatar.setImageResource(resourceId);
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });


    }

    public void goBackProfile(View v){
        if(step==1){
            finish();
        }
        else if(step==2){
            step=1;
            skinColorButton.setEnabled(true);
            skinColorButton.setVisibility(View.VISIBLE);
            hairColorButton.setEnabled(false);
            hairColorButton.setVisibility(View.GONE);
            hairStyleButton.setEnabled(false);
            hairStyleButton.setVisibility(View.GONE);

        }
        else if(step==3){
            step=2;
            skinColorButton.setEnabled(false);
            skinColorButton.setVisibility(View.GONE);
            hairColorButton.setEnabled(true);
            hairColorButton.setVisibility(View.VISIBLE);
            hairStyleButton.setEnabled(false);
            hairStyleButton.setVisibility(View.GONE);
            nextButton.setText("Next");
        }
    }

    public void goNextChange(View v){
        if(step==1){
            step=2;
            skinColorButton.setEnabled(false);
            skinColorButton.setVisibility(View.GONE);
            hairColorButton.setEnabled(true);
            hairColorButton.setVisibility(View.VISIBLE);
            hairStyleButton.setEnabled(false);
            hairStyleButton.setVisibility(View.GONE);

        }
        else if(step==2){
            step=3;
            skinColorButton.setEnabled(false);
            skinColorButton.setVisibility(View.GONE);
            hairColorButton.setEnabled(false);
            hairColorButton.setVisibility(View.GONE);
            hairStyleButton.setEnabled(true);
            hairStyleButton.setVisibility(View.VISIBLE);
            nextButton.setText("Save Avatar");


        }
        else if(step==3){
            finish();
        }

    }

    public void changeSkinColor(View v){
        firestore.collection("Users")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        skinColor = Math.toIntExact(documentSnapshot.getLong("skinColor"));
                        if(skinColor==1||skinColor==2){
                            skinColor++;
                        }
                        else{
                            skinColor=1;
                        }
                        Log.d("SKIN",""+skinColor);
                        DocumentReference documentRef = firestore.collection("Users").document(uid);
                        documentRef.update("skinColor", skinColor)
                                .addOnSuccessListener(aVoid -> {
                                    // Update successful
                                    Log.d("FIRESTORE", "Successfully changed skin color.");
                                    changeAvatar(uid);
                                })
                                .addOnFailureListener(e -> {
                                    // Error occurred
                                    Log.e("FIRESTORE", "Error changing skin color", e);
                                });
                    }
                    Log.d("FIRESTORE", "Firestore data retrieved successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });

    }
    public void changeHairColor(View v){
        firestore.collection("Users")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        hairColor = Math.toIntExact(documentSnapshot.getLong("hairColor"));
                        if(hairColor>=1&&hairColor<4){
                            hairColor++;
                        }
                        else{
                            hairColor=1;
                        }
                        DocumentReference documentRef = firestore.collection("Users").document(uid);
                        documentRef.update("hairColor", hairColor)
                                .addOnSuccessListener(aVoid -> {
                                    // Update successful
                                    Log.d("FIRESTORE", "Successfully changed hair color.");
                                    changeAvatar(uid);
                                })
                                .addOnFailureListener(e -> {
                                    // Error occurred
                                    Log.e("FIRESTORE", "Error changing hair color", e);
                                });
                    }
                    Log.d("FIRESTORE", "Firestore data retrieved successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });

    }
    public void changeHairstyle(View v){
        firestore.collection("Users")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        hairStyle = Math.toIntExact(documentSnapshot.getLong("hairStyle"));
                        if(hairStyle>=1&&hairStyle<5){
                            hairStyle++;
                        }
                        else{
                            hairStyle=1;
                        }
                        DocumentReference documentRef = firestore.collection("Users").document(uid);
                        documentRef.update("hairStyle", hairStyle)
                                .addOnSuccessListener(aVoid -> {
                                    // Update successful
                                    Log.d("FIRESTORE", "Successfully changed hairstyle.");
                                    changeAvatar(uid);
                                })
                                .addOnFailureListener(e -> {
                                    // Error occurred
                                    Log.e("FIRESTORE", "Error changing hairstyle", e);
                                });
                    }
                    Log.d("FIRESTORE", "Firestore data retrieved successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error retrieving Firestore data", e);
                });

    }

}
