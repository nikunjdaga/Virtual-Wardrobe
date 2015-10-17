package com.example.nikunj.virtualwardrobe;

/**
 * Created by nikunj on 30/9/15.
 */


public class SavedPhotoUtil {

    private Integer id;
    private String description;
    private Integer isFavourite;
    private Float created_at;
    private String locationPath;
    private String colorSelected;
    private Integer typeName;
    private Integer collectionName;
    private Integer colorMainBracket;
   // private String brand;



    // constructors
    public SavedPhotoUtil() {
    }

    //constructor without collections name
    public SavedPhotoUtil(String description,Integer isFavourite,
                          Float created_at,String locationPath,
                          String colorSelected, Integer typeName,
                          Integer colorMainBracket) {
        this.description = description;
        this.isFavourite = isFavourite;
        this.created_at = created_at;
        this.colorSelected = colorSelected;
        this.locationPath = locationPath;
        this.typeName = typeName;
        this.colorMainBracket = colorMainBracket;
        // this.brand = brand;
    }



    public SavedPhotoUtil( String description,Integer isFavourite,
                           Float created_at,String locationPath,
                           String colorSelected, Integer typeName,
                           Integer collectionName, Integer colorMainBracket ) {

        this.description = description;
        this.isFavourite = isFavourite;
        this.created_at = created_at;
        this.colorSelected = colorSelected;
        this.locationPath = locationPath;
        this.typeName = typeName;
        this.collectionName = collectionName;
        this.colorMainBracket = colorMainBracket;
        // this.brand = brand;
    }

    public SavedPhotoUtil(Integer id, String description,
                          Integer isFavourite, Float created_at,
                          String locationPath, String colorSelected,
                          Integer typeName, Integer collectionName,
                          Integer colorMainBracket ) {
        this.id = id;
        this.description = description;
        this.isFavourite = isFavourite;
        this.created_at = created_at;
        this.colorSelected = colorSelected;
        this.locationPath = locationPath;
        this.typeName = typeName;
        this.collectionName = collectionName;
        this.colorMainBracket = colorMainBracket;
        // this.brand = brand;
    }


    // setters
    public void setPhotoId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColorSelected(String colorSelected) {
        this.colorSelected = colorSelected;
    }

    public void setColorMainBracketIdFromTable(Integer colorMainBracket) {
        this.colorMainBracket = colorMainBracket;
    }

//    public void setBrand(String brand) {
//        this.brand = brand;
//    }

    public void setIsFavourite(Integer isFavourite) {
        this.isFavourite = isFavourite;
    }

    public void setCreatedAt(Float created_at) {
        this.created_at = created_at;
    }

    public void setLocationPath(String locationPath){
        this.locationPath = locationPath;
    }

    public void setTypeIdFromTable(Integer typeName){
        this.typeName = typeName;
    }

    public void setCollectionIdFromTable(Integer collectionName){
        this.collectionName = collectionName;
    }


    // getters
    public Integer getPhotoId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public String getColorSelected() {
        return this.colorSelected;
    }

    public Integer getColorMainBracketIdFromTable() {
        return this.colorMainBracket;
    }

//    public void getBrand(String brand) {
//        this.brand = brand;
//    }

    public Integer getIsFavourite() {
        return this.isFavourite;
    }
    
    public String getLocationPath(){
        return this.locationPath;
    }

    public Float getCreatedAt(){
        return this.created_at;
    }

    public Integer getTypeIdFromTable(){
        return this.typeName;
    }

    public Integer getCollectionIdFromTable(){
        return this.collectionName;
    }
}