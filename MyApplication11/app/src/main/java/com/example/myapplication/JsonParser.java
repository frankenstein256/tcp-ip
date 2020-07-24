package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Statement;

import static android.content.Context.MODE_PRIVATE;

public class JsonParser {

    public static final String KEY_ROWID = "_id";
    private static final String DATABASE_NAME = "Product.db";
    private static final String DATABASE_TABLE_PRODUCTS = "Products";
    private static final String KEY_PRODUCTS_NAME = "Name";
    private static final String KEY_PRODUCTS_ID = "Id";
    private static final String KEY_PRODUCTS_PRICE = "Price";
    private static final String KEY_PRODUCTS_VAT = "Vat";
    private static final String KEY_PRODUCTS_BARCODE = "Barcode";
    private static int DATABASE_VERSION = 1;
    private SQLiteDatabase ourDatase;
    private final Context ourContext;
    private DbHelper ourhelper;
    public static  String Name;
    private String TAG = "products";
    boolean check;

    public class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            context.deleteDatabase("Product.db");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "tablo yok tablo ekleniyor");
            db.execSQL("CREATE TABLE " + DATABASE_TABLE_PRODUCTS +
                    "(IdNum INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Id INTEGER not NULL, " +
                    " Name TEXT, " +
                    " Price REAL, " +
                    " Vat INTEGER, " +
                    "Barcode TEXT, " +
                    "total REAL)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "tables exist, dropping them and reccreating");
            onCreate(db);


        }
    }

    public JsonParser(Context c) {
        ourContext = c;
    }

    public JsonParser open() throws SQLException {
        ourhelper = new DbHelper(ourContext);
        ourDatase = ourhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourhelper.close();
    }

public void insertProduct(JSONArray jObject)
{
    Log.i(TAG, "veri ekleniyor");
    ContentValues cv=new ContentValues();

    for (int i=0; i < jObject.length(); i++)
    {
        JSONObject productObject;
        try {
            productObject = jObject.getJSONObject(i);


            double Price =productObject.getDouble("Price");
            String Barcode=productObject.getString("Barcode");
            int Vat=productObject.getInt("Vat");
            Name=productObject.getString("Name");
            int Id=productObject.getInt("Id");



                cv.put(KEY_PRODUCTS_ID, Id);
                cv.put(KEY_PRODUCTS_NAME, Name);
                cv.put(KEY_PRODUCTS_PRICE, Price);
                cv.put(KEY_PRODUCTS_VAT, Vat);
                cv.put(KEY_PRODUCTS_BARCODE, Barcode);
                cv.put("total", 0.0);
                        Log.i(TAG,"Json sql'e eklendi:"+Name);
            ourDatase.insert(DATABASE_TABLE_PRODUCTS,null,cv);
        }
        catch (JSONException e)
        {
        e.printStackTrace();
        }
    }
}


}
