package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashwini on 5/27/2016.
 */
public class Package implements Parcelable {

    @SerializedName("packageID")
    @Expose
    private String packageID;
    @SerializedName("packageType")
    @Expose
    private String packageType;
    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * @return The packageID
     */
    public String getPackageID() {
        return packageID;
    }

    /**
     * @param packageID The packageID
     */
    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    /**
     * @return The packageType
     */
    public String getPackageType() {
        return packageType;
    }

    /**
     * @param packageType The packageType
     */
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    /**
     * @return The packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName The packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return The cost
     */
    public String getCost() {
        return cost;
    }

    /**
     * @param cost The cost
     */
    public void setCost(String cost) {
        this.cost = cost;
    }

    /**
     * @return The validity
     */
    public String getValidity() {
        return validity;
    }

    /**
     * @param validity The validity
     */
    public void setValidity(String validity) {
        this.validity = validity;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    protected Package(Parcel in) {
        packageID = in.readString();
        packageType = in.readString();
        packageName = in.readString();
        cost = in.readString();
        validity = in.readString();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(packageID);
        dest.writeString(packageType);
        dest.writeString(packageName);
        dest.writeString(cost);
        dest.writeString(validity);
        dest.writeString(type);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Package> CREATOR = new Parcelable.Creator<Package>() {
        @Override
        public Package createFromParcel(Parcel in) {
            return new Package(in);
        }

        @Override
        public Package[] newArray(int size) {
            return new Package[size];
        }
    };

    @Override
    public String toString() {
        return packageName;
    }
}
