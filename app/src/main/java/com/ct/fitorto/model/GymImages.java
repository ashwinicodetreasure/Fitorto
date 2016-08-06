package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ashwini on 6/2/2016.
 */
public class GymImages implements Parcelable {
    String imageLink;

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    protected GymImages(Parcel in) {
        imageLink = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageLink);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GymImages> CREATOR = new Parcelable.Creator<GymImages>() {
        @Override
        public GymImages createFromParcel(Parcel in) {
            return new GymImages(in);
        }

        @Override
        public GymImages[] newArray(int size) {
            return new GymImages[size];
        }
    };
}