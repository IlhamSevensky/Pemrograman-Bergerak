package com.example.flexiblefragment.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.flexiblefragment.MainActivity;
import com.example.flexiblefragment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {


    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnThird = view.findViewById(R.id.second_btn);
        btnThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThirdFragment fragmentKetiga = new ThirdFragment();
                FragmentManager mFragmentManager = getFragmentManager();

                if (mFragmentManager != null){
                    // Fungsi untuk berpindah antar fragment
                    mFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_content, fragmentKetiga, ThirdFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Fragment Kedua");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("(fragment_second.xml)");
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }
}
