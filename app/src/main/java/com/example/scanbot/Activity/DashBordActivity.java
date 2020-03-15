package com.example.scanbot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.scanbot.R;

public class DashBordActivity extends AppCompatActivity {

    LinearLayoutCompat profile,cart,history,scanner;
    ImageView logoutIv;
    long userid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_bord);

        initialize();
    }

    private void initialize() {

        userid = getIntent().getLongExtra("userid",0);

        profile=findViewById(R.id.profile);
        cart=findViewById(R.id.cart);
        history=findViewById(R.id.history);
        scanner=findViewById(R.id.scanner);
        logoutIv=findViewById(R.id.logoutIv);


        logoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBordActivity.this,ProfileActivity.class);
                startActivity(i);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBordActivity.this,CartActivity.class);
                i.putExtra("userid",userid);
                startActivity(i);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBordActivity.this,ProductActivity.class);
                i.putExtra("userid",userid);
                startActivity(i);
            }
        });

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DashBordActivity.this,ScannerActivity.class);
                i.putExtra("userid",userid);
                startActivity(i);

            }
        });
    }

    private void logout() {
        SharedPreferences settings = DashBordActivity.this.getSharedPreferences("SCANBOOT", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        Intent i =new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
