package com.example.bigproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Table_Category implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id_cat;
    // neu ko dung columnInfo thi ten cot se dc thiet lap theo ten cua doi tuong
    private String name_cat;
    private String img_cat;
    private String type_cat;

    public Table_Category(String name_cat, String img_cat, String type_cat) {
        this.name_cat = name_cat;
        this.img_cat = img_cat;
        this.type_cat = type_cat;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public String getName_cat() {
        return name_cat;
    }

    public void setName_cat(String name_cat) {
        this.name_cat = name_cat;
    }

    public String getImg_cat() {
        return img_cat;
    }

    public void setImg_cat(String img_cat) {
        this.img_cat = img_cat;
    }

    public String getType_cat() {
        return type_cat;
    }

    public void setType_cat(String type_cat) {
        this.type_cat = type_cat;
    }
    public static final Parcelable.Creator<Table_Category> CREATOR = new Parcelable.Creator<Table_Category>() {
        public Table_Category createFromParcel(Parcel in) {
            return new Table_Category(in);
        }

        public Table_Category[] newArray(int size) {

            return new Table_Category[size];
        }

    };
    // parcelable
    public  Table_Category(Parcel in){
        super();
        readFromParcel(in);
    }
    public void readFromParcel(Parcel in){
        id_cat = in.readInt();
        name_cat = in.readString();
        img_cat = in.readString();
        type_cat = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_cat);
        dest.writeString(name_cat);
        dest.writeString(img_cat);
        dest.writeString(type_cat);
    }
}
