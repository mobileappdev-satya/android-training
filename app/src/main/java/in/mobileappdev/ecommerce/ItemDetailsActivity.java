package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import in.mobileappdev.ecommerce.db.SqliteDbHandler;
import in.mobileappdev.ecommerce.model.Item;

public class ItemDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ItemDetailsActivity";
    private SqliteDbHandler sqliteDbHandler;
    private SharedPreferences sp;
    private Set<String> itemsInCart;
    private int itemId;
    private boolean isItemAlreadyInCart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_item_details);
        itemsInCart = new HashSet<String>();

        sp = getSharedPreferences("in.mobileappdev.ecommerce", MODE_PRIVATE);

        //if first time, the sharedprefis empty, and returns empty set
        itemsInCart.addAll(sp.getStringSet("itemsincart", new HashSet<String>()));
        if(itemsInCart.size() == 0){
            Toast.makeText(this, "Your Cart is empty, Please add items to cart.", Toast.LENGTH_LONG).show();
        }


        sqliteDbHandler =  new SqliteDbHandler(this, SqliteDbHandler.DB_NAME, null, 1);

        Intent dataIntent = getIntent();
        itemId = dataIntent.getIntExtra("id", -1);

        if(itemId != -1){

            if(itemsInCart.contains(String.valueOf(itemId))){
                Toast.makeText(this, "Already in your Cart", Toast.LENGTH_LONG).show();
                isItemAlreadyInCart= true;
            }

            Item item = sqliteDbHandler.getItem(itemId);
            if(item != null){
                Log.d(TAG, "Name : "+item.getItemName());
            }
        }
    }

    public void addToCartClick(View v){
        if(!isItemAlreadyInCart){
            itemsInCart.add(String.valueOf(itemId));
            sp.edit().putStringSet("itemsincart",itemsInCart).apply();
        }

    }

    public void buyNowClick(View view) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.got_cart:
                startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
