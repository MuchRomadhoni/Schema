package com.example.myschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView masuk;
    Button buttonreg;
    EditText nama, email, password, telepon;
    FirebaseAuth firebaseAuth;
    String userID;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        masuk = findViewById(R.id.inimasuk);
        buttonreg = findViewById(R.id.buttonreg);
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.emailregister);
        password = findViewById(R.id.passregister);
        telepon = findViewById(R.id.teleponregister);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callmasuk = new Intent(register.this, MainActivity.class);
                startActivity(callmasuk);
            }
        });
        buttonreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailValue = email.getText().toString().trim();
                String passwordValue = password.getText().toString().trim();
                final String namaValue = nama.getText().toString();
                final String teleponValue = telepon.getText().toString();

                if (TextUtils.isEmpty(emailValue)) {
                    email.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(passwordValue)) {
                    password.setError("Password is required");
                    return;
                }

                if (passwordValue.length() < 6) {
                    password.setError("Password must contain 6 character or more");
                }

                //register ke firebase

                firebaseAuth.createUserWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener
                        (new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    //Sent verification link untuk mengetahui emailnya bukan asal2 an
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(register.this, "Verification Email Has Been Sent",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure : Verification not Sent" + e.getMessage());
                                        }
                                    });

                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(register.this, "User Created",
                                            Toast.LENGTH_SHORT).show();

                                    //store data user to firestore
                                    userID = firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = db.collection("users").document(userID);

                                    Map<String, Object> user = new HashMap<>();
                                    user.put("fName", namaValue);
                                    user.put("Email", emailValue);
                                    user.put("Phone", teleponValue);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess : User profile is created for" + userID);
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(register.this, "Register failed."
                                            + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}