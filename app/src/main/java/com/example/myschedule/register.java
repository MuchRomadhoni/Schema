package com.example.myschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.TextView;

public class register extends AppCompatActivity {

    TextView masuk;
    Button buttonreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        masuk = findViewById(R.id.inimasuk);
        buttonreg = findViewById(R.id.buttonreg);

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
                Intent callbuttonreg = new Intent(register.this, MainActivity.class);
                startActivity(callbuttonreg);
            }
        });
    }
}