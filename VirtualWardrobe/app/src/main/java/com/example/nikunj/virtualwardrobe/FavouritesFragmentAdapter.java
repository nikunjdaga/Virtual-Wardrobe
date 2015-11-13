package com.example.nikunj.virtualwardrobe;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikunj on 3/10/15.
 */


public class FavouritesFragmentAdapter extends BaseAdapter
{
    private LayoutInflater inflater;
    Context myContext;
    ArrayList<String> mAllFavouriteLocationUris =  new ArrayList<>();

    public FavouritesFragmentAdapter(Context context,ArrayList<String> AllFavouriteLocationUris)
    {
        myContext = context;
        inflater = LayoutInflater.from(context);
        mAllFavouriteLocationUris =  AllFavouriteLocationUris;
    }

    @Override
    public int getCount() {
        return mAllFavouriteLocationUris.size();
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v = view;
        ImageView picture;

        if(v == null)
        {
            v = inflater.inflate(R.layout.favourite_gridview_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
        }

        picture = (ImageView)v.getTag(R.id.picture);


        Glide.clear(picture);

        Glide.with(myContext)
                .load(new File(mAllFavouriteLocationUris.get(i)))
                .into(picture);

        return v;
    }

}