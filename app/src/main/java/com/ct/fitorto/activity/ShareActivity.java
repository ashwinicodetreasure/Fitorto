package com.ct.fitorto.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.model.JsonResponseAddFeed;
import com.ct.fitorto.network.ApiClientMain;

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
public class ShareActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btngLink;
    private ImageView btncamera;
    private Dialog login;
    private Button btnLink;
    private Button sharebtn;
    private EditText txtLink;
    private EditText edcontent;
    private TextView tvlink;
    private ImageView ivimage;
    private Bitmap bitmap,thumbnail;
    private String picturePath;
    private File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_activity);
        setToolabar();

        edcontent = (EditText) findViewById(R.id.edFeed);

        ivimage = (ImageView) findViewById(R.id.ivimage);
        tvlink = (TextView) findViewById(R.id.tvlink);

        btngLink = (ImageView) findViewById(R.id.ivLink);
        btngLink.setOnClickListener(this);

        btncamera = (ImageView) findViewById(R.id.ivcamera);
        btncamera.setOnClickListener(this);

        sharebtn = (Button) findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener(this);

    }

    private void setToolabar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Share");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.ivcamera:
                selectImage();
                /*Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);*/
                break;

            case R.id.ivLink:
                setLink();

                break;

            case R.id.sharebtn:
                // if(!TextUtils.isEmpty(edcontent.getText() )){
                uploadData();
                ivimage.setImageBitmap(null);
                tvlink.setText("");
                edcontent.setText("");//}
                //else
                // {

                //}

                /*Fragment fr = new Feed_Fragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.show_fragment, fr);
                ft.commit();*/


                break;

        }

    }

    private void uploadData() {

       /*PreferenceManager preferenceManager = new PreferenceManager(ShareActivity.this);
        String id = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_TYPE);*/
        RequestBody id = ApiClientMain.getStringRequestBody("1");
        RequestBody content = ApiClientMain.getStringRequestBody(edcontent.getText().toString());
        RequestBody link = ApiClientMain.getStringRequestBody(txtLink.getText().toString());
        RequestBody flag = ApiClientMain.getStringRequestBody("1");

        /*Bitmap original = BitmapFactory.decodeFile(picturePath);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        original.compress(Bitmap.CompressFormat.JPEG, 100, out);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
       String image1=BitMapToString(decoded);*/

        RequestBody image = RequestBody.create(MediaType.parse(ApiClientMain.MEDIA_TYPE_IMAGE), new File(picturePath));
        Call<JsonResponseAddFeed> response = ApiClientMain.getApiClient().getResponseFeed(id,content ,link ,flag ,image);
        response.enqueue(new Callback<JsonResponseAddFeed>() {
            @Override
            public void onResponse(Call<JsonResponseAddFeed> call, retrofit2.Response<JsonResponseAddFeed> response) {

                JsonResponseAddFeed resp = response.body();

                Toast.makeText(ShareActivity.this, "Shared", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonResponseAddFeed> call, Throwable t) {
                Toast.makeText(ShareActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
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
                String str=txtLink.getText().toString();
                /*Pattern regex = Pattern.compile("^[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|COM|ORG|NET|MIL|EDU)$");
                Matcher matcher = regex.matcher(str);
                if (matcher.matches()) {
                    //It's an url
                    tvlink.setText(str);
                    login.dismiss();
                }else {
                    //Not an url
                    Toast.makeText(ShareActivity.this,"Invalid link",Toast.LENGTH_SHORT).show();

                }*/




                //Pattern regex = Pattern.compile("^[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|COM|ORG|NET|MIL|EDU)$");
                //Matcher matcher = regex.matcher(str);
                //if(matcher.matches()) {
                isValidUrl(str);

                //}
            }
        });

        login.show();
        login.getWindow().setLayout(700, 350);
    }
    private void isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url);
        if(m.matches())
        {tvlink.setText(url);
            login.dismiss();}
        else{
            Toast.makeText(ShareActivity.this,"Please Enter Valid Url/dont leave blank",Toast.LENGTH_SHORT).show();
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
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
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
                // Log.w("path of image from gallery......******************.........", picturePath+"");

                ivimage.setImageBitmap(thumbnail);

            }
        }
    }

}
