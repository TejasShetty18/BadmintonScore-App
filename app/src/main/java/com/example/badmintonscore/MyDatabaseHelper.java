package com.example.badmintonscore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="BadmintonScore.db";
    public Context context;
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_BADMINTON="badminton";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME_1="name1";
    public static final String KEY_NAME_2="name2";
    public static final String KEY_NAME_3="name3";
    public static final String KEY_NAME_4="name4";
    public static final String KEY_SETS_1 = "sets1";
    public static final String KEY_SETS_2 = "sets2";

    public static final String KEY_SETS_3 = "sets3";
    public static final String KEY_SCORE_POINTS_1 = "points1";
    public static final String KEY_SCORE_POINTS_2 = "points2";

    public static final String KEY_SCORE_POINTS_3 = "points3";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MyTag", "Inside DB message.");
        System.out.println("Inside DB");
        String query = ("CREATE TABLE " + TABLE_BADMINTON +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME_1 + " TEXT, " + KEY_NAME_2 + " TEXT, " +
                KEY_NAME_3 + " TEXT, " + KEY_NAME_4 + " TEXT)");
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BADMINTON);
        onCreate(db);
    }
    void addHistory(String n1,String n2,String n3,String n4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME_1, n1);
        cv.put(KEY_NAME_2, n2);
        cv.put(KEY_NAME_3, n3);
        cv.put(KEY_NAME_4, n4);

        try {
            long result = db.insert(TABLE_BADMINTON, null, cv);
            if (result == -1) {
                // Insertion failed
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Log.e("Database Insertion", "Error: " + e.getMessage());
        }
        db.close();
    }
        Cursor readAllData(){
        String query =" SELECT * FROM " + TABLE_BADMINTON;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;
        if(db !=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;

        }

//    public void insertMatchDetails(String n1, String n2, String n3, String n4, Integer sets_1, Integer sets_2, Integer sets_3, Integer score_points_1, Integer score_points_2, Integer score_points_3) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_NAME_1, n1);
//        cv.put(KEY_NAME_2, n2);
//        cv.put(KEY_NAME_3, n3);
//        cv.put(KEY_NAME_4, n4);
//        cv.put(KEY_SETS_1, sets_1);
//        cv.put(KEY_SETS_2, sets_2);
//        cv.put(KEY_SETS_3, sets_3);  // Corrected the key name here
//        cv.put(KEY_SCORE_POINTS_1, score_points_1);
//        cv.put(KEY_SCORE_POINTS_2, score_points_2);
//        cv.put(KEY_SCORE_POINTS_3, score_points_3);  // Corrected the key name here
//
//        long result = db.insert(TABLE_BADMINTON, null, cv);
//        if (result == -1) {
//            Toast.makeText(context, "Failed to insert match details", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Match details inserted successfully", Toast.LENGTH_SHORT).show();
//        }

}
