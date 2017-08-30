package in.mobileappdev.ecommerce;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import in.mobileappdev.ecommerce.adpater.CartItemsAdapter;
import in.mobileappdev.ecommerce.db.SqliteDbHandler;
import in.mobileappdev.ecommerce.listner.CartItemOptionClickListner;
import in.mobileappdev.ecommerce.model.Item;

public class CartListActivity extends AppCompatActivity {

    private static final String TAG =CartListActivity.class.getCanonicalName() ;
    private SqliteDbHandler sqliteDbHandler;
    private RecyclerView recyclerView;
    private CartItemsAdapter cartItemsAdapter;
    private ArrayList<Item> cartIteems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        sqliteDbHandler = new SqliteDbHandler(this, SqliteDbHandler.DB_NAME, null, 1);
        SharedPreferences sp = getSharedPreferences("in.mobileappdev.ecommerce",MODE_PRIVATE);
        Set<String> itemsinCart = sp.getStringSet("itemsincart", new HashSet<String>());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_cart_items);


        //layout type grid/linear
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        //divider
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);
        //
        cartIteems = sqliteDbHandler.getAllItems();

        cartItemsAdapter= new CartItemsAdapter(cartIteems, this);
        cartItemsAdapter.setCartItemOptionClickListner(new CartItemOptionClickListner() {
            @Override
            public void onBuyNowClicked(int pos) {
                Toast.makeText(CartListActivity.this, "Buy Now Option Clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRemoveClicked(int pos) {
                cartIteems.remove(pos);
                cartItemsAdapter.notifyDataSetChanged();
                Toast.makeText(CartListActivity.this, "Remove Item Clicked", Toast.LENGTH_LONG).show();
            }

        });

        recyclerView.setAdapter(cartItemsAdapter);

        Log.d(TAG, "CART ITEM SIZE : "+cartIteems.size());

        //recycler view
        //adaper
        //bind
        //two buttons, one is for delete from cart,second one to go buying screen

    }
}
