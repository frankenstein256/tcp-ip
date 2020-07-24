package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Object;
import java.lang.Throwable;
import   java.lang.Exception;
import      java.io.IOException;
import     java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

String message ="";
    PrintWriter writer;


    TextView port, ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        port = (TextView) findViewById(R.id.port);
        ip = (TextView) findViewById(R.id.ip);
        Toast.makeText(getBaseContext(), ip.getText(), Toast.LENGTH_SHORT).show();
    }
    public void senddata(View v)
    {
        BackgroundTask mt = new BackgroundTask();

        mt.execute();


    }
    class BackgroundTask extends AsyncTask<Void,Void,Void>
    {
        private  Socket s;
        private   ServerSocket ss;
        private  InputStreamReader isr;
        private  BufferedReader br;

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... params) {

            try{
                Socket s = new Socket(ip.getText().toString(),Integer.parseInt(port.getText().toString()) );
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF("Sunucuya  erişildi");
                dos.flush();
                dos.close();
                s.close();
                Intent i = new Intent(getBaseContext(), page1.class);
                i.putExtra("ip", ip.getText().toString());
                i.putExtra("port", port.getText().toString());
                startActivity(i);


            }
            catch (IOException e)
            {


           e.printStackTrace();

            }
            return null;
        }
    }
}