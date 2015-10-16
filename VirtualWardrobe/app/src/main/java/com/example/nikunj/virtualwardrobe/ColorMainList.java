package com.example.nikunj.virtualwardrobe;

/**
 * Created by nikunj on 12/10/15.
 */
public class ColorMainList {

    private Integer id;
    private String colorMainName;
    private Integer drawableId;
    private Integer red;
    private Integer green;
    private Integer blue;
    private Float l_value;
    private Float a_value;
    private Float b_value;

    // constructors
    public ColorMainList() {

    }

    public ColorMainList(String colorMainName, Integer drawableId,
                         Integer red,Integer green,
                         Integer blue,Float l_value,
                         Float a_value,Float b_value) {
        this.colorMainName = colorMainName;
        this.drawableId = drawableId;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.l_value = l_value;
        this.a_value = a_value;
        this.b_value = b_value;
    }

    public ColorMainList(Integer id, String colorMainName, Integer drawableId,
                         Integer red,Integer green,
                         Integer blue,Float l_value,
                         Float a_value,Float b_value) {
        this.id = id;
        this.colorMainName = colorMainName;
        this.drawableId = drawableId;
        this.colorMainName = colorMainName;
        this.drawableId = drawableId;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.l_value = l_value;
        this.a_value = a_value;
        this.b_value = b_value;
    }

    // setter
    public void setColorMainListItemId(Integer id) {
        this.id = id;
    }

    public void setColorMainName(String colorMainName) {
        this.colorMainName = colorMainName;
    }

    public void setColorMainListItemDrawableId(Integer drawableId){
        this.drawableId = drawableId;
    }

    public void setColorMainRed(Integer red) {
        this.red = red;
    }

    public void setColorMainGreen(Integer green) {
        this.green = green;
    }

    public void setColorMainBlue(Integer blue) {
        this.blue = blue;
    }

    public void setColorMainLvalue(Float l_value) {
        this.l_value = l_value;
    }

    public void setColorMainAvalue(Float a_value) {
        this.a_value = a_value;
    }

    public void setColorMainBvalue(Float b_value) {
        this.b_value = b_value;
    }

    // getter
    public Integer getColorMainListItemId() {
        return this.id;
    }

    public String getColorMainName() {
        return this.colorMainName;
    }

    public Integer getColorMainListItemDrawableId(){
        return this.drawableId;
    }

    public Integer getColorMainRed() {
        return this.red;
    }

    public Integer getColorMainGreen() {
        return this.green;
    }

    public Integer getColorMainBlue() {
        return this.blue;
    }

    public Float getColorMainLvalue() {
        return this.l_value;
    }

    public Float getColorMainAvalue() {
        return this.a_value;
    }

    public Float getColorMainBvalue() {
        return this.b_value;
    }

}
