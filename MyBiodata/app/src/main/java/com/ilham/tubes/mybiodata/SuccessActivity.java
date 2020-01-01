package com.ilham.tubes.mybiodata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {
    public static final String EXTRA_STATUS = "extra_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        TextView tvMessageSuccess = findViewById(R.id.tv_message_success);
        Button btnOK = findViewById(R.id.btn_ok_success_delete);

        if (getIntent().getStringExtra(EXTRA_STATUS).equals(getResources().getString(R.string.status_edit))) {
            tvMessageSuccess.setText(getResources().getString(R.string.message_success_update));

        } else if (getIntent().getStringExtra(EXTRA_STATUS).equals(getResources().getString(R.string.status_delete))) {
            tvMessageSuccess.setText(getResources().getString(R.string.message_success_delete));
        } else if (getIntent().getStringExtra(EXTRA_STATUS).equals(getResources().getString(R.string.status_add))) {
            tvMessageSuccess.setText(getResources().getString(R.string.message_success_add));
        } else {
            tvMessageSuccess.setText(getResources().getString(R.string.status_error));
        }

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Leave empty, so user can't press back button
    }
}
