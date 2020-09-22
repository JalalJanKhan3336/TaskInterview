package com.thesoftparrot.storageapp.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.firebase.firestore.DocumentSnapshot;
import com.thesoftparrot.storageapp.myapplication.databinding.FragmentListBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListFragment extends Fragment implements AddToCartCallback<Product> {

    private static final String TAG = "ListFragment";

    private FragmentListBinding mBinding;
    private ProductListAdapter mAdapter;

    private static QuantityAddedListener quantityAddedListener;

    public ListFragment() {
        // Required empty public constructor
    }

    public static void setListener(QuantityAddedListener listener) {
        quantityAddedListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentListBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getList();
    }

    // Fetch data from Firebase Database
    private void getList() {
        final List<Product> list = new ArrayList<>();

        DatabaseHelper.getInstance().fetchAll("Products", new FetchAllCallback() {
            @Override
            public void onAllFetchedSuccess(List<DocumentSnapshot> snapshot) {
                Log.d(TAG, "_onAllFetchedSuccess_Size: "+snapshot.size());
                if(snapshot != null && snapshot.size() > 0){
                    for(DocumentSnapshot ds : snapshot){
                        if(ds != null && ds.exists()){
                            Product product = ds.toObject(Product.class);

                            if(product != null)
                                list.add(product);
                        }
                    }

                    Log.d(TAG, "_getList_Size: "+list.size());
                    setUpRecyclerView(list);
                }

            }

            @Override
            public void onAllFetchedFailure(String error) {
                Toast.makeText(requireContext(), "Error: "+error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpRecyclerView(List<Product> list) {
        if(mAdapter == null)
            mAdapter = new ProductListAdapter(requireContext(), list, this);

        GridLayoutManager layout = new GridLayoutManager(requireContext(),2);
        layout.setSmoothScrollbarEnabled(true);
        mBinding.rcIncluder.recyclerview.setLayoutManager(layout);
        mBinding.rcIncluder.recyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemAdded(final Product item, int position) {
        DatabaseHelper.getInstance().update("Cart", item.getProductId(), item, new UpdateCallback() {
            @Override
            public void onUpdateSuccess(String msg) {
                quantityAddedListener.onQuantityAdded(Integer.parseInt(item.getQuantity()));
                Toast.makeText(requireContext(), item.getProductName()+msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpdateFailure(String error) {
                Toast.makeText(requireContext(), "Error: "+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeQauntity(String quantity) {
        SharedPreferences pref = requireContext().getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key", quantity);
        editor.apply();
    }

    // interface
    public interface QuantityAddedListener {
        void onQuantityAdded(int quantity);
    }
}