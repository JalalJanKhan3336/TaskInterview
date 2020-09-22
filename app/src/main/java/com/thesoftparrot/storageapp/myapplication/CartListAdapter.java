package com.thesoftparrot.storageapp.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thesoftparrot.storageapp.myapplication.databinding.ItemCartBinding;
import com.thesoftparrot.storageapp.myapplication.databinding.ItemProductBinding;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> mList;
    private ItemCartBinding mItemBinding;

    public CartListAdapter(Context mContext, List<Product> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemBinding = ItemCartBinding.inflate(LayoutInflater.from(mContext),parent, false);
        return new MyViewHolder(mItemBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Product product = mList.get(position);
        mItemBinding.nameTv.setText(product.getProductName());
        String quantity = "Quantity: "+product.getQuantity();
        mItemBinding.quantityTv.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
