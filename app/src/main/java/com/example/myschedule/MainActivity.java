package com.example.myschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button daftar, login;
    TextView lupapass;
    EditText emailLogin, passLogin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.buttonMasuk);
        daftar = findViewById(R.id.button_Daftar);
        lupapass = findViewById(R.id.lupapass);
        emailLogin = findViewById(R.id.email);
        passLogin = findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        progressBar.setVisibility(View.GONE);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),homeActivity.class));
            finish();
        }

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View n) {

                startActivity(new Intent(MainActivity.this,register.class));

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View n) {

                String emailValue = emailLogin.getText().toString().trim();
                String passwordValue = passLogin.getText().toString().trim();

                if (TextUtils.isEmpty(emailValue)){
                    emailLogin.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(passwordValue)){
                    passLogin.setError("Password is required");
                    return;
                }

                if (passwordValue.length() < 6){
                    passLogin.setError("Password must contain 6 character or more");
                }

                //authentication user
                fAuth.signInWithEmailAndPassword(emailValue,passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.VISIBLE);

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Login Success",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),homeActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Login failed."
                                    + task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        lupapass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callLupaPassword = new Intent(MainActivity.this, lupaPassword.class);
                startActivity(callLupaPassword);
            }
        });
    }
}
