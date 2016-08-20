package com.ct.fitorto.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.custom.GradientBackgroundPainter;
import com.ct.fitorto.gcm.InstanceIdHelper;
import com.ct.fitorto.model.FitortoUser;
import com.ct.fitorto.model.JsonResponseUser;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;
import com.ct.fitorto.utils.PatternUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Ashwini on 4/28/2016.
 */
public class SignupActivity extends Activity implements View.OnClickListener {

    private EditText user_email;
    private GradientBackgroundPainter gradientBackgroundPainter;
    private EditText user_pass;
    private EditText user_name;
    //   private EditText user_num;
    private EditText user_phone;
    private Button signup;
    private TextView allogin;
    private PreferenceManager preferenceManager;
    private RelativeLayout rlProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        View backgroundImage = findViewById(R.id.root_view);
        final int[] drawables = new int[3];
        drawables[0] = R.drawable.gradient_3;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_1;

        gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage, drawables);
        gradientBackgroundPainter.start();
        user_email = (EditText) findViewById(R.id.edtEmail);
        user_pass = (EditText) findViewById(R.id.edtPassword);
        user_name = (EditText) findViewById(R.id.edtName);
        user_phone = (EditText) findViewById(R.id.edtPhone);
        //  user_num=(EditText)findViewById(R.id.phone_no);
        preferenceManager = new PreferenceManager(this);

        allogin = (TextView) findViewById(R.id.tvBackLogin);
        allogin.setOnClickListener(this);

        signup = (Button) findViewById(R.id.btnLogin);
        signup.setOnClickListener(this);
        rlProgressBar = (RelativeLayout) findViewById(R.id.rlProgressBar);
        gcm();
    }

    private void gcm() {
        InstanceIdHelper instanceIdHelper = new InstanceIdHelper(SignupActivity.this);
        instanceIdHelper.getGcmTokenInBackground(LoginActivity.projectId);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin:
                registerUser();
                break;
            case R.id.tvBackLogin:
                finish();
                SignupActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;


        }
    }

    private void registerUser() {

        user_name.setError(null);
        user_email.setError(null);
        user_pass.setError(null);
        user_phone.setError(null);

        String userName = user_name.getText().toString().trim();
        String emailID = user_email.getText().toString().trim();
        String password = user_pass.getText().toString().trim();
        String phone = user_phone.getText().toString().trim();
        //String phoneNo=user_num.getText().toString().trim();


        String MobilePattern = "[0-9]{10}";

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(userName)) {
            user_name.setError(getString(R.string.error_field_required));
            focusView = user_name;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            user_pass.setError(getString(R.string.error_invalid_password));
            focusView = user_pass;
            cancel = true;
        }
        if (TextUtils.isEmpty(phone)) {
            user_phone.setError(getString(R.string.error_field_required));
            focusView = user_phone;
            cancel = true;
        } else if (!isPhoneNoValid(phone)) {
            user_phone.setError(getString(R.string.error_invalid_phone));
            focusView = user_phone;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(emailID)) {
            user_email.setError(getString(R.string.error_field_required));
            focusView = user_email;
            cancel = true;
        } else if (!LoginActivity.isEmailValid(emailID)) {
            user_email.setError(getString(R.string.error_invalid_email));
            focusView = user_email;
            cancel = true;
        }


        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            registerUserData();
        }
    }

    private void registerUserData() {
        rlProgressBar.setVisibility(View.VISIBLE);
        String gcmToken = preferenceManager.getPreferenceValues(preferenceManager.GCM_TOKEN);
        Call<JsonResponseUser> response = ApiClientMain.getApiClient().registerUser(user_email.getText().toString(), user_email.getText().toString(),
                user_pass.getText().toString(), user_phone.getText().toString(), gcmToken, "");
        response.enqueue(new Callback<JsonResponseUser>() {
            @Override
            public void onResponse(Call<JsonResponseUser> call, retrofit2.Response<JsonResponseUser> response) {
                rlProgressBar.setVisibility(View.GONE);
                JsonResponseUser resp = response.body();
                if (resp != null) {
                    if (resp.getData() != null) {
                        if (resp.getData().size() > 0) {
                            saveUserDetails(resp.getData());
                            Intent i = new Intent(SignupActivity.this, CityActivity.class);
                            i.putExtra(ApplicationData.IS_INITIAL,true);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        } else {
                            Toast.makeText(SignupActivity.this, resp.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, resp.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponseUser> call, Throwable t) {
                rlProgressBar.setVisibility(View.GONE);
                //Log.d("Error", "failed");
                //Toast.makeText(SignupActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isPhoneNoValid(String phoneNo) {
        return !TextUtils.isEmpty(phoneNo) && PatternUtil.INDIAN_MOBILE_NUMBER.matcher(phoneNo).matches();
    }

    public void saveUserDetails(List<FitortoUser> data) {
        if (data.size() > 0) {
            FitortoUser user = data.get(0);
            preferenceManager.putPreferenceValues(preferenceManager.PREF_USER_UserId, user.getUserID());
        }
    }

}



