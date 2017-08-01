package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
    public void onClick(View view) {

        int id = view.getId();

        switch (id){
            case R.id.btnSignIn :
                Intent signInIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(signInIntent);
                break;
            case R.id.txt_create_account:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.txt_forgot_pwd :
                Intent forgotPwdIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPwdIntent);
                break;
        }
    }
}
