package com.example.scanbot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.InputEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.scanbot.Database.ScanBotDatabase;
import com.example.scanbot.Database.model.Cart;
import com.example.scanbot.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class DetailsProductActivity extends AppCompatActivity {

    ImageView productimage;
    TextInputEditText nameEt,priceEt,quantityEt,totalEt;
    Button loginbtn;
    String result;

    String productname,price,quantity,totalprice,image;

    private ScanBotDatabase scanBotDatabase;
    private ProgressDialog progressDialog;

    private Cart cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);

        scanBotDatabase = ScanBotDatabase.getInstance(DetailsProductActivity.this);


        result = getIntent().getStringExtra("result");
        initialize();
    }

    private void initialize() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("loading.....");


        nameEt = findViewById(R.id.nameEt);
        priceEt = findViewById(R.id.priceEt);
        quantityEt = findViewById(R.id.quantityEt);
        totalEt = findViewById(R.id.totalEt);
        loginbtn = findViewById(R.id.loginbtn);
        productimage = findViewById(R.id.productimage);


        if (!result.equalsIgnoreCase(""))
        {
            try {
                JSONObject jsonObject = new JSONObject(result);

                productname = jsonObject.getString("ProductName");
                price = jsonObject.getString("ProductPrice");
                quantity = jsonObject.getString("ProductQuantity");
                totalprice = jsonObject.getString("ProductPrice");
                image = jsonObject.getString("ProductImage");

                nameEt.setText(productname);
                priceEt.setText(price);
                quantityEt.setText(quantity);
                totalEt.setText(totalprice);

                Glide.with(this).load(image).into(productimage);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        quantityEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0) {
                    quantity =s.toString();

                    totalprice = String.valueOf(Double.parseDouble(price)*Integer.parseInt(quantity));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quantity.equalsIgnoreCase(""))
                {

                }else
                {
                    addToCart();
                }

            }
        });
    }

    private void addToCart() {

        cart.setProductname(productname);
        cart.setProductprice(price);
        cart.setTotalprice(totalprice);
        cart.setProductquantity(quantity);
        cart.setImage(image);

        new InsertTask(DetailsProductActivity.this, cart).execute();

    }

    private class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private Cart cart;
        private WeakReference<DetailsProductActivity> activityReference;
        // only retain a weak reference to the activity
        InsertTask(DetailsProductActivity context, Cart cart) {
            this.cart = cart;
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
            long j = activityReference.get().scanBotDatabase.getCart().insertCart(cart);
            Log.e("ID ", "doInBackground: " + j);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {

            HideProgress();
            if (bool) {
                finish();
            }
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



}
