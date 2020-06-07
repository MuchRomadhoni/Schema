package com.example.myschedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class verifikasi extends AppCompatActivity {

    ImageView vnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi);

        vnext = findViewById(R.id.verifikasinext);

        vnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callMain = new Intent(verifikasi.this, MainActivity.class);
                startActivity(callMain);
            }
        });
    }
}
