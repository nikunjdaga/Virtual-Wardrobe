package com.example.nikunj.virtualwardrobe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nikunj on 12/10/15.
 */
public class AssetDBHelperSavePhotoManager extends SQLiteOpenHelper {

    private static String DB_NAME = "photoSaveManager.db";

    private SQLiteDatabase myDataBase;
    private static final Integer DATABASE_VERSION = 1;

    Context context;
    public AssetDBHelperSavePhotoManager(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);

        this.context = context;
        CopyPhotoSaveDBFromAssetFolder.copyPhotoSaveAssetDatabase(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
