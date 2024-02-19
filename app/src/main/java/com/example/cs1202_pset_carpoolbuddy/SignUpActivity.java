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
import com.example.cs1202_pset_carpoolbuddy.Users.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.UUID;


public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private EditText emailAddressField;
    private EditText nameField;
    private EditText passwordField;
    private EditText lastField;
    private String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);

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
        emailAddressField = findViewById(R.id.editEmail);
        passwordField = findViewById(R.id.editPassword);
        nameField = findViewById(R.id.editName);
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


    public void signUp(View v) {
        Log.d("SIGN UP", "Signing Up.");
        String uid = UUID.randomUUID().toString();
        String emailString = emailAddressField.getText().toString();
        String passwordString = passwordField.getText().toString();
        String name = nameField.getText().toString();
        String last = lastField.getText().toString();
        if(!emailString.endsWith("cis.edu.hk")){
            Toast.makeText(SignUpActivity.this, "Please make sure you have entered a CIS email address.",
                    Toast.LENGTH_SHORT).show();
        }
        else if((!TextUtils.isEmpty(emailString)&&!TextUtils.isEmpty(passwordString)&&!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(last)) || (!TextUtils.isEmpty(emailString)&&!TextUtils.isEmpty(passwordString)&&!TextUtils.isEmpty(name)&& userType=="Parent")) {
            mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    // If sign up is successful, update UI
                    if (task.isSuccessful()) {
                        Log.d("SIGN UP", "Successfully signed up the user.");
                        if (userType.equals("Alumni")) {
                            Alumni newUser = new Alumni(uid, name, emailString, userType, last);
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
                            Student newUser = new Student(uid, name, emailString, userType, last);
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
                            Parent newUser = new Parent(uid, name, emailString, userType);
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
                            Teacher newUser = new Teacher(uid.toString(), name, emailString, userType, last);
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
                    // If sign up fails, display a message
                    else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            // Handle weak password exception
                            Toast.makeText(SignUpActivity.this, "Password is too weak. Please choose a stronger password.",
                                    Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            // Handle invalid email exception
                            Toast.makeText(SignUpActivity.this, "Invalid credentials. Please enter a valid email and password.",
                                    Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthUserCollisionException e) {
                            // Handle user collision exception
                            Toast.makeText(SignUpActivity.this, "Email address is already registered. Please use a different email.",
                                    Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthEmailException e) {
                            // Handle email exception
                            Toast.makeText(SignUpActivity.this, "Please provide a valid email address.",
                                    Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthActionCodeException e) {
                            // Handle action code exception
                            Toast.makeText(SignUpActivity.this, "Invalid action code. Please try again.",
                                    Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthInvalidUserException e) {
                            // Handle invalid user exception
                            Toast.makeText(SignUpActivity.this, "Invalid user. Please try again.",
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            // Handle other exceptions
                            Toast.makeText(SignUpActivity.this, "An error occurred during sign up. Please try again.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        updateUI(null);
                    }
                }
            });
        }
        else{
            Toast.makeText(SignUpActivity.this, "Please make sure all fields are filled in.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(View v){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }

    }

}