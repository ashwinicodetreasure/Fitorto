package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Schedule implements Parcelable {

    @SerializedName("Monday")
    @Expose
    private List<Monday> monday = new ArrayList<Monday>();
    @SerializedName("Tuesday")
    @Expose
    private List<Tuesday> tuesday = new ArrayList<Tuesday>();
    @SerializedName("Wednesday")
    @Expose
    private List<Wednesday> wednesday = new ArrayList<Wednesday>();
    @SerializedName("Thursday")
    @Expose
    private List<Thursday> thursday = new ArrayList<Thursday>();
    @SerializedName("Friday")
    @Expose
    private List<Friday> friday = new ArrayList<Friday>();
    @SerializedName("Saturday")
    @Expose
    private List<Saturday> saturday = new ArrayList<Saturday>();
    @SerializedName("Sunday")
    @Expose
    private List<Sunday> sunday = new ArrayList<Sunday>();

    /**
     *
     * @return
     *     The monday
     */
    public List<Monday> getMonday() {
        return monday;
    }

    /**
     *
     * @param monday
     *     The Monday
     */
    public void setMonday(List<Monday> monday) {
        this.monday = monday;
    }

    /**
     *
     * @return
     *     The tuesday
     */
    public List<Tuesday> getTuesday() {
        return tuesday;
    }

    /**
     *
     * @param tuesday
     *     The Tuesday
     */
    public void setTuesday(List<Tuesday> tuesday) {
        this.tuesday = tuesday;
    }

    /**
     *
     * @return
     *     The wednesday
     */
    public List<Wednesday> getWednesday() {
        return wednesday;
    }

    /**
     *
     * @param wednesday
     *     The Wednesday
     */
    public void setWednesday(List<Wednesday> wednesday) {
        this.wednesday = wednesday;
    }

    /**
     *
     * @return
     *     The thursday
     */
    public List<Thursday> getThursday() {
        return thursday;
    }

    /**
     *
     * @param thursday
     *     The Thursday
     */
    public void setThursday(List<Thursday> thursday) {
        this.thursday = thursday;
    }

    /**
     *
     * @return
     *     The friday
     */
    public List<Friday> getFriday() {
        return friday;
    }

    /**
     *
     * @param friday
     *     The Friday
     */
    public void setFriday(List<Friday> friday) {
        this.friday = friday;
    }

    /**
     *
     * @return
     *     The saturday
     */
    public List<Saturday> getSaturday() {
        return saturday;
    }

    /**
     *
     * @param saturday
     *     The Saturday
     */
    public void setSaturday(List<Saturday> saturday) {
        this.saturday = saturday;
    }

    /**
     *
     * @return
     *     The sunday
     */
    public List<Sunday> getSunday() {
        return sunday;
    }

    /**
     *
     * @param sunday
     *     The Sunday
     */
    public void setSunday(List<Sunday> sunday) {
        this.sunday = sunday;
    }


    protected Schedule(Parcel in) {
        if (in.readByte() == 0x01) {
            monday = new ArrayList<Monday>();
            in.readList(monday, Monday.class.getClassLoader());
        } else {
            monday = null;
        }
        if (in.readByte() == 0x01) {
            tuesday = new ArrayList<Tuesday>();
            in.readList(tuesday, Tuesday.class.getClassLoader());
        } else {
            tuesday = null;
        }
        if (in.readByte() == 0x01) {
            wednesday = new ArrayList<Wednesday>();
            in.readList(wednesday, Wednesday.class.getClassLoader());
        } else {
            wednesday = null;
        }
        if (in.readByte() == 0x01) {
            thursday = new ArrayList<Thursday>();
            in.readList(thursday, Thursday.class.getClassLoader());
        } else {
            thursday = null;
        }
        if (in.readByte() == 0x01) {
            friday = new ArrayList<Friday>();
            in.readList(friday, Friday.class.getClassLoader());
        } else {
            friday = null;
        }
        if (in.readByte() == 0x01) {
            saturday = new ArrayList<Saturday>();
            in.readList(saturday, Saturday.class.getClassLoader());
        } else {
            saturday = null;
        }
        if (in.readByte() == 0x01) {
            sunday = new ArrayList<Sunday>();
            in.readList(sunday, Sunday.class.getClassLoader());
        } else {
            sunday = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (monday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(monday);
        }
        if (tuesday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(tuesday);
        }
        if (wednesday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(wednesday);
        }
        if (thursday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(thursday);
        }
        if (friday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(friday);
        }
        if (saturday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(saturday);
        }
        if (sunday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(sunday);
        }
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