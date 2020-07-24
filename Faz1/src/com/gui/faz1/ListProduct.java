package com.gui.faz1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.Statement;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class ListProduct extends ConnectDB {
	String print;
	static String currentDirectory = System.getProperty("user.dir");
	static String vtPath="sql\\dbs.json";
 public String messahges;
 JSONArray array = new JSONArray();
	JSONObject jsonObject = new JSONObject();
	  public  void sendjsonfile() {
		  

          ServerSocket ss;
		try {
			ss = new ServerSocket(8000);
		
          while(true){
              Socket s = ss.accept();
           
              InputStreamReader isr = new InputStreamReader(s.getInputStream());
              BufferedReader   br = new BufferedReader(isr);
     
           
              isr.close();
              br.close();

	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
	    }

	public String list()
	{
		
			
		String sql = "select * from Products";
	
		try (
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql)){
			
			while(rs.next()) {
				JSONObject record = new JSONObject();
				
				 record.put("Id", rs.getInt("ID"));
				   record.put("Name", rs.getString("Name"));
				   record.put("Price", rs.getString("Price"));
				   record.put("Vat", rs.getInt("Vat"));
				   record.put("Barcode", rs.getString("Barcode"));
				   array.add(record);
		   
			}
			jsonObject.put("Products", array);
			FileWriter file = new FileWriter("sql//dbs.json");
	         file.write(jsonObject.toJSONString().substring(11,jsonObject.toJSONString().length()-2));
	         file.close();
	         for(Object o: array){
	        	    if ( o instanceof JSONObject ) {
	        	    	print +=  String.valueOf(((JSONObject)o))+"\n";
	        	    }
	        	}
		
		} catch (Exception e) {
			print = e.getMessage();
		}

		return print;
	}

}
