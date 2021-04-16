package com.example.bigproject.sharePreferenceLogin;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginSharePreference {
    private static final String LOGIN_SHARE_REFERENCE = "LOGIN_SUCCESS";
    private Context context;

    public LoginSharePreference(Context context) {
        this.context = context;
    }

    public void putBooleanLoginVal (String key,boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_SHARE_REFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public boolean getBooleanLogin(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_SHARE_REFERENCE,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }
    // string iduser
    public void putStringLoginVal (String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_SHARE_REFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public String getStringLogin(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_SHARE_REFERENCE,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
    // int idbank
    public void putInIdBank(String key,int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_SHARE_REFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }
    public int getIntIdBank(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_SHARE_REFERENCE,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,0);
    }
    // string catgegory
    public void putStringCategory (String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_SHARE_REFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public String getStringCategory(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_SHARE_REFERENCE,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
    // internet
    public void putIntInternetState(String key,int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_SHARE_REFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }
    public int getIntInternetState(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_SHARE_REFERENCE,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,0);
    }
    // insert zo category Table
    public void putIntStartApptate(String key,int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_SHARE_REFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }
    public int getIntStartApptate(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_SHARE_REFERENCE,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,0);
    }
}
