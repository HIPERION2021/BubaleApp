package com.example.bubaleapp.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.example.bubaleapp.MainActivity;
import com.example.bubaleapp.R;
import com.example.bubaleapp.item;
import com.example.bubaleapp.itemAdapter;
import com.parse.FindCallback;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class shopFragment extends Fragment {

    public static final String TAG = "ShopFragment";
    private RecyclerView rvItems;
    protected itemAdapter adapter;
    protected List<item> allItems;
    private Button btnsearch;
    private EditText search;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvItems = view.findViewById(R.id.rvItems);
        allItems = new ArrayList<>();
        adapter = new itemAdapter(getContext(), allItems);
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        btnsearch = view.findViewById(R.id.btnsearch);
        search = view.findViewById(R.id.etSearch);

        Log.d(TAG,"Query with selector = " + MainActivity.querySelect.toString());
        if(MainActivity.querySelect == 1){queryItems();}else{querySearch();}


        MainActivity.querySelect = 1;

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.queryQ = search.getText().toString();
                MainActivity.querySelect = 0;
                Fragment fragment = new shopFragment();
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.maincont, fragment).commit();
            }
        });

    }


    private void querySearch() {
        String searchQ = MainActivity.queryQ;
        Log.d(TAG, "executing SEARCH...");
        Log.d(TAG, "string is = " + searchQ);
        ParseQuery<item> query2 = ParseQuery.getQuery(item.class).whereContains("title", searchQ);
        query2.orderByDescending(item.KEY_CRATED_KEY);
        query2.findInBackground(new FindCallback<item>() {
            @Override
            public void done(List<item> objects, ParseException e) {
                if(e !=null){
                    Log.e(TAG, "Issue with getting items", e);
                    return;
                }
                Log.d(TAG, "RETURN SIZE = " + objects.size());
                allItems.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void queryItems() {
        Log.d(TAG, "executing ITEMS...");
        ParseQuery<item> query =  ParseQuery.getQuery(item.class);
        query.include(item.KEY_TITLE);
        query.setLimit(20);
        query.addDescendingOrder(item.KEY_CRATED_KEY);
        query.findInBackground(new FindCallback<item>() {
            @Override
            public void done(List<item> items, ParseException e) {
                if(e !=null){
                    Log.e(TAG, "Issue with getting items", e);
                    return;
                }

                allItems.addAll(items);
                adapter.notifyDataSetChanged();

            }
        });
    }
}