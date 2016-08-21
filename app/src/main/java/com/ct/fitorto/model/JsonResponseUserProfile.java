package com.ct.fitorto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashwini on 6/24/2016.
 */
public class JsonResponseUserProfile {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("data")
    @Expose
    private List<FitortoUser> data = new ArrayList<FitortoUser>();
    @SerializedName("pkgCount")
    @Expose
    private int pkgCount;
    @SerializedName("pkgData")
    @Expose
    private List<Package> pkgData = new ArrayList<Package>();
    @SerializedName("gymCount")
    @Expose
    private int gymCount;
    @SerializedName("gyms")
    @Expose
    private ArrayList<Gym> gyms = new ArrayList<Gym>();
    @SerializedName("progressCount")
    @Expose
    private int progressCount;
    @SerializedName("progress")
    @Expose
    private ArrayList<ProgressDetail> progress = new ArrayList<ProgressDetail>();

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
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     * The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     *
     * @return
     * The count
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The data
     */
    public List<FitortoUser> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<FitortoUser> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The pkgCount
     */
    public int getPkgCount() {
        return pkgCount;
    }

    /**
     *
     * @param pkgCount
     * The pkgCount
     */
    public void setPkgCount(int pkgCount) {
        this.pkgCount = pkgCount;
    }

    /**
     *
     * @return
     * The pkgData
     */
    public List<Package> getPkgData() {
        return pkgData;
    }

    /**
     *
     * @param pkgData
     * The pkgData
     */
    public void setPkgData(List<Package> pkgData) {
        this.pkgData = pkgData;
    }

    /**
     *
     * @return
     * The gymCount
     */
    public int getGymCount() {
        return gymCount;
    }

    /**
     *
     * @param gymCount
     * The gymCount
     */
    public void setGymCount(int gymCount) {
        this.gymCount = gymCount;
    }

    /**
     *
     * @return
     * The gyms
     */
    public ArrayList<Gym> getGyms() {
        return gyms;
    }

    /**
     *
     * @param gyms
     * The gyms
     */
    public void setGyms(ArrayList<Gym> gyms) {
        this.gyms = gyms;
    }

    /**
     *
     * @return
     * The progressCount
     */
    public int getProgressCount() {
        return progressCount;
    }

    /**
     *
     * @param progressCount
     * The progressCount
     */
    public void setProgressCount(int progressCount) {
        this.progressCount = progressCount;
    }

    /**
     *
     * @return
     * The progress
     */
    public ArrayList<ProgressDetail> getProgress() {
        return progress;
    }

    /**
     *
     * @param progress
     * The progress
     */
    public void setProgress(ArrayList<ProgressDetail> progress) {
        this.progress = progress;
    }
}
