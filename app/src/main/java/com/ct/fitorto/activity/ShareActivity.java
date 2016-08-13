package com.ct.fitorto.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.JsonResponseAddFeed;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.GUIUtils;
import com.ct.fitorto.utils.OnRevealAnimationListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Ashwini on 6/3/2016.
 */
public class ShareActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btngLink;
    private ImageView btncamera;
    private Dialog login;
    private Button btnLink;
    private Button sharebtn;
    private EditText txtLink;
    private EditText edcontent;
    private TextView tvlink;
    private ImageView ivimage;
    private Bitmap bitmap, thumbnail;
    private String picturePath;
    private File f;
    private ImageView ivCancel;
    private String link = "";
    private RelativeLayout rlContainer;
    private FloatingActionButton mFab;
    private ImageView ivRemove;
    private TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_activity);
        setToolabar();


        edcontent = (EditText) findViewById(R.id.edFeed);
        rlContainer = (RelativeLayout) findViewById(R.id.rlContainer);
        mFab = (FloatingActionButton) findViewById(R.id.activity_contact_fab);
        ivimage = (ImageView) findViewById(R.id.ivimage);
        tvlink = (TextView) findViewById(R.id.tvlink);
        ivRemove = (ImageView) findViewById(R.id.ivRemove);
        //tvUserName = (TextView) findViewById(R.id.tvUserName);
        ivRemove.setOnClickListener(this);

        btngLink = (ImageView) findViewById(R.id.ivLink);
        btngLink.setOnClickListener(this);

        btncamera = (ImageView) findViewById(R.id.ivcamera);
        btncamera.setOnClickListener(this);

        sharebtn = (Button) findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation();
            setupExitAnimation();
        } else {
            mFab.setVisibility(View.GONE);
            initViews();
        }
    }

    private void initViews() {
      /*  new Handler(Looper.getMainLooper()).post(() {
            Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
            animation.setDuration(300);
            *//*mLlContainer.startAnimation(animation);
            mIvClose.startAnimation(animation);
            mLlContainer.setVisibility(View.VISIBLE);
            mIvClose.setVisibility(View.VISIBLE);*//*
        });*/
        /*final Handler handler=new Handler();
        final Runnable r = new Runnable(){
            public void run() {*/
        Animation animation = AnimationUtils.loadAnimation(ShareActivity.this, android.R.anim.fade_in);
        animation.setDuration(300);
        findViewById(R.id.toolbar).startAnimation(animation);
        ivCancel.startAnimation(animation);
        findViewById(R.id.llShareContent).startAnimation(animation);
        findViewById(R.id.container).startAnimation(animation);
        findViewById(R.id.rlImage).startAnimation(animation);
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        findViewById(R.id.llShareContent).setVisibility(View.VISIBLE);
        findViewById(R.id.ivCancel).setVisibility(View.VISIBLE);
        findViewById(R.id.container).setVisibility(View.VISIBLE);
        findViewById(R.id.rlImage).setVisibility(View.VISIBLE);

       /* rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Focus the field.
                edcontent.requestFocus();
                // Show soft keyboard for the user to enter the value.
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edcontent, InputMethodManager.SHOW_IMPLICIT);

                findViewById(R.id.activity_contact_fab).setVisibility(View.INVISIBLE);
            }
        });*/
        // handler.postDelayed(this, 300);
       /*     }
        };
        handler.post(r);*/

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow(rlContainer);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void animateRevealShow(final View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        GUIUtils.animateRevealShow(this, rlContainer, mFab.getWidth() / 2, R.color.button_pressed,
                cx, cy, new OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {
                        initViews();
                    }
                });
    }


    private void setToolabar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            ivCancel = (ImageView) toolbar.findViewById(R.id.ivCancel);
            ivCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        onBackPressed();
                    } else {
                        backPressed();
                    }
                }
            });
           /* getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });*/
        }

    }


    private void finishThis() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            GUIUtils.animateRevealHide(this, rlContainer, R.color.button_pressed, mFab.getWidth() / 2,
                    new OnRevealAnimationListener() {
                        @Override
                        public void onRevealHide() {
                            finishThis();
                        }

                        @Override
                        public void onRevealShow() {

                        }
                    });
        } else {

        }
        //
    }


    private void backPressed() {
        super.onBackPressed();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitAnimation() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(getResources().getInteger(R.integer.animation_duration));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivcamera:
                selectImage();
                break;

            case R.id.ivLink:
                setLink();
                break;

            case R.id.sharebtn:
                uploadData();
                ivimage.setImageBitmap(null);
                tvlink.setText("");
                edcontent.setText("");
                break;
            /*case R.id.ivRemove:
                if(ivimage!=null){
                    picturePath="";
                    ivimage.setImageBitmap(null);
                    ivRemove.setVisibility(View.GONE);
                }
                break;*/
        }
    }

    private void uploadData() {
        String string = edcontent.getText().toString();
        if (!TextUtils.isEmpty(string)) {
            showProgressDialog("Please wait..", false);
            PreferenceManager preferenceManager = new PreferenceManager(ShareActivity.this);
            String userId = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
            RequestBody id = ApiClientMain.getStringRequestBody(userId);
            RequestBody content = ApiClientMain.getStringRequestBody(string);
            RequestBody link = ApiClientMain.getStringRequestBody(this.link);

            RequestBody flag = ApiClientMain.getStringRequestBody("1");
            RequestBody image = null;
            if (!TextUtils.isEmpty(picturePath)) {
                image = RequestBody.create(MediaType.parse(ApiClientMain.MEDIA_TYPE_IMAGE), new File(picturePath));
            } else {
//                image = RequestBody.create(MediaType.parse(ApiClientMain.MEDIA_TYPE_IMAGE), "");
                image = null;
            }

            Call<JsonResponseAddFeed> response = ApiClientMain.getApiClient().getResponseFeed(id, content, link, flag, image);
            response.enqueue(new Callback<JsonResponseAddFeed>() {
                @Override
                public void onResponse(Call<JsonResponseAddFeed> call, retrofit2.Response<JsonResponseAddFeed> response) {
                    //JsonResponseAddFeed resp = response.body();
                    //Toast.makeText(ShareActivity.this, "Shared", Toast.LENGTH_SHORT).show();
                    //finishThis();
                    cancelProgressDialog();
                    JsonResponseAddFeed resp = response.body();
                    if (resp != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            onBackPressed();
                        } else {
                            backPressed();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseAddFeed> call, Throwable t) {
                    cancelProgressDialog();
                    Toast.makeText(ShareActivity.this, "Something went wrong please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ShareActivity.this, "Please write something.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setLink() {
        login = new Dialog(this);
        login.requestWindowFeature(Window.FEATURE_NO_TITLE);
        login.setContentView(R.layout.link_dialog);
        btnLink = (Button) login.findViewById(R.id.btnLink);
        //Button btnCancel = (Button) login.findViewById(R.id.btnCancel);
        txtLink = (EditText) login.findViewById(R.id.txtLink);
        // Attached listener for login GUI button
        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = txtLink.getText().toString().toLowerCase();
                isValidUrl(link);
            }
        });

        login.show();
        login.getWindow().setLayout(700, 350);
    }

    private void isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url);
        if (m.matches()) {
            tvlink.setText(url);
            login.dismiss();
        } else {
            Toast.makeText(ShareActivity.this, "Please Enter Valid Url/don't leave blank", Toast.LENGTH_SHORT).show();
        }

    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ShareActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    ivRemove.setVisibility(View.VISIBLE);
                    ivimage.setImageBitmap(bitmap);

                    String Path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(Path, String.valueOf(System.currentTimeMillis()) + ".jpg");

                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                picturePath = c.getString(columnIndex);
                c.close();
                thumbnail = (BitmapFactory.decodeFile(picturePath));
                ivRemove.setVisibility(View.VISIBLE);
                ivimage.setImageBitmap(thumbnail);

            }
        }
    }

}
