package com.example.myintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ;
    Button btnMove, btnMoveData, btnDial, btnWebPolines, btnInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMove = findViewById(R.id.btn_move_activity);
        btnMoveData = findViewById(R.id.btn_move_with_data_activity);
        btnDial = findViewById(R.id.btn_dial);
        btnWebPolines = findViewById(R.id.btn_web_polines);
        btnInput = findViewById(R.id.btn_input_and_move_data);
        btnMove.setOnClickListener(this);
        btnMoveData.setOnClickListener(this);
        btnDial.setOnClickListener(this);
        btnWebPolines.setOnClickListener(this);
        btnInput.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_move_activity:
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_move_with_data_activity:
                Intent DataIntent = new Intent(MainActivity.this, MoveActivityWithData.class);
                DataIntent.putExtra(MoveActivityWithData.EXTRA_NAME, "Ilham Budi Prasetyo");
                DataIntent.putExtra(MoveActivityWithData.EXTRA_AGE, "19");
                startActivity(DataIntent);
                break;

            case R.id.btn_dial:
                String phoneNumber = "087700152265";
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(dialIntent);
                break;

            case R.id.btn_web_polines:
                String url = "http://polines.ac.id";
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse(url));
                startActivity(webIntent);
                break;

            case R.id.btn_input_and_move_data:
                startActivity(new Intent(MainActivity.this, InputActivity.class));
                break;
        }

    }
}
