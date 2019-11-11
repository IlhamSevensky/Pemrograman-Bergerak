package com.example.polinesukm;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailPage extends AppCompatActivity {

    ImageView imgDetail;
    TextView tvTitle, tvDescriptions;
    String title, descriptions, images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        imgDetail = findViewById(R.id.img_detail);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvDescriptions = findViewById(R.id.tv_detail_descriptions);

        images = getIntent().getStringExtra("img");
        title = getIntent().getStringExtra("title");
        descriptions = getIntent().getStringExtra("descriptions");

        tvTitle.setText(title);
        tvDescriptions.setText(descriptions);
        Glide.with(DetailPage.this)
                .load(images)
                .apply(new RequestOptions().override(250,250))
                .into(imgDetail);
    }
}
