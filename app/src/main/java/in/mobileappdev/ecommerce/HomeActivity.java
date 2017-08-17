package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sp = getSharedPreferences("in.mobileappdev.ecommerce",MODE_PRIVATE);
        String username = sp.getString("username", "UserName");

        TextView txtUserName = (TextView) findViewById(R.id.txt_user_name);

        Intent intent  = getIntent();
       // String username = intent.getStringExtra("usernam");
        Log.d(TAG, "Recived Username : "+username);
        if(username != null){
            txtUserName.setText(username.toLowerCase());
        }


    }
}
