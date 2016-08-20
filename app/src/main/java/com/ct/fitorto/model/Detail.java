package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by codetreasure on 7/22/16.
 */
public class Detail implements Parcelable {

    @SerializedName("progressID")
    @Expose
    private String progressID;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    /**
     * @return The progressID
     */
    public String getProgressID() {
        return progressID;
    }

    /**
     * @param progressID The progressID
     */
    public void setProgressID(String progressID) {
        this.progressID = progressID;
    }

    /**
     * @return The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return The value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return The dateTime
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * @param dateTime The dateTime
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Detail() {

    }

    protected Detail(Parcel in) {
        progressID = in.readString();
        category = in.readString();
        value = in.readString();
        unit = in.readString();
        dateTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(progressID);
        dest.writeString(category);
        dest.writeString(value);
        dest.writeString(unit);
        dest.writeString(dateTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Detail> CREATOR = new Parcelable.Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel in) {
            return new Detail(in);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };

}