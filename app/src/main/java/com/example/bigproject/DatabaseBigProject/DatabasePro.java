package com.example.bigproject.DatabaseBigProject;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.bigproject.Table_Bank;
import com.example.bigproject.Table_Category;
import com.example.bigproject.Table_Transaction_Daily;
import com.example.bigproject.Table_User;

@Database(entities = {Table_Category.class, Table_User.class, Table_Bank.class, Table_Transaction_Daily.class},version = 5,exportSchema = false)
    public abstract class DatabasePro extends RoomDatabase {

    public static final String DATABASE_NAME ="bigproject.db";
    public static DatabasePro instance;
    //.fallbackToDestructiveMigration()
    public static synchronized DatabasePro getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),DatabasePro.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract CategoryDAO catDAO();
    public abstract UserDAO userDAO();
    public abstract MonneyBankDAO bankDAO();
    public abstract TransactionDailyDAO tranDAO();
}
