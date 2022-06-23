package com.example.testproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DataBase extends SQLiteOpenHelper {
    /**** Info general ***/
    public static final String DB_NAME = "MOUZD";
    public static final int DB_VERSION = 2;
    /*** Info syr data Base NIVEAU ****/
    public static final String TB_NAME = "TABLE1";
    public static final String COL1_TB1 = "NIVEAU";
    public static final String COL2_TB1 = "DATE";
    //public static final String COL3_TB1 = "HEURE";


    DataBase(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE  " + TB_NAME + "(" + COL1_TB1 +" TEXT  ," + COL2_TB1 +"TEXT NOT NULL PRIMARY KEY   );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(sqLiteDatabase);
    }
    boolean insertData(ArrayList<Data> da){
       // String sql = "INSERT INTO " + TB_NAME +  "VALUES (" +da.getNiveau() + ","+da.getDate()+"," +da.getHeure()+ ")";
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for(int i=0;i<da.size();i++){
            String temp = da.get(i).getDate()+";"+ da.get(i).getHeure();
            cv.put(COL1_TB1, da.get(i).getNiveau());
            cv.put(COL2_TB1, temp);
        }

        long result = MyDB.insert(TB_NAME, null, cv);
        if (result == -1) return false;
        else
            return true;
    }
    /*****************************/
    ArrayList<Data> information (){
        ArrayList<Data> n = null;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TB_NAME + " where " + COL1_TB1 + "= ? ", new String[]{"57"});
        if (cursor != null) {

            while (cursor.moveToNext()) {

                String Nom = cursor.getString(0);
                String prenom = cursor.getString(1);
                Data dta = new Data(Nom, prenom);
                n.add(dta);
            }


        }
        cursor.close();
        return n;
    }



}