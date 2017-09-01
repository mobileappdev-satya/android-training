package in.mobileappdev.ecommerce.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import in.mobileappdev.ecommerce.listner.OnImageDownloadListner;

/**
 * Created by Techjini on 8/31/2017.
 */

public class DownloadImageAyncTask extends AsyncTask<String, Void, Bitmap>  {

    private OnImageDownloadListner onImageDownloadListner;

    @Override
    protected Bitmap doInBackground(String... param) {
        return downloadImage(param[1]);
    }

    @Override
    protected void onPreExecute() {
        Log.i("Async-Example", "onPreExecute Called");
        onImageDownloadListner.onDownloadStarted();
        // simpleWaitDialog = ProgressDialog.show(ImageDownladerActivity.this,"Wait", "Downloading Image");

    }

    @Override
    protected void onPostExecute(Bitmap result) {
        Log.i("Async-Example", "onPostExecute Called");
        if(result == null){
            onImageDownloadListner.onFailure();
        }else {
            onImageDownloadListner.onSuccess(result);
        }

        //downloadedImg.setImageBitmap(result);
        // simpleWaitDialog.dismiss();

    }

    private Bitmap downloadImage(String url) {

        Bitmap bmp =null;
        try{
            URL ulrn = new URL(url);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            if (null != bmp)
                return bmp;

        }catch(Exception e){
            e.printStackTrace();
        }
        return bmp;
    }


    public void setOnImageDownloadListner(OnImageDownloadListner onImageDownloadListner){
        this.onImageDownloadListner = onImageDownloadListner;
    }
}

