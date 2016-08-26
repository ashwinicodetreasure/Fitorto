package com.ct.fitorto.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.custom.AnimationUtil;
import com.ct.fitorto.custom.GradientBackgroundPainter;
import com.ct.fitorto.fragments.DiscoverFragment;
import com.ct.fitorto.fragments.FeedFragment;
import com.ct.fitorto.gcm.InstanceIdHelper;
import com.ct.fitorto.model.FitortoUser;
import com.ct.fitorto.model.JsonResponseSocial;
import com.ct.fitorto.model.JsonResponseUser;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arvindshukla on 30/07/16.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private GradientBackgroundPainter gradientBackgroundPainter;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 356;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private ImageButton goolgeLogin;
    private TextView skipbtn;
    private ImageButton fbLogin;

    private List<FitortoUser> user = new ArrayList<>();
    public static String projectId = "251491466859";

    private RelativeLayout rlProgressBar;
    private String username = "";
    private String password = "";
    private PreferenceManager preferenceManager;
    Permission[] permissions = new Permission[]{
            Permission.USER_STATUS,
            Permission.EMAIL,
    };
    private SimpleFacebook mSimpleFacebook;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View backgroundImage = findViewById(R.id.root_view);

        final int[] drawables = new int[3];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;

        gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage, drawables);
        gradientBackgroundPainter.start();
        initView();
        googleLoginOption();
        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(getString(R.string.facebook_app_id))
                .setNamespace("")
                .setPermissions(permissions)
                .build();
        SimpleFacebook.setConfiguration(configuration);
        translateAnimation();
        gcm();
    }

    private void initView() {
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
       /* activity_signup = (Button) findViewById(R.id.sign_up);
        activity_signup.setOnClickListener(this);*/
        goolgeLogin = (ImageButton) findViewById(R.id.google_login);
        goolgeLogin.setOnClickListener(this);
        fbLogin = (ImageButton) findViewById(R.id.fb_login);
        fbLogin.setOnClickListener(this);
        findViewById(R.id.ibEmailLogin).setOnClickListener(this);
        findViewById(R.id.tvCreateAccount).setOnClickListener(this);
        findViewById(R.id.tvForgotPassword).setOnClickListener(this);
        rlProgressBar = (RelativeLayout) findViewById(R.id.rlProgressBar);
        preferenceManager = new PreferenceManager(this);
        String userId = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        String isVerified = preferenceManager.getPreferenceValues(ApplicationData.USER_VERIFIED);
        if (!TextUtils.isEmpty(userId)) {
            if (!TextUtils.isEmpty(isVerified)) {
                if (isVerified.equals("1")) {
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtra(ApplicationData.INDEX, FeedFragment.ID);
                    startActivity(i);
                } else {
                    Intent i = new Intent(LoginActivity.this, OtpActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtra(ApplicationData.INDEX, FeedFragment.ID);
                    startActivity(i);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibEmailLogin:
                fadeInAnimation();
                break;
            case R.id.google_login:
                signIn();
                break;
            case R.id.fb_login:
                loginFb();
                break;
            case R.id.btnLogin:
                userLogin();
                break;
            case R.id.tvCreateAccount:
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.tvForgotPassword:
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordWebview.class);
                startActivity(intent);
                break;
        }
    }

    public void translateAnimation() {
        setViewVisbility(false);
        Animation mAnimation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.translate_anim);
        findViewById(R.id.tvLogo).setVisibility(View.INVISIBLE);
        findViewById(R.id.tvSlogan).setVisibility(View.INVISIBLE);
        findViewById(R.id.tvLogo).startAnimation(mAnimation);
        findViewById(R.id.tvSlogan).startAnimation(mAnimation);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                findViewById(R.id.tvLogo).setVisibility(View.VISIBLE);
                findViewById(R.id.tvSlogan).setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fadeInAnimation();
            }
        });

    }

    private void fadeInAnimation() {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        findViewById(R.id.imgEmail).startAnimation(fadeInAnimation);
        findViewById(R.id.imgPassword).startAnimation(fadeInAnimation);
        findViewById(R.id.edtEmail).startAnimation(fadeInAnimation);
        findViewById(R.id.edtPassword).startAnimation(fadeInAnimation);
        findViewById(R.id.btnLogin).startAnimation(fadeInAnimation);
        findViewById(R.id.tvForgotPassword).startAnimation(fadeInAnimation);
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setViewVisbility(true);
            }
        });
    }

    public void setViewVisbility(boolean viewVisbility) {
        findViewById(R.id.imgEmail).setVisibility(viewVisbility ? View.VISIBLE : View.INVISIBLE);
        findViewById(R.id.imgPassword).setVisibility(viewVisbility ? View.VISIBLE : View.INVISIBLE);
        findViewById(R.id.edtEmail).setVisibility(viewVisbility ? View.VISIBLE : View.INVISIBLE);
        findViewById(R.id.edtPassword).setVisibility(viewVisbility ? View.VISIBLE : View.INVISIBLE);
        findViewById(R.id.btnLogin).setVisibility(viewVisbility ? View.VISIBLE : View.INVISIBLE);
        findViewById(R.id.tvForgotPassword).setVisibility(viewVisbility ? View.VISIBLE : View.INVISIBLE);
    }

    private void gcm() {
        InstanceIdHelper instanceIdHelper = new InstanceIdHelper(LoginActivity.this);
        instanceIdHelper.getGcmTokenInBackground(projectId);
    }


    private void userLogin() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        edtEmail.setError(null);
        edtPassword.setError(null);

        // Store values at the time of the login attempt.


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError(getString(R.string.error_invalid_password));
            focusView = edtPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError(getString(R.string.error_field_required));
            focusView = edtEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            edtEmail.setError(getString(R.string.error_invalid_email));
            focusView = edtEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            rlProgressBar.setVisibility(View.VISIBLE);
            String gcmToken = preferenceManager.getPreferenceValues(preferenceManager.GCM_TOKEN);
            Call<JsonResponseUser> response = ApiClientMain.getApiClient().login(email, password, gcmToken, "");
            response.enqueue(new Callback<JsonResponseUser>() {

                @Override
                public void onResponse(Call<JsonResponseUser> call, Response<JsonResponseUser> response) {
                    rlProgressBar.setVisibility(View.GONE);
                    JsonResponseUser resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            saveUserDetails(resp.getData());
                            Intent link = new Intent(LoginActivity.this, CityActivity.class);
                            link.putExtra(ApplicationData.IS_INITIAL, true);
                            link.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(link);

                        } else {
                            Toast.makeText(LoginActivity.this, resp.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseUser> call, Throwable t) {
                    rlProgressBar.setVisibility(View.GONE);
                    //Log.d("Error", "failed");
                    Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }


    private void loginFb() {
        final Profile.Properties properties = new Profile.Properties.Builder()
                .add(Profile.Properties.ID)
                .add(Profile.Properties.NAME)
                .add(Profile.Properties.EMAIL)
                .add(Profile.Properties.PICTURE)
                .build();
        mSimpleFacebook.login(new OnLoginListener() {
            @Override
            public void onLogin(String accessToken, List<Permission> list, List<Permission> list1) {
                String token = accessToken;
                mSimpleFacebook.getProfile(properties, new OnProfileListener() {
                    @Override
                    public void onComplete(Profile response) {
                        super.onComplete(response);
                        //showPhoneNumberDialog(response);

                        //Toast.makeText(MainActivity.this, response.getName(), Toast.LENGTH_LONG).show();
                        if (response != null) {
                           /* String imagelink = "https://graph.facebook.com/" + response.getId() + "/picture?type=large";
                            if (!TextUtils.isEmpty(response.getEmail())) {
                                preferenceManager.putPreferenceValues(preferenceManager.PREF_CLIENT_EMAIL, response.getEmail());
                            }
                            if (!TextUtils.isEmpty(response.getName())) {
                                preferenceManager.putPreferenceValues(preferenceManager.PREF_CLIENT_NAME, response.getName());
                            }
                            if (!TextUtils.isEmpty(response.getGender())) {
                                preferenceManager.putPreferenceValues(preferenceManager.PREF_USER_GENDER, response.getGender());
                            }
                            if (!TextUtils.isEmpty(response.getPicture())) {
                                preferenceManager.putPreferenceValues(preferenceManager.USER_IMAGE_LINK, imagelink);
                            }*/
                            socialFbLogin(response);
                        }
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onException(Throwable throwable) {
                Toast.makeText(LoginActivity.this, throwable.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String reason) {
                String re = reason;
                Toast.makeText(LoginActivity.this, re, Toast.LENGTH_LONG).show();
            }
        });
    }

    //Google Login
    public void googleLoginOption() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //sign_in_button.setSize(SignInButton.SIZE_STANDARD);
        //sign_in_button.setScopes(gso.getScopeArray());
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // client.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
           /* preferenceManager.putPreferenceValues(preferenceManager.PREF_CLIENT_EMAIL, acct.getEmail());
            preferenceManager.putPreferenceValues(preferenceManager.PREF_CLIENT_NAME, acct.getDisplayName());
            //preferenceManager.putPreferenceValues(preferenceManager.PREF_USER_PHONE,acct.ge);
            if (acct.getPhotoUrl() != null) {
                preferenceManager.putPreferenceValues(preferenceManager.USER_IMAGE_LINK, acct.getPhotoUrl().toString());
            }*/
            socialGoogleLogin(acct);

        } else {
            Toast.makeText(LoginActivity.this, "Login error Please try again", Toast.LENGTH_LONG).show();
        }
    }

    private void socialGoogleLogin(GoogleSignInAccount profile) {
        String imagelink = "";
        if (!TextUtils.isEmpty(profile.getEmail())) {
            preferenceManager.putPreferenceValues(preferenceManager.PREF_CLIENT_EMAIL, profile.getEmail());
        }
        if (!TextUtils.isEmpty(profile.getDisplayName())) {
            preferenceManager.putPreferenceValues(preferenceManager.PREF_CLIENT_NAME, profile.getDisplayName());
        }
        if (profile.getPhotoUrl() != null) {
            imagelink = profile.getPhotoUrl().toString();
            preferenceManager.putPreferenceValues(preferenceManager.USER_IMAGE_LINK, profile.getPhotoUrl().toString());
        }
        final String city = preferenceManager.getPreferenceValues(preferenceManager.PREF_City);
        String gcmToken = preferenceManager.getPreferenceValues(preferenceManager.GCM_TOKEN);
        Call<JsonResponseSocial> response = ApiClientMain.getApiClient().getResponseSocial(profile.getDisplayName(), profile.getEmail(), "", imagelink, "", gcmToken, "");
        response.enqueue(new Callback<JsonResponseSocial>() {

            @Override
            public void onResponse(Call<JsonResponseSocial> call, Response<JsonResponseSocial> response) {
                JsonResponseSocial resp = response.body();
                if (resp != null) {
                    if (resp.getStatus().equals("1")) {
                        saveUserDetails(resp.getData());
                        Intent i = new Intent(LoginActivity.this, CityActivity.class);
                        i.putExtra(ApplicationData.IS_INITIAL, true);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        Toast.makeText(LoginActivity.this, "Please Enter Valid username & password", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponseSocial> call, Throwable t) {
                Log.d("Error", "failed");
                Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void socialFbLogin(Profile profile) {
       /* final String email = preferenceManager.getPreferenceValues(preferenceManager.PREF_CLIENT_EMAIL);
        final String name = preferenceManager.getPreferenceValues(preferenceManager.PREF_CLIENT_NAME);
        final String image = preferenceManager.getPreferenceValues(preferenceManager.USER_IMAGE_LINK);*/

        //Todo Login failed when fb login with phone number
        String imagelink = "https://graph.facebook.com/" + profile.getId() + "/picture?type=large";

        if (!TextUtils.isEmpty(profile.getName())) {
            preferenceManager.putPreferenceValues(preferenceManager.PREF_CLIENT_NAME, profile.getName());
        }
        if (!TextUtils.isEmpty(profile.getGender())) {
            preferenceManager.putPreferenceValues(preferenceManager.PREF_USER_GENDER, profile.getGender());
        }
        if (!TextUtils.isEmpty(profile.getPicture())) {
            preferenceManager.putPreferenceValues(preferenceManager.USER_IMAGE_LINK, imagelink);
        }
        final String city = preferenceManager.getPreferenceValues(preferenceManager.PREF_City);
        String gcmToken = preferenceManager.getPreferenceValues(preferenceManager.GCM_TOKEN);
        if (!TextUtils.isEmpty(profile.getEmail())) {
            preferenceManager.putPreferenceValues(preferenceManager.PREF_CLIENT_EMAIL, profile.getEmail());
            Call<JsonResponseSocial> response = ApiClientMain.getApiClient().getResponseSocial(profile.getName(), profile.getEmail(), "", imagelink, "", gcmToken, "");
            response.enqueue(new Callback<JsonResponseSocial>() {

                @Override
                public void onResponse(Call<JsonResponseSocial> call, Response<JsonResponseSocial> response) {
                    JsonResponseSocial resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            saveUserDetails(resp.getData());
                            Intent i = new Intent(LoginActivity.this, CityActivity.class);
                            i.putExtra(ApplicationData.IS_INITIAL, true);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        } else {
                            Toast.makeText(LoginActivity.this, "Please Enter Valid username & password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseSocial> call, Throwable t) {
                    Log.d("Error", "failed");
                    Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            showEmailDialog();
        }
    }

    public void saveUserDetails(List<FitortoUser> data) {
        if (data.size() > 0) {
            FitortoUser user = data.get(0);
            preferenceManager.putPreferenceValues(ApplicationData.USER_VERIFIED, "1");
            preferenceManager.putPreferenceValues(preferenceManager.PREF_USER_UserId, user.getUserID());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop auto scroll when onPause

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void showEmailDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_email_layout);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        final EditText edtEmail = (EditText) dialog.findViewById(R.id.edtEmail);
        Button dialogButton = (Button) dialog.findViewById(R.id.btnVerify);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                if (!TextUtils.isEmpty(email)) {
                    if (isEmailValid(email)) {
                        updateEmailID(email);
                    }
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void updateEmailID(String email) {
        final String city = preferenceManager.getPreferenceValues(preferenceManager.PREF_City);
        String gcmToken = preferenceManager.getPreferenceValues(preferenceManager.GCM_TOKEN);
        final String name = preferenceManager.getPreferenceValues(preferenceManager.PREF_CLIENT_NAME);
        final String image = preferenceManager.getPreferenceValues(preferenceManager.USER_IMAGE_LINK);
        Call<JsonResponseSocial> response = ApiClientMain.getApiClient().getResponseSocial(name, email, "", image, "", gcmToken, "");
        response.enqueue(new Callback<JsonResponseSocial>() {

            @Override
            public void onResponse(Call<JsonResponseSocial> call, Response<JsonResponseSocial> response) {
                JsonResponseSocial resp = response.body();
                if (resp != null) {
                    if (resp.getStatus().equals("1")) {
                        saveUserDetails(resp.getData());
                        Intent i = new Intent(LoginActivity.this, CityActivity.class);
                        i.putExtra(ApplicationData.IS_INITIAL, true);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        Toast.makeText(LoginActivity.this, "Please Enter Valid username & password", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponseSocial> call, Throwable t) {
                Log.d("Error", "failed");
                Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
