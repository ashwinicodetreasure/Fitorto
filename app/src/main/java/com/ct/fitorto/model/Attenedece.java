package com.ct.fitorto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by codetreasure on 8/21/16.
 */
public class Attenedece {
    @SerializedName("isCheckOut")
    @Expose
    private String isCheckOut;

    /**
     * @return The isCheckOut
     */
    public String getIsCheckOut() {
        return isCheckOut;
    }

    /**
     * @param isCheckOut The isCheckOut
     */
    public void setIsCheckOut(String isCheckOut) {
        this.isCheckOut = isCheckOut;
    }

}
