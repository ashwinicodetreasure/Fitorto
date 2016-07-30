package com.ct.fitorto.object;


public class ItemObject {

    private String name;
    private int photo;
    private String content;
    private int display;

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ItemObject(String name, int photo, String content, int display) {
        this.name = name;
        this.photo = photo;
        this.content = content;
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
