package com.example.wenyun.dropboxapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Wenyun on 6/29/2016.
 */
public class Upload extends AsyncTask<String, Void, String> {

    private DropboxAPI mDBApi;
    protected void onPreExecute(){}

    //remote folder
    private String remote_folder="/1/";
    private String internal_folder;
    private int pic_count;

    public Upload(DropboxAPI mDBApi, String folder, int pic_count){
        this.mDBApi = mDBApi;
        this.internal_folder = folder;
        this.pic_count = pic_count;
    }

    protected String doInBackground(String... arg0) {

        DropboxAPI.Entry response = null;
        File file = new File(internal_folder+pic_count+".jpg");
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
            Log.i("file", ": " + file.length());
        }catch(FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try{
            // put the file to dropbox
            response = mDBApi.putFile(remote_folder+pic_count+".jpg", inputStream,
                    file.length(), null, null);
            Log.i("File", "Upload Success"+pic_count);
            Log.i("File", "The uploaded file's rev is: " + response.rev);
            inputStream.close();

        } catch (Exception e){

            e.printStackTrace();
        }

        //delete pic in device
        File todeleteFile = new File(internal_folder + pic_count + ".jpg");
        if (todeleteFile != null) {
            todeleteFile.delete();
        }
        Log.i("File", "file deleted"+pic_count);
        return response.rev;
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.isEmpty() == false){
            Log.e("DbExampleLog", "The uploaded file's rev is: " + result);
        }
    }
}
