package com.ct.fitorto.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wellslock on 02-06-2015.
 */
public class PreferenceManager {

    public static final String USER_OBJECT_ID = "object_id";
    public static final String USER_CREDITES = "credits";
    public static final String PREF_LAST_NAME = "last_name";
    public static final String PREF_FIRST_NAME = "first_name";
    public static final String USER_LOGIN_STATUS = "user_login_status";
    public static final String USER_IMAGE_LINK = "image_link";
    public static final String PREF_CLIENT_NAME = "client_name";
    public final String MY_PREFS_NAME = "myPreference";

    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    public final String PREF_IS_FAITH_WORKER = "pref_faith_worker";
    public final String PREF_EMAIL = "pref_email";
    public final String PREF_PASSWORD = "pref_password";
    public final String PREF_CLIENT_EMAIL = "pref_client_email";
    public final String PREF_INCOMING_CLIENT_EMAIL = "pref_incoming_client_email";
    public final String PREF_CLIENT_PASSWORD = "pref_client_password";
    public final String PREF_USER_TYPE = "pref_user_type";
    public final String PREF_USER_PHONE = "pref_user_PHONE";
    public final String PREF_USER_GENDER = "pref_user_GENDER";
    public final String PREF_City="PREF_City";

    //User Details
    public final String PREF_USER_Email = "pref_user_Email";
    public final String PREF_USER_UserId = "pref_user_UserId";
    public final String PREF_USER_FeedId = "pref_user_FeedId";
    public PreferenceManager(Context context) {
        this.context = context;
        this.editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        this.prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);

    }

    public void putPreferenceBoolValues(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putPreferenceValues(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }


    public void putPreferenceIntValues(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

   /* public void putPreferencefloatValues(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public float getPreferencefloatValues(String key) {
        return prefs.getFloat(key, 0);
    }
*/
    public boolean getPreferenceBoolValues(String key) {
        return prefs.getBoolean(key, true);
    }

    public int getPreferenceIntValues(String key) {
        return prefs.getInt(key, 0);
    }

    public String getPreferenceValues(String key) {
        return prefs.getString(key, "");
    }

    public void clearSharedPreferance() {
        prefs.edit().clear().commit();
    }

    public void putPreferencDoubleValues(String key, double value) {
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.commit();
    }

    public double getPreferencDoubleValues(String key,double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }


}
