package com.example.myintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {
    Button btnSendData;
    EditText edtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        btnSendData = findViewById(R.id.btn_send_data);
        edtData = findViewById(R.id.edt_nama);

        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = edtData.getText().toString();
                startActivity(new Intent(InputActivity.this, DisplayActivity.class).putExtra("hasil", nama));
            }
        });
    }
}
