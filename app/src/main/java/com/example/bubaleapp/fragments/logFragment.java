package com.example.bubaleapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.bubaleapp.MainActivity;
import com.example.bubaleapp.R;
import com.example.bubaleapp.retrieve;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;


public class logFragment extends Fragment {

    public static final String TAG = "logFragment";
    private Button btnForgot;
    private Button btnlogin;
    private EditText etUser;
    private EditText etPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnlogin = view.findViewById(R.id.btnlog);
        etUser = view.findViewById(R.id.etUsername);
        etPass = view.findViewById(R.id.etPassword);


        btnForgot = view.findViewById(R.id.btnforgot);
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoretrieve();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUser.getText().toString();
                String password = etPass.getText().toString();
                loginUser(username, password);

            }
        });

    }

    private void loginUser(String username, String password) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Login issue", e);
                    Toast.makeText(getActivity(), "Login Error, Please try Again", Toast.LENGTH_SHORT).show();
                    return;
                }
                gotomain();
            }
        });

    }


    private void gotomain() {
        Intent g = new Intent(getActivity(), MainActivity.class);
        startActivity(g);
        getActivity().finish();
    }

    private void gotoretrieve() {
        Intent i = new Intent(getActivity(), retrieve.class);
        startActivity(i);
        getActivity().finish();
    }


    }

