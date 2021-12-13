package com.example.bubaleapp;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("item")

    public class item extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_CODE = "objectId";
    public static final String KEY_DES2 = "about";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PRICE = "price";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CRATED_KEY = "createdAt";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public String getCode(){
        return getString(KEY_CODE);
    }
    public void setCode(String code){
        put(KEY_CODE, code);
    }

    public String getDes2(){
        return getString(KEY_DES2);
    }
    public void setDes2(String des2){
        put(KEY_DES2, des2);
    }

    public String getTitle(){
        return getString(KEY_TITLE);
    }
    public void setTitle(String title){ put(KEY_TITLE, title);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public String getPrice(){  return getString(KEY_PRICE);}
    public void setPrice(String price){ put(KEY_PRICE, price);}


}
