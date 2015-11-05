package com.example.nikunj.virtualwardrobe;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikunj on 3/10/15.
 */


public class ColorFragmentAdapter extends BaseAdapter
{
    private List<ColorMainList> items = new ArrayList<>();
    private LayoutInflater inflater;
    Context myContext;
    SavePhotoDBOpenHelper db;

    public ColorFragmentAdapter(Context context)
    {
        myContext = context;
        inflater = LayoutInflater.from(context);

        db = SavePhotoDBOpenHelper.getInstance(myContext);

        items = db.getAllColorMainListItem(myContext);

        db.close();


    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i)
    {
        return items.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return items.get(i).getColorMainListItemDrawableId();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup)
    {
        View v = view;
        ImageView picture;
        TextView name;

        if(v == null)
        {
            v = inflater.inflate(R.layout.color_gridview_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }


        picture = (ImageView)v.getTag(R.id.picture);
        name = (TextView)v.getTag(R.id.text);

        ColorMainList item = (ColorMainList)getItem(position);

        picture.setImageResource(item.getColorMainListItemDrawableId());
        name.setText(item.getColorMainName());

        return v;
    }



}