package com.thesoftparrot.storageapp.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thesoftparrot.storageapp.myapplication.databinding.ItemProductBinding;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> mList;
    private AddToCartCallback<Product> mAddToCartCallback;
    private ItemProductBinding mItemBinding;

    public ProductListAdapter(Context mContext, List<Product> mList, AddToCartCallback<Product> mAddToCartCallback) {
        this.mContext = mContext;
        this.mList = mList;
        this.mAddToCartCallback = mAddToCartCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemBinding = ItemProductBinding.inflate(LayoutInflater.from(mContext),parent, false);
        return new MyViewHolder(mItemBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Product product = mList.get(position);
        mItemBinding.nameTv.setText(product.getProductName());

        mItemBinding.addToCartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(product.getQuantity())+1;
                product.setQuantity(String.valueOf(quantity));
                mAddToCartCallback.onItemAdded(product, position);
            }
        });
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
