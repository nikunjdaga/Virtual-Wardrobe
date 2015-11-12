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
    private static final String DATABASE_NAME = "photoSaveManagerData";

    // Table Names
    private static final String TABLE_COLTHES_TYPE = "clothesType";
    private static final String TABLE_COLLECTIONS = "collections";
    private static final String TABLE_COLOR_MAIN = "colorMain";
    private static final String TABLE_SAVED_PHOTO_UTIL = "savedPhoto";

    private static SavePhotoDBOpenHelper sInstance;

    // Table Create Statements
    // Saved Photo table create statement
//    CREATE TABLE `savedPhoto` (
//            `_id`	INTEGER,
//            `_description`	BLOB,
//            `_is_favourite`	INTEGER,
//            `_created_at`	REAL,
//            `_location_path`	BLOB NOT NULL,
//            `_color_selected`	TEXT NOT NULL,
//            `_type_name_id`	INTEGER,
//            `_collection_name_id`	INTEGER,
//            `_color_main_bracket`	INTEGER,
//    PRIMARY KEY(_id),
//    FOREIGN KEY(`_type_name_id`) REFERENCES clothesType ( `_id` ),
//    FOREIGN KEY(`_collection_name_id`) REFERENCES collections ( `_id` ),
//    FOREIGN KEY(`_color_main_bracket`) REFERENCES colorMain ( `_id` )
//            );
    private static final String CREATE_TABLE_SAVED_PHOTO_UTIL = "CREATE TABLE " + TABLE_SAVED_PHOTO_UTIL
            + " (" + SavedPhotoColumns.ID_VALUE + " INTEGER PRIMARY KEY,"
            + SavedPhotoColumns.DESCRIPTION + " BLOB,"
            + SavedPhotoColumns.IS_FAVOURITE + " INTEGER,"
            + SavedPhotoColumns.CREATED_AT + " REAL,"
            + SavedPhotoColumns.LOCATION_PATH + " BLOB NOT NULL,"
            + SavedPhotoColumns.COLOR_SELECTED + " TEXT NOT NULL,"
            + SavedPhotoColumns.TYPE_NAME_TABLE_ID + " INTEGER,"
            + SavedPhotoColumns.COLLECTIONS_NAME_TABLE_ID + " INTEGER,"
            + SavedPhotoColumns.COLOR_MAIN_BRACKET_TABLE_ID + " INTEGER,"
           // + SavedPhotoColumns.BRAND + " TEXT,"
            +" FOREIGN KEY " + "(" + SavedPhotoColumns.TYPE_NAME_TABLE_ID + ")" + " REFERENCES " + TABLE_COLTHES_TYPE + " (" +TypeNameColumns.ID_VALUE + "),"
            +" FOREIGN KEY " + "(" + SavedPhotoColumns.COLLECTIONS_NAME_TABLE_ID + ")" + " REFERENCES " + TABLE_COLLECTIONS + " (" +CollectionsColumns.ID_VALUE + "),"
            +" FOREIGN KEY " + "(" + SavedPhotoColumns.COLOR_MAIN_BRACKET_TABLE_ID + ")" + " REFERENCES " + TABLE_COLOR_MAIN + " (" +ColorColumns.ID_MAIN_VALUE + ")"
             + " );";

    // Type Clothes table create statement
//    CREATE TABLE `clothesType` (
//            `_id`	INTEGER,
//            `_type_name`	TEXT NOT NULL,
//            `_drawable_id`	INTEGER NOT NULL,
//    PRIMARY KEY(_id)
//    );
    private static final String CREATE_TABLE_COLTHES_TYPE =
            "CREATE TABLE " + TABLE_COLTHES_TYPE
            + " (" + TypeNameColumns.ID_VALUE + " INTEGER PRIMARY KEY,"
            + TypeNameColumns.TYPE_NAME + " TEXT NOT NULL,"
            + TypeNameColumns.DRAWABLE_ID + " INTEGER NOT NULL"
            + " );";

    // Collections table create statement
