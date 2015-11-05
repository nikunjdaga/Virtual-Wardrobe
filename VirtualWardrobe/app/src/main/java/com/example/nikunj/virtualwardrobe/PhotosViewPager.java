package com.example.nikunj.virtualwardrobe;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.blunderer.materialdesignlibrary.activities.AActivity;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;

import java.util.ArrayList;


public class PhotosViewPager extends AActivity {


    Integer dBPositionItemID;

    // Table Names
    private static final String TABLE_COLTHES_TYPE = "clothesType";
    private static final String TABLE_COLLECTIONS = "collections";
    private static final String TABLE_COLOR_MAIN = "colorMain";
    private static final String TABLE_SAVED_PHOTO_UTIL = "savedPhoto";

    ArrayList<String> mAllDescriptionOfClothes = new ArrayList<>();
    ArrayList<String> mAllExactColorSelected = new ArrayList<>();
    ArrayList<String> mAllTypeName= new ArrayList<>();
    ArrayList<String> mAllCollectionName = new ArrayList<>();
    ArrayList<String> mAllColorMainBracketName= new ArrayList<>();
    ArrayList<String> mAllLocationUris =  new ArrayList<>();

    ArrayList<Integer> mAllIsFavInteger = new ArrayList<>();
    ArrayList<Float> mAllCreatedAtText = new ArrayList<>();
    ArrayList<Integer> mAllIDValues = new ArrayList<>();

    SavePhotoDBOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getVariousLists();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (mAllIDValues != null) {
                Log.e("extras not null", "");
                setContentView(R.layout.activity_photos_view_pager);
                dBPositionItemID = extras.getInt("position_for_item");

                Log.e("dbPositionId", dBPositionItemID + "");
            }
            else{
                setContentView(R.layout.photos_view_pager_empty);
            }
        }



        PhotosViewPagerAdapter mCustomPagerAdapter = new PhotosViewPagerAdapter(this,dBPositionItemID,
                                mAllDescriptionOfClothes,mAllExactColorSelected,mAllTypeName,mAllCollectionName,
                                mAllColorMainBracketName,mAllLocationUris,mAllIsFavInteger,mAllCreatedAtText,mAllIDValues);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        db.close();
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return null;
    }


    //query for type name to be found from type id in savedphoto table

//    SELECT clothesType._type_name FROM savedPhoto
//    JOIN clothesType ON savedPhoto._type_name_id = clothesType._id
//    WHERE savedPhoto._id = 1;

    //query for main color name to be found from color main bracket id in saved Photo table
//    SELECT colorMain._color_main_name FROM savedPhoto
//    JOIN colorMain ON savedPhoto._color_main_bracket = colorMain._id
//    WHERE savedPhoto._id = 1;

    //query for collection name to be found from collection id in savedPhoto table
//    SELECT collections._collection_name FROM savedPhoto
//    JOIN collections ON savedPhoto._collection_name_id = collections._id
//    WHERE savedPhoto._id = 1;


//    SELECT * FROM savedPhoto
//    JOIN clothesType ON clothesType._id = savedPhoto._type_name_id
//    JOIN collections ON savedPhoto._collection_name_id = collections._id
//    JOIN colorMain ON savedPhoto._color_main_bracket = colorMain._id
//    WHERE clothesType._id = 1;

    public void getVariousLists(){
        AssetDBHelperSavePhotoManager assetdbhelper= new AssetDBHelperSavePhotoManager(this);
        SQLiteDatabase db = assetdbhelper.getReadableDatabase();
//        String columns[] = {SavedPhotoColumns.ID_VALUE,
//                SavedPhotoColumns.DESCRIPTION,
//                SavedPhotoColumns.IS_FAVOURITE,
//                SavedPhotoColumns.CREATED_AT,
//                SavedPhotoColumns.LOCATION_PATH,
//                SavedPhotoColumns.COLOR_SELECTED,
//                SavedPhotoColumns.TYPE_NAME_TABLE_ID,
//                SavedPhotoColumns.COLLECTIONS_NAME_TABLE_ID,
//                SavedPhotoColumns.COLOR_MAIN_BRACKET_TABLE_ID,
//
//        };

        String selectQuery = "SELECT * FROM " + TABLE_SAVED_PHOTO_UTIL +
                " JOIN " + TABLE_COLTHES_TYPE + " ON " + TABLE_COLTHES_TYPE + "." + TypeNameColumns.ID_VALUE +
                " = " + TABLE_SAVED_PHOTO_UTIL + "." + SavedPhotoColumns.TYPE_NAME_TABLE_ID +
                " JOIN " + TABLE_COLLECTIONS + " ON " + TABLE_COLLECTIONS + "." + CollectionsColumns.ID_VALUE +
                " = " + TABLE_SAVED_PHOTO_UTIL + "." + SavedPhotoColumns.COLLECTIONS_NAME_TABLE_ID +
                " JOIN " + TABLE_COLOR_MAIN + " ON " + TABLE_COLOR_MAIN + "." + ColorColumns.ID_MAIN_VALUE +
                " = " + TABLE_SAVED_PHOTO_UTIL + "." + SavedPhotoColumns.COLOR_MAIN_BRACKET_TABLE_ID +
                " WHERE " + TABLE_COLTHES_TYPE + "." + TypeNameColumns.ID_VALUE + " = " + dBPositionItemID + ";";


        Cursor c = db.rawQuery(selectQuery,null);

        c.moveToFirst();

        while (c.moveToNext()) {
            Integer Idvalue = c.getInt(c.getColumnIndex(SavedPhotoColumns.ID_VALUE));
            String photoDescription = c.getString(c.getColumnIndex(SavedPhotoColumns.DESCRIPTION));
            String photoLocation = c.getString(c.getColumnIndex(SavedPhotoColumns.LOCATION_PATH));
            String photoColorSelected = c.getString(c.getColumnIndex(SavedPhotoColumns.COLOR_SELECTED));
            String photoMainColorName = c.getString(c.getColumnIndex(ColorColumns.NAME_MAIN_VALUE));
            String photoCollectionName = c.getString(c.getColumnIndex(CollectionsColumns.COLLECTION_NAME));
            String photoTypeName = c.getString(c.getColumnIndex(TypeNameColumns.TYPE_NAME));

            Float createdAtDate = c.getFloat(c.getColumnIndex(SavedPhotoColumns.CREATED_AT));
            Integer isfavoriteValue = c.getInt(c.getColumnIndex(SavedPhotoColumns.IS_FAVOURITE));


            mAllIDValues.add(Idvalue);
            mAllDescriptionOfClothes.add(photoDescription);
            mAllLocationUris.add(photoLocation);
            mAllExactColorSelected.add(photoColorSelected);
            mAllColorMainBracketName.add(photoMainColorName);
            mAllCollectionName.add(photoCollectionName);
            mAllTypeName.add(photoTypeName);
            mAllIsFavInteger.add(isfavoriteValue);
            mAllCreatedAtText.add(createdAtDate);

        }
        c.close();
        db.close();

    }

}
