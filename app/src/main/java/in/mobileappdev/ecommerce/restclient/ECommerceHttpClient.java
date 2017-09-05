package in.mobileappdev.ecommerce.restclient;

import in.mobileappdev.ecommerce.service.ECommerceService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Techjini on 9/4/2017.
 */

public class ECommerceHttpClient {

    private static ECommerceHttpClient eCommerceHttpClient;

    private String BASE_URL = "http://mobileappdev.in/androwarriors/items/";

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());


    Retrofit retrofit = builder.client(httpClient.build()).build();

    ECommerseHttpService service = retrofit.create(ECommerseHttpService.class);

    private ECommerceHttpClient(){

    }

    public static ECommerceHttpClient getInstance(){

        if(null == eCommerceHttpClient){
            eCommerceHttpClient = new ECommerceHttpClient();
        }
        return eCommerceHttpClient;
    }

    public ECommerseHttpService getHttpService(){
        return service;
    }



}
