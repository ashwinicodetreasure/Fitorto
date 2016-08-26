package com.ct.fitorto.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

public class SettingActivity extends BaseActivity implements View.OnClickListener {


    private TextView tvInviteFriend;
    private TextView tvEditProfile;
    private TextView tvChangePassword;
    private TextView tvTerms;
    private TextView tvLogout;
    private PreferenceManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initToolbar(true);
        setTitle("Settings");
    }

    private void initView() {
        tvInviteFriend = (TextView) findViewById(R.id.tvInviteFriend);
        tvEditProfile = (TextView) findViewById(R.id.tvEditProfile);
        tvChangePassword = (TextView) findViewById(R.id.tvChangePassword);
        tvTerms = (TextView) findViewById(R.id.tvTerms);
        tvLogout = (TextView) findViewById(R.id.tvLogout);
        tvInviteFriend.setOnClickListener(this);
        tvEditProfile.setOnClickListener(this);
        tvChangePassword.setOnClickListener(this);
        tvTerms.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        manager = new PreferenceManager(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvInviteFriend:
                String userFitortoId = manager.getPreferenceValues(manager.FITORTO_ID);
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Check out Fitorto Fitness App for your smart phone. Download it today from " + "https://play.google.com/store/apps/details?id=com.ct.fitorto";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Fitorto Fitness App");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case R.id.tvEditProfile:
                Intent i = new Intent(this, EditUserProfileActivity.class);
                startActivityForResult(i, ApplicationData.REQUEST_CODE_EDIT_PROFILE);
                break;
            case R.id.tvChangePassword:
                Intent i1 = new Intent(this, ChangePasswordActivity.class);
                startActivityForResult(i1, ApplicationData.REQUEST_CODE_EDIT_PROFILE);
                break;
            case R.id.tvTerms:
                Uri uri = Uri.parse("http://www.fitorto.com/terms.html");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.tvLogout:
                onLogoutClick();
                break;
        }

    }

    private void onLogoutClick() {
        manager.clearSharedPreferance();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
