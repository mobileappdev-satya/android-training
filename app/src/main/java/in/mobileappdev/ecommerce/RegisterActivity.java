package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.mobileappdev.ecommerce.model.GenericResponse;
import in.mobileappdev.ecommerce.restclient.ECommerceHttpClient;
import in.mobileappdev.ecommerce.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener {
    EditText firstname, lastname, email, password, mobileNo;
    Button register;

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

        if(!validateFields()){
            return;
        }

        ECommerceHttpClient client = ECommerceHttpClient.getInstance();
        Call<GenericResponse> createuser = client.getHttpService().createNewUser("register",firstname.getText().toString(),lastname.getText().toString(), password.getText().toString(), email.getText().toString(), mobileNo.getText().toString());
        createuser.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                GenericResponse genericResponse = response.body();
                Toast.makeText(RegisterActivity.this, ""+genericResponse.getMessage(), Toast.LENGTH_LONG).show();
                Intent loginIntent = new Intent(RegisterActivity.this, LoginAppActivity.class);
                loginIntent.putExtra(Constants.KEY_USER_EMAIL, email.getText().toString());
                startActivity(loginIntent);
                finish();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "onFailure : "+t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateFields() {

        if(firstname.getText().length() == 0){
            firstname.setError("Please enter First name");
            return false;
        }

        if(email.getText().length() == 0 && !isEmailValidAndroid(email.getText().toString())){
            email.setError("Please enter email");
            return false;
        }

        if(mobileNo.getText().length() == 0){
            mobileNo.setError("Please enter Mobile number");
            return false;
        }

        if(password.getText().length() == 0){
            password.setError("Please enter First name");
            return false;
        }
        return true;
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    boolean isEmailValidAndroid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
