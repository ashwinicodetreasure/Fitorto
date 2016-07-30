package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashwini on 5/27/2016.
 */
public class Schedule implements Parcelable {
    @SerializedName("monFrom")
    @Expose
    private String monFrom;
    @SerializedName("monTo")
    @Expose
    private String monTo;
    @SerializedName("tueFrom")
    @Expose
    private String tueFrom;
    @SerializedName("tueTo")
    @Expose
    private String tueTo;
    @SerializedName("wedFrom")
    @Expose
    private String wedFrom;
    @SerializedName("wedTo")
    @Expose
    private String wedTo;
    @SerializedName("thuFrom")
    @Expose
    private String thuFrom;
    @SerializedName("thuTo")
    @Expose
    private String thuTo;
    @SerializedName("friFrom")
    @Expose
    private String friFrom;
    @SerializedName("friTo")
    @Expose
    private String friTo;
    @SerializedName("satFrom")
    @Expose
    private String satFrom;
    @SerializedName("satTo")
    @Expose
    private String satTo;
    @SerializedName("sunFrom")
    @Expose
    private String sunFrom;
    @SerializedName("sunTo")
    @Expose
    private String sunTo;

    /**
     *
     * @return
     * The monFrom
     */
    public String getMonFrom() {
        return monFrom;
    }

    /**
     *
     * @param monFrom
     * The monFrom
     */
    public void setMonFrom(String monFrom) {
        this.monFrom = monFrom;
    }

    /**
     *
     * @return
     * The monTo
     */
    public String getMonTo() {
        return monTo;
    }

    /**
     *
     * @param monTo
     * The monTo
     */
    public void setMonTo(String monTo) {
        this.monTo = monTo;
    }

    /**
     *
     * @return
     * The tueFrom
     */
    public String getTueFrom() {
        return tueFrom;
    }

    /**
     *
     * @param tueFrom
     * The tueFrom
     */
    public void setTueFrom(String tueFrom) {
        this.tueFrom = tueFrom;
    }

    /**
     *
     * @return
     * The tueTo
     */
    public String getTueTo() {
        return tueTo;
    }

    /**
     *
     * @param tueTo
     * The tueTo
     */
    public void setTueTo(String tueTo) {
        this.tueTo = tueTo;
    }

    /**
     *
     * @return
     * The wedFrom
     */
    public String getWedFrom() {
        return wedFrom;
    }

    /**
     *
     * @param wedFrom
     * The wedFrom
     */
    public void setWedFrom(String wedFrom) {
        this.wedFrom = wedFrom;
    }

    /**
     *
     * @return
     * The wedTo
     */
    public String getWedTo() {
        return wedTo;
    }

    /**
     *
     * @param wedTo
     * The wedTo
     */
    public void setWedTo(String wedTo) {
        this.wedTo = wedTo;
    }

    /**
     *
     * @return
     * The thuFrom
     */
    public String getThuFrom() {
        return thuFrom;
    }

    /**
     *
     * @param thuFrom
     * The thuFrom
     */
    public void setThuFrom(String thuFrom) {
        this.thuFrom = thuFrom;
    }

    /**
     *
     * @return
     * The thuTo
     */
    public String getThuTo() {
        return thuTo;
    }

    /**
     *
     * @param thuTo
     * The thuTo
     */
    public void setThuTo(String thuTo) {
        this.thuTo = thuTo;
    }

    /**
     *
     * @return
     * The friFrom
     */
    public String getFriFrom() {
        return friFrom;
    }

    /**
     *
     * @param friFrom
     * The friFrom
     */
    public void setFriFrom(String friFrom) {
        this.friFrom = friFrom;
    }

    /**
     *
     * @return
     * The friTo
     */
    public String getFriTo() {
        return friTo;
    }

    /**
     *
     * @param friTo
     * The friTo
     */
    public void setFriTo(String friTo) {
        this.friTo = friTo;
    }

    /**
     *
     * @return
     * The satFrom
     */
    public String getSatFrom() {
        return satFrom;
    }

    /**
     *
     * @param satFrom
     * The satFrom
     */
    public void setSatFrom(String satFrom) {
        this.satFrom = satFrom;
    }

    /**
     *
     * @return
     * The satTo
     */
    public String getSatTo() {
        return satTo;
    }

    /**
     *
     * @param satTo
     * The satTo
     */
    public void setSatTo(String satTo) {
        this.satTo = satTo;
    }

    /**
     *
     * @return
     * The sunFrom
     */
    public String getSunFrom() {
        return sunFrom;
    }

    /**
     *
     * @param sunFrom
     * The sunFrom
     */
    public void setSunFrom(String sunFrom) {
        this.sunFrom = sunFrom;
    }

    /**
     *
     * @return
     * The sunTo
     */
    public String getSunTo() {
        return sunTo;
    }

    /**
     *
     * @param sunTo
     * The sunTo
     */
    public void setSunTo(String sunTo) {
        this.sunTo = sunTo;
    }

    protected Schedule(Parcel in) {
        monFrom = in.readString();
        monTo = in.readString();
        tueFrom = in.readString();
        tueTo = in.readString();
        wedFrom = in.readString();
        wedTo = in.readString();
        thuFrom = in.readString();
        thuTo = in.readString();
        friFrom = in.readString();
        friTo = in.readString();
        satFrom = in.readString();
        satTo = in.readString();
        sunFrom = in.readString();
        sunTo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(monFrom);
        dest.writeString(monTo);
        dest.writeString(tueFrom);
        dest.writeString(tueTo);
        dest.writeString(wedFrom);
        dest.writeString(wedTo);
        dest.writeString(thuFrom);
        dest.writeString(thuTo);
        dest.writeString(friFrom);
        dest.writeString(friTo);
        dest.writeString(satFrom);
        dest.writeString(satTo);
        dest.writeString(sunFrom);
        dest.writeString(sunTo);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
}