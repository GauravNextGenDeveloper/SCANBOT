package com.example.scanbot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.scanbot.Database.ScanBotDatabase;
import com.example.scanbot.Database.model.BuyOne;
import com.example.scanbot.Database.model.Cart;
import com.example.scanbot.R;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PurchaseActivity extends AppCompatActivity {

    TextInputEditText totalEt,addressTv;
    Button loginbtn;
    CheckBox casCheck;
    private String MY_PREFS_NAME = "SCANBOOT";
    long userid=0;
    double total=0;
    private ScanBotDatabase scanBotDatabase;
    private List<Cart> cartListarray;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        cartListarray = new ArrayList<>();

        initialize();
    }

    private void initialize() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("loading.....");


        scanBotDatabase = ScanBotDatabase.getInstance(PurchaseActivity.this);
        new RetrieveTask(this).execute();


        totalEt = findViewById(R.id.totalEt);
        addressTv = findViewById(R.id.addressTv);
        loginbtn = findViewById(R.id.loginbtn);
        casCheck = findViewById(R.id.casCheck);

        userid=getIntent().getLongExtra("userid",0);
        total=getIntent().getDoubleExtra("totalfare",0);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String address = prefs.getString("name", "");
        addressTv.setText(address);
        totalEt.setText(total+"");

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private class RetrieveTask extends AsyncTask<Void, Void, List<Cart>> {

        private WeakReference<PurchaseActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(PurchaseActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Cart> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().scanBotDatabase.getCart().findByIds(userid);
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Cart> cartList) {
            if (cartList != null && cartList.size() > 0) {
                dothings(cartList);
            }
        }
    }

    private void dothings(List<Cart> cartList) {

        if (cartList.size()!=0)
        {
            cartListarray.addAll(cartList);
        }

    }



    private void ShowProgress()
    {
        if (!progressDialog.isShowing())
        {
            progressDialog.show();
        }
    }

    private void HideProgress()
    {
        if (progressDialog.isShowing())
        {
            progressDialog.cancel();
        }
    }


    private void addToCart() {

        if (cartListarray.size()!=0)
        {
            for (int i=0;i<cartListarray.size();i++)
            {
                BuyOne buyOne = new BuyOne();
                buyOne.setProductname(cartListarray.get(i).getProductname());
                buyOne.setProductprice(cartListarray.get(i).getProductprice());
                buyOne.setTotalprice(cartListarray.get(i).getTotalprice());
                buyOne.setProductquantity(cartListarray.get(i).getProductquantity());
                buyOne.setImage(cartListarray.get(i).getImage());

                new InsertTask(PurchaseActivity.this, buyOne).execute();

            }
        }
    }

    private class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private BuyOne buyOne;
        private WeakReference<PurchaseActivity> activityReference;
        // only retain a weak reference to the activity
        InsertTask(PurchaseActivity context, BuyOne buyOne) {
            this.buyOne = buyOne;
        }

        @Override
        protected void onPreExecute() {

            ShowProgress();
            super.onPreExecute();

        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented note id
            long j = activityReference.get().scanBotDatabase.gethistory().insertCart(buyOne);
            Log.e("ID ", "doInBackground: " + j);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            activityReference.get().scanBotDatabase.getCart().deleteCart();
            HideProgress();
            if (bool) {
                finish();
            }
        }
    }


}