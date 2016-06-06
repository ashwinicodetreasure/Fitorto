package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashwini on 5/25/2016.
 */
public class City implements Parcelable {

    @SerializedName("cityID")
    @Expose
    private String cityID;
    @SerializedName("cityName")
    @Expose
    private String cityName;

    /**
     *
     * @return
     * The cityID
     */
    public String getCityID() {
        return cityID;
    }

    /**
     *
     * @param cityID
     * The cityID
     */
    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    /**
     *
     * @return
     * The cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     *
     * @param cityName
     * The cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }



    protected City(Parcel in) {
        cityID = in.readString();
        cityName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityID);
        dest.writeString(cityName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}