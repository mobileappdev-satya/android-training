package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
               Toast.makeText(this,"SUCCESSFULLY REGISTER",Toast.LENGTH_LONG).show();
               /* Intent i = new Intent(getApplicationContext(), );
                startActivity(i);*/
                break;
        }
    }
}
