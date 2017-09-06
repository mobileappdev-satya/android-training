package in.mobileappdev.ecommerce;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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

import in.mobileappdev.ecommerce.model.GenericResponse;
import in.mobileappdev.ecommerce.reciever.NetworkBroadcastReciever;
import in.mobileappdev.ecommerce.restclient.ECommerceHttpClient;
import in.mobileappdev.ecommerce.service.ECommerceService;
import in.mobileappdev.ecommerce.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAppActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = LoginAppActivity.class.getSimpleName();
    private EditText edtUsrname,edtPassword;
    private SharedPreferences sp;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "LifeCycle - onCreate");
        sp = getSharedPreferences("in.mobileappdev.ecommerce", MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        if(sp.getBoolean(Constants.SP_ISLOGGED_IN, false)){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        Intent intent = getIntent();

        //registering views from XML
        Button buttonSignIn = (Button) findViewById(R.id.btnSignIn);
        TextView textCreateAccount = (TextView) findViewById(R.id.txt_create_account);
        TextView textForgotPassword = (TextView) findViewById(R.id.txt_forgot_pwd);

        edtUsrname = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);

        if(intent.getStringExtra(Constants.KEY_USER_EMAIL) != null){
            edtUsrname.setText(intent.getStringExtra(Constants.KEY_USER_EMAIL));
        }

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
                loginUser();
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


    private void loginUser() {
        progressDialog.setMessage("Signing in...");
        progressDialog.show();
        if(!validateFields()){
            return;
        }

        ECommerceHttpClient client = ECommerceHttpClient.getInstance();
        Call<GenericResponse> createuser = client.getHttpService().loginUser(Constants.TAG_LOGIN, edtUsrname.getText().toString(), edtPassword.getText().toString());
        createuser.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                progressDialog.dismiss();
                GenericResponse genericResponse = response.body();
                if(null == genericResponse){
                    Toast.makeText(LoginAppActivity.this, R.string.error_message, Toast.LENGTH_LONG).show();
                    return;
                }

                int status = genericResponse.getSuccess();

                switch (status){
                    case 0 :
                        Toast.makeText(LoginAppActivity.this,  genericResponse.getMessage(), Toast.LENGTH_LONG).show();
                        break;
                    case 1 :
                        Toast.makeText(LoginAppActivity.this, ""+genericResponse.getMessage(), Toast.LENGTH_LONG).show();
                        Intent loginIntent = new Intent(LoginAppActivity.this, LoginAppActivity.class);
                        sp.edit().putBoolean(Constants.SP_ISLOGGED_IN, true).apply();
                        sp.edit().putString(Constants.SP_USERNAME, edtUsrname.getText().toString()).apply();
                        startActivity(loginIntent);
                        finish();
                        break;

                    case -1 :
                        Toast.makeText(LoginAppActivity.this, genericResponse.getMessage(), Toast.LENGTH_LONG).show();
                        break;
                }


            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginAppActivity.this, "onFailure : "+t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateFields() {

        if(!isEmailValidAndroid(edtUsrname.getText().toString())){
            edtUsrname.setError("Please enter valid email id");
            return false;
        }

        if(edtPassword.getText().length() == 0 || edtPassword.getText().length() < 5){
            edtPassword.setError("Please enter password");
            return false;
        }

        return true;
    }

    boolean isEmailValidAndroid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
