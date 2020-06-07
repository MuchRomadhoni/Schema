package com.example.myschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button daftar;
    Button login;
    TextView lupapass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.buttonMasuk);
        daftar = findViewById(R.id.button_Daftar);
        lupapass = findViewById(R.id.lupapass);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View n) {

                Intent callDaftar = new Intent(MainActivity.this, register.class);
                startActivity(callDaftar);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View n) {

                Intent callLogin = new Intent(MainActivity.this, homeActivity.class);
                startActivity(callLogin);

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
