package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashwini on 5/28/2016.
 */
public class JsonResponseKeywords implements Parcelable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("data")
    @Expose
    private List<categoryName> data = new ArrayList<categoryName>();
    @SerializedName("countLocations")
    @Expose
    private int countLocations;
    @SerializedName("locations")
    @Expose
    private List<Location> locations = new ArrayList<Location>();

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     * The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     *
     * @return
     * The count
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The data
     */
    public List<categoryName> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<categoryName> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The countLocations
     */
    public int getCountLocations() {
        return countLocations;
    }

    /**
     *
     * @param countLocations
     * The countLocations
     */
    public void setCountLocations(int countLocations) {
        this.countLocations = countLocations;
    }

    /**
     *
     * @return
     * The locations
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     *
     * @param locations
     * The locations
     */
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }


    protected JsonResponseKeywords(Parcel in) {
        status = in.readString();
        msg = in.readString();
        count = in.readInt();
        if (in.readByte() == 0x01) {
            data = new ArrayList<categoryName>();
            in.readList(data, categoryName.class.getClassLoader());
        } else {
            data = null;
        }
        countLocations = in.readInt();
        if (in.readByte() == 0x01) {
            locations = new ArrayList<Location>();
            in.readList(locations, Location.class.getClassLoader());
        } else {
            locations = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(msg);
        dest.writeInt(count);
        if (data == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(data);
        }
        dest.writeInt(countLocations);
        if (locations == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(locations);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<JsonResponseKeywords> CREATOR = new Parcelable.Creator<JsonResponseKeywords>() {
        @Override
        public JsonResponseKeywords createFromParcel(Parcel in) {
            return new JsonResponseKeywords(in);
        }

        @Override
        public JsonResponseKeywords[] newArray(int size) {
            return new JsonResponseKeywords[size];
        }
    };
}