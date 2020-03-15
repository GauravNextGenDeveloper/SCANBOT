package com.example.scanbot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scanbot.R;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {

    TextInputEditText nametv,mobiletv,emailtv,addressTv;
    private String MY_PREFS_NAME = "SCANBOOT";
    Button btnsub;
    String name,mobile,email,address;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editor =  getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
         name = prefs.getString("name", "");
         email = prefs.getString("email", "");
         mobile = prefs.getString("mobile", "");
         address = prefs.getString("address", "");

        initialize();
    }

    private void initialize() {

        nametv=findViewById(R.id.nametv);
        mobiletv=findViewById(R.id.mobiletv);
        emailtv=findViewById(R.id.emailtv);
        addressTv=findViewById(R.id.addressTv);
        btnsub=findViewById(R.id.btnsub);

        nametv.setText(name);
        mobiletv.setText(mobile);
        emailtv.setText(email);
        addressTv.setText(address);

        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("name", nametv.getText().toString());
                editor.putString("email", emailtv.getText().toString());
                editor.putString("mobile", mobiletv.getText().toString());
                editor.putString("address", addressTv.getText().toString());
                editor.apply();
                editor.commit();

                finish();

            }
        });
    }
}
