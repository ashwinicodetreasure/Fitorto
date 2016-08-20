package com.ct.fitorto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by codetreasure on 8/20/16.
 */
public class Coupon {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("couponID")
    @Expose
    private String couponID;
    @SerializedName("gymID")
    @Expose
    private String gymID;
    @SerializedName("couponType")
    @Expose
    private String couponType;
    @SerializedName("benifit")
    @Expose
    private String benifit;
    @SerializedName("requestForUse")
    @Expose
    private String requestForUse;
    @SerializedName("isUsed")
    @Expose
    private String isUsed;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;

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
     * The couponID
     */
    public String getCouponID() {
        return couponID;
    }

    /**
     *
     * @param couponID
     * The couponID
     */
    public void setCouponID(String couponID) {
        this.couponID = couponID;
    }

    /**
     *
     * @return
     * The gymID
     */
    public String getGymID() {
        return gymID;
    }

    /**
     *
     * @param gymID
     * The gymID
     */
    public void setGymID(String gymID) {
        this.gymID = gymID;
    }

    /**
     *
     * @return
     * The couponType
     */
    public String getCouponType() {
        return couponType;
    }

    /**
     *
     * @param couponType
     * The couponType
     */
    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    /**
     *
     * @return
     * The benifit
     */
    public String getBenifit() {
        return benifit;
    }

    /**
     *
     * @param benifit
     * The benifit
     */
    public void setBenifit(String benifit) {
        this.benifit = benifit;
    }

    /**
     *
     * @return
     * The requestForUse
     */
    public String getRequestForUse() {
        return requestForUse;
    }

    /**
     *
     * @param requestForUse
     * The requestForUse
     */
    public void setRequestForUse(String requestForUse) {
        this.requestForUse = requestForUse;
    }

    /**
     *
     * @return
     * The isUsed
     */
    public String getIsUsed() {
        return isUsed;
    }

    /**
     *
     * @param isUsed
     * The isUsed
     */
    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
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

}
