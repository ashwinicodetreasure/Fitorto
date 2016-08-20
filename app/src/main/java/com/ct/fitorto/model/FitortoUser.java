package com.ct.fitorto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashwini on 5/25/2016.
 */
public class FitortoUser {
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
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("isFollowing")
    @Expose
    private String isFollowing;
    @SerializedName("followingCount")
    @Expose
    private String followingCount;
    @SerializedName("followersCount")
    @Expose
    private String followersCount;
    @SerializedName("AndroidRegKey")
    @Expose
    private String androidRegKey;
    @SerializedName("iosRegKey")
    @Expose
    private String iosRegKey;
    @SerializedName("fitortoID")
    @Expose
    private String fitortoID;
    @SerializedName("feedCount")
    @Expose
    private String feedCount;

    public String getFeedCount() {
        return feedCount;
    }

    public void setFeedCount(String feedCount) {
        this.feedCount = feedCount;
    }

    public String getFitortoID() {
        return fitortoID;
    }

    public void setFitortoID(String fitortoID) {
        this.fitortoID = fitortoID;
    }

    /**
     * @return The userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID The userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The emailID
     */
    public String getEmailID() {
        return emailID;
    }

    /**
     * @param emailID The EmailID
     */
    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    /**
     * @return The phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo The PhoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * @return The profilePic
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * @param profilePic The ProfilePic
     */
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    /**
     * @return The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return The dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @param dob The dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return The height
     */
    public String getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * @return The weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * @param weight The weight
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * @return The bloodGroup
     */
    public String getBloodGroup() {
        return bloodGroup;
    }

    /**
     * @param bloodGroup The bloodGroup
     */
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The isFollowing
     */
    public String getIsFollowing() {
        return isFollowing;
    }

    /**
     * @param isFollowing The isFollowing
     */
    public void setIsFollowing(String isFollowing) {
        this.isFollowing = isFollowing;
    }

    /**
     * @return The followingCount
     */
    public String getFollowingCount() {
        return followingCount;
    }

    /**
     * @param followingCount The followingCount
     */
    public void setFollowingCount(String followingCount) {
        this.followingCount = followingCount;
    }

    /**
     * @return The followersCount
     */
    public String getFollowersCount() {
        return followersCount;
    }

    /**
     * @param followersCount The followersCount
     */
    public void setFollowersCount(String followersCount) {
        this.followersCount = followersCount;
    }

    /**
     * @return The androidRegKey
     */
    public String getAndroidRegKey() {
        return androidRegKey;
    }

    /**
     * @param androidRegKey The AndroidRegKey
     */
    public void setAndroidRegKey(String androidRegKey) {
        this.androidRegKey = androidRegKey;
    }

    /**
     * @return The iosRegKey
     */
    public String getIosRegKey() {
        return iosRegKey;
    }

    /**
     * @param iosRegKey The iosRegKey
     */
    public void setIosRegKey(String iosRegKey) {
        this.iosRegKey = iosRegKey;
    }
}
