package com.ilham.tubes.mybiodata.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "biodata.db";
    private static final String TABLE_NAME = "tb_biodata";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_BIRTHDATE = "birthdate";
    public static final String COLUMN_MAJOR = "major";
    public static final String COLUMN_STUDY_PROGRAM = "study_program";
    public static final String COLUMN_GENDER = "gender";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_AGE + " INTEGER NOT NULL, " +
                COLUMN_GENDER + " TEXT NOT NULL, " +
                COLUMN_BIRTHDATE + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL, " +
                COLUMN_MAJOR + " TEXT NOT NULL, " +
                COLUMN_STUDY_PROGRAM + " TEXT NOT NULL " +
                " )";

        db.execSQL(SQL_CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method untuk mengambil semua data pada tabel
    public ArrayList<HashMap<String, Object>> getAllData() {
        ArrayList<HashMap<String, Object>> wordList;
        wordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        Log.e("Number of rows", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, Object> map = new HashMap<>();
                map.put(COLUMN_ID, cursor.getInt(0));
                map.put(COLUMN_NAME, cursor.getString(1));
                map.put(COLUMN_AGE, cursor.getInt(2));
                map.put(COLUMN_GENDER, cursor.getString(3));
                map.put(COLUMN_BIRTHDATE, cursor.getString(4));
                map.put(COLUMN_ADDRESS, cursor.getString(5));
                map.put(COLUMN_MAJOR, cursor.getString(6));
                map.put(COLUMN_STUDY_PROGRAM, cursor.getString(7));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        Log.e("SELECT TABLE DATA", "" + wordList);

        cursor.close();
        database.close();
        return wordList;
    }

    public void insert(String name, int age, String gender, String birthdate, String address, String major, String study_program) {
        SQLiteDatabase database = this.getWritableDatabase();
        String insertQuery = "INSERT INTO " + TABLE_NAME + " (name, age, gender, birthdate, address, major, study_program)" + " VALUES ('" + name + "'," + age + ",'" + gender + "','" + birthdate + "' ,'" + address + "','" + major + "','" + study_program + "')";

        Log.e("INSERT TABLE DATA", "" + insertQuery);
        database.execSQL(insertQuery);
        database.close();
    }

    public void update(int id, String name, int age, String gender, String birthdate, String address, String major, String study_program) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_NAME + " SET " +
                COLUMN_NAME + " = '" + name + "', " +
                COLUMN_AGE + " = '" + age + "', " +
                COLUMN_GENDER + " = '" + gender + "', " +
                COLUMN_BIRTHDATE + " = '" + birthdate + "', " +
                COLUMN_ADDRESS + " = '" + address + "', " +
                COLUMN_MAJOR + " = '" + major + "', " +
                COLUMN_STUDY_PROGRAM + " = '" + study_program + "'" +
                " WHERE " + COLUMN_ID + " = '" + id + "'";

        Log.e("UPDATE TABLE DATA", "" + updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "'";

        Log.e("DELETE TABLE DATA", "" + deleteQuery);
        database.execSQL(deleteQuery);
        database.close();
    }
}
