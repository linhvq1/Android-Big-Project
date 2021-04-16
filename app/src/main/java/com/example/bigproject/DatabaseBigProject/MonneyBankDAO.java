package com.example.bigproject.DatabaseBigProject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bigproject.Table_Bank;

import java.util.List;

@Dao
public interface MonneyBankDAO {
    @Insert
    void InsertBank(Table_Bank bank);

    @Query("select * from monney_bank")
    List<Table_Bank> getListBank();

    @Delete
    void deleteCurrentBank(Table_Bank bank);

    @Update
    void updateCurrentBank(Table_Bank bank);

    @Query("select * from monney_bank where id_u = :idUser ")
    List<Table_Bank> checkBankIdUser(String idUser);

    @Query("select * from monney_bank where date_start = :date_start and date_end = :date_end and id_u = :idU")
    List<Table_Bank> checkBankDate(String date_start,String date_end,int idU);

    @Query("update monney_bank set current_monney = :monney,monney_start = :monney, current_state = 1 where date_start = :date_start and date_end = :date_end and id_u = :idU")
    void updateMonney(double monney, String date_start, String date_end,int idU);

    @Query("update monney_bank set current_state = 0 where id_u = :idU")
    void updateCurrentState(int idU);

    @Query("select * from monney_bank where current_state = 1 and id_u = :idU")
    List<Table_Bank> getIDCurrentState(int idU);

    @Query("select * from monney_bank where id_u_bank = :id_bank")
    List<Table_Bank> getValuesfromIDBank(int id_bank);
}
