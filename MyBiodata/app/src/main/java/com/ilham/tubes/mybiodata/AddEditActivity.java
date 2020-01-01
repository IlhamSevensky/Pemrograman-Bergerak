package com.ilham.tubes.mybiodata;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.ilham.tubes.mybiodata.helper.DbHelper;
import com.ilham.tubes.mybiodata.model.BiodataModel;

public class AddEditActivity extends AppCompatActivity {

    TextView tvId, tvMainTitle, tvErrorGender;
    EditText edtName, edtAge, edtAddress;
    AppCompatSpinner spinnerMajor, spinnerStudyProgram;
    Button btnBack, btnAddEdit;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale;
    ScrollView scrollView;
    final DbHelper SQLite = new DbHelper(this);
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_TITLE = "extra_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        init();

        // Set Spinner Resource
        spinnerMajor.setAdapter(new ArrayAdapter<>(AddEditActivity.this,
                R.layout.spinner_dialog_item,
                getResources().getStringArray(R.array.list_major)));

        // Activity Edit Here
        if (getIntent().getStringExtra(EXTRA_TITLE).equals(getResources().getString(R.string.title_edit))) {
            BiodataModel model_intent = getIntent().getParcelableExtra(EXTRA_DATA);
            tvMainTitle.setText(getResources().getString(R.string.title_edit));
            tvId.setText(String.valueOf(model_intent.getId()));
            edtName.setText(model_intent.getName());
            edtAge.setText(String.valueOf(model_intent.getAge()));
            edtAddress.setText(model_intent.getAddress());
            btnAddEdit.setText(getResources().getString(R.string.btn_text_update));

            if (model_intent.getGender().equals(getResources().getString(R.string.gender_male))) {
                rbMale.setChecked(true);
            } else if (model_intent.getGender().equals(getResources().getString(R.string.gender_female))) {
                rbFemale.setChecked(true);
            } else {
                // gender not valid
            }

            setSpinnerItemByData(model_intent);

            spinnerMajor.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if (view.performClick()) {
                        spinnerMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                                String selectedItem = spinnerMajor.getItemAtPosition(position).toString();

                                checkAndSetSpinnerList(selectedItem);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    return false;
                }
            });

            // Activity Add Here
        } else if (getIntent().getStringExtra(EXTRA_TITLE).equals(getResources().getString(R.string.title_add))) {
            tvMainTitle.setText(getResources().getString(R.string.title_add));
            btnAddEdit.setText(getResources().getString(R.string.btn_text_add));

            spinnerMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                    String selectedItem = spinnerMajor.getItemAtPosition(position).toString();

                    checkAndSetSpinnerList(selectedItem);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } else {
            // Unknown activity
        }


        btnAddEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnAddEdit.getText().toString().equals(getResources().getString(R.string.btn_text_add))) {
                    if (validate()) {
                        SQLite.insert(edtName.getText().toString(),
                                Integer.parseInt(edtAge.getText().toString()),
                                getGender(),
                                edtAddress.getText().toString(),
                                spinnerMajor.getSelectedItem().toString(),
                                spinnerStudyProgram.getSelectedItem().toString());

                        Intent intent = new Intent(AddEditActivity.this, SuccessActivity.class)
                                .putExtra(SuccessActivity.EXTRA_STATUS, getResources().getString(R.string.status_add));
                        startActivity(intent);
                    }
                } else if (btnAddEdit.getText().toString().equals(getResources().getString(R.string.btn_text_update))) {
                    if (validate()) {
                        SQLite.update(Integer.parseInt(tvId.getText().toString()),
                                edtName.getText().toString(),
                                Integer.parseInt(edtAge.getText().toString()),
                                getGender(),
                                edtAddress.getText().toString(),
                                spinnerMajor.getSelectedItem().toString(),
                                spinnerStudyProgram.getSelectedItem().toString());

                        Intent intent = new Intent(AddEditActivity.this, SuccessActivity.class)
                                .putExtra(SuccessActivity.EXTRA_STATUS, getResources().getString(R.string.status_edit));
                        startActivity(intent);
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                tvErrorGender.setVisibility(View.GONE);
            }
        });

    }

    private void init() {
        scrollView = findViewById(R.id.scrollView1);
        tvMainTitle = findViewById(R.id.tv_add_edit_title);
        tvErrorGender = findViewById(R.id.tv_error_gender);
        tvId = findViewById(R.id.tv_detail_id);
        edtName = findViewById(R.id.edt_name);
        edtAge = findViewById(R.id.edt_age);
        edtAddress = findViewById(R.id.edt_address);
        spinnerMajor = findViewById(R.id.spinner_major);
        spinnerStudyProgram = findViewById(R.id.spinner_study_program);
        btnBack = findViewById(R.id.btn_back);
        btnAddEdit = findViewById(R.id.btn_add_edit);
        rgGender = findViewById(R.id.rg_gender);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
    }

    private String getGender() {
        String gender;

        int selectedGender = rgGender.getCheckedRadioButtonId();
        if (selectedGender == rbMale.getId()) {
            gender = getResources().getString(R.string.gender_male);
        } else if (selectedGender == rbFemale.getId()) {
            gender = getResources().getString(R.string.gender_female);
        } else {
            gender = "unknown";
        }

        return gender;
    }

    private void checkAndSetSpinnerList(String selectedItem) {

        if (selectedItem.equals(getResources().getStringArray(R.array.list_major)[0])) { // Mesin
            spinnerStudyProgram.setAdapter(new ArrayAdapter<>(AddEditActivity.this,
                    R.layout.spinner_dialog_item,
                    getResources().getStringArray(R.array.list_study_program_mesin)));

        } else if (selectedItem.equals(getResources().getStringArray(R.array.list_major)[1])) { // Elektro
            spinnerStudyProgram.setAdapter(new ArrayAdapter<>(AddEditActivity.this,
                    R.layout.spinner_dialog_item,
                    getResources().getStringArray(R.array.list_study_program_elektro)));
        } else if (selectedItem.equals(getResources().getStringArray(R.array.list_major)[2])) { // Sipil
            spinnerStudyProgram.setAdapter(new ArrayAdapter<>(AddEditActivity.this,
                    R.layout.spinner_dialog_item,
                    getResources().getStringArray(R.array.list_study_program_sipil)));
        } else if (selectedItem.equals(getResources().getStringArray(R.array.list_major)[3])) { // AB
            spinnerStudyProgram.setAdapter(new ArrayAdapter<>(AddEditActivity.this,
                    R.layout.spinner_dialog_item,
                    getResources().getStringArray(R.array.list_study_program_ab)));
        } else if (selectedItem.equals(getResources().getStringArray(R.array.list_major)[4])) { // AK
            spinnerStudyProgram.setAdapter(new ArrayAdapter<>(AddEditActivity.this,
                    R.layout.spinner_dialog_item,
                    getResources().getStringArray(R.array.list_study_program_ak)));
        } else {
            // spinner hide
        }
    }

    private void setSpinnerItemByData(BiodataModel model_intent) {
        int position = checkSpinnerPosition(spinnerMajor, model_intent.getMajor());

        spinnerMajor.setSelection(position);

        int tes = 1;

        if (position == 0) { // Mesin
            spinnerStudyProgram.setAdapter(new ArrayAdapter<>(AddEditActivity.this,
                    R.layout.spinner_dialog_item,
                    getResources().getStringArray(R.array.list_study_program_mesin)));
            tes = checkSpinnerPosition(spinnerStudyProgram, model_intent.getStudy_program());

        } else if (position == 1) { // Elektro
            spinnerStudyProgram.setAdapter(new ArrayAdapter<>(AddEditActivity.this,
                    R.layout.spinner_dialog_item,
                    getResources().getStringArray(R.array.list_study_program_elektro)));
            tes = checkSpinnerPosition(spinnerStudyProgram, model_intent.getStudy_program());


        } else if (position == 2) { // Sipil
            spinnerStudyProgram.setAdapter(new ArrayAdapter<>(AddEditActivity.this,
                    R.layout.spinner_dialog_item,
                    getResources().getStringArray(R.array.list_study_program_sipil)));
            tes = checkSpinnerPosition(spinnerStudyProgram, model_intent.getStudy_program());

        } else if (position == 3) { // AB
            spinnerStudyProgram.setAdapter(new ArrayAdapter<>(AddEditActivity.this,
                    R.layout.spinner_dialog_item,
                    getResources().getStringArray(R.array.list_study_program_ab)));
            tes = checkSpinnerPosition(spinnerStudyProgram, model_intent.getStudy_program());

        } else if (position == 4) { // AK
            spinnerStudyProgram.setAdapter(new ArrayAdapter<>(AddEditActivity.this,
                    R.layout.spinner_dialog_item,
                    getResources().getStringArray(R.array.list_study_program_ak)));
            tes = checkSpinnerPosition(spinnerStudyProgram, model_intent.getStudy_program());

        } else {
            // spinner hide
        }

        spinnerStudyProgram.setSelection(tes);
    }

    private int checkSpinnerPosition(Spinner spinner, String text) {
        for (int pos = 0; pos < spinner.getCount(); pos++) {
            if (spinner.getItemAtPosition(pos).toString().equals(text)) {
                return pos;
            }
        }
        return 0;
    }

    private boolean validate() {
        String name = edtName.getText().toString();
        String age = edtAge.getText().toString();
        String address = edtAddress.getText().toString();
        boolean error;

        if (TextUtils.isEmpty(name)) {
            edtName.setError(getResources().getString(R.string.text_field_error));
            error = false;
            tvErrorGender.setVisibility(View.GONE);
            edtName.requestFocus();
        } else if (TextUtils.isEmpty(age)) {
            edtAge.setError(getResources().getString(R.string.text_field_error));
            error = false;
            tvErrorGender.setVisibility(View.GONE);
            edtAge.requestFocus();
        } else if (TextUtils.isEmpty(address)) {
            edtAddress.setError(getResources().getString(R.string.text_field_error));
            error = false;
            tvErrorGender.setVisibility(View.GONE);
            edtAddress.requestFocus();
        } else if (spinnerMajor.getSelectedItem().toString().isEmpty()) {
            TextView errorMajor = (TextView) spinnerMajor.getSelectedView();
            errorMajor.setError(getResources().getString(R.string.text_field_error));
            error = false;
            tvErrorGender.setVisibility(View.GONE);
            spinnerMajor.requestFocus();
        } else if (spinnerStudyProgram.getSelectedItem().toString().isEmpty()) {
            TextView errorStudy = (TextView) spinnerStudyProgram.getSelectedView();
            errorStudy.setError(getResources().getString(R.string.text_field_error));
            error = false;
            tvErrorGender.setVisibility(View.GONE);
            spinnerStudyProgram.requestFocus();
        } else if (rgGender.getCheckedRadioButtonId() == -1) {
            error = false;
            tvErrorGender.setVisibility(View.VISIBLE);
            scrollView.scrollTo(0, rgGender.getTop());
        } else {
            error = true;
        }

        return error;
    }

}
