package com.example.nikunj.virtualwardrobe;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
        import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
        import java.util.List;

public class TypeFragment extends Fragment
{
    SavePhotoDBOpenHelper db;
    private FloatingActionButton fabCameraPhotoButton;
    private FloatingActionButton fabGalleryPhotoButton;
    private FloatingActionButton fabAddTypeButton;
    private FloatingActionMenu floatingActionMenu;

    private List<FloatingActionMenu> menus = new ArrayList<>();

    private Handler mUiHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View gridview_layout = inflater.inflate(R.layout.type_fragment_gridview, container, false);

        db = SavePhotoDBOpenHelper.getInstance(getActivity());


        GridView gridView = (GridView)gridview_layout.findViewById(R.id.typeGridview);
        gridView.setAdapter(new TypeFragmentAdapter(getContext()));
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //Intent intent = new Intent(getActivity(), HollyViewPagerActivity.class);
//                // intent.putExtra(key, titleName);
//                //startActivity(intent);
//                Integer itemId = gridView.getAdapter().getItem(position).;
//                Toast.makeText(getActivity(), "You Clicked at " + position, Toast.LENGTH_SHORT).show();
//
//            }
//        });

        Log.e("Drawable",R.drawable.collection_casual_wear + "");
        Log.e("Drawable",R.drawable.collection_ethnic_casual + "");
        Log.e("Drawable",R.drawable.collection_ethnic_formal + "");
        Log.e("Drawable",R.drawable.collection_excercise_wear + "");
        Log.e("Drawable",R.drawable.collection_formal_wear + "");
        Log.e("Drawable",R.drawable.collection_party_wear + "");
        Log.e("Drawable",R.drawable.collection_summer + "");
        Log.e("Drawable",R.drawable.collection_winter + "");
        Log.e("Drawable",R.drawable.color_beige + "");
        Log.e("Drawable",R.drawable.color_black + "");
        Log.e("Drawable",R.drawable.color_brown + "");
        Log.e("Drawable",R.drawable.color_dark_blue + "");
        Log.e("Drawable",R.drawable.color_gray + "");
        Log.e("Drawable",R.drawable.color_green + "");
        Log.e("Drawable",R.drawable.color_light_blue + "");
        Log.e("Drawable",R.drawable.color_maroon + "");
        Log.e("Drawable",R.drawable.color_orange + "");
        Log.e("Drawable",R.drawable.color_pink + "");
        Log.e("Drawable",R.drawable.color_purple + "");
        Log.e("Drawable",R.drawable.color_red + "");
        Log.e("Drawable",R.drawable.color_white + "");
        Log.e("Drawable",R.drawable.color_yellow + "");
        Log.e("Drawable",R.drawable.type_accessories + "");
        Log.e("Drawable",R.drawable.type_bags + "");
        Log.e("Drawable",R.drawable.type_dress + "");
        Log.e("Drawable",R.drawable.type_footwear + "");
        Log.e("Drawable",R.drawable.type_grooming + "");
        Log.e("Drawable",R.drawable.type_jackets + "");
        Log.e("Drawable",R.drawable.type_kurtis + "");
        Log.e("Drawable",R.drawable.type_lower + "");
        Log.e("Drawable",R.drawable.type_salwaar_suit + "");
        Log.e("Drawable",R.drawable.type_saree + "");
        Log.e("Drawable",R.drawable.type_skirts + "");
        Log.e("Drawable",R.drawable.type_tees + "");


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());

                // set title
                //alertDialogBuilder.setTitle("Your Title");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Edit Name or Delete Type")
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

        floatingActionMenu = (FloatingActionMenu) gridview_layout.findViewById(R.id.menu1);
        fabCameraPhotoButton= (FloatingActionButton) gridview_layout.findViewById(R.id.cameraPhotoButton);
        fabGalleryPhotoButton = (FloatingActionButton) gridview_layout.findViewById(R.id.galleryPhotoButton);
        fabAddTypeButton = (FloatingActionButton) gridview_layout.findViewById(R.id.addTypeButton);

        fabCameraPhotoButton.setOnClickListener(clickListener);
        fabGalleryPhotoButton.setOnClickListener(clickListener);
        fabAddTypeButton.setOnClickListener(clickListener);

        floatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (floatingActionMenu.isOpened()) {
                }

                floatingActionMenu.toggle(true);
            }
        });
        menus.add(floatingActionMenu);

        floatingActionMenu.hideMenuButton(false);

        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }

        floatingActionMenu.setClosedOnTouchOutside(true);


        db.close();
        return gridview_layout;
    }

   

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.cameraPhotoButton:
                    Intent intent=new Intent();
                    intent.setClass(getActivity(), CameraPhoto.class);
                    startActivity(intent);
                    floatingActionMenu.toggle(true);
                    break;
                case R.id.galleryPhotoButton:
                    Intent intent2=new Intent();
                    intent2.setClass(getActivity(),GalleryPhoto.class);
                    startActivity(intent2);
                    floatingActionMenu.toggle(true);
                    break;
                case R.id.addTypeButton:
                    floatingActionMenu.toggle(true);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());
                    //alertDialogBuilder.setView(R.layout.add_collection_type_alert_dialog);

                    final View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_clothes_type_alert_dialog, null);
                    alertDialogBuilder.setView(view);

                    // set title

                    alertDialogBuilder
                            .setTitle("Add a collection")
                            .setCancelable(true)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    final EditText setTypeName = (EditText) view.findViewById(R.id.setTypeItemNameEditText);
                                    final String typeItemName = setTypeName.getText().toString();

                                    if (typeItemName != "") {
                                        db.addTypeListItem(getActivity(), new TypeList(typeItemName, R.drawable.nature1));


                                    } else {
                                        dialog.dismiss();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                    break;

            }
            db.close();
        }
    };


}