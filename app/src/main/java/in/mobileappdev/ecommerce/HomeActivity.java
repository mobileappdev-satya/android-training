package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.mobileappdev.ecommerce.adpater.ItemsAdapter;
import in.mobileappdev.ecommerce.db.SqliteDbHandler;
import in.mobileappdev.ecommerce.model.Item;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private SqliteDbHandler sqliteDbHandler;
    private String username;
    //private  TextView txtUserName;
    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private ArrayList<Item> items;

    private String jsonString = "{\"products\":[{\"id\":\"null\",\"name\":\"Panasonic P77 (Grey, 16 GB) (1 GB RAM)\",\"price\":\"6\",\"discount\":\"24\",\"description\":\"Fun or work - with the Panasonic P77, you can have an entertaining time, thanks to its 12.7 cm (5) HD IPS screen and 1 GHz quad-core processor.\",\"quantity\":\"10\",\"url\":\"https:\\/\\/www.lootdealsindia.in\\/wp-content\\/uploads\\/thumbs_dir\\/panasonic-p77-grey-16-gb1-gb-ram-ndngbrbxiz5jgeeh6yxvxihw3hc0wuhe4x1mysh8fe.jpeg\"},{\"id\":\"null\",\"name\":\"SAF BUDDHA PREMIUM LARGE 4 PANEL PAINTING Ink Pain\",\"price\":\"4500\",\"discount\":\"80\",\"description\":\"Overview A beautiful painting involves the action or skill of using paint in the right manner; hence, the end product will be a picture that can speak a thousand words they say. Arts have been in trend for quite some time now. It can give different viewer different meanings Style and Design The SAF Sparkle Large Panel Painting is quite abstract and mysteriously beautiful. You can gift this to a family or a friend\",\"quantity\":\"8\",\"url\":\"http:\\/\\/secularbuddhism.org\\/wp-content\\/uploads\\/2011\\/06\\/budda1.jpg\"}],\"success\":\"1\"}";
    private  String jsonStrinng1 = "{\"id\":\"null\",\"name\":\"Panasonic P77 (Grey, 16 GB) (1 GB RAM)\",\"price\":\"100\",\"discount\":\"24\",\"description\":\"Fun or work - with the Panasonic P77, you can have an entertaining time, thanks to its 12.7 cm (5) HD IPS screen and 1 GHz quad-core processor.\",\"quantity\":\"10\",\"url\":\"https:\\/\\/www.lootdealsindia.in\\/wp-content\\/uploads\\/thumbs_dir\\/panasonic-p77-grey-16-gb1-gb-ram-ndngbrbxiz5jgeeh6yxvxihw3hc0wuhe4x1mysh8fe.jpeg\"}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ArrayList<Item> jsonArrayItems = new ArrayList<>();
        try {
            JSONObject  completeData = new JSONObject(jsonString);
            JSONArray jsonArray = completeData.getJSONArray("products");
            for(int i= 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                int price = jsonObject.getInt("price");
                String description = jsonObject.getString("description");
                int qty = jsonObject.getInt("qty");
                Item item  = new Item(0, name, description,price,qty );
                jsonArrayItems.add(item);
            }

            Log.d(TAG, "LEGTH of the JSON Array Contents : "+jsonArrayItems.size());


        } catch (JSONException e) {
            Log.e(TAG, "INVALID Json");
            e.printStackTrace();
        }

        try {
            JSONObject jsonItem = new JSONObject(jsonStrinng1);

            String name = jsonItem.getString("name");
            int price = jsonItem.getInt("price");
            String description = jsonItem.getString("description");
            int qty = jsonItem.getInt("qty");

            Item item  = new Item(0, name, description,price,qty );

        } catch (JSONException e) {
            Log.e(TAG, "INVALID Json");
            e.printStackTrace();
        }


        sqliteDbHandler = new SqliteDbHandler(this, SqliteDbHandler.DB_NAME, null, 1);
        items = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("in.mobileappdev.ecommerce",MODE_PRIVATE);
        username = sp.getString("username", "UserName");

        //adapter
        adapter = new ItemsAdapter(items, this);

        //recycler view creation
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_items);

        //setting adapter to recycler view
        recyclerView.setAdapter(adapter);

        //layout type grid/linear
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        //divider
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);

       // txtUserName = (TextView) findViewById(R.id.txt_user_name);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int countOftheItems = sqliteDbHandler.getItemsCount();
        //txtUserName.setText("Hi "+username.toLowerCase()+"\n We have "+countOftheItems +" items for you.");

        //data
        items.clear();
        items.addAll(sqliteDbHandler.getAllItems());
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_item:
                startActivity(new Intent(HomeActivity.this, AddItemActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
