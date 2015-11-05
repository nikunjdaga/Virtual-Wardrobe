package com.example.nikunj.virtualwardrobe;




import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.Menu;
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
public class ColorFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View gridview_layout = inflater.inflate(R.layout.color_fragment_gridview, container, false);


        GridView gridView = (GridView)gridview_layout.findViewById(R.id.colorGridview);
        gridView.setAdapter(new ColorFragmentAdapter(getContext()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Intent intent = new Intent(getActivity(), HollyViewPagerActivity.class);
               // intent.putExtra(key, titleName);
               // startActivity(intent);
                Toast.makeText(getActivity(), "You Clicked at " + position, Toast.LENGTH_SHORT).show();

            }
        });
        return gridview_layout;
    }


}