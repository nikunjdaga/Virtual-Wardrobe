package com.example.nikunj.virtualwardrobe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikunj on 30/9/15.
 */
public class SavePhotoDBOpenHelper extends SQLiteOpenHelper{

    // Logcat tag
    private static final String LOG = "SavePhotoDatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "photoSaveManager";

    // Table Names
    private static final String TABLE_SAVED_PHOTO_UTIL = "savedPhoto";
    private static final String TABLE_COLTHES_TYPE = "colthesType";
    private static final String TABLE_COLLECTIONS = "collections";
    private static final String TABLE_SAVED_TAG = "saved_tags";



    // Table Create Statements
    // Saved Photo table create statement
    private static final String CREATE_TABLE_SAVED_PHOTO_UTIL = "CREATE TABLE " + TABLE_SAVED_PHOTO_UTIL
            + "(" + SavedPhotoColumns.ID_VALUE + " INTEGER PRIMARY KEY,"
            + SavedPhotoColumns.COLOR_MAIN_BRACKET + " TEXT,"
            + SavedPhotoColumns.COLOR_SELECTED + " TEXT,"
            + SavedPhotoColumns.DESCRIPTION + " TEXT,"
            + SavedPhotoColumns.BRAND + " TEXT,"
            + SavedPhotoColumns.IS_FAVOURITE + " NUMBER,"
            + SavedPhotoColumns.LOCATION_PATH + " TEXT,"
            + SavedPhotoColumns.CREATED_AT + " DATETIME" + ")";

    // Type Clothes table create statement
    private static final String CREATE_TABLE_COLTHES_TYPE =
            "CREATE TABLE " + TABLE_COLTHES_TYPE
            + "(" + TypeNameColumns.ID_VALUE + " INTEGER PRIMARY KEY,"
            + TypeNameColumns.TYPE_NAME + " TEXT,"
            + TypeNameColumns.DRAWABLE_ID + " INTEGER,"
            + TypeNameColumns.CREATED_AT + " DATETIME" + ")";

    // Collections table create statement
    private static final String CREATE_TABLE_COLLECTIONS =
            "CREATE TABLE " + TABLE_COLLECTIONS
            + "(" + CollectionsColumns.ID_VALUE + " INTEGER PRIMARY KEY,"
            + CollectionsColumns.COLLECTION_NAME + " TEXT,"
            + CollectionsColumns.DRAWABLE_ID + " INTEGER,"
            + CollectionsColumns.CREATED_AT + " DATETIME" + ")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_SAVED_TAG =
            "CREATE TABLE " + TABLE_SAVED_TAG
            + "(" + SavedPhotoTagColumns.ID_VALUE + " INTEGER PRIMARY KEY,"
            + SavedPhotoTagColumns.SAVED_PHOTO_TABLE_ID + " INTEGER,"
            + SavedPhotoTagColumns.TYPE_NAME_TABLE_ID + " INTEGER,"
            + SavedPhotoTagColumns.COLLECTONS_TABLE_ID + " INTEGER,"
            + SavedPhotoTagColumns.CREATED_AT + " DATETIME" + ")";

