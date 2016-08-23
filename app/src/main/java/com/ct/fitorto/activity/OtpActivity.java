package com.ct.fitorto.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.JsonResponseFollow;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.service.HttpService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends BaseActivity implements View.OnClickListener {

    private EditText mOTPEditText;
    private Button mConfirmButton;
    private Button mResendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initToolbar(true);
        initView();
    }

    private void initView() {
        mOTPEditText = (EditText) findViewById(R.id.edtOtp);
        mConfirmButton = (Button) findViewById(R.id.btnConfirm);
        mResendButton = (Button) findViewById(R.id.btnResend);
        mConfirmButton.setOnClickListener(this);
        mResendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnVerify:
                verifyOtp();
                break;
            case R.id.btnResend:
                resendOtp();
                break;
        }
    }

    private void resendOtp() {
        PreferenceManager manager = new PreferenceManager(this);
        String userId = manager.getPreferenceValues(manager.PREF_USER_UserId);
        Call<JsonResponseFollow> call = ApiClientMain.getApiClient().resendOTP(userId);
        call.enqueue(new Callback<JsonResponseFollow>() {
            @Override
            public void onResponse(Call<JsonResponseFollow> call, Response<JsonResponseFollow> response) {

            }

            @Override
            public void onFailure(Call<JsonResponseFollow> call, Throwable t) {

            }
        });
    }

    private void verifyOtp() {
        String otp = mOTPEditText.getText().toString().trim();
        if (!otp.isEmpty()) {
            Intent grapprIntent = new Intent(getApplicationContext(), HttpService.class);
            grapprIntent.putExtra("otp", otp);
            startService(grapprIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }
    }
}
