package in.mobileappdev.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import in.mobileappdev.ecommerce.db.SqliteDbHandler;
import in.mobileappdev.ecommerce.listner.ECommerceAsycTaskListner;
import in.mobileappdev.ecommerce.model.Item;

public class ItemDetailsActivity extends AppCompatActivity implements ECommerceAsycTaskListner {

    private static final String TAG = "ItemDetailsActivity";
    private SharedPreferences sp;
    private Set<String> itemsInCart;
    private String itemJSON;
    private boolean isItemAlreadyInCart = false;
    private Menu menu;
    private ImageView imgItemIcon;
    private ProgressBar progressBar;
    private  Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        //bind view id's
        TextView txtItemName = (TextView) findViewById(R.id.item_name);
        TextView txtItemDesc = (TextView) findViewById(R.id.item_desc);
        TextView txtItemCost = (TextView) findViewById(R.id.item_cost);
        TextView txtItemQty = (TextView) findViewById(R.id.item_qty);
        imgItemIcon = (ImageView) findViewById(R.id.item_image);
        progressBar = (ProgressBar) findViewById(R.id.imgLoading);


        itemsInCart = new HashSet<String>();

        sp = getSharedPreferences("in.mobileappdev.ecommerce", MODE_PRIVATE);

        //if first time, the sharedprefis empty, and returns empty set
        itemsInCart.addAll(sp.getStringSet("itemsincart", new HashSet<String>()));
        if (itemsInCart.size() == 0) {
            Toast.makeText(this, "Your Cart is empty, Please add items to cart.", Toast.LENGTH_LONG).show();
        }


        SqliteDbHandler sqliteDbHandler = new SqliteDbHandler(this, SqliteDbHandler.DB_NAME, null, 1);

        Intent dataIntent = getIntent();
        itemJSON = dataIntent.getStringExtra("item");

        Gson gson = new Gson();
        item = gson.fromJson(itemJSON, Item.class);

        if(null == item ){
            return;
        }

        if (itemsInCart.contains(String.valueOf(item.getId()))) {
            Toast.makeText(this, "Already in your Cart", Toast.LENGTH_LONG).show();
            isItemAlreadyInCart = true;
        }

        txtItemName.setText(item.getName());
        txtItemDesc.setText(item.getDescription());
        txtItemCost.setText("Rs. "+item.getPrice());
        txtItemQty.setText("Only "+item.getQuantity()+" items left!!");

        Glide.with(this).load(item.getUrl()).into(imgItemIcon);

       /* ECommerceAsyncTask eCommerceAsyncTask = new ECommerceAsyncTask();
        eCommerceAsyncTask.seteCommerceAsycTaskListner(this);
        eCommerceAsyncTask.execute("https://i.ytimg.com/vi/lt0WQ8JzLz4/maxresdefault.jpg");*/

    }

    public void addToCartClick(View v) {
        if (!isItemAlreadyInCart) {
            itemsInCart.add(String.valueOf(item.getId()));
            sp.edit().putStringSet("itemsincart", itemsInCart).apply();
            Toast.makeText(this, "Added this item to your Cart", Toast.LENGTH_LONG).show();
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView iv = (ImageView) inflater.inflate(R.layout.item_menu_cart_layout, null);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        iv.startAnimation(rotation);
        menu.findItem(R.id.got_cart).setActionView(iv);

    }

    public void buyNowClick(View view) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        this.menu = menu;
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

    @Override
    public void onDownloadStarted() {
        if (!progressBar.isShown()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }

        //Covert Bitmap
        Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
        imgItemIcon.setImageBitmap(imageBitmap);
    }

    @Override
    public void onFailure() {
        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
