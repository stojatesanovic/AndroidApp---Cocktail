package com.example.ispitniprojekat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity{

    TextView labelShowUser;
    SharedPreferences preferences; //ta klasa sluzi za interno cuvanje podataka u toj sesiji

    protected void onCreate(Bundle savedInstanceState) { //Bundel je vrsta mape, smesta parove kljuc-vrednost vise tipova podataka moze da smesti
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        preferences = getSharedPreferences("Userinfo", 0);

        labelShowUser = findViewById(R.id.labelShowUser);
        String preuzimanjeTeksta = "Username: " + preferences.getString("username", "") + "\n" + "Email: " + preferences.getString("email", "")
                + "\n" + "Birth date: " + preferences.getString("birthday", "");

        labelShowUser.setText(preuzimanjeTeksta);

    }
}
