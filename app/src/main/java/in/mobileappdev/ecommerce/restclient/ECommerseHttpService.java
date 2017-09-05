package in.mobileappdev.ecommerce.restclient;

import in.mobileappdev.ecommerce.model.GenericResponse;
import in.mobileappdev.ecommerce.model.GetAllItemsResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Techjini on 9/4/2017.
 */

public interface ECommerseHttpService {

    @GET("get_all_products.php")
    Call<GetAllItemsResponse> getAllProducts();

    @POST("create_product.php")

    Call<GenericResponse> createPruduct(@Field("name") String name, @Field("discription") String description, @Field("discount") int d, int price, String url);

    @FormUrlEncoded
    @POST("api/index.php")
    Call<GenericResponse>  createNewUser(@Field("tag") String tag, @Field("firstname") String firstName,@Field("lastname") String lastName, @Field("password") String pwd, @Field("email") String email, @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("api/index.php")
    Call<GenericResponse>  loginUser(@Field("tag") String tag,  @Field("email") String firstName,@Field("password") String lastName);

}

