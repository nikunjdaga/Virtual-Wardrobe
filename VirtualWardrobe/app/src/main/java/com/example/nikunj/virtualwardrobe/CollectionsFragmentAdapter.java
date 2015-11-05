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

public class CollectionsFragmentAdapter extends BaseAdapter
{
    private List<CollectionsList> items = new ArrayList<>();
    private LayoutInflater inflater;
    Context myContext;
    SavePhotoDBOpenHelper db;

    public CollectionsFragmentAdapter(Context context)
    {
        myContext = context;
        inflater = LayoutInflater.from(context);
//
//        items.add(new CollectionsList("Image 1", R.drawable.nature1));
//        items.add(new CollectionsList("Image 2", R.drawable.nature2));
//        items.add(new CollectionsList("Image 3", R.drawable.tree1));
//        items.add(new CollectionsList("Image 4", R.drawable.nature3));
//        items.add(new CollectionsList("Image 5", R.drawable.tree2));
        db = SavePhotoDBOpenHelper.getInstance(myContext);
        //db.addCollectionListItem(myContext,new CollectionsList("Image 1", R.drawable.nature1));
        //db.addCollectionListItem(myContext,new CollectionsList("Image 2",R.drawable.nature2));
        //db.addCollectionListItem(myContext,new CollectionsList("Image 3",R.drawable.nature3));
        //db.addCollectionListItem(myContext,new CollectionsList("Image 4",R.drawable.tree1));
        //db.addCollectionListItem(myContext,new CollectionsList("Image 5",R.drawable.tree2));
        //db.addCollectionListItem(myContext,new CollectionsList("Image 6", R.drawable.nature2));


        items = db.getAllCollectionListItem(myContext);

//        for (CollectionsList collectionlistsitems : items) {
//            items.add(new CollectionsList(collectionlistsitems.getCollectionsName(),collectionlistsitems.getCollectionListItemDrawableId()));
//
//        }
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
        return items.get(i).getCollectionListItemDrawableId();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup)
    {
        View v = view;
        ImageView picture;
        TextView name;
        ImageView gridItemMenu;

        if(v == null)
        {
            v = inflater.inflate(R.layout.collections_gridview_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
            v.setTag(R.id.grid_item_menu,v.findViewById(R.id.grid_item_menu));
        }

        picture = (ImageView)v.getTag(R.id.picture);
        name = (TextView)v.getTag(R.id.text);
        final Integer positionItemId = items.get(position).getCollectionListItemId();
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
                                Toast.makeText(myContext, "Type Name to be edited ", Toast.LENGTH_SHORT).show();
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



        CollectionsList item = (CollectionsList)getItem(position);

        picture.setImageResource(item.getCollectionListItemDrawableId());
        name.setText(item.getCollectionsName());

        return v;
    }

}