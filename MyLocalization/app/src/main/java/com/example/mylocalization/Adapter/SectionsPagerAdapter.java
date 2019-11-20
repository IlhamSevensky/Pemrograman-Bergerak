package com.example.mylocalization.Adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mylocalization.Fragment.HomeFragment;
import com.example.mylocalization.Fragment.ProfileFragment;
import com.example.mylocalization.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;

    // must have constructor
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // Resource String on TabLayout
    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text1,
            R.string.tab_text2
    };

    // Set Fragment Position on TabLayout
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new ProfileFragment();
                break;
        }

        return fragment;
    }

    // Set Tab Title Based on Fragment Position
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);

        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    // How much Tab Exist
    @Override
    public int getCount() {
        return 2;
    }
}
