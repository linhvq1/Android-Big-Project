package com.example.bigproject.sharePreferenceLogin;

import android.content.Context;

import com.example.bigproject.Table_Category;
import com.google.gson.Gson;

public class dataLocalLoginManager {
    private static final String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";
    private static final String ID_USER_LOGIN = "ID_USER_LOGIN";
    private static final String ID_BANK = "ID_BANK";
    private static final String PRE_OBJECT_CAT = "PRE_OBJECT_CAT";
    private static final String ID_INTERNET_STATE = "ID_INTERNET_STATE";
    private static final String INSERT_CATEGORY_STATE = "INSERT_CATEGORY_STATE";
    private static final String INSTALL_SUCCESSFUL = "INSTALL_SUCCESSFUL";
    private static dataLocalLoginManager instance;
    private LoginSharePreference loginSharePreference;
    public static void initLoginData(Context context){
        instance = new dataLocalLoginManager();
        instance.loginSharePreference = new LoginSharePreference(context);
    }
    public static dataLocalLoginManager getInstance(){
        if (instance == null){
            instance = new dataLocalLoginManager();
        }
        return instance;
    }
    public static void setLoginState(boolean isLogin){
        dataLocalLoginManager.getInstance().loginSharePreference.putBooleanLoginVal(LOGIN_SUCCESSFUL,isLogin);
    }

    public static boolean getLoginSuccess(){
        return dataLocalLoginManager.getInstance().loginSharePreference.getBooleanLogin(LOGIN_SUCCESSFUL);
    }

    //string iduser
    public static void setIdLogin(String idLogin){
        dataLocalLoginManager.getInstance().loginSharePreference.putStringLoginVal(ID_USER_LOGIN,idLogin);
    }

    public static String getIdLogin(){
        return dataLocalLoginManager.getInstance().loginSharePreference.getStringLogin(ID_USER_LOGIN);
    }
    // id bank
    public static void setIdBank(int idbank){
        dataLocalLoginManager.getInstance().loginSharePreference.putInIdBank(ID_BANK,idbank);
    }
    public static int getIdBank(){
        return dataLocalLoginManager.getInstance().loginSharePreference.getIntIdBank(ID_BANK);
    }

    // values category
    public static void setCategory(Table_Category cat){
        Gson gson = new Gson();
        String strJsonCat = gson.toJson(cat);
        dataLocalLoginManager.getInstance().loginSharePreference.putStringCategory(PRE_OBJECT_CAT,strJsonCat);
    }
    public static Table_Category getCatgory(){
        String strJson = dataLocalLoginManager.getInstance().loginSharePreference.getStringCategory(PRE_OBJECT_CAT);
        Gson gson = new Gson();
        Table_Category cat = gson.fromJson(strJson,Table_Category.class);
        return cat;
    }
    // check internet
    public static void setInternetState(int state){
        dataLocalLoginManager.getInstance().loginSharePreference.putIntInternetState(ID_INTERNET_STATE,state);
    }
    public static int getInternetState(){
        return dataLocalLoginManager.getInstance().loginSharePreference.getIntInternetState(ID_INTERNET_STATE);
    }
    // insert Category Table
    public static void setInsertCategory(int state){
        dataLocalLoginManager.getInstance().loginSharePreference.putIntInternetState(INSERT_CATEGORY_STATE,state);
    }
    public static int getInsertCategory(){
        return dataLocalLoginManager.getInstance().loginSharePreference.getIntInternetState(INSERT_CATEGORY_STATE);
    }
    // app is install
    public static void setInstallState(boolean isLogin){
        dataLocalLoginManager.getInstance().loginSharePreference.putBooleanLoginVal(INSTALL_SUCCESSFUL,isLogin);
    }

    public static boolean getInstallState(){
        return dataLocalLoginManager.getInstance().loginSharePreference.getBooleanLogin(INSTALL_SUCCESSFUL);
    }
}
