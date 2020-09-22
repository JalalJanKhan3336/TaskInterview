package com.thesoftparrot.storageapp.myapplication.apiwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thesoftparrot.storageapp.myapplication.AddToCartCallback;
import com.thesoftparrot.storageapp.myapplication.Product;
import com.thesoftparrot.storageapp.myapplication.R;
import com.thesoftparrot.storageapp.myapplication.databinding.ItemProductBinding;
import com.thesoftparrot.storageapp.myapplication.databinding.ItemTestItemBinding;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Item> mList;
    private ItemTestItemBinding mItemBinding;

    public ItemListAdapter(Context mContext, List<Item> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemBinding = ItemTestItemBinding.inflate(LayoutInflater.from(mContext),parent, false);
        return new MyViewHolder(mItemBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Item product = mList.get(position);
        mItemBinding.nameTv.setText(product.getName());

        Glide
                .with(mContext)
                .load(product.getImageLink())
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .into(mItemBinding.imageIv);
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
