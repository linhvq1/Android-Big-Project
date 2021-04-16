package com.example.bigproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "transactionD",foreignKeys = {
        @ForeignKey(onDelete = CASCADE,entity = Table_User.class,parentColumns = "id_u",childColumns = "id_u"),
        @ForeignKey(onDelete = CASCADE,entity = Table_Category.class,parentColumns = "id_cat",childColumns = "id_cat"),
        @ForeignKey(onDelete = CASCADE,entity = Table_Bank.class,parentColumns = "id_u_bank",childColumns = "id_u_bank")
},indices = {@Index(value = "id_u"),@Index(value = "id_cat"),@Index(value = "id_u_bank")})
public class Table_Transaction_Daily implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id_tran;

    private int id_u;
    private int id_cat;
    private int id_u_bank;
    private String date_tran_daily;
    private String time_tran_daily;
    @ColumnInfo(name = "monney_tran_daily",defaultValue = "0")
    private double monney_tran_daily;
    private String detail_tran;


    public Table_Transaction_Daily(int id_u, int id_cat, int id_u_bank, String date_tran_daily, String time_tran_daily, double monney_tran_daily, String detail_tran) {
        this.id_u = id_u;
        this.id_cat = id_cat;
        this.id_u_bank = id_u_bank;
        this.date_tran_daily = date_tran_daily;
        this.time_tran_daily = time_tran_daily;
        this.monney_tran_daily = monney_tran_daily;
        this.detail_tran = detail_tran;
    }

    protected Table_Transaction_Daily(Parcel in) {
        id_tran = in.readInt();
        id_u = in.readInt();
        id_cat = in.readInt();
        id_u_bank = in.readInt();
        date_tran_daily = in.readString();
        time_tran_daily = in.readString();
        monney_tran_daily = in.readDouble();
        detail_tran = in.readString();
    }

    public static final Creator<Table_Transaction_Daily> CREATOR = new Creator<Table_Transaction_Daily>() {
        @Override
        public Table_Transaction_Daily createFromParcel(Parcel in) {
            return new Table_Transaction_Daily(in);
        }

        @Override
        public Table_Transaction_Daily[] newArray(int size) {
            return new Table_Transaction_Daily[size];
        }
    };

    public int getId_tran() {
        return id_tran;
    }

    public void setId_tran(int id_tran) {
        this.id_tran = id_tran;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public int getId_u_bank() {
        return id_u_bank;
    }

    public void setId_u_bank(int id_u_bank) {
        this.id_u_bank = id_u_bank;
    }

    public String getDate_tran_daily() {
        return date_tran_daily;
    }

    public void setDate_tran_daily(String date_tran_daily) {
        this.date_tran_daily = date_tran_daily;
    }

    public String getTime_tran_daily() {
        return time_tran_daily;
    }

    public void setTime_tran_daily(String time_tran_daily) {
        this.time_tran_daily = time_tran_daily;
    }

    public double getMonney_tran_daily() {
        return monney_tran_daily;
    }

    public void setMonney_tran_daily(double monney_tran_daily) {
        this.monney_tran_daily = monney_tran_daily;
    }

    public String getDetail_tran() {
        return detail_tran;
    }

    public void setDetail_tran(String detail_tran) {
        this.detail_tran = detail_tran;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_tran);
        dest.writeInt(id_u);
        dest.writeInt(id_cat);
        dest.writeInt(id_u_bank);
        dest.writeString(date_tran_daily);
        dest.writeString(time_tran_daily);
        dest.writeDouble(monney_tran_daily);
        dest.writeString(detail_tran);
    }
}
