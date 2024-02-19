package com.example.cs1202_pset_carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class AuthActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailAddressField;
    private EditText passwordField;
    public static GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authactivity);
        mAuth = FirebaseAuth.getInstance();
        emailAddressField = findViewById(R.id.editName);
        passwordField = findViewById(R.id.editEmail);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("114736672133-mpftg3vhl0b6buhh4mv7a1trujoq927t.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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
        if(emailString.isEmpty()||passwordString.isEmpty()){
            Toast.makeText(AuthActivity.this, "Please make sure all fields are filled in.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //If sign in is successful, update UI
                    if (task.isSuccessful()) {
                        Log.d("SIGN IN", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    }
                    //If sign in fails, display a message
                    else {
                        Log.w("SIGN IN", "signInWithEmail:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // Handle FirebaseAuthInvalidCredentialsException
                            Toast.makeText(AuthActivity.this, "Invalid Credentials. Please try again.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle other exceptions
                            Toast.makeText(AuthActivity.this, "Sign in failed. Please try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

        }

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


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    checkIfUserExistsInFirestore(user);
                } else {
                    Toast.makeText(AuthActivity.this, "Google sign in failed. Please try again.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkIfUserExistsInFirestore(FirebaseUser user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userEmail = user.getEmail();
        // Query Firestore to check if user exists with the same email
        db.collection("Users")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null && !task.getResult().isEmpty()) {
                                // User exists in Firestore
                                updateUI(user);
                            } else {
                                // User does not exist in Firestore
                                Intent intent = new Intent(AuthActivity.this,SignUpGoogleActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void signInWithGoogle(View v) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("SIGN IN", "Google sign in failed", e);
                Toast.makeText(AuthActivity.this, "Google sign in failed. Please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }




}