package com.gui.faz1;

import java.awt.TextField;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ConnectServer {
	static Socket s;
	static ServerSocket ss;
	static InputStreamReader isr;
	static BufferedReader br;
	static String messages;
	
	public  ConnectServer(String  a)
	{
		Thread t = new Thread(){

		    @Override
		    public void run(){
		        System.out.println("Server is running and listening ... ");
		        try{
		            ServerSocket ss = new ServerSocket(8000);
		            while(true){
		                Socket s = ss.accept();
		                System.out.println("Connesso");
		                DataInputStream dis = new DataInputStream(s.getInputStream());
		                System.out.println("Received from Client: "+ dis.readUTF());
		                dis.close();
		                s.close();
		            }
		        }catch(IOException e){
		            e.printStackTrace();
		        }


		    }
		};
		    t.start();
	}
		

	
		
	
	
	
}
