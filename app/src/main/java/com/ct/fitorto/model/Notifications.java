package com.ct.fitorto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by codetreasure on 7/7/16.
 */
public class Notifications {

    @SerializedName("senderID")
    @Expose
    private String senderID;
    @SerializedName("notificationID")
    @Expose
    private String notificationID;
    @SerializedName("senderName")
    @Expose
    private String senderName;
    @SerializedName("profilePic")
    @Expose
    private String profilePic;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("isUser")
    @Expose
    private String isUser;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;
    @SerializedName("userCouponID")
    @Expose
    private String userCouponID;
    @SerializedName("isUsed")
    @Expose
    private int isUsed;
    @SerializedName("notificationCategory")
    @Expose
    private int notificationCategory;

    /**
     *
     * @return
     * The senderID
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     *
     * @param senderID
     * The senderID
     */
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    /**
     *
     * @return
     * The notificationID
     */
    public String getNotificationID() {
        return notificationID;
    }

    /**
     *
     * @param notificationID
     * The notificationID
     */
    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    /**
     *
     * @return
     * The senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     *
     * @param senderName
     * The senderName
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
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
     * The profilePic
     */
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

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
     * The notification
     */
    public String getNotification() {
        return notification;
    }

    /**
     *
     * @param notification
     * The notification
     */
    public void setNotification(String notification) {
        this.notification = notification;
    }

    /**
     *
     * @return
     * The isUser
     */
    public String getIsUser() {
        return isUser;
    }

    /**
     *
     * @param isUser
     * The isUser
     */
    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }

    /**
     *
     * @return
     * The dateTime
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     *
     * @param dateTime
     * The dateTime
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     *
     * @return
     * The userCouponID
     */
    public String getUserCouponID() {
        return userCouponID;
    }

    /**
     *
     * @param userCouponID
     * The userCouponID
     */
    public void setUserCouponID(String userCouponID) {
        this.userCouponID = userCouponID;
    }

    /**
     *
     * @return
     * The isUsed
     */
    public int getIsUsed() {
        return isUsed;
    }

    /**
     *
     * @param isUsed
     * The isUsed
     */
    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    /**
     *
     * @return
     * The notificationCategory
     */
    public int getNotificationCategory() {
        return notificationCategory;
    }

    /**
     *
     * @param notificationCategory
     * The notificationCategory
     */
    public void setNotificationCategory(int notificationCategory) {
        this.notificationCategory = notificationCategory;
    }

}