package com.example.myschedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class lupaPassword extends AppCompatActivity {

    ImageView lpnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        lpnext = findViewById(R.id.lpnext);

        lpnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callVerifikasi = new Intent(lupaPassword.this, verifikasi.class);
                startActivity(callVerifikasi);
            }
        });
    }
}
