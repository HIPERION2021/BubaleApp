package com.example.bubaleapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("orders")

public class orders extends ParseObject{
    public static final String KEY_ORDER = "order";
    public static final String KEY_USER = "user";
    public static final String KEY_ITEM = "item";
    public static final String KEY_PRICE = "price";
    public static final String KEY_CRATED_KEY = "createdAt";
    public static final String KEY_STATUS = "state";

    public String getOrder(){
        return getString(KEY_ORDER);
    }
    public void setOrder(String order){
        put(KEY_ORDER, order);
    }

    public String getUser(){
        return getString(KEY_USER);
    }
    public void setUser(String user){
        put(KEY_USER, user);
    }

    public String getOrderItem(){
        return getString(KEY_ITEM);
    }
    public void setOrderItem(String item){ put(KEY_ITEM, item);}

    public String getPrice(){  return getString(KEY_PRICE);}
    public void setPrice(String price){ put(KEY_PRICE, price);}

    public Number getStatus(){ return getNumber(KEY_STATUS);}
    public void setStatus(Number status){ put(KEY_STATUS, status);}

}

