package com.ct.fitorto.gcm;

import android.content.Context;
import android.os.AsyncTask;

import com.ct.fitorto.preferences.PreferenceManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by Ashvini on 8/4/2015.
 */
public class InstanceIdHelper {
    private final Context mContext;
    private PreferenceManager manager;


    public InstanceIdHelper(Context context) {
        mContext = context;
        manager = new PreferenceManager(context);
    }

    /**
     * Register for GCM
     *
     * @param senderId the project id used by the app's server
     */
    public void getGcmTokenInBackground(final String senderId) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String token = InstanceID.getInstance(mContext).getToken(senderId,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    manager.putPreferenceValues(manager.GCM_TOKEN, token);
                    // Save the token in the address book
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    /**
     * Unregister by deleting the token
     *
     * @param senderId the project id used by the app's server
     */
    public void deleteGcmTokeInBackground(final String senderId) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    InstanceID.getInstance(mContext).deleteToken(senderId,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                } catch (final IOException e) {

                }
                return null;
            }
        }.execute();
    }
}
