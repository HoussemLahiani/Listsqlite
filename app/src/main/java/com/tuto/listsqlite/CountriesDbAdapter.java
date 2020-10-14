package com.tuto.listsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class CountriesDbAdapter extends SQLiteOpenHelper {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_CODE = "code";
    public static final String KEY_NAME = "name";
    public static final String KEY_CONTINENT = "continent";
    public static final String KEY_REGION = "region";
    private static final String TAG = "CountriesDbAdapter";
       private static final String DATABASE_NAME = "World";
    private static final String SQLITE_TABLE = "Country";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_CODE + "," +
                    KEY_NAME + "," +
                    KEY_CONTINENT + "," +
                    KEY_REGION + "," +
                    " UNIQUE (" + KEY_CODE +"));";

    public CountriesDbAdapter(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
        onCreate(db);
    }




    public void createCountry(String code, String name,
                              String continent, String region) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CODE, code);
        cv.put(KEY_NAME, name);
        cv.put(KEY_CONTINENT, continent);
        cv.put(KEY_REGION, region);
         db.insert(SQLITE_TABLE, null, cv);
         db.close();
    }

    public void deleteAllCountries()
    {
        SQLiteDatabase db=this.getWritableDatabase();

        db.delete(SQLITE_TABLE, null , null);

       db.close();

    }

    public Cursor fetchCountriesByName(String inputText) throws SQLException {

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = db.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_CODE, KEY_NAME, KEY_CONTINENT, KEY_REGION},
                    null, null, null, null, null);

        }
        else {
            mCursor = db.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_CODE, KEY_NAME, KEY_CONTINENT, KEY_REGION},
                    KEY_NAME + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllCountries() {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor mCursor = db.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                        KEY_CODE, KEY_NAME, KEY_CONTINENT, KEY_REGION},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeCountries() {

        createCountry("TN","Tunisia","Africa","Maghreb");
        createCountry("ALB","Albania","Europe","Southern Europe");
        createCountry("DZA","Algeria","Africa","Maghreb");
        createCountry("FR","France","Europe","UE");
        createCountry("AND","Andorra","Europe","Southern Europe");
        createCountry("AGO","Angola","Africa","Central Africa");
        createCountry("AIA","Anguilla","North America","Caribbean");

    }


}