package com.example.pemrograman_bergerak_uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.pemrograman_bergerak_uts.Adapter.ListDestinasiAdapter;
import com.example.pemrograman_bergerak_uts.Data.DataDestinasi;
import com.example.pemrograman_bergerak_uts.Model.Model_Destinasi;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvDestinasi;
    private Toolbar toolbar; //androidx toolbar
    private ArrayList<Model_Destinasi> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        setTitle("");

        rvDestinasi = findViewById(R.id.rv_destinasi);
        rvDestinasi.setHasFixedSize(true);

        list.addAll(DataDestinasi.getListData());
        showRecyclerList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    private void showRecyclerList() {
        rvDestinasi.setLayoutManager(new LinearLayoutManager(this));
        ListDestinasiAdapter listDestinasiAdapter = new ListDestinasiAdapter(list);
        rvDestinasi.setAdapter(listDestinasiAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profile_detail:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