//    CREATE TABLE `collections` (
//            `_id`	INTEGER,
//            `_collection_name`	TEXT NOT NULL,
//            `_drawable_id`	INTEGER NOT NULL,
//    PRIMARY KEY(_id)
//    );
    private static final String CREATE_TABLE_COLLECTIONS =
            "CREATE TABLE " + TABLE_COLLECTIONS
            + " (" + CollectionsColumns.ID_VALUE + " INTEGER PRIMARY KEY,"
            + CollectionsColumns.COLLECTION_NAME + " TEXT NOT NULL,"
            + CollectionsColumns.DRAWABLE_ID + " INTEGER NOT NULL"
            + " );";

    // todo_tag table create statement
//    CREATE TABLE `colorMain` (
//            `_id`	INTEGER,
//            `_color_main_name`	TEXT,
//            `_drawable_id`	INTEGER,
//            `_red`	INTEGER,
//            `_green`	INTEGER,
//            `_blue`	INTEGER,
//            `_L`	REAL,
//            `_A`	REAL,
//            `_B`	REAL,
//    PRIMARY KEY(_id)
//    );
    private static final String CREATE_TABLE_COLOR_MAIN =
            "CREATE TABLE " + TABLE_COLOR_MAIN
            + " (" + ColorColumns.ID_MAIN_VALUE + " INTEGER PRIMARY KEY,"
            + ColorColumns.NAME_MAIN_VALUE + " TEXT,"
            + ColorColumns.DRAWABLE_ID_MAIN_VALUE + " INTEGER,"
            + ColorColumns.RED_MAIN_VALUE + " INTEGER,"
            + ColorColumns.GREEN_MAIN_VALUE + " INTEGER,"
            + ColorColumns.BLUE_MAIN_VALUE + " INTEGER,"
            + ColorColumns.L_MAIN_VALUE + " REAL,"
            + ColorColumns.A_MAIN_VALUE + " REAL,"
            + ColorColumns.B_MAIN_VALUE + " REAL"
            + " );";

    protected SavePhotoDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized SavePhotoDBOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new SavePhotoDBOpenHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL(CREATE_TABLE_SAVED_PHOTO_UTIL);
        db.execSQL(CREATE_TABLE_COLTHES_TYPE);
        db.execSQL(CREATE_TABLE_COLLECTIONS);
        db.execSQL(CREATE_TABLE_COLOR_MAIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_PHOTO_UTIL);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLTHES_TYPE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLECTIONS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLOR_MAIN);

            // create new tables
            onCreate(db);
        }
    }

    public void onOpen(SQLiteDatabase db){
        db.execSQL("PRAGMA foreign_keys=ON");
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations for Collections
     */

    // Adding new collection list item
    void addCollectionListItem(Context context, CollectionsList collectionsList) {
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CollectionsColumns.COLLECTION_NAME, collectionsList.getCollectionsName()); // Collection Name
        values.put(CollectionsColumns.DRAWABLE_ID, collectionsList.getCollectionListItemDrawableId());
        // Inserting Row
        db.insert(TABLE_COLLECTIONS, null, values);
        db.close(); // Closing database connection
    }



    // Getting single collection item
    CollectionsList getCollectionListItem(Context context, int id) {
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COLLECTIONS, new String[]{CollectionsColumns.ID_VALUE,
                        CollectionsColumns.COLLECTION_NAME, CollectionsColumns.DRAWABLE_ID}, CollectionsColumns.ID_VALUE + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CollectionsList collectionListItems = new CollectionsList(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),Integer.parseInt(cursor.getString(2)));

        cursor.close();
        db.close();
        // return collecton list item
        return collectionListItems;
    }

    // Getting All Collection list item Name
    public List<CollectionsList> getAllCollectionListItem(Context context) {
        List<CollectionsList> collectionList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COLLECTIONS;
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CollectionsList collectionsListItem = new CollectionsList();
                collectionsListItem.setCollectionListItemId(Integer.parseInt(cursor.getString(0)));
                collectionsListItem.setCollectionsName(cursor.getString(1));
                collectionsListItem.setCollectionListItemDrawableId(Integer.parseInt(cursor.getString(2)));
                // Adding collection item to list
                collectionList.add(collectionsListItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return collection list
        return collectionList;
    }

    // Updating single collection list item
    public int updateCollection(Context context, CollectionsList collectionsList) {
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CollectionsColumns.COLLECTION_NAME, collectionsList.getCollectionsName());
        values.put(CollectionsColumns.DRAWABLE_ID, collectionsList.getCollectionListItemDrawableId());

        // updating row
        Integer xa =  db.update(TABLE_COLLECTIONS, values, CollectionsColumns.ID_VALUE + " = ?",
                new String[] { String.valueOf(collectionsList.getCollectionListItemId()) });
        db.close();
        return  xa;
    }

    // Deleting single collection list item
    public void deleteCollection(Context context, CollectionsList collectionsList) {
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getWritableDatabase();
        db.delete(TABLE_COLLECTIONS, CollectionsColumns.ID_VALUE + " = ?",
                new String[]{String.valueOf(collectionsList.getCollectionListItemId())});
        db.close();
    }


    // Getting collection items Count
    public int getCollectionsCount(Context context) {
        String countQuery = "SELECT  * FROM " + TABLE_COLLECTIONS;
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        Integer x = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return x;
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations for Type
     */

    // Adding new type list item
    void addTypeListItem(Context context, TypeList typeList) {
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TypeNameColumns.TYPE_NAME, typeList.getTypeName()); // Type Name
        values.put(TypeNameColumns.DRAWABLE_ID, typeList.getTypeListItemDrawableId());
        // Inserting Row
        db.insert(TABLE_COLTHES_TYPE, null, values);
        db.close(); // Closing database connection
    }



    // Getting single type item
    TypeList getTypeListItem(Context context, int id) {
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COLTHES_TYPE, new String[]{TypeNameColumns.ID_VALUE,
                        TypeNameColumns.TYPE_NAME, TypeNameColumns.DRAWABLE_ID}, TypeNameColumns.ID_VALUE + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TypeList typeList = new TypeList(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),Integer.parseInt(cursor.getString(2)));

        cursor.close();
        db.close();
        // return type list item
        return typeList;
    }

    // Getting All Collection list item Name
    public List<TypeList> getAllTypeListItem(Context context) {
        List<TypeList> typeList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COLTHES_TYPE;
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TypeList typeListItem = new TypeList();
                typeListItem.setTypeListItemId(Integer.parseInt(cursor.getString(0)));
                typeListItem.setTypeName(cursor.getString(1));
                typeListItem.setTypeListItemDrawableId(Integer.parseInt(cursor.getString(2)));
                // Adding type item to list
                typeList.add(typeListItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return type list
        return typeList;
    }

    // Updating single type list item
    public int updateType(Context context, TypeList typeList) {
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TypeNameColumns.TYPE_NAME, typeList.getTypeName());
        values.put(TypeNameColumns.DRAWABLE_ID, typeList.getTypeListItemDrawableId());

        // updating row
        Integer ty =db.update(TABLE_COLTHES_TYPE, values, TypeNameColumns.ID_VALUE + " = ?",
                new String[] { String.valueOf(typeList.getTypeListItemId()) });
        db.close();
        return ty;
    }

    // Deleting single type list item
    public void deleteType(Context context, TypeList typeList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COLTHES_TYPE, TypeNameColumns.ID_VALUE + " = ?",
                new String[]{String.valueOf(typeList.getTypeListItemId())});
        db.close();
    }


    // Getting type items Count
    public int getTypeCount(Context context) {
        String countQuery = "SELECT  * FROM " + TABLE_COLTHES_TYPE;
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        Integer x = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return x;
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations for SAVED PHOTO
     */

    // Adding new type list item with collection name


    void addPhotoItem(Context context, SavedPhotoUtil savedPhotoUtil) {
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);


        ContentValues values = new ContentValues();
        values.put(SavedPhotoColumns.DESCRIPTION,savedPhotoUtil.getDescription());
        values.put(SavedPhotoColumns.IS_FAVOURITE,savedPhotoUtil.getIsFavourite());
        values.put(SavedPhotoColumns.CREATED_AT, savedPhotoUtil.getCreatedAt());
        values.put(SavedPhotoColumns.LOCATION_PATH,savedPhotoUtil.getLocationPath());
        values.put(SavedPhotoColumns.COLOR_SELECTED,savedPhotoUtil.getColorSelected());
        values.put(SavedPhotoColumns.TYPE_NAME_TABLE_ID,savedPhotoUtil.getTypeIdFromTable());
        values.put(SavedPhotoColumns.COLLECTIONS_NAME_TABLE_ID, savedPhotoUtil.getCollectionIdFromTable());
        values.put(SavedPhotoColumns.COLOR_MAIN_BRACKET_TABLE_ID,savedPhotoUtil.getColorMainBracketIdFromTable());// Inserting Row
        SQLiteDatabase db = assetdbhelper.getWritableDatabase();
        db.insert(TABLE_SAVED_PHOTO_UTIL, null, values);
        db.close(); // Closing database connection
    }



    // Getting single photo item with collection name
    SavedPhotoUtil getPhotoItem(Context context, int id) {
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getReadableDatabase();
        SavedPhotoUtil savedPhotoUtil = null;
        Cursor cursor = db.query(TABLE_SAVED_PHOTO_UTIL, new String[]{SavedPhotoColumns.ID_VALUE,
                        SavedPhotoColumns.DESCRIPTION, SavedPhotoColumns.IS_FAVOURITE,
                        SavedPhotoColumns.CREATED_AT, SavedPhotoColumns.LOCATION_PATH,
                        SavedPhotoColumns.COLOR_SELECTED, SavedPhotoColumns.TYPE_NAME_TABLE_ID,
                        SavedPhotoColumns.COLLECTIONS_NAME_TABLE_ID, SavedPhotoColumns.COLOR_MAIN_BRACKET_TABLE_ID
                }, SavedPhotoColumns.ID_VALUE + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null){
            if (cursor.moveToNext()) {

                 savedPhotoUtil = new SavedPhotoUtil(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                        Float.parseFloat(cursor.getString(3)), cursor.getString(4),
                        cursor.getString(5), Integer.parseInt(cursor.getString(6)),
                        Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)));
            }

             cursor.close();
         }
        db.close();
        // return saved photo list item
        return savedPhotoUtil;
    }

    // Getting All Saved photo list item Name
    public List<SavedPhotoUtil> getAllPhotoItem(Context context) {
        List<SavedPhotoUtil> savedPhotoItemsList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SAVED_PHOTO_UTIL;
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                SavedPhotoUtil savedPhotoUtilItem = new SavedPhotoUtil();
                  savedPhotoUtilItem.setPhotoId(Integer.parseInt(cursor.getString(0)));
                  savedPhotoUtilItem.setDescription(cursor.getString(1));
                  savedPhotoUtilItem.setIsFavourite(Integer.parseInt(cursor.getString(2)));
                  savedPhotoUtilItem.setCreatedAt(Float.parseFloat(cursor.getString(3)));
                  savedPhotoUtilItem.setLocationPath(cursor.getString(4));
                  savedPhotoUtilItem.setColorSelected(cursor.getString(5));
                  savedPhotoUtilItem.setTypeIdFromTable(Integer.parseInt(cursor.getString(6)));
                  savedPhotoUtilItem.setCollectionIdFromTable(Integer.parseInt(cursor.getString(7)));
                  savedPhotoUtilItem.setColorMainBracketIdFromTable(Integer.parseInt(cursor.getString(8)));



                // Adding saved photo item to list
                savedPhotoItemsList.add(savedPhotoUtilItem);

            } while (cursor.moveToNext());
        }
            cursor.close();
        db.close();
        // return saved photo item list
        return savedPhotoItemsList;
    }

    // Updating single photo item
    public int updatePhotoItem(Context context, SavedPhotoUtil savedPhotoUtil) {
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SavedPhotoColumns.DESCRIPTION,savedPhotoUtil.getDescription());
        values.put(SavedPhotoColumns.IS_FAVOURITE,savedPhotoUtil.getIsFavourite());
        values.put(SavedPhotoColumns.CREATED_AT, savedPhotoUtil.getCreatedAt());
        values.put(SavedPhotoColumns.LOCATION_PATH,savedPhotoUtil.getLocationPath());
        values.put(SavedPhotoColumns.COLOR_SELECTED,savedPhotoUtil.getColorSelected());
        values.put(SavedPhotoColumns.TYPE_NAME_TABLE_ID,savedPhotoUtil.getTypeIdFromTable());
        values.put(SavedPhotoColumns.COLLECTIONS_NAME_TABLE_ID, savedPhotoUtil.getCollectionIdFromTable());
        values.put(SavedPhotoColumns.COLOR_MAIN_BRACKET_TABLE_ID, savedPhotoUtil.getColorMainBracketIdFromTable());
        // updating row
        Integer y = db.update(TABLE_SAVED_PHOTO_UTIL, values, SavedPhotoColumns.ID_VALUE + " = ?",
                new String[] { String.valueOf(savedPhotoUtil.getPhotoId()) });

        db.close();
        return y;
    }

    // Deleting single photo item
    public void deleteSavedPhotoItem(Context context, SavedPhotoUtil savedPhotoUtil) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SAVED_PHOTO_UTIL, SavedPhotoColumns.ID_VALUE + " = ?",
                new String[]{String.valueOf(savedPhotoUtil.getPhotoId())});
        db.close();
    }


    // Getting photo items Count
    public int getSavedPhotoItemCount(Context context) {
        String countQuery = "SELECT  * FROM " + TABLE_SAVED_PHOTO_UTIL;
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        Integer x = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return x;
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations for Color
     */

    // Getting single color main item
    ColorMainList getColorMainListItem(Context context, int id) {
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getReadableDatabase();
        ColorMainList colorMainListItems = null;
        Cursor cursor = db.query(TABLE_COLOR_MAIN, new String[]{ColorColumns.ID_MAIN_VALUE,
                        ColorColumns.NAME_MAIN_VALUE, ColorColumns.DRAWABLE_ID_MAIN_VALUE,
                        ColorColumns.RED_MAIN_VALUE, ColorColumns.GREEN_MAIN_VALUE,
                        ColorColumns.BLUE_MAIN_VALUE, ColorColumns.L_MAIN_VALUE,
                        ColorColumns.A_MAIN_VALUE, ColorColumns.B_MAIN_VALUE}, CollectionsColumns.ID_VALUE + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            if(cursor.moveToFirst()) {

                 colorMainListItems = new ColorMainList(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5)), Float.parseFloat(cursor.getString(6)),
                        Float.parseFloat(cursor.getString(7)), Float.parseFloat(cursor.getString(8)));
            }
            cursor.close();
        }
        // return color list item
        db.close();
        return colorMainListItems;
    }

    // Getting All Color Main list item Name
    public List<ColorMainList> getAllColorMainListItem(Context context) {
        List<ColorMainList> colorMainList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COLOR_MAIN;
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ColorMainList colorMainListItem = new ColorMainList();
                colorMainListItem.setColorMainListItemId(Integer.parseInt(cursor.getString(0)));
                colorMainListItem.setColorMainName(cursor.getString(1));
                colorMainListItem.setColorMainListItemDrawableId(Integer.parseInt(cursor.getString(2)));
                colorMainListItem.setColorMainRed(Integer.parseInt(cursor.getString(3)));
                colorMainListItem.setColorMainGreen(Integer.parseInt(cursor.getString(4)));
                colorMainListItem.setColorMainBlue(Integer.parseInt(cursor.getString(5)));
                colorMainListItem.setColorMainLvalue(Float.parseFloat(cursor.getString(6)));
                colorMainListItem.setColorMainAvalue(Float.parseFloat(cursor.getString(7)));
                colorMainListItem.setColorMainBvalue(Float.parseFloat(cursor.getString(8)));


                // Adding color main item to list
                colorMainList.add(colorMainListItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return collection list
        return colorMainList;
    }

    // Getting color main items Count
    public int getColorMainListCount(Context context) {
        String countQuery = "SELECT  * FROM " + TABLE_COLOR_MAIN;
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(context);
        SQLiteDatabase db = assetdbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        Integer x = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return x;
    }


}
