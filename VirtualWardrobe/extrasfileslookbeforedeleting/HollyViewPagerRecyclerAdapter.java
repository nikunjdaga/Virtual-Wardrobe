package com.example.nikunj.virtualwardrobe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by florentchampigny on 07/08/15.
 */
public class HollyViewPagerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_CELL = 1;

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0: return TYPE_HEADER;
            default: return TYPE_CELL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view;
        switch (type){
            case TYPE_HEADER:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hvp_header_placeholder,viewGroup,false);
                break;
            default:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_card,viewGroup,false);
                break;
        }
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
