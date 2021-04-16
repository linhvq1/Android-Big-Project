package com.example.bigproject;

import android.os.Parcel;
import android.os.Parcelable;

public class chartExpensesClass implements Parcelable {
    private int idCatChart;
    private String nameCatChart;
    private int countCatChart;
    private double monneySumCat;
    private double percentCat;
    private double sumcatPerMonth;
    private String imgCatChart;
    private String typeCatChart;

    public chartExpensesClass(int idCatChart, String nameCatChart, int countCatChart, double monneySumCat, double percentCat, double sumcatPerMonth, String imgCatChart, String typeCatChart) {
        this.idCatChart = idCatChart;
        this.nameCatChart = nameCatChart;
        this.countCatChart = countCatChart;
        this.monneySumCat = monneySumCat;
        this.percentCat = percentCat;
        this.sumcatPerMonth = sumcatPerMonth;
        this.imgCatChart = imgCatChart;
        this.typeCatChart = typeCatChart;
    }

    protected chartExpensesClass(Parcel in) {
        idCatChart = in.readInt();
        nameCatChart = in.readString();
        countCatChart = in.readInt();
        monneySumCat = in.readDouble();
        percentCat = in.readDouble();
        sumcatPerMonth = in.readDouble();
        imgCatChart = in.readString();
        typeCatChart = in.readString();
    }

    public int getIdCatChart() {
        return idCatChart;
    }

    public void setIdCatChart(int idCatChart) {
        this.idCatChart = idCatChart;
    }

    public String getNameCatChart() {
        return nameCatChart;
    }

    public void setNameCatChart(String nameCatChart) {
        this.nameCatChart = nameCatChart;
    }

    public int getCountCatChart() {
        return countCatChart;
    }

    public void setCountCatChart(int countCatChart) {
        this.countCatChart = countCatChart;
    }

    public double getMonneySumCat() {
        return monneySumCat;
    }

    public void setMonneySumCat(double monneySumCat) {
        this.monneySumCat = monneySumCat;
    }

    public double getPercentCat() {
        return percentCat;
    }

    public void setPercentCat(double percentCat) {
        this.percentCat = percentCat;
    }

    public double getSumcatPerMonth() {
        return sumcatPerMonth;
    }

    public void setSumcatPerMonth(double sumcatPerMonth) {
        this.sumcatPerMonth = sumcatPerMonth;
    }

    public String getImgCatChart() {
        return imgCatChart;
    }

    public void setImgCatChart(String imgCatChart) {
        this.imgCatChart = imgCatChart;
    }

    public String getTypeCatChart() {
        return typeCatChart;
    }

    public void setTypeCatChart(String typeCatChart) {
        this.typeCatChart = typeCatChart;
    }

    public static Creator<chartExpensesClass> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<chartExpensesClass> CREATOR = new Creator<chartExpensesClass>() {
        @Override
        public chartExpensesClass createFromParcel(Parcel in) {
            return new chartExpensesClass(in);
        }

        @Override
        public chartExpensesClass[] newArray(int size) {
            return new chartExpensesClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCatChart);
        dest.writeString(nameCatChart);
        dest.writeInt(countCatChart);
        dest.writeDouble(monneySumCat);
        dest.writeDouble(percentCat);
        dest.writeDouble(sumcatPerMonth);
        dest.writeString(imgCatChart);
        dest.writeString(typeCatChart);
    }
}
