package com.example.bubaleapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bubaleapp.GMailSender;
import com.example.bubaleapp.R;
import com.parse.ParseUser;


public class contactFrag extends Fragment {

    private final String TAG = "contact";
    private Button submit;
    private Button back;
    private String MAIL_USER;
    private String MAIL_PASS;
    private EditText message;
    private String body;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        submit = view.findViewById(R.id.btnSendM);
        back = view.findViewById(R.id.btnBackP);
        MAIL_USER = getResources().getString(R.string.MAIL_USER);
        MAIL_PASS = getResources().getString(R.string.MAIL_PASS);
        message = view.findViewById(R.id.metMessage);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new homeFragment();
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.maincont, fragment).commit();

            }
        });

      submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              body = message.getText().toString();
              sendmessage();
              Toast.makeText(getContext(), "Message Sent", Toast.LENGTH_SHORT).show();
              Fragment fragment = new homeFragment();
              ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.maincont, fragment).commit();
          }
      });

    }

    private void sendmessage() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GMailSender sender = new GMailSender(MAIL_USER, MAIL_PASS);
                try {
                    Log.d(TAG, "email sent");
                    sender.sendMail("message form user "+ ParseUser.getCurrentUser().getUsername().toString(),
                            "Message content is: \n" + body,
                            ParseUser.getCurrentUser().getEmail().toString(),
                            "bubadevelopment@gmail.com");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        });
        thread.start();

    }
}