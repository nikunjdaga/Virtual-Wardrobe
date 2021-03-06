package com.example.nikunj.virtualwardrobe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.hollyviewpager.HollyViewPagerBus;

/**
 * Created by florentchampigny on 07/08/15.
 */
public class HollyViewPagerRecyclerViewFragment extends Fragment {

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View recyclerViewFragment=  inflater.inflate(R.layout.fragment_recyclerview, container, false);
         recyclerView= (RecyclerView)recyclerViewFragment.findViewById(R.id.recyclerView);
        return recyclerViewFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new HollyViewPagerRecyclerAdapter());

        HollyViewPagerBus.registerRecyclerView(getActivity(), recyclerView);
    }
}
