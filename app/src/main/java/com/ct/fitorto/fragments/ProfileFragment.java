package com.ct.fitorto.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;

import com.ct.fitorto.activity.EditUserProfileActivity;
import com.ct.fitorto.activity.FollowersActivity;
import com.ct.fitorto.activity.LoginActivity;
import com.ct.fitorto.baseclass.BaseFragment;
import com.ct.fitorto.model.Detail;
import com.ct.fitorto.model.FitortoUser;
import com.ct.fitorto.model.Gym;
import com.ct.fitorto.model.JsonResponseUpdateProfile;
import com.ct.fitorto.model.JsonResponseUserProfile;
import com.ct.fitorto.model.ProgressDetail;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;
import com.ct.fitorto.utils.Utility;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ashwini on 6/16/2016.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener {


    private PreferenceManager preferenceManager;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView followers;
    private TextView following;
    private TextView username;
    private CircleImageView profile;
    private TextView userstatus;
    private ViewPagerAdapter adapter;
    private TextView edit;
    private List<FitortoUser> data = new ArrayList<>();
    private String str = "";
    private int a = 1;
    private int b = 2;
    String picturePath;
    private String userChoosenTask;
    private File destination;
    public RelativeLayout rlEmptyView;
    private AppBarLayout appbar;
    private TextView btnLogin;
    private FitortoUser user;
    private TextView post;
    private JsonResponseUserProfile resp;
    private TextView tvFitortoID;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_profile_fragment, null);
        initView(view);
        setUserData(true);
        return view;
    }

    private void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        preferenceManager = new PreferenceManager(getActivity());
        //setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        followers = (TextView) view.findViewById(R.id.followers);
        following = (TextView) view.findViewById(R.id.following);
        username = (TextView) view.findViewById(R.id.user_name);
        userstatus = (TextView) view.findViewById(R.id.user_status);
        edit = (TextView) view.findViewById(R.id.editbtn);
        rlEmptyView = (RelativeLayout) view.findViewById(R.id.rlEmptyView);
        appbar = (AppBarLayout) view.findViewById(R.id.appbar);
        btnLogin = (TextView) view.findViewById(R.id.tvLogin);
        post = (TextView) view.findViewById(R.id.post);
        tvFitortoID = (TextView) view.findViewById(R.id.tvFitortoID);
        edit.setOnClickListener(this);
        profile = (CircleImageView) view.findViewById(R.id.profile_image);
        profile.setOnClickListener(this);
        view.findViewById(R.id.llFollowing).setOnClickListener(this);
        view.findViewById(R.id.llFollowers).setOnClickListener(this);

    }

    public void setUserData(final boolean showDialog) {     //to displaying user data
        final String userId = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        if (!userId.equals("0")) {
            if (showDialog) {
                showProgressDialog("Please wait...", false);
            }
            rlEmptyView.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            appbar.setVisibility(View.VISIBLE);
            Call<JsonResponseUserProfile> response = ApiClientMain.getApiClient().getResponseUserprofile(userId);
            response.enqueue(new Callback<JsonResponseUserProfile>() {
                @Override
                public void onResponse(Call<JsonResponseUserProfile> call, Response<JsonResponseUserProfile> response) {
                    JsonResponseUserProfile resp = response.body();
                    if (showDialog) {
                        cancelProgressDialog();
                    }

                    if (resp != null) {
                        updateUserData(resp);
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseUserProfile> call, Throwable t) {
                    if (showDialog) {
                        cancelProgressDialog();
                    }
                }
            });
        } else {
            rlEmptyView.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            appbar.setVisibility(View.GONE);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }


    }

    private void updateUserData(JsonResponseUserProfile resp) {
        this.resp = resp;
        if (resp.getData().size() > 0) {
            user = resp.getData().get(0);           //retrieving user data
            if (user != null) {
                if (!TextUtils.isEmpty(user.getProfilePic())) {
                    Picasso.with(getActivity())
                            .load(user.getProfilePic())
                            .error(R.drawable.ic_profile)
                            .placeholder(R.drawable.ic_profile)
                            .into(profile);
                } else {
                    Picasso.with(getActivity())
                            .load(R.drawable.ic_profile)
                            .placeholder(R.drawable.ic_profile)
                            .into(profile);
                }
                if (!TextUtils.isEmpty(user.getName())) {
                    username.setText(user.getName());
                }
                if (!TextUtils.isEmpty(user.getFitortoID())) {
                    tvFitortoID.setText("FiTORTO ID: " + user.getFitortoID());
                    preferenceManager.putPreferenceValues(preferenceManager.FITORTO_ID, user.getFitortoID());
                }
                if (!TextUtils.isEmpty(user.getFeedCount())) {
                    post.setText(user.getFeedCount());
                }

                if (!TextUtils.isEmpty(user.getStatus())) {
                    preferenceManager.putPreferenceValues(ApplicationData.STATUS, user.getStatus());
                    userstatus.setText(user.getStatus());
                } else {
                    userstatus.setText("No Status");
                }

                if (!TextUtils.isEmpty(String.valueOf(user.getFollowersCount()))) {
                    followers.setText(String.valueOf(user.getFollowersCount()));
                } else {
                    followers.setText("0");
                }

                if (!TextUtils.isEmpty(String.valueOf(user.getFollowingCount()))) {
                    following.setText(String.valueOf(user.getFollowingCount()));
                } else {
                    following.setText("0");
                }

                setupViewPager(viewPager, resp.getGyms(), resp.getProgress());
            }
        }
    }

    private void setupViewPager(ViewPager viewPager, ArrayList<Gym> gyms, ArrayList<ProgressDetail> progress) {
        if (adapter != null) {
            adapter.clearAll();
        }
        adapter.addFragment(AboutFragment.getInstance(gyms, progress), "About");
        adapter.addFragment(new MyFeedFragment(), "Feed");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private FragmentManager fragMan;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            fragMan = manager;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void clearAll() {
            for (int i = 0; i < mFragmentList.size(); i++)
                fragMan.beginTransaction().remove(mFragmentList.get(i)).commit();
            mFragmentList.clear();
        }

    }

    /*Selection picture from gallery and camera*/
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, b);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, a);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == b) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContext().getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                // Log.w("path of image from gallery......******************.........", picturePath+"");
                profile.setImageBitmap(thumbnail);
                uploadData(picturePath);
            } else if (requestCode == a) {
                onCaptureImageResult(data);
            } else if (requestCode == ApplicationData.REQUEST_CODE_EDIT_PROFILE) {
                setUserData(false);
            } else if (requestCode == ApplicationData.REQUEST_CODE_STATUS) {
                setUserData(false);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        //Log.d("path", destination.toString());
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profile.setImageBitmap(thumbnail);
        uploadData(destination.toString());
    }


    /*updating user profile pic*/
    private void uploadData(String str) {
        final String userid = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        RequestBody id = ApiClientMain.getStringRequestBody(userid);
        showProgressDialog("Please wait..", false);
        RequestBody image = RequestBody.create(MediaType.parse(ApiClientMain.MEDIA_TYPE_IMAGE), new File(str));
        Call<JsonResponseUpdateProfile> response = ApiClientMain.getApiClient().getResponseUpdateProfile(id, image);
        response.enqueue(new Callback<JsonResponseUpdateProfile>() {
            @Override
            public void onResponse(Call<JsonResponseUpdateProfile> call, Response<JsonResponseUpdateProfile> response) {
                cancelProgressDialog();
                JsonResponseUpdateProfile resp = response.body();
                Toast.makeText(getActivity(), "updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonResponseUpdateProfile> call, Throwable t) {
                cancelProgressDialog();
                Toast.makeText(getActivity(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                //Log.d("Error", t.getMessage());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editbtn:
                Intent i = new Intent(getActivity(), EditUserProfileActivity.class);
                startActivityForResult(i, ApplicationData.REQUEST_CODE_EDIT_PROFILE);
                break;
            case R.id.profile_image:
                selectImage();
                break;
            case R.id.llFollowing:
                Intent intent = new Intent(getActivity(), FollowersActivity.class);
                intent.putExtra(ApplicationData.IS_FOLLOWER, false);
                startActivityForResult(intent, ApplicationData.REQUEST_CODE_EDIT_PROFILE);
                break;
            case R.id.llFollowers:
                Intent intent1 = new Intent(getActivity(), FollowersActivity.class);
                intent1.putExtra(ApplicationData.IS_FOLLOWER, true);
                startActivityForResult(intent1, ApplicationData.REQUEST_CODE_EDIT_PROFILE);
                break;
        }
    }
}
