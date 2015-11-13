package com.example.nikunj.virtualwardrobe;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

        import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
        import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
        import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
        import java.util.List;

/**
 * Created by nikunj on 3/10/15.
 */

public class FavouritesFragment extends Fragment
{


    // Table Names
    private static final String TABLE_SAVED_PHOTO_UTIL = "savedPhoto";

    private ArrayList<String> mAllFavouriteLocationUris =  new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFavouritePhotoLocationList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mAllFavouriteLocationUris != null) {

            View gridview_layout = inflater.inflate(R.layout.favourite_fragment_gridview, container, false);


            GridView gridView = (GridView) gridview_layout.findViewById(R.id.favouriteGridview);
            gridView.setAdapter(new FavouritesFragmentAdapter(getContext(),mAllFavouriteLocationUris));
//            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    /// Intent intent = new Intent(getActivity(), HollyViewPagerActivity.class);
//                    // intent.putExtra(key, titleName);
//                    //startActivity(intent);
//                    Toast.makeText(getActivity(), "You Clicked at " + position, Toast.LENGTH_SHORT).show();
//
//                }
//            });
            return gridview_layout;
        }else {
            View gridview_layout = inflater.inflate(R.layout.favourite_fragment_empty, container, false);
            return gridview_layout;
        }
    }


//    SELECT * FROM savedPhoto WHERE savedPhoto._is_favourite = 1;

    public void getFavouritePhotoLocationList(){
        AssetDBHelperSavePhotoManager assetdbhelper= AssetDBHelperSavePhotoManager.getInstance(getActivity());
        SQLiteDatabase db = assetdbhelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SAVED_PHOTO_UTIL +
                " WHERE " + TABLE_SAVED_PHOTO_UTIL + "." + SavedPhotoColumns.IS_FAVOURITE + " = 1 " +   ";";


        Cursor c = db.rawQuery(selectQuery,null);

        if(c != null && c.moveToFirst()) {

            do {
                String photoLocation = c.getString(c.getColumnIndex(SavedPhotoColumns.LOCATION_PATH));

                mAllFavouriteLocationUris.add(photoLocation);

            }while (c.moveToNext());


            c.close();
        }

        db.close();

    }


}