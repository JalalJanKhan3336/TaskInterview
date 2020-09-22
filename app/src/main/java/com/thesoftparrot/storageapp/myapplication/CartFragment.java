package com.thesoftparrot.storageapp.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.DocumentSnapshot;
import com.thesoftparrot.storageapp.myapplication.databinding.FragmentCartBinding;
import com.thesoftparrot.storageapp.myapplication.databinding.FragmentListBinding;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private FragmentCartBinding mBinding;
    private CartListAdapter mAdapter;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentCartBinding.inflate(inflater, container, false);
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

        DatabaseHelper.getInstance().fetchAll("Cart", new FetchAllCallback() {
            @Override
            public void onAllFetchedSuccess(List<DocumentSnapshot> snapshot) {
                if(snapshot != null && snapshot.size() > 0){
                    for(DocumentSnapshot ds : snapshot){
                        if(ds != null && ds.exists()){
                            Product product = ds.toObject(Product.class);

                            if(product != null)
                                list.add(product);
                        }
                    }

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
            mAdapter = new CartListAdapter(requireContext(), list);

        LinearLayoutManager layout = new LinearLayoutManager(requireContext());
        layout.setSmoothScrollbarEnabled(true);
        mBinding.rcIncluder.recyclerview.setLayoutManager(layout);
        mBinding.rcIncluder.recyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}