package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ashwini on 6/27/2016.
 */
public class ProgressDetail   implements Parcelable {

    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("details")
    @Expose
    private ArrayList<Detail> details = new ArrayList<Detail>();

    /**
     *
     * @return
     * The category
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The details
     */
    public ArrayList<Detail> getDetails() {
        return details;
    }

    /**
     *
     * @param details
     * The details
     */
    public void setDetails(ArrayList<Detail> details) {
        this.details = details;
    }





    protected ProgressDetail(Parcel in) {
        category = in.readString();
        if (in.readByte() == 0x01) {
            details = new ArrayList<Detail>();
            in.readList(details, Detail.class.getClassLoader());
        } else {
            details = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        if (details == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(details);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProgressDetail> CREATOR = new Parcelable.Creator<ProgressDetail>() {
        @Override
        public ProgressDetail createFromParcel(Parcel in) {
            return new ProgressDetail(in);
        }

        @Override
        public ProgressDetail[] newArray(int size) {
            return new ProgressDetail[size];
        }
    };
}