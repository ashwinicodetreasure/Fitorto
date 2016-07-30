package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashwini on 5/28/2016.
 */
public class categoryName implements Parcelable {
    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    /**
     *
     * @return
     * The categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     *
     * @param categoryName
     * The categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    protected categoryName(Parcel in) {
        categoryName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<categoryName> CREATOR = new Parcelable.Creator<categoryName>() {
        @Override
        public categoryName createFromParcel(Parcel in) {
            return new categoryName(in);
        }

        @Override
        public categoryName[] newArray(int size) {
            return new categoryName[size];
        }
    };
    @Override
    public String toString() {
        return categoryName;
    }
}