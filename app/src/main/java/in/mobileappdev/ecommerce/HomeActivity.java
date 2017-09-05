package in.mobileappdev.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.mobileappdev.ecommerce.adpater.ItemsAdapter;
import in.mobileappdev.ecommerce.async.ECommerceAsyncTask;
import in.mobileappdev.ecommerce.model.GetAllItemsResponse;
import in.mobileappdev.ecommerce.model.Item;
import in.mobileappdev.ecommerce.restclient.ECommerceHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    // private SqliteDbHandler sqliteDbHandler;
    private String username;
    //private  TextView txtUserName;
    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private ArrayList<Item> items;
    private TextView txtErrorMsg;
    private LinearLayout layoutError;
    private ProgressDialog progressDialog;
    private String json = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ECommerceAsyncTask eCommerceAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressDialog = new ProgressDialog(this);

        txtErrorMsg = (TextView) findViewById(R.id.error_msh_home);
        layoutError = (LinearLayout) findViewById(R.id.error_layout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProducts();
            }
        });

        // sqliteDbHandler = new SqliteDbHandler(this, SqliteDbHandler.DB_NAME, null, 1);

        SharedPreferences sp = getSharedPreferences("in.mobileappdev.ecommerce", MODE_PRIVATE);
        username = sp.getString("username", "UserName");
        items = new ArrayList<>();
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
        getProducts();

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


    private void getProducts() {

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        ECommerceHttpClient httpClient = ECommerceHttpClient.getInstance();
        Call<GetAllItemsResponse> allUsers = httpClient.getHttpService().getAllProducts();
        allUsers.enqueue(new Callback<GetAllItemsResponse>() {
            @Override
            public void onResponse(Call<GetAllItemsResponse> call, Response<GetAllItemsResponse> response) {
                Log.d(TAG, "ECommerceHttpClient - onResponse");

                GetAllItemsResponse getAllItemsResponse = response.body();
                progressDialog.dismiss();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (getAllItemsResponse != null) {
                    items.clear();
                    if(getAllItemsResponse.getProducts() != null){
                        layoutError.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }else{
                        layoutError.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        return;
                    }
                    items.addAll(getAllItemsResponse.getProducts());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetAllItemsResponse> call, Throwable t) {
                Log.d(TAG, "ECommerceHttpClient - onFailure");
                progressDialog.dismiss();
                layoutError.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}
