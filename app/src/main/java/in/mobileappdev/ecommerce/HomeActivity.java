package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import in.mobileappdev.ecommerce.adpater.ItemsAdapter;
import in.mobileappdev.ecommerce.db.SqliteDbHandler;
import in.mobileappdev.ecommerce.model.Item;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private SqliteDbHandler sqliteDbHandler;
    private String username;
    //private  TextView txtUserName;
    private RecyclerView recyclerViewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sqliteDbHandler = new SqliteDbHandler(this, SqliteDbHandler.DB_NAME, null, 1);

        SharedPreferences sp = getSharedPreferences("in.mobileappdev.ecommerce",MODE_PRIVATE);
        username = sp.getString("username", "UserName");

        recyclerViewItems = (RecyclerView) findViewById(R.id.recycler_view_items);

       // txtUserName = (TextView) findViewById(R.id.txt_user_name);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int countOftheItems = sqliteDbHandler.getItemsCount();
        //txtUserName.setText("Hi "+username.toLowerCase()+"\n We have "+countOftheItems +" items for you.");

        //data
        ArrayList<Item> items = sqliteDbHandler.getAllItems();
        if(items.size()>0){

            //adapter
            ItemsAdapter adapter = new ItemsAdapter(items, HomeActivity.this);

            //View
            recyclerViewItems.setAdapter(adapter);


        }else{
            //txtUserName.setText("Not contans any Items, please add items first");
        }
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
