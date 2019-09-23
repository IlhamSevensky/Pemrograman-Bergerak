package com.example.myintent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MoveActivityWithData extends AppCompatActivity {
    String Nama, Umur;
    public static final String EXTRA_AGE = "extra age";
    public static final String EXTRA_NAME = "extra name";
    TextView tvDataReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_with_data);

        tvDataReceiver = findViewById(R.id.tv_data_received);

        Nama = getIntent().getStringExtra(EXTRA_NAME);
        Umur = getIntent().getStringExtra(EXTRA_AGE);

        String text = "Nama : " + Nama + "\nUmur : " + Umur;

        tvDataReceiver.setText(text);

    }
}
