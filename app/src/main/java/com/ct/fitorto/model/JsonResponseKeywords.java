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
    private List<Location> data = new ArrayList<Location>();

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
    public List<Location> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Location> data) {
        this.data = data;
    }



    protected JsonResponseKeywords(Parcel in) {
        status = in.readString();
        msg = in.readString();
        count = in.readInt();
        if (in.readByte() == 0x01) {
            data = new ArrayList<Location>();
            in.readList(data, categoryName.class.getClassLoader());
        } else {
            data = null;
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