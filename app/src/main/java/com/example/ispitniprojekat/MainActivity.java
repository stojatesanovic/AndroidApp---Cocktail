package com.example.ispitniprojekat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    EditText inputUsername;
    EditText inputPassword;
    Button buttonSignIn;
    Button buttonRegister;
    TextView labelLoginError;

    SharedPreferences preferences;

    Button buttonAuth;
    TextView labelAuthStatus;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonRegister = findViewById(R.id.buttonRegister);
        labelLoginError = findViewById(R.id.labelLoginError);
        preferences = getSharedPreferences("Userinfo", 0);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();

                String registeredUsername = preferences.getString("username", "");
                String registeredPassword = preferences.getString("password", "");

                if (!(username.length() > 3) || !(password.length() > 8)) {
                    labelLoginError.setText("Invalid login credentials! Username must be longer than 3 characters, and password must be longer than 8.");
                } else if (username.equals(registeredUsername) && password.equals(registeredPassword)) {
                    labelLoginError.setText("Invalid login credentials! The user does not exist.");
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonAuth = findViewById(R.id.buttonAuth);
        labelAuthStatus = findViewById(R.id.labelAuthStatus);

        //init the values
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //in any error come while auth
                labelAuthStatus.setText("Error: " + errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //Auth successfull
                //labelAuthStatus.setText("Successfully Auth");
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //failed to auth
                labelAuthStatus.setText("Authentication Failed");
            }
        });

        //setup title, descritpion on auth dialog
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using fingerprint or face")
                .setNegativeButtonText("Cancel")
                .build();

        buttonAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show auth dialog
                biometricPrompt.authenticate(promptInfo);
            }
        });
       checkBiometricSupport();
    }

    //checking security settings
    private boolean checkBiometricSupport(){
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);

        if(!keyguardManager.isKeyguardSecure()){
            labelAuthStatus.setText("Lock screen security not enable in Settings");
            return false;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED){
            labelAuthStatus.setText("Fingerprint authentication permission not enabled");
            return false;
        }

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
//            labelAuthStatus.setText(" ");
            return true;
        }

        return true;
    }
}
