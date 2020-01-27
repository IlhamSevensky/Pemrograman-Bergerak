package com.ilham.tubes.mybiodata;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ilham.tubes.mybiodata.adapter.ListBiodataAdapter;
import com.ilham.tubes.mybiodata.helper.DbHelper;
import com.ilham.tubes.mybiodata.model.BiodataModel;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList<BiodataModel> list = new ArrayList<>();
    private FloatingActionButton mainFab;
    private RecyclerView rvBiodata;
    private Toolbar main_toolbar;
    DbHelper SQLite = new DbHelper(this);
    static BottomSheetDialog detailDialog;

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

        setContentView(R.layout.activity_main);
        detailDialog = new BottomSheetDialog(this, R.style.BottomSheedDialogTheme);
        init(); // Function Initialize
        setSupportActionBar(main_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        getAllData();

        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class)
                        .putExtra(AddEditActivity.EXTRA_TITLE, getResources().getString(R.string.title_add));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        list.clear();
        getAllData();
        Log.d("STATUS ACTIVITY : ", "RESUME");
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain); // Back to Home
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_language) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.action_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        main_toolbar = findViewById(R.id.main_toolbar);
        mainFab = findViewById(R.id.main_fab);
        rvBiodata = findViewById(R.id.rv_biodata);
        rvBiodata.setHasFixedSize(true);
    }

    private void getAllData() {
        // set adapter
        rvBiodata.setLayoutManager(new LinearLayoutManager(this));
        ListBiodataAdapter listBiodataAdapter = new ListBiodataAdapter(list);
        rvBiodata.setAdapter(listBiodataAdapter);

        ArrayList<HashMap<String, Object>> row = SQLite.getAllData();

        for (int i = 0; i < row.size(); i++) {
            Integer id = (Integer) row.get(i).get(DbHelper.COLUMN_ID);
            String name = (String) row.get(i).get(DbHelper.COLUMN_NAME);
            Integer age = (Integer) row.get(i).get(DbHelper.COLUMN_AGE);
            String gender = (String) row.get(i).get(DbHelper.COLUMN_GENDER);
            String birthdate = (String) row.get(i).get(DbHelper.COLUMN_BIRTHDATE);
            String address = (String) row.get(i).get(DbHelper.COLUMN_ADDRESS);
            String major = (String) row.get(i).get(DbHelper.COLUMN_MAJOR);
            String study_program = (String) row.get(i).get(DbHelper.COLUMN_STUDY_PROGRAM);

            BiodataModel data = new BiodataModel();

            data.setId(id);
            data.setName(name);
            data.setAge(age);
            data.setGender(gender);
            data.setBirthdate(birthdate);
            data.setAddress(address);
            data.setMajor(major);
            data.setStudy_program(study_program);

            list.add(data);
        }

        listBiodataAdapter.notifyDataSetChanged();

        listBiodataAdapter.setOnItemClickCallback(new ListBiodataAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(BiodataModel data) {
                showBottomSheetDialogDetail(MainActivity.this, detailDialog, data);
            }
        });
    }


    private void showBottomSheetDialogDetail(Context context, final BottomSheetDialog detailDialog, final BiodataModel model) {
        detailDialog.setContentView(R.layout.bottom_sheet_dialog);
        detailDialog.setCanceledOnTouchOutside(true);
        // for full height bottomsheetdialog
        detailDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog dialog = detailDialog;
                FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

                BottomSheetBehavior.from(bottomSheet)
                        .setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        detailDialog.show();

        TextView tvDetailID, tvDetailName, tvDetailAge, tvDetailGender, tvDetailAddress, tvDetailMajor, tvDetailStudyProgram, tvDetailBirthdate;
        ImageView imgDetailPhoto;

        final Button btnEdit, btnDelete;

        tvDetailID = detailDialog.findViewById(R.id.tv_detail_id);
        tvDetailName = detailDialog.findViewById(R.id.tv_detail_name);
        tvDetailAge = detailDialog.findViewById(R.id.tv_detail_age);
        tvDetailAddress = detailDialog.findViewById(R.id.tv_detail_address);
        tvDetailGender = detailDialog.findViewById(R.id.tv_detail_gender);
        tvDetailBirthdate = detailDialog.findViewById(R.id.tv_detail_birthdate);
        tvDetailMajor = detailDialog.findViewById(R.id.tv_detail_major);
        tvDetailStudyProgram = detailDialog.findViewById(R.id.tv_detail_study_program);
        imgDetailPhoto = detailDialog.findViewById(R.id.img_detail_photo);
        btnEdit = detailDialog.findViewById(R.id.btn_detail_edit);
        btnDelete = detailDialog.findViewById(R.id.btn_detail_delete);
        tvDetailID.setText(String.valueOf(model.getId()));
        tvDetailName.setText(model.getName());
        tvDetailAge.setText(String.valueOf(model.getAge()));
        tvDetailGender.setText(model.getGender());
        tvDetailBirthdate.setText(model.getBirthdate());
        tvDetailAddress.setText(model.getAddress());
        tvDetailMajor.setText(model.getMajor());
        tvDetailStudyProgram.setText(model.getStudy_program());

        if (model.getGender().equals(context.getResources().getString(R.string.gender_male))) {
            imgDetailPhoto.setImageResource(R.drawable.icon_male);
        } else if (model.getGender().equals(context.getResources().getString(R.string.gender_female))) {
            imgDetailPhoto.setImageResource(R.drawable.icon_female);
        } else {
            imgDetailPhoto.setImageResource(R.drawable.ic_unavailable);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialogDelete(view.getContext(), model.getId());
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BiodataModel model_intent = new BiodataModel();
                model_intent.setId(model.getId());
                model_intent.setName(model.getName());
                model_intent.setAge(model.getAge());
                model_intent.setGender(model.getGender());
                model_intent.setBirthdate(model.getBirthdate());
                model_intent.setAddress(model.getAddress());
                model_intent.setMajor(model.getMajor());
                model_intent.setStudy_program(model.getStudy_program());

                Intent intent = new Intent(view.getContext(), AddEditActivity.class)
                        .putExtra(AddEditActivity.EXTRA_TITLE, getResources().getString(R.string.title_edit))
                        .putExtra(AddEditActivity.EXTRA_DATA, model_intent);
                view.getContext().startActivity(intent);
            }
        });

    }

    private void showCustomDialogDelete(Context context, final int id) {
        final Dialog dialog = new Dialog(context);

        // membuat background dialog transparent agar shape rounded corner terlihat
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Mengeset layout
        dialog.setContentView(R.layout.custom_confirm_dialog);

        // Membuat agar dialog tidak hilang saat di click di area luar dialog
        dialog.setCanceledOnTouchOutside(false);

        Button btnConfirmDelete = dialog.findViewById(R.id.btn_dialog_delete);
        Button btnCancelDelete = dialog.findViewById(R.id.btn_dialog_cancel);

        btnConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper SQLite = new DbHelper(v.getContext());
                SQLite.delete(id);
                Intent intent = new Intent(v.getContext(), SuccessActivity.class)
                        .putExtra(SuccessActivity.EXTRA_STATUS, getResources().getString(R.string.status_delete));
                v.getContext().startActivity(intent);
            }
        });

        btnCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Menampilkan custom dialog
        dialog.show();
    }
}
