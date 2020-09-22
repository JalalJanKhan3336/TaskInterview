package com.thesoftparrot.storageapp.myapplication.apiwork;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thesoftparrot.storageapp.myapplication.DatabaseHelper;
import com.thesoftparrot.storageapp.myapplication.databinding.FragmentApiWorkBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApiWorkFragment extends Fragment {

    private static final String TAG = "ApiWorkFragment";

    private FragmentApiWorkBinding mBinding;
    private ItemListAdapter mAdapter;

    public ApiWorkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentApiWorkBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchData();
    }

/*
    private void getDummyList() {
        final List<Item> list = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            String name = "Product No. "+i;
            String image = null;

            list.add(new Item(name, image));
        }

        setUpRecyclerView(list);
    }
*/

    private void fetchData() {
        final List<Item> list = new ArrayList<>();

        DatabaseHelper.getInstance().getProductList(KeyUtils.CATEGORY_URL, new JsonArrayResponseCallback() {
            @Override
            public void onJsonArrayResponseSuccess(JSONArray jsonArray) {
                Log.d(TAG, "_onJsonArrayResponseSuccess_JsonArray_Size: "+jsonArray.length());

                if(jsonArray != null && jsonArray.length() > 0){
                    for(int i = 0; i < jsonArray.length(); i++){
                        try {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("name");
                            String image = object.getJSONObject("image").getString("src");

                            list.add(new Item(name, image));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    setUpRecyclerView(list);
                }

            }

            @Override
            public void onJsonArrayResponseFailed(String error) {
                Log.e(TAG, "_onJsonArrayResponseFailed_JsonArray_Error: "+error);
            }
        });
    }

    private void setUpRecyclerView(List<Item> list) {
        if(mAdapter == null)
            mAdapter = new ItemListAdapter(requireContext(),list);

        GridLayoutManager layout = new GridLayoutManager(requireContext(), 2);
        layout.setSmoothScrollbarEnabled(true);
        mBinding.rcIncluder.recyclerview.setLayoutManager(layout);
        mBinding.rcIncluder.recyclerview.setHasFixedSize(true);
        mBinding.rcIncluder.recyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}