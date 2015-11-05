package com.example.nikunj.virtualwardrobe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nikunj.virtualwardrobe.CopyDatabaseFromAssetFolder;

/**
 * Created by nikunj on 24/9/15.
 */
public class AssetDataBaseHelper extends SQLiteOpenHelper{

        private static String DB_NAME = "colorlist.db";

         private SQLiteDatabase myDataBase;
         private static final Integer DATABASE_VERSION = 1;

          Context context;
    private static AssetDataBaseHelper sInstance;

    public static synchronized AssetDataBaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new AssetDataBaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    protected AssetDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);

        this.context = context;
        CopyDatabaseFromAssetFolder.copyAssetDatabase(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



//    public CIE_LAB getLAB(int count) {
//        CIE_LAB lab = new CIE_LAB();
//        final String TABLE_USER_DETAILS = "_tbcolordata";
//        String query = "select L,A,B From " + TABLE_USER_DETAILS + "where id=" + count;
//        Cursor cursor = myDataBase.rawQuery(query, null);
//        if (cursor.getCount() > 0) {
//            if (cursor.moveToFirst()) {
//                while (cursor.moveToNext()) {
//                    lab.L = cursor.getInt(0);
//                    lab.A= cursor.getInt(1);
//                    lab.B = cursor.getInt(2);
//                }
//            }
//
//        }
//        cursor.close();
//        myDataBase.close();
//        return lab;
//
//
//    }
}
