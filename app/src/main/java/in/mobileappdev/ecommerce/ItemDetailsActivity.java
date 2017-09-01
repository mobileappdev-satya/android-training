package in.mobileappdev.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.util.HashSet;
import java.util.Set;

import in.mobileappdev.ecommerce.async.DownloadImageAyncTask;
import in.mobileappdev.ecommerce.db.SqliteDbHandler;
import in.mobileappdev.ecommerce.listner.OnImageDownloadListner;
import in.mobileappdev.ecommerce.model.Item;

public class ItemDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ItemDetailsActivity";
    private SharedPreferences sp;
    private Set<String> itemsInCart;
    private int itemId;
    private boolean isItemAlreadyInCart = false;
    private Menu menu;
    private ImageView imgItemIcon;
    private ProgressBar progressBar;

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
        if(itemsInCart.size() == 0){
            Toast.makeText(this, "Your Cart is empty, Please add items to cart.", Toast.LENGTH_LONG).show();
        }


        SqliteDbHandler sqliteDbHandler = new SqliteDbHandler(this, SqliteDbHandler.DB_NAME, null, 1);

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
                txtItemName.setText(item.getItemName());
                txtItemDesc.setText(item.getItemDesc());
                txtItemCost.setText("Rs. "+item.getItemCost());
                txtItemQty.setText("Only "+item.getItemQuantity()+" items left!!");
            }
        }

        Glide.with(this).load("https://i.ytimg.com/vi/lt0WQ8JzLz4/maxresdefault.jpg").into(imgItemIcon);

        DownloadImageAyncTask downloadImageAyncTask = new DownloadImageAyncTask();
        downloadImageAyncTask.setOnImageDownloadListner(new OnImageDownloadListner() {
            @Override
            public void onDownloadStarted() {
                if(progressBar.isShown()){
                    progressBar.setVisibility(View.VISIBLE);
                }

                //Toast.makeText(ItemDetailsActivity.this, "image download started", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(Bitmap bitmapImage) {
                imgItemIcon.setImageBitmap(bitmapImage);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure() {
                Toast.makeText(ItemDetailsActivity.this, "image download failed", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

       //downloadImageAyncTask.execute("http://mobileappdev.in/androwarriors/items/images/android8.jpg","http://mobileappdev.in/androwarriors/items/get_all_products.php");
    }

    public void addToCartClick(View v){
        if(!isItemAlreadyInCart){
            itemsInCart.add(String.valueOf(itemId));
            sp.edit().putStringSet("itemsincart",itemsInCart).apply();
            Toast.makeText(this, "Added this item to your Cart", Toast.LENGTH_LONG).show();
        }

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView iv = (ImageView)inflater.inflate(R.layout.item_menu_cart_layout, null);
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
}
