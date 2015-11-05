package com.example.nikunj.virtualwardrobe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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


public class TypeFragmentAdapter extends BaseAdapter
{
    private List<TypeList> items = new ArrayList<>();
    private LayoutInflater inflater;
    Context myContext;
    SavePhotoDBOpenHelper db;
    Integer positionGridView;

    public TypeFragmentAdapter(Context context)
    {
        myContext = context;
        inflater = LayoutInflater.from(context);


        db = SavePhotoDBOpenHelper.getInstance(myContext);



        items = db.getAllTypeListItem(myContext);

        db.close();
    }

    public TypeFragmentAdapter(Integer position){

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
        return items.get(i).getTypeListItemDrawableId();
    }


//    public Integer getItemDbId(int i){
//        return items.get(i).getTypeListItemId();
//    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        View v = view;
        ImageView picture;
        TextView name;
        ImageView gridItemMenu;
        final Integer positionItemId = items.get(position).getTypeListItemId();;

        if(v == null)
        {
            v = inflater.inflate(R.layout.type_gridview_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
            v.setTag(R.id.grid_item_menu,v.findViewById(R.id.grid_item_menu));

        }

//        Log.e("position for item1",positionItemId +"");

        picture = (ImageView)v.getTag(R.id.picture);
        name = (TextView)v.getTag(R.id.text);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(myContext, PhotosViewPager.class);
                i.putExtra("position_for_item",positionItemId);
//                Log.e("position for item2",positionItemId +"");
                myContext.startActivity(i);
                Toast.makeText(myContext, "You Clicked at Db position " + positionItemId, Toast.LENGTH_SHORT).show();


            }
        });

        gridItemMenu = (ImageView) v.findViewById(R.id.grid_item_menu);
        gridItemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myContext);

                // set title
                //alertDialogBuilder.setTitle("Your Title");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Edit Name or Delete Type")
                        .setCancelable(true)
                        .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                Toast.makeText(myContext, "Type Name to be edited", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Delete",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                Toast.makeText(myContext,"Type will be deleted",Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        TypeList item = (TypeList)getItem(position);

        picture.setImageResource(item.getTypeListItemDrawableId());
        name.setText(item.getTypeName());

        return v;
    }


}