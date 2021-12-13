package com.example.bubaleapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bubaleapp.Login;
import com.example.bubaleapp.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class signFragment extends Fragment {

    public static final String TAG = "signFragment";
    private EditText etuser;
    private EditText etpass;
    private EditText etmail;
    private EditText etfirst;
    private EditText etlast;
    private Button btnSub;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_sign, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();
        etuser = view.findViewById(R.id.etUser);
        etpass = view.findViewById(R.id.etPass);
        etmail = view.findViewById(R.id.etMail);
        etfirst = view.findViewById(R.id.etFirst);
        etlast = view.findViewById(R.id.etLast);
        btnSub = view.findViewById(R.id.btnSubmit);

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etuser.getText().toString();
                String password = etpass.getText().toString();
                String email = etmail.getText().toString();
                String first = etfirst.getText().toString();
                String last = etlast.getText().toString();
                if(username.isEmpty() | password.isEmpty() | email.isEmpty() | first.isEmpty() | last.isEmpty()){
                    Toast.makeText(getContext(), "Please complete all the Fields", Toast.LENGTH_SHORT).show();
                }else{
                    saveUser(username, password, first, last, email);
                }
           }

        });
    }

    private void saveUser(String user, String pass, String first, String last, String email) {

        ParseUser User = new ParseUser();
        User.setUsername(user);
        User.setPassword(pass);
        User.setEmail(email);
        User.put("first", first);
        User.put("last", last);
        User.put("correo", email);
        User.put("append", pass);

        User.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                    if(e==null){
                        Toast.makeText(context.getApplicationContext(), "User saved!!", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.e(TAG, "something went wrong" + e);
                    }
            }
        });

                Log.i(TAG, "user saved!!");
                etuser.setText("");
                etpass.setText("");
                etmail.setText("");
                etfirst.setText("");
                etlast.setText("");
                Fragment fragment = new logFragment();
               ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment).commit();
                Login.logmenu.setSelectedItemId(R.id.action_log);

        }
    }