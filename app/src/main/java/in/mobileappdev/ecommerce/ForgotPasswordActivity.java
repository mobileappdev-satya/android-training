package in.mobileappdev.ecommerce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

public class ForgotPasswordActivity extends AppCompatActivity {


    private static final String TAG = "ForgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        EditText edtForgotPwd = (EditText) findViewById(R.id.edt_email_forgot);
        Intent intent  = getIntent();
        String username = intent.getStringExtra("usernam");
        Log.d(TAG, "Recived Username : "+username);
        if(username != null){
            edtForgotPwd.setText(username.toLowerCase());
        }

        final TextView cc = (TextView) findViewById(R.id.txt_contact_cc);
        cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    Uri uri = Uri.parse("sms:0000000000");
                Intent ccintent = new Intent(Intent.ACTION_VIEW, uri);
               // ccintent.setPackage("com.facebook.orca");
                startActivity(ccintent);*/
              /*  File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/aadhar.pdf");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);*/

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=12.9553615,77.7148373&daddr=12.9555595,77.726554"));
                startActivity(intent);
            }
        });
    }
}
