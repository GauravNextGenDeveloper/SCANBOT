package com.example.scanbot.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.scanbot.Database.model.BuyOne;
import com.example.scanbot.Database.model.Cart;
import com.example.scanbot.R;

import java.util.List;

/**
 * Created by Pavneet_Singh on 12/20/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.BeanHolder> {

    private List<BuyOne> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public ProductAdapter(List<BuyOne> list, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }


    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cart_list_item, parent, false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        Log.e("bind", "onBindViewHolder: " + list.get(position));
        holder.productnameTv.setText("Name: " + list.get(position).getProductname());
        holder.productqteTv.setText("Quantity: " + list.get(position).getProductquantity());
        holder.productpriceTv.setText("Price: " + list.get(position).getProductprice());

        Glide.with(context).load(list.get(position).getImage()).into(holder.productimage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView productnameTv;
        TextView productqteTv;
        TextView productpriceTv;
        ImageView productimage;

        public BeanHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            productnameTv = itemView.findViewById(R.id.productnameTv);
            productqteTv = itemView.findViewById(R.id.productqteTv);
            productpriceTv = itemView.findViewById(R.id.productpriceTv);
            productimage = itemView.findViewById(R.id.productimage);
        }

        @Override
        public void onClick(View view) {
        }
    }

    public interface OnNoteItemClick {
        void onNoteClick(int pos);
    }
}