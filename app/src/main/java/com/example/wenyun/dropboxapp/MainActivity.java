package com.example.wenyun.dropboxapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    final static private String APP_KEY = "azdrv2epggkccqe";
    final static private String APP_SECRET = "0dyrc7s7ogmr7i8";

    String folder = "/storage/emulated/0/test/"; //internal folder to store temp image
//    String file_name = "/screen.jpg";
//    String file_path = Environment.getExternalStorageDirectory()
//            .getAbsolutePath() + file_name;

    public int pic_count = 0;
    private DropboxAPI<AndroidAuthSession> mDBApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        boolean link = mDBApi.getSession().isLinked();
        Log.i("status", "Linked: "+link);
        if(link == false) {
            mDBApi.getSession().startOAuth2Authentication(MainActivity.this);
        }
    }

    @Override
    protected void onResume() {
//        Log.i("file", "start resume");
        super.onResume();

        boolean link = mDBApi.getSession().isLinked();
        Log.i("file", "before Resume Linked: "+link);

        if (mDBApi.getSession().authenticationSuccessful()) {
            try {

                mDBApi.getSession().finishAuthentication();

                String accessToken = mDBApi.getSession().getOAuth2AccessToken();
                Log.i("file", "token: " + accessToken);
                //upload pic
                if (pic_count != 0) {
                    new Upload(mDBApi, folder, pic_count).execute();
                }
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }
        }
    }

//    protected void initialize_session(){
//        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
//        AndroidAuthSession session = new AndroidAuthSession(appKeys);
//
//        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
//
//        mDBApi.getSession().startOAuth2Authentication(MainActivity.this);
//    }


    public void openCamera(View view) {
        pic_count++;
        File newdir = new File(folder);
        newdir.mkdir();

        String file = folder + pic_count + ".jpg";
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri temp_uri = Uri.fromFile(newfile);

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, temp_uri);
        startActivityForResult(i, 0);
        Log.i("Pic", "pic taken");
    }


    public void uploadFiles(View view) {
        new Upload(mDBApi, folder, pic_count).execute();
    }

//    public void downloadFiles(View view){
//        downloadFile();
//    }

//    public void downloadFile() {
//
//        File file = new File(file_path);
//        FileOutputStream outputStream = null;
//
//        try {
//            outputStream = new FileOutputStream(file);
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        DropboxAPI.DropboxFileInfo info = null;
//        try {
//            info = mDBApi.getFile("/screen1.jpg", null, outputStream, null);
//
//            Log.i("File", "The file's rev is: "
//                    + info.getMetadata().rev);
//        } catch (DropboxException e) {
//            // TODO Auto-generated catch block
//
//            e.printStackTrace();
//        }
//
//    }


}
