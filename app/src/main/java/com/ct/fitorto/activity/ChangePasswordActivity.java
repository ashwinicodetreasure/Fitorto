package com.ct.fitorto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.JsonResponseFollow;
import com.ct.fitorto.model.JsonResponseUser;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by codetreasure on 8/23/16.
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText edtCurrentPassword;
    private EditText edtNewPassword;
    private EditText edtTypePassword;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initToolbar(true);
        initView();
        setTitle("Change Password");
    }

    private void initView() {
        findViewById(R.id.tvForgotPassword).setOnClickListener(this);
        findViewById(R.id.btnSave).setOnClickListener(this);
        edtCurrentPassword = (EditText) findViewById(R.id.edtCurrentPassword);
        edtNewPassword = (EditText) findViewById(R.id.edtNewPassword);
        edtTypePassword = (EditText) findViewById(R.id.edtTypePassword);
        preferenceManager = new PreferenceManager(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvChangePassword:
                Intent intent = new Intent(ChangePasswordActivity.this, ForgotPasswordWebview.class);
                startActivity(intent);
                break;
            case R.id.btnSave:
                changePassword();
                break;
        }
    }

    private void changePassword() {
        String currentPassword = edtCurrentPassword.getText().toString();
        String newPassword = edtNewPassword.getText().toString();
        String retypePassword = edtTypePassword.getText().toString();

        edtCurrentPassword.setError(null);
        edtNewPassword.setError(null);
        edtTypePassword.setError(null);

        // Store values at the time of the login attempt.


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(currentPassword)) {
            edtCurrentPassword.setError(getString(R.string.error_field_required));
            focusView = edtCurrentPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(newPassword)) {
            edtNewPassword.setError(getString(R.string.error_field_required));
            focusView = edtNewPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(retypePassword)) {
            edtTypePassword.setError(getString(R.string.error_field_required));
            focusView = edtTypePassword;
            cancel = true;
        }

        if (!TextUtils.isEmpty(newPassword) && !TextUtils.isEmpty(retypePassword)) {
            if (!retypePassword.equals(newPassword)) {
                edtTypePassword.setError(getString(R.string.error_pass_does_not_matched));
                focusView = edtTypePassword;
                cancel = true;
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            showProgressDialog("Please wait..", false);
            String userId = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
            Call<JsonResponseFollow> response = ApiClientMain.getApiClient().changePassword(userId, currentPassword, newPassword);
            response.enqueue(new Callback<JsonResponseFollow>() {

                @Override
                public void onResponse(Call<JsonResponseFollow> call, Response<JsonResponseFollow> response) {
                    cancelProgressDialog();
                    JsonResponseFollow resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            Toast.makeText(ChangePasswordActivity.this, resp.getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, resp.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseFollow> call, Throwable t) {
                    cancelProgressDialog();
                    //Log.d("Error", "failed");
                    Toast.makeText(ChangePasswordActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
