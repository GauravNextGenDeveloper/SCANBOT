package com.example.scanbot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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
    CheckBox casCheck,olCheck;
    private String MY_PREFS_NAME = "SCANBOOT";
    long userid=0;
    double total=0;
    private ScanBotDatabase scanBotDatabase;
    private List<Cart> cartListarray;
    private ProgressDialog progressDialog;
    boolean cash=true,online=false;

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
        olCheck = findViewById(R.id.olCheck);

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

        casCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true)
                {
                    cash =isChecked;
                    olCheck.setChecked(false);

                }else
                {
                    olCheck.setChecked(true);
                    online=true;
                }
            }
        });

        olCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true)
                {
                    online =isChecked;
                    casCheck.setChecked(false);

                }else
                {
                    casCheck.setChecked(true);
                    cash=true;
                }

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
                buyOne.setProduct_id(cartListarray.get(i).getProduct_id());
                buyOne.setUser_id(userid);
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
            activityReference = new WeakReference<>(context);
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
                if (cash==true)
                {
                    finish();
                }else if (online==true)
                {
                    Intent intent = new Intent(PurchaseActivity.this,WebviewActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }


}
