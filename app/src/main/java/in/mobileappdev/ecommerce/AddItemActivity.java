package in.mobileappdev.ecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.mobileappdev.ecommerce.db.SqliteDbHandler;

public class AddItemActivity extends AppCompatActivity {

    private EditText edtItemName, edtItemCost, edtItemDesc, edtItemQuantity;
    private Button btnAddItem;
    private String itemName;
    private String itemDesc;
    private int itemCost;
    private int itemQuantity;

    private SqliteDbHandler sqliteDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //creating DB instance
        sqliteDbHandler = new SqliteDbHandler(this, SqliteDbHandler.DB_NAME, null, 1);

        edtItemName = (EditText) findViewById(R.id.edt_item_name);
        edtItemDesc = (EditText) findViewById(R.id.edt_item_desc);
        edtItemCost = (EditText) findViewById(R.id.edt_item_cost);
        edtItemQuantity = (EditText) findViewById(R.id.edt_item_quantity);

        btnAddItem = (Button) findViewById(R.id.btn_add_item);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemName = edtItemName.getText().toString();
                itemDesc = edtItemDesc.getText().toString();
                itemCost = Integer.parseInt(edtItemCost.getText().toString());
                itemQuantity = Integer.parseInt(edtItemQuantity.getText().toString());
                //save to db
                sqliteDbHandler.insertItem(itemName,itemDesc,itemCost,itemQuantity);

                Toast.makeText(AddItemActivity.this, "Item Saved", Toast.LENGTH_LONG).show();

                edtItemName.setText(null);
                edtItemDesc.setText(null);
                edtItemCost.setText(null);
                edtItemQuantity.setText(null);

            }
        });
    }
}
