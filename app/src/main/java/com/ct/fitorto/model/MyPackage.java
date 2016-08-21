package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by codetreasure on 8/21/16.
 */
public class MyPackage implements Parcelable {

    @SerializedName("packageID")
    @Expose
    private String packageID;
    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("ammount")
    @Expose
    private String ammount;
    @SerializedName("ammountPaid")
    @Expose
    private String ammountPaid;
    @SerializedName("joiningDate")
    @Expose
    private String joiningDate;
    @SerializedName("expiryDate")
    @Expose
    private String expiryDate;

    /**
     *
     * @return
     *     The packageID
     */
    public String getPackageID() {
        return packageID;
    }

    /**
     *
     * @param packageID
     *     The packageID
     */
    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    /**
     *
     * @return
     *     The packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     *
     * @param packageName
     *     The packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     *
     * @return
     *     The duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     *     The duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     *
     * @return
     *     The ammount
     */
    public String getAmmount() {
        return ammount;
    }

    /**
     *
     * @param ammount
     *     The ammount
     */
    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    /**
     *
     * @return
     *     The ammountPaid
     */
    public String getAmmountPaid() {
        return ammountPaid;
    }

    /**
     *
     * @param ammountPaid
     *     The ammountPaid
     */
    public void setAmmountPaid(String ammountPaid) {
        this.ammountPaid = ammountPaid;
    }

    /**
     *
     * @return
     *     The joiningDate
     */
    public String getJoiningDate() {
        return joiningDate;
    }

    /**
     *
     * @param joiningDate
     *     The joiningDate
     */
    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    /**
     *
     * @return
     *     The expiryDate
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     *
     * @param expiryDate
     *     The expiryDate
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }


    protected MyPackage(Parcel in) {
        packageID = in.readString();
        packageName = in.readString();
        duration = in.readString();
        ammount = in.readString();
        ammountPaid = in.readString();
        joiningDate = in.readString();
        expiryDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(packageID);
        dest.writeString(packageName);
        dest.writeString(duration);
        dest.writeString(ammount);
        dest.writeString(ammountPaid);
        dest.writeString(joiningDate);
        dest.writeString(expiryDate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MyPackage> CREATOR = new Parcelable.Creator<MyPackage>() {
        @Override
        public MyPackage createFromParcel(Parcel in) {
            return new MyPackage(in);
        }

        @Override
        public MyPackage[] newArray(int size) {
            return new MyPackage[size];
        }
    };
}