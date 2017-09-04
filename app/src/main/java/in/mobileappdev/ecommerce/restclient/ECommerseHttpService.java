package in.mobileappdev.ecommerce.restclient;

import java.util.List;

import in.mobileappdev.ecommerce.model.CreateProductResponse;
import in.mobileappdev.ecommerce.model.GetAllItemsResponse;
import in.mobileappdev.ecommerce.model.Item;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Techjini on 9/4/2017.
 */

public interface ECommerseHttpService {

    @GET("get_all_products.php")
    Call<GetAllItemsResponse> getAllProducts();

    @POST("create_product.php")

    Call<CreateProductResponse> createPruduct(@Field("name") String name, @Field("discription") String description, @Field("discount") int d, int price, String url);

}
