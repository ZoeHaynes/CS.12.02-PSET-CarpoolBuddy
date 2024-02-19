package com.example.cs1202_pset_carpoolbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cs1202_pset_carpoolbuddy.Users.Alumni;
import com.example.cs1202_pset_carpoolbuddy.Users.Parent;
import com.example.cs1202_pset_carpoolbuddy.Users.Student;
import com.example.cs1202_pset_carpoolbuddy.Users.Teacher;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;


public class SignUpGoogleActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private EditText lastField;
    private String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupgoogleactivity);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.user_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        lastField = findViewById(R.id.editTexter);
        lastField.setVisibility(View.GONE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (selectedItem.equals("Alumni")) {
                    lastField.setHint("Year Graduated");
                    lastField.setVisibility(View.VISIBLE);
                    userType = "Alumni";
                }
                else if(selectedItem.equals("Student")){
                    lastField.setHint("Graduating Year");
                    lastField.setVisibility(View.VISIBLE);
                    userType = "Student";

                }
                else if(selectedItem.equals("Teacher")){
                    lastField.setHint("In School Title");
                    lastField.setVisibility(View.VISIBLE);
                    userType = "Teacher";
                }
                else if(selectedItem.equals("Parent")){
                    lastField.setVisibility(View.GONE);
                    userType = "Parent";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    public void signUpGoogle(View v) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Log.d("SIGN UP", "Signing Up.");
        String uid = UUID.randomUUID().toString();
        String last = lastField.getText().toString();
        if((!TextUtils.isEmpty(last)) || userType.equals("Parent")) {
                if (userType.equals("Alumni")) {
                    Alumni newUser = new Alumni(uid, account.getDisplayName(), account.getEmail(), userType, last);
                    firestore.collection("Users").document(uid).set(newUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Document successfully written
                                    Log.d("SIGN UP", "User data saved successfully.");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // An error occurred while writing the document
                                    Log.w("SIGN UP", "Error saving user data", e);
                                }
                            });
                } else if (userType.equals("Student")) {
                    Student newUser = new Student(uid, account.getDisplayName(), account.getEmail(), userType, last);
                    firestore.collection("Users").document(uid).set(newUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Document successfully written
                                    Log.d("SIGN UP", "User data saved successfully.");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // An error occurred while writing the document
                                    Log.w("SIGN UP", "Error saving user data", e);
                                }
                            });
                } else if (userType.equals("Parent")) {
                    Parent newUser = new Parent(uid, account.getDisplayName(), account.getEmail(), userType);
                    firestore.collection("Users").document(uid).set(newUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Document successfully written
                                    Log.d("SIGN UP", "User data saved successfully.");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // An error occurred while writing the document
                                    Log.w("SIGN UP", "Error saving user data", e);
                                }
                            });
                } else if (userType.equals("Teacher")) {
                    Teacher newUser = new Teacher(uid.toString(), account.getDisplayName(), account.getEmail(), userType, last);
                    firestore.collection("Users").document(uid).set(newUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Document successfully written
                                    Log.d("SIGN UP", "User data saved successfully.");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // An error occurred while writing the document
                                    Log.w("SIGN UP", "Error saving user data", e);
                                }
                            });
                }

                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            }
        else{
            Toast.makeText(SignUpGoogleActivity.this, "Please make sure all fields are filled in.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void goBackGoogle(View v){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }

    }

}