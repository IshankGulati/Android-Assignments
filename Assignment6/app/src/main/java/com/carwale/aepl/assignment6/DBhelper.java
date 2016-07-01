package com.carwale.aepl.assignment6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by aepl on 30/6/16.
 */
public class DBhelper extends SQLiteOpenHelper {

    private static DBhelper dBhelper = null;
    private static final String DB_NAME = "carwale.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "employeen";
    private static final String SEPERATOR = ",";

    private DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DBhelper getInstance(Context context){
        if(dBhelper == null){
            dBhelper = new DBhelper(context);
        }
        return dBhelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + TABLE_NAME + " (id integer primary key, name text, dob integer, " +
                        "doj integer, designation text, salary integer)"
        );
   }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private long convertDateToMillis(String dateInddmmyyyy){
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = sdf.parse(dateInddmmyyyy);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cal.getTimeInMillis();
    }

    public void insertEmployeeData(int id, String name, String dob, String doj, String designation,
                                   int salary){

        long b = convertDateToMillis(dob);
        long j = convertDateToMillis(doj);
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("insert into " + TABLE_NAME  +
//        " values(" + id + SEPERATOR + name + SEPERATOR + b + SEPERATOR + j + SEPERATOR + designation
//                + SEPERATOR + salary +")");

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("dob", b);
        contentValues.put("doj", j);
        contentValues.put("designation", designation);
        contentValues.put("salary", salary);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void updateDesignation(String newDesignation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("designation", newDesignation);
        String[] args = new String[]{ getDateComparator() + ""};
        db.update(TABLE_NAME, content, "doj<?", args);
    }

    private long getDateComparator(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -2);
        return cal.getTimeInMillis();
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }

    public void updateEmployeeData(int id, String name, String dob, String doj, String designation,
                                   int salary){

        long b = convertDateToMillis(dob);
        long j = convertDateToMillis(doj);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("dob", b);
        contentValues.put("doj", j);
        contentValues.put("designation", designation);
        contentValues.put("salary", salary);

        String where = "id=?";
        String[] whereArgs = new String[] {String.valueOf(id)};

        db.update(TABLE_NAME, contentValues, where, whereArgs);
    }
}
