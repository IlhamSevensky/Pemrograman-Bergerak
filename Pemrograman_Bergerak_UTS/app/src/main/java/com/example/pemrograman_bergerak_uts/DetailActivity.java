package com.example.pemrograman_bergerak_uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailActivity extends AppCompatActivity {

    Toolbar toolbar; // androidx toolbar
    ImageView imgDetail;
    TextView tvTitle, tvDescriptions;
    String title, descriptions, images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        images = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("name");
        descriptions = getIntent().getStringExtra("detail");



        imgDetail = findViewById(R.id.img_detail);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvDescriptions = findViewById(R.id.tv_detail_descriptions);
        toolbar = findViewById(R.id.toolbar_menu_detail);
        setSupportActionBar(toolbar);
        setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        tvTitle.setText(title);
        tvDescriptions.setText(descriptions);
        Glide.with(DetailActivity.this)
                .load(images)
                .apply(new RequestOptions().override(500,500))
                .into(imgDetail);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
