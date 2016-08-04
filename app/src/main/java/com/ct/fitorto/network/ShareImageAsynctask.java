package com.ct.fitorto.network;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.ct.fitorto.model.Feed;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by Ashwini on 2/27/2016.
 */
public class ShareImageAsynctask extends AsyncTask<Void, Void, String>   {

    Activity act;
    Feed result;
    private ProgressDialog progressDialog;
    private static int COUNT;
    ArrayList<Uri> imageUriArray = new ArrayList<Uri>();

    public ShareImageAsynctask(Activity act, Feed result) {
        this.act = act;
        this.result = result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            if (progressDialog != null) {
                if (COUNT <= 1) {
                    progressDialog.cancel();
                    progressDialog = null;
                }
                COUNT--;
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(act);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        COUNT++;
    }

    @Override
    protected String doInBackground(Void... params) {


        String image = null;

        image = result.getImageLink();

        File file = new File(image);

        URL url = null;
        try {
            url = new URL(image);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        InputStream in = null;
        try {
            in = new BufferedInputStream(url.openStream());
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        try {
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        byte[] response = out.toByteArray();

        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file" + ".jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //imageUriArray.add(Uri.parse("file:///sdcard/temporary_file" + ".jpg"));
        //}
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, result.getFeed());
        share.setType("image/jpeg");
        //share.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUriArray);
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory()+ File.separator + "temporary_file" + ".jpg"));
        act.startActivity(Intent.createChooser(share, "Share Image"));




        return null;


    }


    /*public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(act,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted");
                return true;
            } else {

              //  Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
           // Log.v(TAG,"Permission is granted");
            return true;
        }


    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }*/

}