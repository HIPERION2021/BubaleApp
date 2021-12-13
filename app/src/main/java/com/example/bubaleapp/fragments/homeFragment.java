package com.example.bubaleapp.fragments;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bubaleapp.R;
import com.parse.ParseUser;

public class homeFragment extends Fragment {
    private TextView name;
    private TextView first;
    private TextView last;
    private TextView mail;
    private TextView address;
    private TextView address2;
    private TextView nzip;
    private Button btnOrder;
    private Button btnProfile;
    private Button btncontact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.name);
        first = view.findViewById(R.id.tvname);
        last = view.findViewById(R.id.tvlast);
        mail = view.findViewById(R.id.tvmail);
        address = view.findViewById(R.id.tvstreet);
        address2 = view.findViewById(R.id.tvstreet2);
        nzip = view.findViewById(R.id.tvzip);
        btncontact = view.findViewById(R.id.btncontact);
        btnOrder = view.findViewById(R.id.prevOr);
        btnProfile = view.findViewById(R.id.btnprofile);


        name.setText(ParseUser.getCurrentUser().getUsername());
        mail.setText(ParseUser.getCurrentUser().getEmail());
        first.setText(ParseUser.getCurrentUser().getString("first"));
        last.setText(ParseUser.getCurrentUser().getString("last"));
        if(ParseUser.getCurrentUser().getString("address") != null){
            address.setText(ParseUser.getCurrentUser().getString("address"));
        }else{
            address.setText("NOT SET");
        }
        if(ParseUser.getCurrentUser().getString("address2") != null){
            address2.setText(ParseUser.getCurrentUser().getString("address2"));
        }else{
            address2.setText("NOT SET");
        }
        if(ParseUser.getCurrentUser().getString("zip") != null){
            nzip.setText(ParseUser.getCurrentUser().getString("zip"));
        }else{
            nzip.setText("NOT SET");
        }

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditProfile();
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.maincont, fragment).commit();

            }
        });

        btncontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new contactFrag();
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.maincont, fragment).commit();


            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new prevOrders();
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.maincont, fragment).commit();


            }
        });



    }
}