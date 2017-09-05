package in.mobileappdev.ecommerce.async;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import in.mobileappdev.ecommerce.listner.ECommerceAsycTaskListner;

/**
 * Created by Techjini on 9/2/2017.
 */

public class ECommerceAsyncTask extends AsyncTask<String, Void, InputStream>{

    private ECommerceAsycTaskListner eCommerceAsycTaskListner;

    @Override
    protected InputStream doInBackground(String... params) {
        //As per your requirement
        //downloading large files
        //reading files from storage
        //.... etc
        InputStream is = null;
        try{
            URL ulrn = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            is = con.getInputStream();
            con.disconnect();
        }catch (IOException ie){
           // eCommerceAsycTaskListner.onFailure();
        }

        return is;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(null == eCommerceAsycTaskListner){
            return;
        }
        eCommerceAsycTaskListner.onDownloadStarted();
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        super.onPostExecute(inputStream);
        if(null == eCommerceAsycTaskListner){
            return;
        }
        if(null == inputStream){
            eCommerceAsycTaskListner.onFailure();
            return;
        }
        eCommerceAsycTaskListner.onSuccess(inputStream);
    }

    public void seteCommerceAsycTaskListner(ECommerceAsycTaskListner eCommerceAsycTaskListner) {
        this.eCommerceAsycTaskListner = eCommerceAsycTaskListner;
    }
}
