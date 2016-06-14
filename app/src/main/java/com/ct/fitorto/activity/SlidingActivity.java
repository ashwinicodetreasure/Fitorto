package com.ct.fitorto.activity;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.Slidingimage_Adapter;
import com.ct.fitorto.model.FitortoUser;
import com.ct.fitorto.model.JsonResponseSocial;
import com.ct.fitorto.model.JsonResponseUser;
import com.ct.fitorto.network.ApiClient;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
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
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ashwini on 5/11/2016.
 */
public class SlidingActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static ViewPager mpager;
    private static int currentpage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] Images = {R.drawable.a, R.drawable.b, R.drawable.c};
    private static final String[] string = {"The secret of getting ahead is getting started.", "Reading is to the mind what exercise is to the body", "Get comfortable with being uncomfortable!"};
    private ArrayList<Integer> ImageArray = new ArrayList<Integer>();
    private ArrayList<String> stringArrayList = new ArrayList<String>();
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 356;
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private Button loginbtn;
    private Button goolgeLogin;
    private TextView skipbtn;
    private Button signup;
    private Button fbLogin;
    private Dialog login;
    private List<FitortoUser> user = new ArrayList<>();

    //private List<FitortoUser> data=new ArrayList<>();
    private String username = "";
    private String password = "";
    private PreferenceManager preferenceManager;
    Permission[] permissions = new Permission[]{
            Permission.USER_STATUS,
            Permission.EMAIL,
    };
    private SimpleFacebook mSimpleFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.slider_main);

        preferenceManager = new PreferenceManager(SlidingActivity.this);
        String email = preferenceManager.getPreferenceValues(preferenceManager.PREF_CLIENT_EMAIL);
        String name = preferenceManager.getPreferenceValues(preferenceManager.PREF_CLIENT_NAME);
        String city = preferenceManager.getPreferenceValues(preferenceManager.PREF_City);

        if (!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(name) && !TextUtils.isEmpty(city) ) {
            Intent intent = new Intent(SlidingActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }




        // hashkey();
        init();

        googleLoginOption();
        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(getString(R.string.facebook_app_id))
                .setNamespace("")
                .setPermissions(permissions)
                .build();
        SimpleFacebook.setConfiguration(configuration);

        loginbtn = (Button) findViewById(R.id.Login);
        loginbtn.setOnClickListener(this);
        skipbtn = (TextView) findViewById(R.id.skip_button);
        skipbtn.setOnClickListener(this);
        signup = (Button) findViewById(R.id.sign_up);
        signup.setOnClickListener(this);
        goolgeLogin=(Button)findViewById(R.id.google_login);
        goolgeLogin.setOnClickListener(this);
        fbLogin=(Button)findViewById(R.id.fb_login);
        fbLogin.setOnClickListener(this);



    }

    private void init() {
        for (int i = 0; i < Images.length; i++)
            ImageArray.add(Images[i]);
        for (int i = 0; i < string.length; i++)
            stringArrayList.add(string[i]);

        mpager = (ViewPager) findViewById(R.id.pager);
        mpager.setAdapter(new Slidingimage_Adapter(SlidingActivity.this, ImageArray, stringArrayList));


        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mpager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density); //set radius for indicator
        NUM_PAGES = Images.length;

        final Handler handler = new Handler(); //auto slide viewpager
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentpage == NUM_PAGES) {
                    currentpage = 0;
                }

                mpager.setCurrentItem(currentpage++, true);

            }
        };

        Timer swipetime = new Timer();
        swipetime.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);

            }
        }, 3000, 3000);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentpage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }




    private void userLogin() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        Call<JsonResponseUser> response = apiClient.operation(txtUsername.getText().toString(), txtPassword.getText().toString());

        response.enqueue(new Callback<JsonResponseUser>() {

            @Override
            public void onResponse(Call<JsonResponseUser> call, Response<JsonResponseUser> response) {
                JsonResponseUser resp = response.body();
              //  FitortoUser fu= (FitortoUser) resp.getData();
                List<FitortoUser> fu= (ArrayList<FitortoUser>) resp.getData();



                    if (resp.getStatus().equals("1")) {
                       /* if(resp.getData().size()>0) {
                        preferenceManager.putPreferenceValues(preferenceManager.PREF_USER_Email, fu.getEmailID());
                        preferenceManager.putPreferenceValues(preferenceManager.PREF_USER_UserId, fu.getUserID());*/



                        Intent link = new Intent(SlidingActivity.this, CityActivity.class);
                        startActivity(link);

                        login.dismiss();
                    } else {
                        Toast.makeText(SlidingActivity.this, "Please Enter Valid username & password", Toast.LENGTH_SHORT).show();
                    }
                //}
            }

            @Override
            public void onFailure(Call<JsonResponseUser> call, Throwable t) {


                Log.d("Error", "failed");
                Toast.makeText(SlidingActivity.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }
   /* public void hashkey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ct.fitorto", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }*/
    //Facebook

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
                        preferenceManager.putPreferenceValues(preferenceManager.PREF_CLIENT_EMAIL,response.getEmail());
                        preferenceManager.putPreferenceValues(preferenceManager.PREF_CLIENT_NAME,response.getName());
                        preferenceManager.putPreferenceValues(preferenceManager.PREF_USER_GENDER,response.getGender());
                        preferenceManager.putPreferenceValues(preferenceManager.USER_IMAGE_LINK,response.getPicture().toString());
                        socialLogin();


                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onException(Throwable throwable) {


                Toast.makeText(SlidingActivity.this,throwable.getMessage().toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail(String reason) {
                String re = reason;
                Toast.makeText(SlidingActivity.this, re, Toast.LENGTH_LONG).show();
            }
        });
    }
    //Google Login
   public void googleLoginOption(){
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
            preferenceManager.putPreferenceValues(preferenceManager.PREF_CLIENT_EMAIL,acct.getEmail());
            preferenceManager.putPreferenceValues(preferenceManager.PREF_CLIENT_NAME,acct.getDisplayName());
            //preferenceManager.putPreferenceValues(preferenceManager.PREF_USER_PHONE,acct.ge);
            preferenceManager.putPreferenceValues(preferenceManager.USER_IMAGE_LINK,acct.getPhotoUrl().toString());
            socialLogin();



        } else {

            Toast.makeText(SlidingActivity.this, "Login error Please try again", Toast.LENGTH_LONG).show();
        }
    }

    private void socialLogin() {
       final String email = preferenceManager.getPreferenceValues(preferenceManager.PREF_CLIENT_EMAIL);
        final String name = preferenceManager.getPreferenceValues(preferenceManager.PREF_CLIENT_NAME);
        final String image = preferenceManager.getPreferenceValues(preferenceManager.USER_IMAGE_LINK);
        final String city = preferenceManager.getPreferenceValues(preferenceManager.PREF_City);


        Call<JsonResponseSocial> response = ApiClientMain.getApiClient().getResponseSocial(name,email,"",image,"");

        response.enqueue(new Callback<JsonResponseSocial>() {

            @Override
            public void onResponse(Call<JsonResponseSocial> call, Response<JsonResponseSocial> response) {
                JsonResponseSocial resp = response.body();
                if(resp.getStatus().equals("1")){

                    Intent i=new Intent(SlidingActivity.this,CityActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    Toast.makeText(SlidingActivity.this,"Welcom"+" "+name,Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(SlidingActivity.this, "Please Enter Valid username & password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonResponseSocial> call, Throwable t) {


                Log.d("Error", "failed");
                Toast.makeText(SlidingActivity.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop auto scroll when onPause

    }




    @Override
    public void onClick(View v) {
        //if (v == loginbtn) {
        switch (v.getId()) {
            case R.id.Login:
                // Create Object of Dialog class
                login = new Dialog(this);
                login.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // Set GUI of login screen
                login.setContentView(R.layout.login_dialog);

                // Init button of login GUI
                btnLogin = (Button) login.findViewById(R.id.btnLogin);
                //Button btnCancel = (Button) login.findViewById(R.id.btnCancel);

                txtUsername = (EditText) login.findViewById(R.id.txtUsername);
                txtPassword = (EditText) login.findViewById(R.id.txtPassword);
                // Attached listener for login GUI button
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userLogin();

                    }
                });
            /*btnCancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    login.dismiss();
                }
            });*/

                // Make dialog box visible.\
                login.show();
                login.getWindow().setLayout(700, 500);

                break;

            case R.id.skip_button:

                Intent intent = new Intent(SlidingActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.sign_up:
                /*Intent intent1 = new Intent(SlidingActivity.this, SignupActivity.class);
                startActivity(intent1);*/
                Intent intent1 = new Intent(SlidingActivity.this, SignupActivity.class);
                startActivity(intent1);
                break;
            case R.id.google_login:
                signIn();
                break;
            case R.id.fb_login:
                    loginFb();
                break;
        }


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}



