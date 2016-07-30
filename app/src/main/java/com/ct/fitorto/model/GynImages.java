package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ashwini on 6/2/2016.
 */
public class GynImages implements Parcelable {
    String imageLink;

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    protected GynImages(Parcel in) {
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
    public static final Parcelable.Creator<GynImages> CREATOR = new Parcelable.Creator<GynImages>() {
        @Override
        public GynImages createFromParcel(Parcel in) {
            return new GynImages(in);
        }

        @Override
        public GynImages[] newArray(int size) {
            return new GynImages[size];
        }
    };
}