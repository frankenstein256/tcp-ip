package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class page1 extends AppCompatActivity {

    String port;
    String ip;
    String messages;
    Intent path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);

        Intent path = getIntent();
        ip = path.getStringExtra("ip");
        port = path.getStringExtra("port");
        Thread ar = new Thread(new MyServerThread());
        ar.start();

    }

    public void nextpage(View v) {

        Intent i = new Intent(getBaseContext(), Page2.class);
        i.putExtra("path", messages);
        startActivity(i);
 ;
    }
    public void sendpermission(View v) {

        BackgroundTask mt = new BackgroundTask();
        mt.execute();
        Toast.makeText(getBaseContext(), "mesajınız gönderliyor...",Toast.LENGTH_SHORT).show();

    }
    class MyServerThread implements Runnable {
        Socket s;
        ServerSocket ss;
        InputStreamReader isr;
        BufferedReader bufferedReader;
        Handler h =new Handler();

        @Override
        public void run() {
            try{

                ss=new ServerSocket(Integer.parseInt(port));

                while (true)
                {

                    s= ss.accept();
                    isr= new InputStreamReader(s.getInputStream());
                    bufferedReader=new BufferedReader(isr);
                    messages = bufferedReader.readLine();
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), messages, Toast.LENGTH_SHORT).show();


                        }
                    });
                    isr.close();
                    bufferedReader.close();
                    ss.close();
                    s.close();
                }

            } catch (IOException e) {
            //    Toast.makeText(getBaseContext(), "iletim hatası", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, Void> {
        String message = "";
        PrintWriter writer;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                System.out.println("Starting Connection");
                Socket s = new Socket(ip.toString(),Integer.parseInt(port.toString()));
                System.out.println("Connection DONE");
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                dos.writeUTF("a"+getIPAddress(true));

                dos.flush();
                dos.close();
                s.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }

}