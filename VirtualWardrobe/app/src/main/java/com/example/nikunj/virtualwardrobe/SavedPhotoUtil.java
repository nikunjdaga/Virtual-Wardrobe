package com.example.nikunj.virtualwardrobe;

/**
 * Created by root on 30/9/15.
 */


public class SavedPhotoUtil {

    private Integer id;
    private String description;
    private Integer isFavourite;
    private String created_at;
    private String colorSelected;
    private String colorMainBracket;
    private String brand;
    private String locationPath;

    // constructors
    public SavedPhotoUtil() {
    }

    public SavedPhotoUtil(String description, Integer isFavourite) {
        this.description = description;
        this.isFavourite = isFavourite;
    }

    public SavedPhotoUtil(Integer id, String description, Integer isFavourite) {
        this.id = id;
        this.description = description;
        this.isFavourite = isFavourite;
    }

    public SavedPhotoUtil(Integer id, String description, Integer isFavourite,String locationPath, String colorSelected, String colorMainBracket, String brand) {
        this.id = id;
        this.description = description;
        this.isFavourite = isFavourite;
        this.colorSelected = colorSelected;
        this.colorMainBracket = colorMainBracket;
        this.brand = brand;
        this.locationPath = locationPath;
    }


    // setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColorSelected(String colorSelected) {
        this.colorSelected = colorSelected;
    }

    public void setColorMainBracket(String colorMainBracket) {
        this.colorMainBracket = colorMainBracket;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setStatus(Integer isFavourite) {
        this.isFavourite = isFavourite;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String setLocationpath(){
        return this.locationPath;
    }

    // getters
    public long getId() {
        return this.id;
    }

    public void getDescription(String description) {
        this.description = description;
    }

    public void getColorSelected(String colorSelected) {
        this.colorSelected = colorSelected;
    }

    public void getColorMainBracket(String colorMainBracket) {
        this.colorMainBracket = colorMainBracket;
    }

    public void getBrand(String brand) {
        this.brand = brand;
    }

    public Integer getIsFavourite() {
        return this.isFavourite;
    }
    
    public String getLocationpath(String locationPath){
        return this.locationPath = locationPath;
    }
}