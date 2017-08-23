package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
