package in.mobileappdev.ecommerce.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import in.mobileappdev.ecommerce.listner.OnFetchItemsListner;
import in.mobileappdev.ecommerce.listner.OnImageDownloadListner;

/**
 * Created by Techjini on 8/31/2017.
 */

public class GetAllItemsAyncTask extends AsyncTask<String, Void, String>  {

    private OnFetchItemsListner onFetchItemsListner;

    @Override
    protected String doInBackground(String... param) {
        return getItemsFromServer(param[0]);
    }

    @Override
    protected void onPreExecute() {
        Log.i("Async-Example", "onPreExecute Called");
        onFetchItemsListner.onDownloadStarted();
        // simpleWaitDialog = ProgressDialog.show(ImageDownladerActivity.this,"Wait", "Downloading Image");

    }

    @Override
    protected void onPostExecute(String result) {
        Log.i("Async-Example", "onPostExecute Called");
        if(result == null){
            onFetchItemsListner.onFailure();
        }else {
            onFetchItemsListner.onSuccess(result);
        }

        //downloadedImg.setImageBitmap(result);
        // simpleWaitDialog.dismiss();

    }


    public void setOnFetchItemsListner(OnFetchItemsListner onFetchItemsListner){
        this.onFetchItemsListner = onFetchItemsListner;
    }

    public String getItemsFromServer(String urlString){
        String stream = null;
        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Check the connection status
            if(urlConnection.getResponseCode() == 200)
            {
                // if response code = 200 ok
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //bitmap--convert bitmap
                //Strig -- convert string

                // Read the BufferedInputStream
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
                stream = sb.toString();
                // End reading...............

                // Disconnect the HttpURLConnection
                urlConnection.disconnect();
            }
            else
            {
                // Do something
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {

        }
        // Return the data from specified url
        return stream;
    }
}

