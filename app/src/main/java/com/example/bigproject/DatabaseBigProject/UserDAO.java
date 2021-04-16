package com.example.bigproject.DatabaseBigProject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bigproject.Table_User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void InsertUser(Table_User user);

    @Query("select * from user")
    List<Table_User> getListUser();

    @Delete
    void deleteCat(Table_User user);

    @Update
    void updateUser(Table_User user);

    @Query("select * from user where email = :emailUser ")
    List<Table_User> checkEmailUser(String emailUser);

    @Query("select id_u from user where email = :emailUser ")
    List<Table_User> getIdUserFromEmail(String emailUser);

    @Query("select * from user where email =:email and password = :password ")
    List<Table_User> checkLoginUser(String email,String password);

    @Query("select * from user where id_u = :idU ")
    List<Table_User> getUserFromId(String idU);

}
