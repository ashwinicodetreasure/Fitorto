package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by codetreasure on 6/21/16.
 */
public class Following implements Parcelable {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("EmailID")
    @Expose
    private String emailID;
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("ProfilePic")
    @Expose
    private String profilePic;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;
    @SerializedName("isFollowing")
    @Expose
    private String isFollowing;
    @SerializedName("AndroidRegKey")
    @Expose
    private String androidRegKey;
    @SerializedName("iosRegKey")
    @Expose
    private String iosRegKey;
    @SerializedName("status")
    @Expose
    private String status;
    /**
     *
     * @return
     * The userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     * The userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The emailID
     */
    public String getEmailID() {
        return emailID;
    }

    /**
     *
     * @param emailID
     * The EmailID
     */
    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    /**
     *
     * @return
     * The phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     *
     * @param phoneNo
     * The PhoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     *
     * @return
     * The profilePic
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     *
     * @param profilePic
     * The ProfilePic
     */
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The dob
     */
    public String getDob() {
        return dob;
    }

    /**
     *
     * @param dob
     * The dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The height
     */
    public String getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     *
     * @return
     * The weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     *
     * @param weight
     * The weight
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     *
     * @return
     * The bloodGroup
     */
    public String getBloodGroup() {
        return bloodGroup;
    }

    /**
     *
     * @param bloodGroup
     * The bloodGroup
     */
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    /**
     *
     * @return
     * The isFollowing
     */
    public String getIsFollowing() {
        return isFollowing;
    }

    /**
     *
     * @param isFollowing
     * The isFollowing
     */
    public void setIsFollowing(String isFollowing) {
        this.isFollowing = isFollowing;
    }

    /**
     *
     * @return
     * The androidRegKey
     */
    public String getAndroidRegKey() {
        return androidRegKey;
    }

    /**
     *
     * @param androidRegKey
     * The AndroidRegKey
     */
    public void setAndroidRegKey(String androidRegKey) {
        this.androidRegKey = androidRegKey;
    }

    /**
     *
     * @return
     * The iosRegKey
     */
    public String getIosRegKey() {
        return iosRegKey;
    }

    /**
     *
     * @param iosRegKey
     * The iosRegKey
     */
    public void setIosRegKey(String iosRegKey) {
        this.iosRegKey = iosRegKey;
    }

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
     * The userID
     */
    public void setStatus(String status) {
        this.status = status;
    }


    protected Following(Parcel in) {
        userID = in.readString();
        name = in.readString();
        emailID = in.readString();
        phoneNo = in.readString();
        profilePic = in.readString();
        gender = in.readString();
        dob = in.readString();
        address = in.readString();
        height = in.readString();
        weight = in.readString();
        bloodGroup = in.readString();
        isFollowing = in.readString();
        androidRegKey = in.readString();
        iosRegKey = in.readString();
        status=in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userID);
        dest.writeString(name);
        dest.writeString(emailID);
        dest.writeString(phoneNo);
        dest.writeString(profilePic);
        dest.writeString(gender);
        dest.writeString(dob);
        dest.writeString(address);
        dest.writeString(height);
        dest.writeString(weight);
        dest.writeString(bloodGroup);
        dest.writeString(isFollowing);
        dest.writeString(androidRegKey);
        dest.writeString(iosRegKey);
        dest.writeString(status);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Following> CREATOR = new Parcelable.Creator<Following>() {
        @Override
        public Following createFromParcel(Parcel in) {
            return new Following(in);
        }

        @Override
        public Following[] newArray(int size) {
            return new Following[size];
        }
    };
}