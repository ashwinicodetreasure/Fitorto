package com.ct.fitorto.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.ct.fitorto.R;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.Filter;


/**
 * Created by Ashwini on 12/21/2015.
 */
public class FilterActivity extends BaseActivity implements View.OnClickListener {

    public static final String FILTER_SORT = "FILTER";
    public static final String FILTER_FEES = "FILTER_FEES";
    public static final String FILTER_RATING = "FILTER_RATING";

    private Button mFilterButton;
    private Button mClearFilterButton;
    private Button selectedSort;
    private Button selectedFees;
    private Button selectedRating;
    private Button mFeesButton;
    private Button mBelowButton;
    private Button mBetweenButton;
    private Button mAboveButton;
   // private Button mRecommnationButton;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private RatingBar ratingBar;
    private float Rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initToolbar(true);
        initLayout();
        initClickListeners();
    }


    private void initClickListeners() {
        mFilterButton.setOnClickListener(this);
        mClearFilterButton.setOnClickListener(this);
        mFeesButton.setOnClickListener(this);
        //mRecommnationButton.setOnClickListener(this);
        mBelowButton.setOnClickListener(this);
        mBetweenButton.setOnClickListener(this);
        mAboveButton.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
    }

    private void initLayout() {
        mFilterButton = (Button) findViewById(R.id.btn_filter);
        mClearFilterButton = (Button) findViewById(R.id.btn_clear_filter);
        mFeesButton = (Button) findViewById(R.id.btn_fees);
        //mRecommnationButton = (Button) findViewById(R.id.btn_rec);
        mBelowButton= (Button) findViewById(R.id.btn_below_200);
        mBetweenButton= (Button) findViewById(R.id.btn_between_500);
        mAboveButton= (Button) findViewById(R.id.btn_above_500);

        btn_1= (Button) findViewById(R.id.btn_1);
        btn_2= (Button) findViewById(R.id.btn_2);
        btn_3= (Button) findViewById(R.id.btn_3);
        btn_4= (Button) findViewById(R.id.btn_4);
        btn_5= (Button) findViewById(R.id.btn_5);

        ratingBar= (RatingBar) findViewById(R.id.ratingBar);

        if(SearchResultActivity.mFilter!=null){
            selectedFees=SearchResultActivity.mFilter.getFees();
            caseCheckSelectedFeeesButton(SearchResultActivity.mFilter.getFees().getId());
            selectedSort=SearchResultActivity.mFilter.getSortBy();
            caseCheckSelectedSortButton(SearchResultActivity.mFilter.getSortBy().getId());
            selectedRating=SearchResultActivity.mFilter.getRating();
            caseCheckSelectedRatingButton(SearchResultActivity.mFilter.getRating().getId());
            //ratingBar.setRating(SearchResultActivity.mFilter.getRating());
        }else{
            selectedFees=mFeesButton;
            selectedSort=mClearFilterButton;
            selectedRating=mClearFilterButton;
            //ratingBar.setRating(0f);
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Rating = rating;
            }
        });
    }

    private void caseCheckSelectedRatingButton(int id) {
        switch (id){
            case R.id.btn_1:
                setRatingSelected(btn_1, true);
                break;
            case R.id.btn_2:
                setRatingSelected(btn_2, true);
                break;
            case R.id.btn_3:
                setRatingSelected(btn_3, true);
                break;
            case R.id.btn_4:
                setRatingSelected(btn_4, true);
                break;
            case R.id.btn_5:
                setRatingSelected(btn_5, true);
                break;
        }
    }

    private void caseCheckSelectedSortButton(int id) {
        switch (id){
            case R.id.btn_fees:
                setSelected(mFeesButton, true);
                break;
            /*case R.id.btn_rec:
                setSelected(mRecommnationButton, true);
                break;*/
        }
    }

    private void caseCheckSelectedFeeesButton(int id) {
        switch (id){
            case R.id.btn_below_200:
                setFeesSelected(mBelowButton, true);
                break;
            case R.id.btn_between_500:
                setFeesSelected(mBetweenButton,true);
                break;
            case R.id.btn_above_500:
                setFeesSelected(mAboveButton,true);
                break;
        }
    }

    private void setSelected(View view, boolean isSelected) {
        if (view instanceof Button) {
            if (isSelected) {
                setSelected(selectedSort, false);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.bbuton_info_rounded));
                ((Button) view).setTextColor(Color.WHITE);
                selectedSort = (Button) view;
            } else {
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.bbutton_transpaent));
                ((Button) view).setTextColor(Color.BLACK);
            }
        }
    }

    private void setFeesSelected(View view, boolean isSelected) {
        if (view instanceof Button) {
            if (isSelected) {
                setFeesSelected(selectedFees, false);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.bbuton_info_rounded));
                ((Button) view).setTextColor(Color.WHITE);
                selectedFees = (Button) view;

            } else {
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.bbutton_transpaent));
                ((Button) view).setTextColor(Color.BLACK);
            }
        }
    }

    private void setRatingSelected(View view, boolean isSelected) {
        if (view instanceof Button) {
            if (isSelected) {
                setRatingSelected(selectedRating, false);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.bbuton_info_rounded));
                ((Button) view).setTextColor(Color.WHITE);
                selectedRating = (Button) view;
            } else {
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.bbutton_transpaent));
                ((Button) view).setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fees:
                setSelected(mFeesButton, true);
                selectedSort=mFeesButton;
                break;
           /* case R.id.btn_rec:
                setSelected(mRecommnationButton, true);
                selectedSort=mRecommnationButton;
                break;*/
            case R.id.btn_below_200:
                setFeesSelected(mBelowButton, true);
                selectedFees=mBelowButton;
                break;
            case R.id.btn_between_500:
                setFeesSelected(mBetweenButton,true);
                selectedFees=mBetweenButton;
                break;
            case R.id.btn_above_500:
                setFeesSelected(mAboveButton,true);
                selectedFees=mAboveButton;
                break;
            case R.id.btn_1:
                setRatingSelected(btn_1, true);
                selectedRating=btn_1;
                break;
            case R.id.btn_2:
                setRatingSelected(btn_2, true);
                selectedRating=btn_2;
                break;
            case R.id.btn_3:
                setRatingSelected(btn_3, true);
                selectedRating=btn_3;
                break;
            case R.id.btn_4:
                setRatingSelected(btn_4, true);
                selectedRating=btn_4;
                break;
            case R.id.btn_5:
                setRatingSelected(btn_5, true);
                selectedRating=btn_5;
                break;
            case R.id.btn_filter:
                applyFilter();
                /*Intent intent = new Intent();
                intent.putExtra(FILTER_SORT, selectedSort.getId());
                intent.putExtra(FILTER_FEES,selectedFees.getId());
                intent.putExtra(FILTER_RATING,selectedRating.getId());
                setResult(ApplicationData.REQUEST_CODE_FILTER_ACTIVITY, intent);*/
                finishActivity();
                break;
            case R.id.btn_clear_filter:
                removeFilter();
                break;
        }
    }

    private void removeFilter() {
        SearchResultActivity.mFilter = null;
        finishActivity();
    }
    
    private void applyFilter() {
        SearchResultActivity.mFilter = new Filter();
        SearchResultActivity.mFilter.setSortBy(selectedSort);
        SearchResultActivity.mFilter.setFees(selectedFees);
        SearchResultActivity.mFilter.setRating(selectedRating);
    }

    private void finishActivity() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
