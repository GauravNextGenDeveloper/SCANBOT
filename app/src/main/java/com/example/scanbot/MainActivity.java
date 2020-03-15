package com.example.scanbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.scanbot.Activity.DashBordActivity;
import com.example.scanbot.Activity.RegistrationActivity;

public class MainActivity extends AppCompatActivity {
    private String MY_PREFS_NAME = "SCANBOOT";
    boolean login=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        login = prefs.getBoolean("login",false);

        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

            @Override

            public void run() {

                if (login)
                {
                    Intent i = new Intent(MainActivity.this, DashBordActivity.class);

                    startActivity(i);

                    // close this activity

                    finish();

                }else
                {
                    Intent i = new Intent(MainActivity.this, RegistrationActivity.class);

                    startActivity(i);

                    // close this activity

                    finish();

                }

            }

        }, 5*1000); // wait for 5 seconds
    }
}
