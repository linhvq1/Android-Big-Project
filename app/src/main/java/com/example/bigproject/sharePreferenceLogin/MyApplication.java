package com.example.bigproject.sharePreferenceLogin;

import android.app.Application;

import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.Table_Category;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        dataLocalLoginManager.initLoginData(getApplicationContext());
        //dataLocalLoginManager.setLoginState(false);
        //insertCategory();
    }
}
