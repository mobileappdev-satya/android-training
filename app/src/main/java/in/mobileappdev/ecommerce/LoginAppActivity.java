package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginAppActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = LoginAppActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "LifeCycle - onCreate");
        //registering views from XML
        Button buttonSignIn = (Button) findViewById(R.id.btnSignIn);
        TextView textCreateAccount = (TextView) findViewById(R.id.txt_create_account);
        TextView textForgotPassword = (TextView) findViewById(R.id.txt_forgot_pwd);

        //registering click listener
        buttonSignIn.setOnClickListener(this);
        textCreateAccount.setOnClickListener(this);
        textForgotPassword.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "LifeCycle - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "LifeCycle - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "LifeCycle - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "LifeCycle - onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "LifeCycle - onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "LifeCycle - onDestroy");
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){
            case R.id.btnSignIn :
                Intent signInIntent = new Intent(LoginAppActivity.this, RegisterActivity.class);
                startActivity(signInIntent);
                break;
            case R.id.txt_create_account:
                Intent registerIntent = new Intent(LoginAppActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.txt_forgot_pwd :
                Intent forgotPwdIntent = new Intent(LoginAppActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPwdIntent);
                break;
        }
    }
}
