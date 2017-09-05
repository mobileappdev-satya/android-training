package in.mobileappdev.ecommerce.model;

import java.util.ArrayList;

/**
 * Created by Techjini on 9/4/2017.
 */

public class GetAllItemsResponse {

   private ArrayList<Item> products;
   private int success;

    public ArrayList<Item> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Item> products) {
        this.products = products;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
