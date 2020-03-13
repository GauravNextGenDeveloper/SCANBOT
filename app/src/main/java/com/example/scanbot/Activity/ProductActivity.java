package com.example.scanbot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.scanbot.Adapter.ProductAdapter;
import com.example.scanbot.Database.ScanBotDatabase;
import com.example.scanbot.Database.model.BuyOne;
import com.example.scanbot.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ScanBotDatabase scanBotDatabase;

    private RecyclerView produvtRv;
    private ProductAdapter productAdapter;

    long userid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        produvtRv = findViewById(R.id.produvtRv);

        userid = getIntent().getLongExtra("userid",0);

        scanBotDatabase = ScanBotDatabase.getInstance(ProductActivity.this);
        new RetrieveTask(this).execute();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        produvtRv.setLayoutManager(mLayoutManager);
        produvtRv.setItemAnimator(new DefaultItemAnimator());

    }

    private class RetrieveTask extends AsyncTask<Void, Void, List<BuyOne>> {

        private WeakReference<ProductActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(ProductActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<BuyOne> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().scanBotDatabase.gethistory().findByIds(userid);
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<BuyOne> buyOneList) {
            if (buyOneList != null && buyOneList.size() > 0) {

                dothings(buyOneList);
            }
        }
    }

    private void  dothings(List<BuyOne> buyOneList)
    {
        productAdapter = new ProductAdapter(buyOneList,this);
        produvtRv.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

}
