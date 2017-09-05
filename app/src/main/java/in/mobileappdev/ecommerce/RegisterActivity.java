package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.mobileappdev.ecommerce.model.GenericResponse;
import in.mobileappdev.ecommerce.restclient.ECommerceHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener {
    EditText firstname, lastname, email, password, mobileNo;
    Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstname = (EditText) findViewById(R.id.input_firstname);
        lastname = (EditText) findViewById(R.id.input_lastname);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        mobileNo = (EditText) findViewById(R.id.input_mobile);
        register = (Button) findViewById(R.id.btn_registr);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {

           case R.id.btn_registr:
                regisetrUser();
                break;
        }
    }

    private void regisetrUser() {

        ECommerceHttpClient client = ECommerceHttpClient.getInstance();
        Call<GenericResponse> createuser = client.getHttpService().createNewUser(firstname.getText().toString(), password.getText().toString(), email.getText().toString(), mobileNo.getText().toString(), "");
        createuser.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                GenericResponse genericResponse = response.body();
                Toast.makeText(RegisterActivity.this, ""+genericResponse.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {

            }
        });
    }
}
