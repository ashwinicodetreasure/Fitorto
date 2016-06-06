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
    @SerializedName("AndroidRegKey")
    @Expose
    private String androidRegKey;
    @SerializedName("iosRegKey")
    @Expose
    private String iosRegKey;

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

}

