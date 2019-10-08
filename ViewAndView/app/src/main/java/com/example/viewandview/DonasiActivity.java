package com.example.viewandview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class DonasiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DonasiTheme);

        setContentView(R.layout.activity_donasi);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Donasi");
        }
    }
}
