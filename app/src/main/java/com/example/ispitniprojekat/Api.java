package com.example.ispitniprojekat;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Api {
    public static void getJSON(String url, final ReadDataHandler rdh) {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {

            //Task koji radi u posebnoj niti

            @Override
            protected String doInBackground(String... strings) {
                String response = "";

                try {
                    URL url = new URL(strings[0]);
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String red;
                    while ((red = br.readLine()) != null) {
                        response += red + "\n";
                    }
                    br.close();
                } catch (Exception e) {
                    response = "[]";
                }
                return response;
            }

            //sta ce se uraditi sa podacima kada stignu iz doInBackground metode
            @Override
            protected void onPostExecute(String response) {
                rdh.setJson(response);
                rdh.sendEmptyMessage(0); // notifikacija glavnoj aktivnosti da je podatak spreman i da ga moze preuzeti

            }
        };

        task.execute(url);
    }
}
