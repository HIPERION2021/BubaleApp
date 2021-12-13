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

import com.example.bubaleapp.PrevOrAdapter;
import com.example.bubaleapp.R;
import com.example.bubaleapp.item;
import com.example.bubaleapp.itemAdapter;
import com.example.bubaleapp.orders;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class prevOrders extends Fragment {

    private static final String TAG = "prevOrders";
    private Button btnback;
    protected PrevOrAdapter adapter;
    protected List<orders> allItems;
    private RecyclerView rvPrev;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prev_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPrev = view.findViewById(R.id.rvprevious);
        btnback = view.findViewById(R.id.btnBack);
        allItems = new ArrayList<>();
        adapter = new PrevOrAdapter(getContext(), allItems);
        rvPrev.setAdapter(adapter);
        rvPrev.setLayoutManager(new LinearLayoutManager(getContext()));

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new homeFragment();
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.maincont, fragment).commit();
            }
        });

        ParseQuery<orders> query = ParseQuery.getQuery("orders");
        query.whereEqualTo("user", ParseUser.getCurrentUser().getUsername().toString());
        query.findInBackground(new FindCallback<orders>() {
            @Override
            public void done(List<orders> objects, ParseException e) {
                if(e !=null){
                    Log.e(TAG, "Issue getting orders", e);
                    return;
                }
                Log.d(TAG, "RETURN SIZE = " + objects.size());
                allItems.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });


    }
}