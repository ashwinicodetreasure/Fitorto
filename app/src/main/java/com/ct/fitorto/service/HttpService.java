package com.ct.fitorto.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ct.fitorto.activity.CityActivity;
import com.ct.fitorto.model.FitortoUser;
import com.ct.fitorto.model.JsonResponseUser;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by codetreasure on 8/23/16.
 */
public class HttpService extends IntentService {

    private static String TAG = HttpService.class.getSimpleName();
    private PreferenceManager manager;

    public HttpService() {
        super(HttpService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String otp = intent.getStringExtra("otp");
            verifyOtp(otp);
        }
    }

    /**
     * Posting the OTP to server and activating the user
     *
     * @param otp otp received in the SMS
     */
    private void verifyOtp(final String otp) {
        manager = new PreferenceManager(this);
        String userId = manager.getPreferenceValues(manager.PREF_USER_UserId);
        Call<JsonResponseUser> call = ApiClientMain.getApiClient().verifyOTP(userId, otp);
        call.enqueue(new Callback<JsonResponseUser>() {
            @Override
            public void onResponse(Call<JsonResponseUser> call, Response<JsonResponseUser> response) {
                if (response.body() != null) {
                    JsonResponseUser user = response.body();
                    if (user.getData() != null) {
                        if (user.getData().size() > 0) {
                            FitortoUser fitortoUser = user.getData().get(0);
                            if (fitortoUser != null) {
                                if (fitortoUser.getIsActive().equals("1")) {
                                    manager.putPreferenceValues(ApplicationData.USER_VERIFIED,"1");
                                    Intent i = new Intent(HttpService.this, CityActivity.class);
                                    i.putExtra(ApplicationData.IS_INITIAL, true);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                }else {
                                    Toast.makeText(HttpService.this, "Wrong Otp.Please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponseUser> call, Throwable t) {
                Toast.makeText(HttpService.this, "Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

