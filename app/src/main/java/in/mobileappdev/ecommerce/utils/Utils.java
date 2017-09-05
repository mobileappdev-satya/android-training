package in.mobileappdev.ecommerce.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.mobileappdev.ecommerce.model.Item;

/**
 * Created by Techjini on 9/5/2017.
 */

public class Utils {


    private static final String TAG = Utils.class.getSimpleName();

    public static ArrayList<Item> parseItemsJSON(String jsonString) {
        ArrayList<Item> jsonArrayItems = new ArrayList<>();
        try {
            JSONObject completeData = new JSONObject(jsonString);
            JSONArray jsonArray = completeData.getJSONArray("products");
            for(int i= 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                int price = jsonObject.getInt("price");
                String description = jsonObject.getString("description");
                int qty = jsonObject.getInt("quantity");
                int discount = jsonObject.getInt("discount");
                String url = jsonObject.getString("url");
                Item item  = new Item(0, name, description,price,qty, discount, url );
                jsonArrayItems.add(item);
            }

            Log.d(TAG, "LEGTH of the JSON Array Contents : "+jsonArrayItems.size());


        } catch (JSONException e) {
            Log.e(TAG, "INVALID Json");
            e.printStackTrace();
        }

        return jsonArrayItems;
    }

    public static double getDiscountedPrice(int price, int discount){
        int dis = 100-discount;
        return (dis * price)/100;
    }

}
