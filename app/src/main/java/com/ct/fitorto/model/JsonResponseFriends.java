package com.ct.fitorto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by codetreasure on 6/21/16.
 */
public class JsonResponseFriends {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("followersCount")
    @Expose
    private int followersCount;
    @SerializedName("followers")
    @Expose
    private ArrayList<Follower> followers = new ArrayList<Follower>();
    @SerializedName("followingCount")
    @Expose
    private int followingCount;
    @SerializedName("following")
    @Expose
    private ArrayList<Following> following = new ArrayList<Following>();

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
     * @return The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return The followersCount
     */
    public int getFollowersCount() {
        return followersCount;
    }

    /**
     * @param followersCount The followersCount
     */
    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    /**
     * @return The followers
     */
    public ArrayList<Follower> getFollowers() {
        return followers;
    }

    /**
     * @param followers The followers
     */
    public void setFollowers(ArrayList<Follower> followers) {
        this.followers = followers;
    }

    /**
     * @return The followingCount
     */
    public int getFollowingCount() {
        return followingCount;
    }

    /**
     * @param followingCount The followingCount
     */
    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    /**
     * @return The following
     */
    public ArrayList<Following> getFollowing() {
        return following;
    }

    /**
     * @param following The following
     */
    public void setFollowing(ArrayList<Following> following) {
        this.following = following;
    }

}
