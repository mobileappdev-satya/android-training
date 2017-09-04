package in.mobileappdev.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import in.mobileappdev.ecommerce.adpater.ItemsAdapter;
import in.mobileappdev.ecommerce.async.ECommerceAsyncTask;
import in.mobileappdev.ecommerce.listner.ECommerceAsycTaskListner;
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

         ECommerceHttpClient httpClient = ECommerceHttpClient.getInstance();
         Call<GetAllItemsResponse> allUsers =  httpClient.getHttpService().getAllProducts();
         allUsers.enqueue(new Callback<GetAllItemsResponse>() {
             @Override
             public void onResponse(Call<GetAllItemsResponse> call, Response<GetAllItemsResponse> response) {
                 Log.d(TAG, "ECommerceHttpClient - onResponse");
             }

             @Override
             public void onFailure(Call<GetAllItemsResponse> call, Throwable t) {
                 Log.d(TAG, "ECommerceHttpClient - onFailure");
             }
         });

         txtErrorMsg = (TextView) findViewById(R.id.error_msh_home);
         layoutError = (LinearLayout) findViewById(R.id.error_layout);
         swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

         swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                // eCommerceAsyncTask.execute("http://mobileappdev.in/androwarriors/items/get_all_products.php");
             }
         });



       // sqliteDbHandler = new SqliteDbHandler(this, SqliteDbHandler.DB_NAME, null, 1);
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
        eCommerceAsyncTask = new ECommerceAsyncTask();
        eCommerceAsyncTask.seteCommerceAsycTaskListner(new ECommerceAsycTaskListner() {
            @Override
            public void onDownloadStarted() {
                progressDialog.setMessage("Loading Products...");
                    progressDialog.show();
            }

            @Override
            public void onSuccess(final InputStream inputStream) {

                if(null == inputStream){
                    return;
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        try {
                            while ((line = r.readLine()) != null) {
                                sb.append(line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        json = sb.toString();
                    }
                });


                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }

                items.clear();
                items.addAll(parseItemsJSON(json));

                if(items.size()==0){
                    recyclerView.setVisibility(View.GONE);
                    layoutError.setVisibility(View.VISIBLE);
                    txtErrorMsg.setText("No Products found  ");
                }
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure() {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                recyclerView.setVisibility(View.GONE);
                layoutError.setVisibility(View.VISIBLE);
                //txtErrorMsg.setText("Error msg from Code");

            }
        });
        eCommerceAsyncTask.execute("http://mobileappdev.in/androwarriors/items/get_all_products.php");

    }

    private ArrayList<Item> parseItemsJSON(String jsonString) {
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

    @Override
    protected void onResume() {
        super.onResume();
      /*  int countOftheItems = sqliteDbHandler.getItemsCount();
        //txtUserName.setText("Hi "+username.toLowerCase()+"\n We have "+countOftheItems +" items for you.");

        //data
        items.clear();
        items.addAll(sqliteDbHandler.getAllItems());
        adapter.notifyDataSetChanged();*/

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
