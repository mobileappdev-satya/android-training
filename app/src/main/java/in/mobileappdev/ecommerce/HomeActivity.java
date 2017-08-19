package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sp = getSharedPreferences("in.mobileappdev.ecommerce",MODE_PRIVATE);
        String username = sp.getString("username", "UserName");

        TextView txtUserName = (TextView) findViewById(R.id.txt_user_name);

        Intent intent  = getIntent();
       // String username = intent.getStringExtra("usernam");
        Log.d(TAG, "Recived Username : "+username);
        if(username != null){
            txtUserName.setText(username.toLowerCase());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.logout){
            sp.edit().putBoolean("isLogged",false).apply();
            startActivity(new Intent(HomeActivity.this, LoginAppActivity.class));
            finish();
        }else if( item.getItemId() == R.id.abot){

        }
        return super.onOptionsItemSelected(item);
    }
}
