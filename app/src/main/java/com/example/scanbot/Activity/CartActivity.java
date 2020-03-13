package com.example.scanbot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.scanbot.Adapter.CartAdapter;
import com.example.scanbot.Database.ScanBotDatabase;
import com.example.scanbot.Database.model.Cart;
import com.example.scanbot.Database.model.Register;
import com.example.scanbot.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ScanBotDatabase scanBotDatabase;

    private RecyclerView cartRv;
    private TextView totalTv;
    private Button submitbtn;
    private CartAdapter cartAdapter;
    long userid=0;
    double totalfar=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRv = findViewById(R.id.cartRv);
        totalTv = findViewById(R.id.totalTv);
        submitbtn = findViewById(R.id.submitbtn);
        submitbtn.setVisibility(View.GONE);

        userid = getIntent().getLongExtra("userid",0);

        scanBotDatabase = ScanBotDatabase.getInstance(CartActivity.this);
        new RetrieveTask(this).execute();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        cartRv.setLayoutManager(mLayoutManager);
        cartRv.setItemAnimator(new DefaultItemAnimator());

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this,PurchaseActivity.class);
                i.putExtra("userid",userid);
                i.putExtra("totalfare",totalfar);
                startActivity(i);
                finish();
            }
        });

    }


    private class RetrieveTask extends AsyncTask<Void, Void, List<Cart>> {

        private WeakReference<CartActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(CartActivity context) {
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

    private void  dothings(List<Cart> cartList)
    {
        cartAdapter = new CartAdapter(cartList,this);
        cartRv.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        double total=0;

        for (int i=0;i<cartList.size();i++)
        {
            double price  = Double.parseDouble(cartList.get(i).getTotalprice());
            total = price+total;
        }

        if (total==0)
        {
            submitbtn.setVisibility(View.GONE);
        }else {
            totalTv.setText(total + "");
            totalfar = total;
            submitbtn.setVisibility(View.VISIBLE);
        }
    }

}
