package com.ct.fitorto.object;

/**
 * Created by Ashwini on 5/19/2016.
 */
public class FeedObject {
    private int image;
    private String name;
    private String content;
    private String time;
    private int display;
    private String link;
    private String like;

    public FeedObject(int image, String name, String content, String time, int display, String link, String like) {
        this.image = image;
        this.name = name;
        this.content = content;
        this.time = time;
        this.display = display;
        this.link = link;
        this.like = like;
    }

    public int getImage() {

        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
