package com.example.nikunj.virtualwardrobe;

/**
 * Created by root on 24/9/15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper  extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
        // TODO Auto-generated constructor stub
    }

    public static final String DATABASE = "colordatamain";

    public static final int VERSION = 1;

    public static final String COLOR_DATA_TABLE_NAME = "tb_colordata";

    public static final String COLOR_DATA_NAME_COLUMN = "_name";

    public static final String COLOR_DATA_L_VALUE = "_L";

    public static final String COLOR_DATA_A_VALUE = "_A";

    public static final String COLOR_DATA_B_VALUE = "_B";

    public static final String COLOR_DATA_RED_VALUE = "_red";

    public static final String COLOR_DATA_GREEN_VALUE = "_green";

    public static final String COLOR_DATA_BLUE_VALUE = "_blue";

    public static final String COLOR_DATA_ID = "_id";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + COLOR_DATA_TABLE_NAME +
                " ( " + COLOR_DATA_ID + " INTEGER PRIMARY KEY, "
                + COLOR_DATA_NAME_COLUMN + " TEXT, "
                + COLOR_DATA_RED_VALUE + " NUMBER, "
                + COLOR_DATA_GREEN_VALUE + " NUMBER, "
                + COLOR_DATA_BLUE_VALUE + " NUMBER, "
                + COLOR_DATA_L_VALUE + " NUMBER, "
                + COLOR_DATA_A_VALUE + " NUMBER, "
                + COLOR_DATA_B_VALUE + " NUMBER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stu
    }
}