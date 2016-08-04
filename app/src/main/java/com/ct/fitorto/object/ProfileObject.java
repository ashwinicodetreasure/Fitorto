package com.ct.fitorto.object;

/**
 * Created by Ashwini on 5/21/2016.
 */
public class ProfileObject {
    private int gym_photo;
    private String gym_title;
    private int profie;
    private String profile_title;
    private String following;
    private String follower;

    public ProfileObject(int gym_photo, String gym_title) {
        this.gym_photo = gym_photo;
        this.gym_title = gym_title;

    }

      public int getGym_photo() {

        return gym_photo;
    }

    public void setGym_photo(int gym_photo) {
        this.gym_photo = gym_photo;
    }

    public String getGym_title() {
        return gym_title;
    }

    public void setGym_title(String gym_title) {
        this.gym_title = gym_title;
    }
}