    public SavePhotoDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_SAVED_PHOTO_UTIL);
        db.execSQL(CREATE_TABLE_COLTHES_TYPE);
        db.execSQL(CREATE_TABLE_COLLECTIONS);
        db.execSQL(CREATE_TABLE_SAVED_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_PHOTO_UTIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLTHES_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLECTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_TAG);

        // create new tables
        onCreate(db);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations for Collections
     */

    // Adding new collection list item
    void addCollectionListItem(CollectionsList collectionsList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CollectionsColumns.COLLECTION_NAME, collectionsList.getCollectionsName()); // Collection Name
        values.put(CollectionsColumns.DRAWABLE_ID,collectionsList.getCollectionListItemDrawableId());
        // Inserting Row
        db.insert(TABLE_COLLECTIONS, null, values);
        db.close(); // Closing database connection
    }



    // Getting single collection item
    CollectionsList getCollectionListItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COLLECTIONS, new String[] { CollectionsColumns.ID_VALUE,
                        CollectionsColumns.COLLECTION_NAME,CollectionsColumns.DRAWABLE_ID}, CollectionsColumns.ID_VALUE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CollectionsList collectionListItems = new CollectionsList(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),Integer.parseInt(cursor.getString(2)));

        cursor.close();
        // return collecton list item
        return collectionListItems;
    }

    // Getting All Collection list item Name
    public List<CollectionsList> getAllCollectionListItem() {
        List<CollectionsList> collectionList = new ArrayList<CollectionsList>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COLLECTIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CollectionsList collectionsListItem = new CollectionsList();
                collectionsListItem.setCollectionListItemId(Integer.parseInt(cursor.getString(0)));
                collectionsListItem.setCollectionsName(cursor.getString(1));
                collectionsListItem.setCollectionListItemDrawableId(Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                collectionList.add(collectionsListItem);
            } while (cursor.moveToNext());
        }

        // return collection list
        return collectionList;
    }

    // Updating single collection list item
    public int updateCollection(CollectionsList collectionsList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CollectionsColumns.COLLECTION_NAME, collectionsList.getCollectionsName());
        values.put(CollectionsColumns.DRAWABLE_ID, collectionsList.getCollectionListItemDrawableId());

        // updating row
        return db.update(TABLE_COLLECTIONS, values, CollectionsColumns.ID_VALUE + " = ?",
                new String[] { String.valueOf(collectionsList.getCollectionListItemId()) });
    }

    // Deleting single collection list item
    public void deleteCollection(CollectionsList collectionsList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COLLECTIONS, CollectionsColumns.ID_VALUE + " = ?",
                new String[]{String.valueOf(collectionsList.getCollectionListItemId())});
        db.close();
    }


    // Getting collection items Count
    public int getCollectionsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_COLLECTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations for Type
     */

    // Adding new type list item
    void addTypeListItem(TypeList typeList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TypeNameColumns.TYPE_NAME, typeList.getTypeName()); // Collection Name
        values.put(TypeNameColumns.DRAWABLE_ID,typeList.getTypeListItemDrawableId());
        // Inserting Row
        db.insert(TABLE_COLTHES_TYPE, null, values);
        db.close(); // Closing database connection
    }



    // Getting single type item
    TypeList getTypeListItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COLTHES_TYPE, new String[] { TypeNameColumns.ID_VALUE,
                        TypeNameColumns.TYPE_NAME,TypeNameColumns.DRAWABLE_ID}, TypeNameColumns.ID_VALUE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TypeList typeList = new TypeList(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),Integer.parseInt(cursor.getString(2)));

        cursor.close();
        // return type list item
        return typeList;
    }

    // Getting All Collection list item Name
    public List<TypeList> getAllTypeListItem() {
        List<TypeList> typeList = new ArrayList<TypeList>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COLTHES_TYPE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TypeList typeListItem = new TypeList();
                typeListItem.setTypeListItemId(Integer.parseInt(cursor.getString(0)));
                typeListItem.setTypeName(cursor.getString(1));
                typeListItem.setTypeListItemDrawableId(Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                typeList.add(typeListItem);
            } while (cursor.moveToNext());
        }

        // return type list
        return typeList;
    }

    // Updating single type list item
    public int updateType(TypeList typeList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TypeNameColumns.TYPE_NAME, typeList.getTypeName());
        values.put(TypeNameColumns.DRAWABLE_ID, typeList.getTypeListItemDrawableId());

        // updating row
        return db.update(TABLE_COLTHES_TYPE, values, TypeNameColumns.ID_VALUE + " = ?",
                new String[] { String.valueOf(typeList.getTypeListItemId()) });
    }

    // Deleting single type list item
    public void deleteType(TypeList typeList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COLTHES_TYPE, TypeNameColumns.ID_VALUE + " = ?",
                new String[]{String.valueOf(typeList.getTypeListItemId())});
        db.close();
    }


    // Getting type items Count
    public int getTypeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_COLTHES_TYPE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
