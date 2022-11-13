package com.example.ispitniprojekat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText inputUsername;
    EditText inputPassword;
    EditText inputEmail;
    DatePicker inputBirthday;
    Button buttonSignIn;
    Button buttonRegister;
    TextView labelSignupError;

    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        inputEmail = findViewById(R.id.inputEmail);
        inputBirthday = findViewById(R.id.inputBirthday);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonRegister = findViewById(R.id.buttonRegister);
        labelSignupError = findViewById(R.id.labelSignupError);

        preferences = getSharedPreferences("Userinfo", 0);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();
                String email = inputEmail.getText().toString();
                String birthday = String.format("%2d-%2d-%4d", inputBirthday.getDayOfMonth(),
                        inputBirthday.getMonth() + 1, inputBirthday.getYear());

                if (!(username.length() > 3) || !(password.length() > 8) || !(email.length() > 6)) {
                    labelSignupError.setText("Invalid signup credentials! Username must be longer than 3 characters, password must be longer than 8 and email must be longer than 6.");
                } else {
                    SharedPreferences.Editor editor = preferences.edit(); //instanciram klasu koja omogucava stavljanje podataka u instancu
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putString("email", email);
                    editor.putString("birthday", birthday);
                    editor.apply();
                    Toast.makeText(RegisterActivity.this, "User is registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent); // kad iz jednog aktivitija prelazimo u drugi
                }
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
