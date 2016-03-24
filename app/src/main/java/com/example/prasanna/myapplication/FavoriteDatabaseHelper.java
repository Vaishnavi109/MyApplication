package com.example.prasanna.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Prasanna on 3/21/16.
 */
public class FavoriteDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "runs.sqlite";
    private static final int VERSION = 1;

    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_FAVORITES_RESTAURANT_ID= "restaurant_id";

    public FavoriteDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the "run" table
        db.execSQL("create table favorites (" +
                "_id integer primary key autoincrement, restaurant_id varchar(100))");
        // Create the "location" table
//        db.execSQL("create table location (" +
//                " timestamp integer, latitude real, longitude real, altitude real," +
//                " provider varchar(100), run_id integer references run(_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement schema changes and data massage here when upgrading
    }

    public long insertRun(Restaurant restaurant) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FAVORITES_RESTAURANT_ID, restaurant.getRestId());
        return getWritableDatabase().insert(TABLE_FAVORITES, null, cv);
    }
}