package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import in.mobileappdev.ecommerce.db.SqliteDbHandler;
import in.mobileappdev.ecommerce.model.Item;

public class ItemDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ItemDetailsActivity";
    private SqliteDbHandler sqliteDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        sqliteDbHandler =  new SqliteDbHandler(this, SqliteDbHandler.DB_NAME, null, 1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent dataIntent = getIntent();
        int itemId = dataIntent.getIntExtra("id", -1);

        if(itemId != -1){
            Item item = sqliteDbHandler.getItem(itemId);
            if(item != null){
                Log.d(TAG, "Name : "+item.getItemName());
            }

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
