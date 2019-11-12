package com.example.flexiblefragment.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.flexiblefragment.MainActivity;
import com.example.flexiblefragment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Fragment Pertama");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("(fragment_first.xml)");
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

        Button btnSecond = view.findViewById(R.id.first_btn);
        btnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondFragment fragmentKedua = new SecondFragment();
                FragmentManager mFragmentManager = getFragmentManager();

                if (mFragmentManager != null){
                    // Fungsi untuk berpindah antar fragment
                    mFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_content, fragmentKedua, SecondFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

    }
}
