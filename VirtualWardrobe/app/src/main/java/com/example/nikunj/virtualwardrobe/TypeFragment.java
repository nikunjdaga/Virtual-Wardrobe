package com.example.nikunj.virtualwardrobe;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
import android.view.View;
        import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

    private List<FloatingActionMenu> menus = new ArrayList<>();

    private Handler mUiHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View gridview_layout = inflater.inflate(R.layout.type_fragment_gridview, container, false);

        GridView gridView = (GridView)gridview_layout.findViewById(R.id.gridview1);
        gridView.setAdapter(new MyAdapter(getContext()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HollyViewPagerActivity.class);
                // intent.putExtra(key, titleName);
                startActivity(intent);
                Toast.makeText(getActivity(), "You Clicked at " + position, Toast.LENGTH_SHORT).show();

            }
        });

        final FloatingActionMenu menu1 = (FloatingActionMenu) gridview_layout.findViewById(R.id.menu1);
        fab1 = (FloatingActionButton) gridview_layout.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) gridview_layout.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) gridview_layout.findViewById(R.id.fab3);

        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);

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


        return gridview_layout;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";

            switch (v.getId()) {
                case R.id.fab1:
                    Intent intent=new Intent();
                    intent.setClass(getActivity(),CameraPhoto.class);
                    startActivity(intent);
                    break;
                case R.id.fab2:
                    Intent intent2=new Intent();
                    intent2.setClass(getActivity(),GalleryPhoto.class);
                    startActivity(intent2);
                    break;
                case R.id.fab3:
                    text = fab3.getLabelText();
                    break;

            }

            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        }
    };

    private class MyAdapter extends BaseAdapter
    {
        private List<Item> items = new ArrayList<Item>();
        private LayoutInflater inflater;

        public MyAdapter(Context context)
        {
            inflater = LayoutInflater.from(context);

            items.add(new Item("Image 1", R.drawable.nature1));
            items.add(new Item("Image 2", R.drawable.nature2));
            items.add(new Item("Image 3", R.drawable.tree1));
            items.add(new Item("Image 4", R.drawable.nature3));
            items.add(new Item("Image 5", R.drawable.tree2));
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
            return items.get(i).drawableId;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            View v = view;
            ImageView picture;
            TextView name;

            if(v == null)
            {
                v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
                v.setTag(R.id.text, v.findViewById(R.id.text));
            }

            picture = (ImageView)v.getTag(R.id.picture);
            name = (TextView)v.getTag(R.id.text);

            Item item = (Item)getItem(i);

            picture.setImageResource(item.drawableId);
            name.setText(item.name);

            return v;
        }

        private class Item
        {
            final String name;
            final int drawableId;

            Item(String name, int drawableId)
            {
                this.name = name;
                this.drawableId = drawableId;
            }
        }
    }
}