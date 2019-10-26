package com.example.polinesukm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.polinesukm.Adapter.listAdapterUKM;
import com.example.polinesukm.Data.dataUKM;
import com.example.polinesukm.Model.ukm_polines;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvUKM;
    private ArrayList<ukm_polines> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvUKM = findViewById(R.id.rv_UKM);
        rvUKM.setHasFixedSize(true);

        list.addAll(dataUKM.getListData());
        showRecyclerList();
    }

    private void showRecyclerList() {
        rvUKM.setLayoutManager(new LinearLayoutManager(this));
        listAdapterUKM listAdapterUKM = new listAdapterUKM(list);
        rvUKM.setAdapter(listAdapterUKM);
    }
}
