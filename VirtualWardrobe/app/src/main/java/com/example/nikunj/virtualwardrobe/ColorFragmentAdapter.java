package com.example.nikunj.virtualwardrobe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    private Context myContext;
    private SavePhotoDBOpenHelper db;
    Integer positionGridView;

    public ColorFragmentAdapter(Context context)
    {
        myContext = context;
        inflater = LayoutInflater.from(context);

        db = SavePhotoDBOpenHelper.getInstance(myContext);

        items = db.getAllColorMainListItem(myContext);

        db.close();


    }

    public ColorFragmentAdapter(Integer position){

        positionGridView = position;
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
        final Integer positionItemId = items.get(position).getColorMainListItemId();

        if(v == null)
        {
            v = inflater.inflate(R.layout.color_gridview_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }


        picture = (ImageView)v.getTag(R.id.picture);
        name = (TextView)v.getTag(R.id.text);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(myContext, ColorPhotosViewPager.class);
                i.putExtra("position_for_color_item",positionItemId);
//                Log.e("position for item2",positionItemId +"");
                myContext.startActivity(i);
                Toast.makeText(myContext, "You Clicked at Db position " + positionItemId, Toast.LENGTH_SHORT).show();


            }
        });

        ColorMainList item = (ColorMainList)getItem(position);

        picture.setImageResource(item.getColorMainListItemDrawableId());
        name.setText(item.getColorMainName());

        return v;
    }



}