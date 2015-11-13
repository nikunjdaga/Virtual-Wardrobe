package com.example.nikunj.virtualwardrobe;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nikunj on 13/11/15.
 */

class ColorPhotosViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    Integer mPositionItemId;

    private ImageView photoViewPagerItem;
    private TextView descriptionOfClothes,isFavInteger,
            createdAtText,exactColorSelectedText,typeNameText,
            collectionNameText,colorMainBracketNameText;




    private ArrayList<String> mAllDescriptionOfClothes = new ArrayList<>();
    private ArrayList<String> mAllExactColorSelected = new ArrayList<>();
    private ArrayList<String> mAllTypeName= new ArrayList<>();
    private ArrayList<String> mAllCollectionName = new ArrayList<>();
    private ArrayList<String> mAllColorMainBracketName= new ArrayList<>();
    private ArrayList<String> mAllLocationUris =  new ArrayList<>();

    private ArrayList<Integer> mAllIsFavInteger = new ArrayList<>();
    private ArrayList<Long> mAllCreatedAtText = new ArrayList<>();
    private ArrayList<Integer> mAllIDValues = new ArrayList<>();

    // SavePhotoDBOpenHelper db;
//    private List<SavedPhotoUtil> items = new ArrayList<>();

    public ColorPhotosViewPagerAdapter(Context context, Integer positionItemId,
                                      ArrayList<String> AllDescriptionOfClothes, ArrayList<String> AllExactColorSelected,
                                      ArrayList<String> AllTypeName, ArrayList<String> AllCollectionName,
                                      ArrayList<String> AllColorMainBracketName, ArrayList<String> AllLocationUris,
                                      ArrayList<Integer> AllIsFavInteger, ArrayList<Long> AllCreatedAtText, ArrayList<Integer> AllIDValues)  {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPositionItemId = positionItemId;
//        db = new SavePhotoDBOpenHelper(mContext);
//        items = db.getAllPhotoItem(mContext);
        mAllDescriptionOfClothes = AllDescriptionOfClothes;
        mAllExactColorSelected = AllExactColorSelected;
        mAllTypeName= AllTypeName;
        mAllCollectionName = AllCollectionName;
        mAllColorMainBracketName= AllColorMainBracketName;
        mAllLocationUris =  AllLocationUris;

        mAllIsFavInteger = AllIsFavInteger;
        mAllCreatedAtText = AllCreatedAtText;
        mAllIDValues = AllIDValues;



    }

    @Override
    public int getCount() {
        return mAllIDValues.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.type_photos_view_pager, container, false);



        photoViewPagerItem = (ImageView) itemView.findViewById(R.id.photoViewPagerItem);


//        photoViewPagerItem.setImageURI(Uri.parse("file://" + mAllLocationUris.get(position)));
        File file = new File(mAllLocationUris.get(position));

        Glide.clear(photoViewPagerItem);
        Glide.with(mContext)
                .load(file)
                .asBitmap()
                .into(photoViewPagerItem);

        Log.e("photoLocation", mAllLocationUris.get(position));

        descriptionOfClothes = (TextView)itemView.findViewById(R.id.descriptionOfClothes);
        descriptionOfClothes.setText(mAllDescriptionOfClothes.get(position));
        isFavInteger = (TextView)itemView.findViewById(R.id.isFavInteger);
        isFavInteger.setText(String.valueOf(mAllIsFavInteger.get(position)));
        createdAtText = (TextView)itemView.findViewById(R.id.createdAtText);
        createdAtText.setText(String.valueOf(mAllCreatedAtText.get(position)));
        Log.e("photoCreatedDate",mAllCreatedAtText.get(position)+"");
        exactColorSelectedText = (TextView)itemView.findViewById(R.id.exactColorSelectedText);
        exactColorSelectedText.setText(mAllExactColorSelected.get(position));
        typeNameText = (TextView)itemView.findViewById(R.id.typeNameText);
        typeNameText.setText(mAllTypeName.get(position));
        collectionNameText = (TextView)itemView.findViewById(R.id.collectionNameText);
        collectionNameText.setText(mAllCollectionName.get(position));
        colorMainBracketNameText = (TextView)itemView.findViewById(R.id.colorMainBracketNameText);
        colorMainBracketNameText.setText(mAllColorMainBracketName.get(position));
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


}