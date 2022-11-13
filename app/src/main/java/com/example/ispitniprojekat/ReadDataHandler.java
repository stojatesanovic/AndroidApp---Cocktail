package com.example.ispitniprojekat;

import android.os.Handler;

//klasa koja smesta Json odgovor u sebe, upravlja podacima koji su dovuceni
public class ReadDataHandler extends Handler {

    private String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
