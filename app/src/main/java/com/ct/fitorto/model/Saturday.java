package com.ct.fitorto.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Saturday implements Parcelable {

    @SerializedName("scheduleID")
    @Expose
    private String scheduleID;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("From")
    @Expose
    private String from;
    @SerializedName("To")
    @Expose
    private String to;
    @SerializedName("category")
    @Expose
    private String category;

    /**
     *
     * @return
     *     The scheduleID
     */
    public String getScheduleID() {
        return scheduleID;
    }

    /**
     *
     * @param scheduleID
     *     The scheduleID
     */
    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    /**
     *
     * @return
     *     The day
     */
    public String getDay() {
        return day;
    }

    /**
     *
     * @param day
     *     The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     *
     * @return
     *     The from
     */
    public String getFrom() {
        return from;
    }

    /**
     *
     * @param from
     *     The From
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     *
     * @return
     *     The to
     */
    public String getTo() {
        return to;
    }

    /**
     *
     * @param to
     *     The To
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     *
     * @return
     *     The category
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     *     The category
     */
    public void setCategory(String category) {
        this.category = category;
    }


    protected Saturday(Parcel in) {
        scheduleID = in.readString();
        day = in.readString();
        from = in.readString();
        to = in.readString();
        category = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(scheduleID);
        dest.writeString(day);
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(category);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Saturday> CREATOR = new Parcelable.Creator<Saturday>() {
        @Override
        public Saturday createFromParcel(Parcel in) {
            return new Saturday(in);
        }

        @Override
        public Saturday[] newArray(int size) {
            return new Saturday[size];
        }
    };
}