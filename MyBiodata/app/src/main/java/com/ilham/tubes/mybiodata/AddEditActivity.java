package com.ilham.tubes.mybiodata;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.ilham.tubes.mybiodata.helper.DbHelper;
import com.ilham.tubes.mybiodata.model.BiodataModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditActivity extends AppCompatActivity {

    TextView tvId, tvMainTitle, tvErrorGender, tvErrorBirthdate;
    EditText edtName, edtAge, edtAddress, edtBirthday;
    AppCompatSpinner spinnerMajor, spinnerStudyProgram;
    ImageButton btnDatePicker;
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

        setContentView(R.layout.activity_add_edit);

        init();

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view.getContext());
            }
        });

        // Set Spinner Resource
        spinnerMajor.setAdapter(new ArrayAdapter<>(AddEditActivity.this,
                R.layout.spinner_dialog_item,
                getResources().getStringArray(R.array.list_major)));

        // Activity Edit Here
        if (getIntent().getStringExtra(EXTRA_TITLE) != null) {

            if (getIntent().getStringExtra(EXTRA_TITLE).equals(getResources().getString(R.string.title_edit))) {
                BiodataModel model_intent = getIntent().getParcelableExtra(EXTRA_DATA);
                tvMainTitle.setText(getResources().getString(R.string.title_edit));
                tvId.setText(String.valueOf(model_intent.getId()));
                edtName.setText(model_intent.getName());
                edtAge.setText(String.valueOf(model_intent.getAge()));
                edtAddress.setText(model_intent.getAddress());
                edtBirthday.setText(model_intent.getBirthdate());
                btnAddEdit.setText(getResources().getString(R.string.btn_text_update));

                if (model_intent.getGender().equals(getResources().getString(R.string.gender_male))) {
                    rbMale.setChecked(true);
                } else if (model_intent.getGender().equals(getResources().getString(R.string.gender_female))) {
                    rbFemale.setChecked(true);
                } else {
                    // Gender is not choosed
                    Toast.makeText(AddEditActivity.this, getResources().getString(R.string.text_error_gender_not_valid), Toast.LENGTH_SHORT).show();
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
                // There is no such activity layout
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.text_error_activity), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddEditActivity.this, MainActivity.class));
            }
        }


        btnAddEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnAddEdit.getText().toString().equals(getResources().getString(R.string.btn_text_add))) {
                    if (validate()) {
                        SQLite.insert(edtName.getText().toString().substring(0,1).toUpperCase() + edtName.getText().toString().substring(1),
                                Integer.parseInt(edtAge.getText().toString()),
                                getGender(),
                                edtBirthday.getText().toString(),
                                edtAddress.getText().toString().substring(0,1).toUpperCase() + edtAddress.getText().toString().substring(1),
                                spinnerMajor.getSelectedItem().toString(),
                                spinnerStudyProgram.getSelectedItem().toString());

                        Intent intent = new Intent(AddEditActivity.this, SuccessActivity.class)
                                .putExtra(SuccessActivity.EXTRA_STATUS, getResources().getString(R.string.status_add));
                        startActivity(intent);
                    }
                } else if (btnAddEdit.getText().toString().equals(getResources().getString(R.string.btn_text_update))) {
                    if (validate()) {
                        SQLite.update(Integer.parseInt(tvId.getText().toString()),
                                edtName.getText().toString().substring(0,1).toUpperCase() + edtName.getText().toString().substring(1),
                                Integer.parseInt(edtAge.getText().toString()),
                                getGender(),
                                edtBirthday.getText().toString(),
                                edtAddress.getText().toString().substring(0,1).toUpperCase() + edtAddress.getText().toString().substring(1),
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

    protected void showDatePickerDialog(@NonNull final Context context){
        Calendar calendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update EditText dengan tanggal yang kita pilih
                 */
                if (getAge(year, monthOfYear + 1, dayOfMonth) < 0){
                    showCustomDialogError(context);
                } else {
                    tvErrorBirthdate.setVisibility(View.GONE);
                    edtAge.setText(String.valueOf(getAge(year, monthOfYear + 1, dayOfMonth)));

                    Locale localeID = new Locale("in", "ID");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", localeID);
                    edtBirthday.setText(dateFormatter.format(newDate.getTime()));
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

        datePickerDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_4){
                    Toast.makeText(context, "KODE : " + i , Toast.LENGTH_SHORT).show();
                    return true;
                }else
                    return false;
            }
        });

    }

    protected void showCustomDialogError(@NonNull Context context) {
        final Dialog dialog = new Dialog(context);

        // membuat background dialog transparent agar shape rounded corner terlihat
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Mengeset layout
        dialog.setContentView(R.layout.custom_error_dialog);

        // Membuat agar dialog tidak hilang saat di click di area luar dialog
        dialog.setCanceledOnTouchOutside(false);

        Button btnOk = dialog.findViewById(R.id.btn_dialog_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Menampilkan custom dialog
        dialog.show();
    }

    public int getAge (int yearBirth, int monthBirth, int dayBirth) {

        Calendar cal = Calendar.getInstance();
        int yearNow, monthNow, dayNow, age;

        yearNow = cal.get(Calendar.YEAR);
        monthNow = cal.get(Calendar.MONTH) + 1;
        dayNow = cal.get(Calendar.DAY_OF_MONTH);

        age = yearNow - yearBirth;
        if (monthBirth > monthNow){
            --age;
        }
        else if (monthBirth == monthNow){
            if (dayBirth > dayNow){
                --age;
            }
        }

        return age;
    }

    private void init() {
        scrollView = findViewById(R.id.scrollView1);
        tvMainTitle = findViewById(R.id.tv_add_edit_title);
        tvErrorGender = findViewById(R.id.tv_error_gender);
        tvErrorBirthdate = findViewById(R.id.tv_error_birtdate);
        tvId = findViewById(R.id.tv_detail_id);
        edtName = findViewById(R.id.edt_name);
        edtAge = findViewById(R.id.edt_age);
        edtAddress = findViewById(R.id.edt_address);
        edtBirthday = findViewById(R.id.edt_birthday);
        spinnerMajor = findViewById(R.id.spinner_major);
        spinnerStudyProgram = findViewById(R.id.spinner_study_program);
        btnBack = findViewById(R.id.btn_back);
        btnAddEdit = findViewById(R.id.btn_add_edit);
        btnDatePicker = findViewById(R.id.btn_date_picker);
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

    protected void checkAndSetSpinnerList(@NonNull String selectedItem) {

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
            spinnerStudyProgram.setVisibility(View.GONE);
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
            spinnerStudyProgram.setVisibility(View.GONE);
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

    protected boolean validate() {
        String name = edtName.getText().toString();
        String age = edtAge.getText().toString();
        String address = edtAddress.getText().toString();
        String birthdate = edtBirthday.getText().toString();
        boolean error;

        if (TextUtils.isEmpty(name)) {
            edtName.setError(getResources().getString(R.string.text_field_error));
            error = false;
            tvErrorGender.setVisibility(View.GONE);
            tvErrorBirthdate.setVisibility(View.GONE);
            edtName.requestFocus();
        } else if (TextUtils.isEmpty(birthdate) || TextUtils.isEmpty(age)){
            tvErrorBirthdate.setText(getResources().getString(R.string.text_error_birthdate));
            tvErrorBirthdate.setVisibility(View.VISIBLE);
            error = false;
            tvErrorGender.setVisibility(View.GONE);
            scrollView.smoothScrollTo(0, edtBirthday.getTop());
        } else if (TextUtils.isEmpty(address)) {
            edtAddress.setError(getResources().getString(R.string.text_field_error));
            error = false;
            tvErrorGender.setVisibility(View.GONE);
            tvErrorBirthdate.setVisibility(View.GONE);
            edtAddress.requestFocus();
        } else if (spinnerMajor.getSelectedItem().toString().isEmpty()) {
            TextView errorMajor = (TextView) spinnerMajor.getSelectedView();
            errorMajor.setError(getResources().getString(R.string.text_field_error));
            error = false;
            tvErrorGender.setVisibility(View.GONE);
            tvErrorBirthdate.setVisibility(View.GONE);
            spinnerMajor.requestFocus();
        } else if (spinnerStudyProgram.getSelectedItem().toString().isEmpty()) {
            TextView errorStudy = (TextView) spinnerStudyProgram.getSelectedView();
            errorStudy.setError(getResources().getString(R.string.text_field_error));
            error = false;
            tvErrorGender.setVisibility(View.GONE);
            tvErrorBirthdate.setVisibility(View.GONE);
            spinnerStudyProgram.requestFocus();
        } else if (rgGender.getCheckedRadioButtonId() == -1) {
            error = false;
            tvErrorGender.setVisibility(View.VISIBLE);
            tvErrorBirthdate.setVisibility(View.GONE);
            scrollView.smoothScrollTo(0, rgGender.getTop());
        } else {
            error = true;
        }

        return error;
    }

}
