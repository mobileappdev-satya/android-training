package in.mobileappdev.ecommerce.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import in.mobileappdev.ecommerce.model.Item;

/**
 * Created by Techjini on 8/17/2017.
 */

public class SqliteDbHandler extends SQLiteOpenHelper{

    public static final String DB_NAME = "ecommDB";
    public static  final String TABLE_ITEMS = "ITEMS";
    public static final String COL_ITEM_ID = "id";
    public static final String COL_ITEM_NAME = "item_name";
    public static final String COL_ITEM_DESC = "item_desc";
    public static final String COL_ITEM_COST = "item_cost";
    public static final String COL_ITEM_QUANTITY = "item_quantity";
    private static final String TAG = "SqliteDbHandler";


    public SqliteDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE "+TABLE_ITEMS+" ( "+COL_ITEM_ID+" INT, "+COL_ITEM_NAME+" VARCHAR(20) , "+COL_ITEM_DESC+" VARCHAR(50), "+COL_ITEM_COST+" INT, "+COL_ITEM_QUANTITY+" INT);";
        Log.d(TAG, "CREATE_ITEMS_TABLE = "+CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //don't worry about this for now
    }


    public void insertItem(String name, String desc, int cost, int qty){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ITEM_NAME, name);
        contentValues.put(COL_ITEM_DESC, desc);
        contentValues.put(COL_ITEM_COST, cost);
        contentValues.put(COL_ITEM_QUANTITY, qty);
        getWritableDatabase().insert(TABLE_ITEMS, null, contentValues);
    }

    public int getItemsCount(){
        String SELECT_TEMS = "SELECT * FROM "+TABLE_ITEMS+";";
        Cursor cursor = getWritableDatabase().rawQuery(SELECT_TEMS, null);
        int count  = cursor.getCount();
        cursor.close();
        return count;
    }

    public ArrayList<Item> getAllItems(){

        ArrayList<Item> allItems = new ArrayList<>();

        String SELECT_TEMS = "SELECT * FROM "+TABLE_ITEMS+";";
        Cursor cursor = getWritableDatabase().rawQuery(SELECT_TEMS, null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(SqliteDbHandler.COL_ITEM_ID) );
            String name = cursor.getString(cursor.getColumnIndex(SqliteDbHandler.COL_ITEM_NAME) );
            String desc = cursor.getString(cursor.getColumnIndex(SqliteDbHandler.COL_ITEM_DESC) );
            int cost = cursor.getInt(cursor.getColumnIndex(SqliteDbHandler.COL_ITEM_COST) );
            int qty = cursor.getInt(cursor.getColumnIndex(SqliteDbHandler.COL_ITEM_QUANTITY));

            Item item = new Item(id, name, desc, cost, qty);
            allItems.add(item);
        }

        cursor.close();
        return allItems;
    }
}
