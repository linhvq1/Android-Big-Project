package com.example.bigproject.DatabaseBigProject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bigproject.Table_Bank;
import com.example.bigproject.Table_Transaction_Daily;
import com.example.bigproject.chartExpensesClass;
import com.example.bigproject.user_Item_data;

import java.util.List;

@Dao
public interface TransactionDailyDAO {

    @Insert
    void InsertTransactionDaily(Table_Transaction_Daily tran);

    @Update
    void UpdateTransactionDaily(Table_Transaction_Daily tran);

    @Delete
    void DeleteTransactionDaily(Table_Transaction_Daily tran);

    @Query("select * from transactionD")
    List<Table_Transaction_Daily> getTransaction();

    @Query("select * from transactionD where id_tran = :idTran")
    List<Table_Transaction_Daily> getTransactionFromIdTran(int idTran);

    @Query("select  date_tran_daily as date_time,(select SUM(monney_tran_daily)  from transactionD,category where  category.id_cat = transactionD.id_cat and category.type_cat = 'Expenses' and id_u =:idU) as expense,\n" +
            "(select SUM(monney_tran_daily)  from transactionD,category where  category.id_cat = transactionD.id_cat and category.type_cat = 'Income' and id_u =:idU) as income,\n" +
            "name_cat as name_categories,detail_tran as discribe,monney_tran_daily as money_used,img_cat as resource_img,type_cat as typeCat,id_tran as idTransaction,time_tran_daily as timeTran from transactionD,category  where  category.id_cat = transactionD.id_cat and id_u =:idU group by id_tran")
    List<user_Item_data> getListData(int idU);

    @Query("select sum(monney_tran_daily) from transactionD,category where transactionD.id_cat =category.id_cat and type_cat = 'Expenses' and id_u = :idU and date_tran_daily = :date")
    List<Double> getSumExpenses(int idU, String date);

    @Query("select sum(monney_tran_daily) from transactionD,category where transactionD.id_cat =category.id_cat and type_cat = 'Income' and id_u = :idU and date_tran_daily = :date")
    List<Double> getSumIncome(int idU, String date);

    @Query("select transactionD.id_u_bank,transactionD.id_u,monney_start,current_monney,date_start,date_end,current_state from transactionD,monney_bank where transactionD.id_u_bank = monney_bank.id_u_bank " +
            "and transactionD.id_u_bank = :idUBank and transactionD.id_u = :idU")
    List<Table_Bank> getDateTransaction(int idUBank, int idU);

    @Query("select SUM(monney_tran_daily) from transactionD,category where transactionD.id_cat = category.id_cat and type_cat ='Expenses' and id_u = :idU and date_tran_daily between :dateStart and :dateEnd")
    List<Double> getSumExPerMonth(String dateStart,String dateEnd,int idU);

    @Query("select SUM(monney_tran_daily) from transactionD,category where transactionD.id_cat = category.id_cat and type_cat ='Income' and id_u = :idU and date_tran_daily between :dateStart and :dateEnd")
    List<Double> getSumInPerMonth(String dateStart,String dateEnd,int idU);

    @Query("select  date_tran_daily as date_time,(select SUM(monney_tran_daily)  from transactionD,category where  category.id_cat = transactionD.id_cat and category.type_cat = 'Expenses' and id_u =:idU) as expense,\n" +
            "(select SUM(monney_tran_daily)  from transactionD,category where  category.id_cat = transactionD.id_cat and category.type_cat = 'Income' and id_u =:idU) as income,\n" +
            "name_cat as name_categories,detail_tran as discribe,monney_tran_daily as money_used,img_cat as resource_img,type_cat as typeCat,id_tran as idTransaction,time_tran_daily as timeTran from transactionD,category  where  category.id_cat = transactionD.id_cat and id_u =:idU and id_tran =:idTran group by id_tran")
    List<user_Item_data> ReloadDataDetail(int idU,int idTran);
    //thong ke
    //EXpenses
    @Query("select SUM(monney_tran_daily) from transactionD,category where TRANSACTIOND.id_cat = CATEGORY.id_cat and id_u = :idU and type_cat = 'Expenses' and date_tran_daily between :dateStart and :dateEnd")
    List<Double> getListMoneyExPerMonth(int idU,String dateStart,String dateEnd);

    @Query("select TRANSACTIOND. id_cat as idCatChart,name_cat as nameCatChart,count(TRANSACTIOND.id_cat) as countCatChart,SUM(monney_tran_daily) as monneySumCat,round((SUM(monney_tran_daily)/(select SUM(monney_tran_daily) from transactionD,category where TRANSACTIOND.id_cat = CATEGORY.id_cat and id_u = :idU and type_cat = 'Expenses' and date_tran_daily between :dateStart and :dateEnd)),2)as percentCat,(select SUM(monney_tran_daily) from transactionD,category where TRANSACTIOND.id_cat = CATEGORY.id_cat and id_u = :idU and type_cat = 'Expenses' and date_tran_daily between :dateStart and :dateEnd) as sumcatPerMonth,img_cat as imgCatChart,type_cat astypeCatChart from transactionD,category where transactionD.id_cat = category.id_cat and type_cat = 'Expenses' and id_u = :idU and date_tran_daily between :dateStart and :dateEnd group by TRANSACTIOND.id_cat \n")
    List<chartExpensesClass> ExChart(int idU,String dateStart,String dateEnd);
    //income
    @Query("select SUM(monney_tran_daily) from transactionD,category where TRANSACTIOND.id_cat = CATEGORY.id_cat and id_u = :idU and type_cat = 'Income' and date_tran_daily between :dateStart and :dateEnd")
    List<Double> getListMoneyInPerMonth(int idU,String dateStart,String dateEnd);

    @Query("select TRANSACTIOND. id_cat as idCatChart,name_cat as nameCatChart,count(TRANSACTIOND.id_cat) as countCatChart,SUM(monney_tran_daily) as monneySumCat,round((SUM(monney_tran_daily)/(select SUM(monney_tran_daily) from transactionD,category where TRANSACTIOND.id_cat = CATEGORY.id_cat and id_u = :idU and type_cat = 'Income' and date_tran_daily between :dateStart and :dateEnd)),2)as percentCat,(select SUM(monney_tran_daily) from transactionD,category where TRANSACTIOND.id_cat = CATEGORY.id_cat and id_u = :idU and type_cat = 'Income' and date_tran_daily between :dateStart and :dateEnd) as sumcatPerMonth,img_cat as imgCatChart,type_cat astypeCatChart from transactionD,category where transactionD.id_cat = category.id_cat and type_cat = 'Income' and id_u = :idU and date_tran_daily between :dateStart and :dateEnd group by TRANSACTIOND.id_cat \n")
    List<chartExpensesClass> InChart(int idU,String dateStart,String dateEnd);

}
