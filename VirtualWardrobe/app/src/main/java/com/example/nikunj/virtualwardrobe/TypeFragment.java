package com.example.nikunj.virtualwardrobe;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
        import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.TextView;
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

    private List<FloatingActionMenu> menus = new ArrayList<>();

    private Handler mUiHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View gridview_layout = inflater.inflate(R.layout.type_fragment_gridview, container, false);

        db = new SavePhotoDBOpenHelper(getActivity());

        GridView gridView = (GridView)gridview_layout.findViewById(R.id.typeGridview);
        gridView.setAdapter(new TypeFragmentAdapter(getContext()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(getActivity(), HollyViewPagerActivity.class);
                // intent.putExtra(key, titleName);
                //startActivity(intent);
                Toast.makeText(getActivity(), "You Clicked at " + position, Toast.LENGTH_SHORT).show();

            }
        });


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
                        .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                Toast.makeText(getActivity(),"Type Name to be edited",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Delete",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                Toast.makeText(getActivity(),"Type will be deleted",Toast.LENGTH_SHORT).show();
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

        final FloatingActionMenu menu1 = (FloatingActionMenu) gridview_layout.findViewById(R.id.menu1);
        fabCameraPhotoButton= (FloatingActionButton) gridview_layout.findViewById(R.id.cameraPhotoButton);
        fabGalleryPhotoButton = (FloatingActionButton) gridview_layout.findViewById(R.id.galleryPhotoButton);
        fabAddTypeButton = (FloatingActionButton) gridview_layout.findViewById(R.id.addTypeButton);

        fabCameraPhotoButton.setOnClickListener(clickListener);
        fabGalleryPhotoButton.setOnClickListener(clickListener);
        fabAddTypeButton.setOnClickListener(clickListener);

        menu1.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menu1.isOpened()) {
                    Toast.makeText(getActivity(), menu1.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
                }

                menu1.toggle(true);
            }
        });
        menus.add(menu1);

        menu1.hideMenuButton(false);

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

        menu1.setClosedOnTouchOutside(true);

        db.close();
        return gridview_layout;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";

            switch (v.getId()) {
                case R.id.cameraPhotoButton:
                    Intent intent=new Intent();
                    intent.setClass(getActivity(),CameraPhoto.class);
                    startActivity(intent);
                    break;
                case R.id.galleryPhotoButton:
                    Intent intent2=new Intent();
                    intent2.setClass(getActivity(),GalleryPhoto.class);
                    startActivity(intent2);
                    break;
                case R.id.addTypeButton:
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
                                        db.addCollectionListItem(new CollectionsList(typeItemName,R.drawable.nature1));

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

                    break;

            }

            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        }
    };


}