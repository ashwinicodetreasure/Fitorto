package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashwini on 5/27/2016.
 */
public class Package implements Parcelable {


    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("oneMonth")
    @Expose
    private String oneMonth;
    @SerializedName("threeMonth")
    @Expose
    private String threeMonth;
    @SerializedName("sixMonth")
    @Expose
    private String sixMonth;
    @SerializedName("oneYear")
    @Expose
    private String oneYear;


    /**
     *
     * @return
     * The packageName
     */
    public String getPackageName() {
        return packageName==null?"":packageName;
    }

    /**
     *
     * @param packageName
     * The packageName
     */
    public void setPackageName(String packageName) {
        this.packageName   = packageName;
    }


    /**
     *
     * @return
     * The oneMonth
     */
    public String getOneMonth() {
        return oneMonth==null?"":oneMonth;
    }

    /**
     *
     * @param oneMonth
     * The oneMonth
     */
    public void setOneMonth(String oneMonth) {
        this.oneMonth = oneMonth;
    }

    /**
     *
     * @return
     * The threeMonth
     */
    public String getThreeMonth() {
        return threeMonth==null?"":threeMonth;
    }

    /**
     *
     * @param threeMonth
     * The threeMonth
     */
    public void setThreeMonth(String threeMonth) {
        this.threeMonth = threeMonth;
    }

    /**
     *
     * @return
     * The sixMonth
     */
    public String getSixMonth() {
        return sixMonth==null?"":sixMonth;
    }

    /**
     *
     * @param sixMonth
     * The sixMonth
     */
    public void setSixMonth(String sixMonth) {
        this.sixMonth = sixMonth;
    }

    /**
     *
     * @return
     * The oneYear
     */
    public String getOneYear() {
        return oneYear==null?"":oneYear;
    }

    /**
     *
     * @param oneYear
     * The oneYear
     */
    public void setOneYear(String oneYear) {
        this.oneYear = oneYear;
    }

    protected Package(Parcel in) {
        packageName = in.readString();
        oneMonth = in.readString();
        threeMonth = in.readString();
        sixMonth = in.readString();
        oneYear = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(packageName);
        dest.writeString(oneMonth);
        dest.writeString(threeMonth);
        dest.writeString(sixMonth);
        dest.writeString(oneYear);
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
        return this.packageName;
    }
}