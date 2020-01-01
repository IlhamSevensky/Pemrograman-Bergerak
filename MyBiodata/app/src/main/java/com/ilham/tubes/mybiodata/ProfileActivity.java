package com.ilham.tubes.mybiodata;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.ilham.tubes.mybiodata.adapter.ViewPagerAdapter;

public class ProfileActivity extends AppCompatActivity {

    private int[] layouts = {R.layout.layout_profile_ilham, R.layout.layout_profile_fahmi};
    private LinearLayout dotsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ViewPager mPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, layouts);
        Button btnBackProfile = findViewById(R.id.btn_profile_back);
        mPager.setAdapter(viewPagerAdapter);

        dotsLayout = findViewById(R.id.dots_layout);
        createDots(0);

        btnBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createDots(int currentPosition){

        if (dotsLayout != null){
            dotsLayout.removeAllViews();
        }

        ImageView[] dots = new ImageView[layouts.length];

        for (int i = 0 ; i < layouts.length ; i++){
            dots[i] = new ImageView(this);

            if (i == currentPosition) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.page_indicator_active_dots));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.page_indicator_inactive_dots));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,0,10,0);

            dotsLayout.addView(dots[i], params);
        }
    }

}
