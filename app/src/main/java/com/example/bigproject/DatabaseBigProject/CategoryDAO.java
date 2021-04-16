package com.example.bigproject.DatabaseBigProject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bigproject.Table_Category;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert
    void InsertCat(Table_Category cat);

    @Query("select * from category")
    List<Table_Category> getListCategory();

    @Delete
    void deleteCat(Table_Category cat);

    @Update
    void updateCat(Table_Category cat);

    @Query("select * from category where name_cat = :nameCat ")
    List<Table_Category> checkCategory(String nameCat);

    @Query("select * from category where type_cat ='Expenses' ")
    List<Table_Category> getListExCategory();

    @Query("select * from category where type_cat ='Income' ")
    List<Table_Category> getListInCategory();

    @Query("select * from category where id_cat = :idCat")
    List<Table_Category> getListCatFromId(int idCat);
}
