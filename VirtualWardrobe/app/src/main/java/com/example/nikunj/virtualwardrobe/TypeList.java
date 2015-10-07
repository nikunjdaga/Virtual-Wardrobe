package com.example.nikunj.virtualwardrobe;

/**
 * Created by nikunj on 30/9/15.
 */


public class TypeList {

    

        private Integer id;
        private String clothes_type_name;
        private Integer drawableId;

        // constructors
        public TypeList() {

        }

        public TypeList(String clothes_type_name,Integer drawableId) {
            this.clothes_type_name = clothes_type_name;
            this.drawableId = drawableId;
        }

        public TypeList(Integer id, String clothes_type_name,Integer drawableId) {
            this.id = id;
            this.clothes_type_name = clothes_type_name;
            this.drawableId = drawableId;
        }

        // setter
        public void setTypeListItemId(Integer id) {
            this.id = id;
        }

        public void setTypeName(String clothes_type_name) {
            this.clothes_type_name = clothes_type_name;
        }

        public void setTypeListItemDrawableId(Integer drawableId){
             this.drawableId = drawableId;
            }

        // getter
        public Integer getTypeListItemId() {
            return this.id;
        }

        public String getTypeName() {
            return this.clothes_type_name;
        }

        public Integer getTypeListItemDrawableId(){
            return this.drawableId;
        }
    
}