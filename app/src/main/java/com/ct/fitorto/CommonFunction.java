package com.ct.fitorto;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;

import com.ct.fitorto.activity.SlidingActivity;

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
                        Intent intent = new Intent(activity, SlidingActivity.class);
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
}
