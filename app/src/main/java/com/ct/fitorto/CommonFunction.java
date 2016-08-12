package com.ct.fitorto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;

import com.ct.fitorto.activity.LoginActivity;

/**
 * Created by Ashwini on 6/10/2016.
 */
public class CommonFunction {
    Activity activity;

    public CommonFunction(Activity activity) {
        this.activity = activity;
    }

    public void showDialog() {
        new AlertDialog.Builder(activity)
                .setMessage("Please Login to continue")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Intent intent = new Intent(activity, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public boolean checkFirstRun() {
        boolean isFirstRun = activity.getSharedPreferences("myPreference", activity.MODE_PRIVATE).getBoolean("isFirstRun", true);
        return isFirstRun;
    }

    public static void showProgressDialog(ProgressDialog mProgressDialog,Activity activity,String message,Boolean cancelable){
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setMessage(String.valueOf(message));
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.show();
    }

    public static void cancelProgressDialog(ProgressDialog mProgressDialog){
        try {
            if ((mProgressDialog != null) &&mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            mProgressDialog = null;
        }
    }
}
