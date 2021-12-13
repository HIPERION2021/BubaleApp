package com.example.bubaleapp.fragments;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.bubaleapp.R;
import com.example.bubaleapp.detailAdapter;
import com.example.bubaleapp.item;

import com.parse.FindCallback;

import com.parse.ParseException;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ItemDetail extends Fragment {

    private static final String TAG = "ItemDetail";
    protected detailAdapter adapter;
    protected List<item> detailitem;
    private RecyclerView rvDetail;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_item_detail, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDetail = view.findViewById(R.id.rvDetail);
        detailitem = new ArrayList<>();
        adapter = new detailAdapter(getContext(), detailitem);
        rvDetail.setAdapter(adapter);
        rvDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        queryitem();



    }

    private void queryitem() {
        String Title = getArguments().getString("Title");
        ParseQuery<item> query = ParseQuery.getQuery(item.class).whereEqualTo("title", Title);
        query.setLimit(1);
        query.findInBackground(new FindCallback<item>() {
            @Override
            public void done(List<item> detail, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting item", e);
                    return;
                }
                detailitem.addAll(detail);
                adapter.notifyDataSetChanged();
            }
        });
    }
}