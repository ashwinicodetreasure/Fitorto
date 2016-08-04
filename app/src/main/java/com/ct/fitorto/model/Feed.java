package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashwini on 6/3/2016.
 */
public class Feed implements Parcelable {
    @SerializedName("feedID")
    @Expose
    private String feedID;
    @SerializedName("feed")
    @Expose
    private String feed;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("imageLink")
    @Expose
    private String imageLink;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("shares")
    @Expose
    private String shares;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userProfilePic")
    @Expose
    private String userProfilePic;
    @SerializedName("isUser")
    @Expose
    private String isUser;

    @SerializedName("feedIsLike")
    @Expose
    private String isLike;
    @SerializedName("feedIsShare")
    @Expose
    private String isShare;


    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    /**
     *
     * @return
     * The feedID
     */
    public String getFeedID() {
        return feedID;
    }

    /**
     *
     * @param feedID
     * The feedID
     */
    public void setFeedID(String feedID) {
        this.feedID = feedID;
    }

    /**
     *
     * @return
     * The feed
     */
    public String getFeed() {
        return feed;
    }

    /**
     *
     * @param feed
     * The feed
     */
    public void setFeed(String feed) {
        this.feed = feed;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The imageLink
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     *
     * @param imageLink
     * The imageLink
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    /**
     *
     * @return
     * The created
     */
    public String getCreated() {
        return created;
    }

    /**
     *
     * @param created
     * The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     *
     * @return
     * The likes
     */
    public String getLikes() {
        return likes;
    }

    /**
     *
     * @param likes
     * The likes
     */
    public void setLikes(String likes) {
        this.likes = likes;
    }

    /**
     *
     * @return
     * The shares
     */
    public String getShares() {
        return shares;
    }

    /**
     *
     * @param shares
     * The shares
     */
    public void setShares(String shares) {
        this.shares = shares;
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
     * The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     * The userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     * The userProfilePic
     */
    public String getUserProfilePic() {
        return userProfilePic;
    }

    /**
     *
     * @param userProfilePic
     * The userProfilePic
     */
    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
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

    protected Feed(Parcel in) {
        feedID = in.readString();
        feed = in.readString();
        url = in.readString();
        imageLink = in.readString();
        created = in.readString();
        likes = in.readString();
        shares = in.readString();
        userID = in.readString();
        userName = in.readString();
        userProfilePic = in.readString();
        isUser = in.readString();
        isLike = in.readString();
        isShare = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(feedID);
        dest.writeString(feed);
        dest.writeString(url);
        dest.writeString(imageLink);
        dest.writeString(created);
        dest.writeString(likes);
        dest.writeString(shares);
        dest.writeString(userID);
        dest.writeString(userName);
        dest.writeString(userProfilePic);
        dest.writeString(isUser);
        dest.writeString(isLike);
        dest.writeString(isShare);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}