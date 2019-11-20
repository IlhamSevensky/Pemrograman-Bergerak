package com.example.mylocalization.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mylocalization.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    TextView lbName, lbAddress, lbHobby, lbQuote;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false);


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, null);
        lbName = root.findViewById(R.id.label_name);
        lbAddress = root.findViewById(R.id.label_address);
        lbHobby = root.findViewById(R.id.label_hobby);
        lbQuote = root.findViewById(R.id.label_quote);

        lbName.setText(getResources().getString(R.string.label_name));
        lbAddress.setText(getResources().getString(R.string.label_address));
        lbHobby.setText(getResources().getString(R.string.label_hobby));
        lbQuote.setText(getResources().getString(R.string.label_quote));

        return root;
    }

}
