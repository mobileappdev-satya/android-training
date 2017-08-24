package in.mobileappdev.ecommerce;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import in.mobileappdev.ecommerce.db.SqliteDbHandler;
import in.mobileappdev.ecommerce.model.Item;

public class CartListActivity extends AppCompatActivity {

    private static final String TAG =CartListActivity.class.getCanonicalName() ;
    private SqliteDbHandler sqliteDbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        sqliteDbHandler = new SqliteDbHandler(this, SqliteDbHandler.DB_NAME, null, 1);

        SharedPreferences sp = getSharedPreferences("in.mobileappdev.ecommerce",MODE_PRIVATE);
        Set<String> itemsinCart = sp.getStringSet("itemsincart", new HashSet<String>());

        ArrayList<Item> cartIteems = sqliteDbHandler.getAllItemsInCart(itemsinCart);
        Log.d(TAG, "CART ITEM SIZE : "+cartIteems.size());

        //recycler view
        //adaper
        //bind
        //two buttons, one is for delete from cart,second one to go buying screen

    }
}
