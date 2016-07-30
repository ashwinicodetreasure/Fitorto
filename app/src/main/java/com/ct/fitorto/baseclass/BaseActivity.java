package com.ct.fitorto.baseclass;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ct.fitorto.R;


/**
 * Created by Ashwini on 11/20/2015.
 */
public class BaseActivity extends AppCompatActivity {



    private ProgressDialog mProgressDialog;

    protected Toolbar initToolbar(boolean homeAsUpEnabled) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (homeAsUpEnabled) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
               return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    protected void showProgressDialog(String message,Boolean cancelable){
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(String.valueOf(message));
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.show();
    }

    protected void cancelProgressDialog(){
        try {
            if ((this.mProgressDialog != null) && this.mProgressDialog.isShowing()) {
                this.mProgressDialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            this.mProgressDialog = null;
        }
    }


    protected void showToastMessage(String message) {
    }
}
