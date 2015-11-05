package com.example.nikunj.virtualwardrobe;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
        import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
        import java.util.List;

/**
 * Created by nikunj on 1/10/15.
 */
public class CollectionsFragment extends Fragment
{
    SavePhotoDBOpenHelper db;
    private FloatingActionButton fabAddCollection;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View gridview_layout = inflater.inflate(R.layout.collections_fragment_gridview, container, false);

         db = new SavePhotoDBOpenHelper(getActivity());

        GridView gridView = (GridView)gridview_layout.findViewById(R.id.collectionsGridview);
        gridView.setAdapter(new CollectionsFragmentAdapter(getContext()));
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //  Intent intent = new Intent(getActivity(), HollyViewPagerActivity.class);
//                // intent.putExtra(key, titleName);
//                //startActivity(intent);
//                Toast.makeText(getActivity(), "You Clicked at " + position, Toast.LENGTH_SHORT).show();
//
//            }
//        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());


                // set title
                //alertDialogBuilder.setTitle("Your Title");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Edit Collection or Delete Type")
                        .setCancelable(true)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                Toast.makeText(getActivity(), "Type Name to be edited", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing

                                Toast.makeText(getActivity(), "Type will be deleted", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                return true;
            }
        });

        fabAddCollection = (FloatingActionButton) gridview_layout.findViewById(R.id.fabAddCollections);
        fabAddCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                //alertDialogBuilder.setView(R.layout.add_collection_type_alert_dialog);

                final View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_collection_type_alert_dialog, null);
                alertDialogBuilder.setView(view);

                // set title

                alertDialogBuilder
                        .setTitle("Add a collection")
                        .setCancelable(true)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                final EditText setCollectionName = (EditText) view.findViewById(R.id.setCollectionItemNameEditText);
                                final String collectionItemName = setCollectionName.getText().toString();

                                if (collectionItemName != "") {
                                    db.addCollectionListItem(getActivity(),new CollectionsList(collectionItemName,R.drawable.nature1));

                                } else {
                                    dialog.dismiss();
                                }
//                       // dialog.dismiss();

                                Toast.makeText(getActivity(), "Type Name to be edited", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                Toast.makeText(getActivity(), "Type will be deleted", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();



            }
        });
        db.close();
        return gridview_layout;
    }

}