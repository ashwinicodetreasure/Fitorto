package com.ct.fitorto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by codetreasure on 6/21/16.
 */
public class JsonResponseSearchFriends {
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
    private ArrayList<Friends> data = new ArrayList<Friends>();

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
    public ArrayList<Friends> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(ArrayList<Friends> data) {
        this.data = data;
    }

}
