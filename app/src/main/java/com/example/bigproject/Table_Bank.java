package com.example.bigproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Date;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "monney_bank",
        foreignKeys = @ForeignKey(onDelete = CASCADE,entity = Table_User.class,
                                    parentColumns = "id_u",
                                    childColumns = "id_u"),indices = @Index(value = "id_u"))
public class Table_Bank {
    @PrimaryKey(autoGenerate = true)
    private int id_u_bank;

    private int id_u;

    @ColumnInfo(name = "monney_start",defaultValue = "0")
    private double monney_start;

    @ColumnInfo(name = "current_monney",defaultValue = "0")
    private double current_monney;

    private String date_start;
    private String date_end;
    @ColumnInfo(name = "current_state",defaultValue = "0")
    private int current_state;

    public Table_Bank(int id_u, double monney_start,double current_monney, String date_start, String date_end, int current_state) {
        this.id_u = id_u;
        this.monney_start = monney_start;
        this.current_monney = current_monney;
        this.date_start = date_start;
        this.date_end = date_end;
        this.current_state = current_state;
    }

    public double getMonney_start() {
        return monney_start;
    }

    public void setMonney_start(double monney_start) {
        this.monney_start = monney_start;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public int getId_u_bank() {
        return id_u_bank;
    }

    public void setId_u_bank(int id_u_bank) {
        this.id_u_bank = id_u_bank;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }

    public double getCurrent_monney() {
        return current_monney;
    }

    public void setCurrent_monney(double current_monney) {
        this.current_monney = current_monney;
    }

    public int getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(int current_state) {
        this.current_state = current_state;
    }
}
