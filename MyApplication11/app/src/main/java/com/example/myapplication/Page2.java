package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Page2 extends AppCompatActivity {
Context context;
ListView listViewOfversion;
ArrayAdapter<String> adapter;
    ArrayList<String> versionlist;
    Double total=0.0;
    int idcnt;
    SQLiteDatabase vt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        listViewOfversion  = (ListView) findViewById(R.id.listView1);

        versionlist = new ArrayList<>();

        Intent path = getIntent();
            context=this;
       String message = path.getStringExtra("path");
      Toast.makeText(this, message.substring(2), Toast.LENGTH_SHORT).show();

        try {
            JSONArray array =new JSONArray(message.substring(2));
            JsonParser jsonParser= new JsonParser(context);
            jsonParser.open();
            jsonParser.insertProduct(array);
            //Toast.makeText(context, JsonParser.Name, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, JsonParser.Name, Toast.LENGTH_SHORT).show();
        }
        Allfetch();

   //     Toast.makeText(context, resultSet.getString(0), Toast.LENGTH_SHORT).show();
        listViewOfversion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {


              idcnt= Integer.parseInt(((TextView) view).getText().toString().substring(0,1));
                 vt = openOrCreateDatabase("Product.db",MODE_PRIVATE,null);
                Cursor c = vt.rawQuery("Select * from Products where id = "+idcnt,null);
                for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    Toast.makeText(getApplicationContext(), c.getString(2), Toast.LENGTH_SHORT).show();
                    String strSQL = "UPDATE Products SET total = " + (c.getDouble(3) + c.getDouble(6)) + " WHERE IdNum = " + c.getInt(0);
                    total = total + c.getDouble(3);
                    vt.execSQL(strSQL);
                    adapter.clear();
                    Allfetch();
                }

                }
            catch(Exception e){}
            }
        });


    }
    public Cursor Allfetch() {
        SQLiteDatabase vt = openOrCreateDatabase("Product.db",MODE_PRIVATE,null);
        Cursor c = vt.rawQuery("Select * from Products",null);


        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

          versionlist.add(c.getString(0)+"ürün adı:"+c.getString(2)+"   ürün fiyatı:"+c.getString(3)+"toplam fiyat:"+c.getDouble(6));
        }
        versionlist.add("toplam tutar:"+total);
       adapter = new  ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, versionlist);
        listViewOfversion.setAdapter(adapter);
        vt.close();
        return c;
    }
    SQLiteDatabase mydatabase;
    public  void createSaleDb()
    {
         mydatabase = openOrCreateDatabase("SaleDb",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE SaleTable" +
                "(RecipCount INTEGER, " +
                " TotalAmount REAL, " +
                " CashPayment INTEGER, " +
                " CreditPayment INTEGER, " +
                "Barcode TEXT, " +
                "total REAL)");

        mydatabase.execSQL("CREATE TABLE SaleDetails" +
                "(Id INTEGER, " +
                " ProductName TEXT, "+
                "Amout REAL)");
    }
    public  void CreditPayment()
    {

    }
    public  void CashPayment()
    {
      //  mydatabase.execSQL("INSERT INTO SaleTable() VALUES('','');");
    }
    public void SaleDetails()
    {

    }
}