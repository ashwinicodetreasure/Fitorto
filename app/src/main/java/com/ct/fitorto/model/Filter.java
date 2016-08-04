package com.ct.fitorto.model;

import android.widget.Button;

/**
 * Created by Ashwini on 12/22/2015.
 */
public class Filter {

    private Button SortBy;
    private Button Fees;
    private Button Rating;

    public Filter() {
    }

    public Button getSortBy() {
        return SortBy;
    }

    public void setSortBy(Button sortBy) {
        SortBy = sortBy;
    }

    public Button getFees() {
        return Fees;
    }

    public void setFees(Button fees) {
        Fees = fees;
    }

    public Button getRating() {
        return Rating;
    }

    public void setRating(Button rating) {
        Rating = rating;
    }
}