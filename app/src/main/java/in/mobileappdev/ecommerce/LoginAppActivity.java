package in.mobileappdev.ecommerce;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.mobileappdev.ecommerce.reciever.NetworkBroadcastReciever;
import in.mobileappdev.ecommerce.service.ECommerceService;

public class LoginAppActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = LoginAppActivity.class.getSimpleName();
    private EditText edtUsrname,edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "LifeCycle - onCreate");
        //registering views from XML
        Button buttonSignIn = (Button) findViewById(R.id.btnSignIn);
        TextView textCreateAccount = (TextView) findViewById(R.id.txt_create_account);
        TextView textForgotPassword = (TextView) findViewById(R.id.txt_forgot_pwd);

        edtUsrname = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);

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

        createCustomDialog();

        stopService(new Intent(this, ECommerceService.class));
        NetworkBroadcastReciever networkBroadcastReciever = new NetworkBroadcastReciever();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(networkBroadcastReciever, filter);
        Log.d(TAG, "LifeCycle - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        startService(new Intent(this, ECommerceService.class));
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
                Intent signInIntent = new Intent(LoginAppActivity.this, HomeActivity.class);
                String email = edtUsrname.getText().toString();
                if(email.length()>0){
                    signInIntent.putExtra("usernam", email);
                    startActivity(signInIntent);
                }else {
                    showEmailAlertDialog();
                }

                break;
            case R.id.txt_create_account:
                Intent registerIntent = new Intent(LoginAppActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.txt_forgot_pwd :
                Intent forgotPwdIntent = new Intent(LoginAppActivity.this, ForgotPasswordActivity.class);

                String email1 = edtUsrname.getText().toString();
                if(email1.length()>0){
                    forgotPwdIntent.putExtra("usernam", email1);
                }
                startActivity(forgotPwdIntent);
                break;
        }
    }

    private void showEmailAlertDialog(){
        AlertDialog.Builder alertBuilder = new  AlertDialog.Builder(this);
        alertBuilder.setTitle("Fields required");
        alertBuilder.setMessage("Pleas enter all mandatory fields to proceed");
        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(LoginAppActivity.this, "No Clicked", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(LoginAppActivity.this, "YES Clicked", Toast.LENGTH_LONG).show();
            }
        });
        Dialog dialog = alertBuilder.create();
        dialog.show();

    }

    private void createCustomDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Get the layout inflater
            LayoutInflater inflater = getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_welcome, null))
                    // Add action buttons
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
        Dialog dialog = builder.create();
        dialog.show();

    }
}
