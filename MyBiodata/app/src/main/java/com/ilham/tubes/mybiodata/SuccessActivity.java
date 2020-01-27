package com.ilham.tubes.mybiodata;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {
    public static final String EXTRA_STATUS = "extra_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make the main layout fill the screen
        Window window = getWindow();
        final int layoutFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        window.getDecorView().setSystemUiVisibility(layoutFlags);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        // make the status bar translucent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0x00000000);
        }

        setContentView(R.layout.activity_success);

        TextView tvMessageSuccess = findViewById(R.id.tv_message_success);
        Button btnOK = findViewById(R.id.btn_ok_success_delete);

        if (getIntent().getStringExtra(EXTRA_STATUS) != null) {
            if (getIntent().getStringExtra(EXTRA_STATUS).equals(getResources().getString(R.string.status_edit))) {
                tvMessageSuccess.setText(getResources().getString(R.string.message_success_update));

            } else if (getIntent().getStringExtra(EXTRA_STATUS).equals(getResources().getString(R.string.status_delete))) {
                tvMessageSuccess.setText(getResources().getString(R.string.message_success_delete));
            } else if (getIntent().getStringExtra(EXTRA_STATUS).equals(getResources().getString(R.string.status_add))) {
                tvMessageSuccess.setText(getResources().getString(R.string.message_success_add));
            } else {
                tvMessageSuccess.setText(getResources().getString(R.string.status_error));
            }
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
