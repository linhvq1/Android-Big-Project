package com.example.bigproject;

import android.os.Parcel;
import android.os.Parcelable;

public class user_Item_data implements Parcelable {
    String date_time;
    double expense,income,money_used;
    String name_categories,discribe,typeCat,resource_img,timeTran;
    int idTransaction;

    public user_Item_data(String date_time, double expense, double income, String name_categories, String discribe, double money_used, String resource_img,String typeCat,int idTransaction,String timeTran) {
        this.date_time = date_time;
        this.expense = expense;
        this.income = income;
        this.name_categories = name_categories;
        this.discribe = discribe;
        this.money_used = money_used;
        this.resource_img= resource_img;
        this.typeCat = typeCat;
        this.idTransaction = idTransaction;
        this.timeTran = timeTran;
    }

    protected user_Item_data(Parcel in) {
        super();
        date_time = in.readString();
        expense = in.readDouble();
        income = in.readDouble();
        name_categories = in.readString();
        discribe = in.readString();
        typeCat = in.readString();
        money_used = in.readDouble();
        resource_img = in.readString();
        idTransaction = in.readInt();
        timeTran = in.readString();
    }

    public static final Parcelable.Creator<user_Item_data> CREATOR = new Creator<user_Item_data>() {
        @Override
        public user_Item_data createFromParcel(Parcel in) {
            return new user_Item_data(in);
        }

        @Override
        public user_Item_data[] newArray(int size) {
            return new user_Item_data[size];
        }
    };

    public String getTypeCat() {
        return typeCat;
    }

    public void setTypeCat(String typeCat) {
        this.typeCat = typeCat;
    }


    public String getName_categories() {
        return name_categories;
    }

    public void setName_categories(String name_categories) {
        this.name_categories = name_categories;
    }

    public String getDiscribe() {
        return discribe;
    }

    public void setDiscribe(String discribe) {
        this.discribe = discribe;
    }


    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }


    public String getDate_time() {
        return date_time;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getMoney_used() {
        return money_used;
    }

    public void setMoney_used(double money_used) {
        this.money_used = money_used;
    }

    public String getResource_img() {
        return resource_img;
    }

    public void setResource_img(String resource_img) {
        this.resource_img = resource_img;
    }

    public static Creator<user_Item_data> getCREATOR() {
        return CREATOR;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getTimeTran() {
        return timeTran;
    }

    public void setTimeTran(String timeTran) {
        this.timeTran = timeTran;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // sap dung thu tu trong ham tao
        dest.writeString(date_time);
        dest.writeDouble(expense);
        dest.writeDouble(income);
        dest.writeString(name_categories);
        dest.writeString(discribe);
        dest.writeString(typeCat);
        dest.writeDouble(money_used);
        dest.writeString(resource_img);
        dest.writeInt(idTransaction);
        dest.writeString(timeTran);
    }
}
