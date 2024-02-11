package com.example.cs1202_pset_carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class AuthActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private EditText emailAddressField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authactivity);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        emailAddressField = findViewById(R.id.editName);
        passwordField = findViewById(R.id.editEmail);

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
    }
    public void signIn(View v){
        Log.d("SIGN IN", "Signing Up.");
        String emailString = emailAddressField.getText().toString();
        String passwordString = passwordField.getText().toString();
        mAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //If sign in is successful, update UI
                if(task.isSuccessful()) {
                    Log.d("SIGN IN", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }
                //If sign in fails, display a message
                else{
                    Log.w("SIGN IN", "signInWithEmail:failure", task.getException());
                    updateUI(null);
                }
            }
        });

    }

    public void goToSignUp(View v){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }
    public void updateUI(FirebaseUser user){
        if(user != null) {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }

    }


    void uploadData(FirebaseUser mUser){

    }


    private void firebaseAuthWithGoogle(String idToken){

    }




}