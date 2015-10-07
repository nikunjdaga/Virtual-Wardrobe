package com.example.nikunj.virtualwardrobe;

/**
 * Created by nikunj on 30/9/15.
 */


public class CollectionsList {



    private Integer id;
    private String collections_name;
    private Integer drawableId;

    // constructors
    public CollectionsList() {

    }

    public CollectionsList(String collections_name,Integer drawableId) {
        this.collections_name = collections_name;
        this.drawableId = drawableId;
    }

    public CollectionsList(Integer id, String collections_name,Integer drawableId) {
        this.id = id;
        this.collections_name = collections_name;
        this.drawableId = drawableId;
    }

    // setter
    public void setCollectionListItemId(Integer id) {
        this.id = id;
    }

    public void setCollectionsName(String collections_name) {
        this.collections_name = collections_name;
    }

    public void setCollectionListItemDrawableId(Integer drawableId){
        this.drawableId = drawableId;
    }

    // getter
    public Integer getCollectionListItemId() {
        return this.id;
    }

    public String getCollectionsName() {
        return this.collections_name;
    }

    public Integer getCollectionListItemDrawableId(){
        return this.drawableId;
    }

}