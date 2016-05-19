package com.example.erikk.smartshop;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLConnection;



/**
 * Created by Erikk on 16/02/2016.
 */
public class HttpControl extends AsyncTask<URL,Integer,Long> {
    protected Long doInBackground(URL... urls) {
        long totalSize = 80;
        for (URL url : urls) {
            this.prueba(url);
        }
        return totalSize;
    }
       public void prueba(URL url) {
           try{
               URLConnection conn = url.openConnection ();
               // Get the response
               BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
               StringBuilder sb = new StringBuilder();
               String line;
               while ((line = rd.readLine()) != null)
               {
                   sb.append(line);
                   System.out.println(line);
               }
               rd.close();
               wait(10);
               System.out.println("fin");
            } catch (Exception e) {
                System.out.println(e.toString());
            }
       }
    public void printar(){
        System.out.println("HOLAA");
    }
}
