package com.example.bubaleapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.parse.ParseQuery;
import com.parse.ParseUser;

public class retrieve extends AppCompatActivity {
    private String TAG = "retrieve";
    private Button btnback;
    private Button btnSubmit;
    private String MAIL_USER;
    private String MAIL_PASS;
    private String recipient;
    private EditText First;
    private EditText Last;
    private EditText mail;
    private EditText user;
    private String pass1;
    private String senduser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_retrieve);

        First = findViewById(R.id.etFirst);
        Last = findViewById(R.id.etLast);
        mail = findViewById(R.id.etMail);
        user = findViewById(R.id.etUser);
        MAIL_USER = getResources().getString(R.string.MAIL_USER);
        MAIL_PASS = getResources().getString(R.string.MAIL_PASS);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(retrieve.this, Login.class);
                startActivity(j);
                finish();
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkuser = user.getText().toString();
                senduser = user.getText().toString();
                String checkfirst = First.getText().toString();
                String checklast = Last.getText().toString();
                String checkmail = mail.getText().toString();

                if (checkuser.length() < 1  || checkfirst.length() < 1 || checklast.length() < 1 || checkmail.length() < 1) {
                    Toast.makeText(getApplicationContext(), "Please complete all the fields", Toast.LENGTH_SHORT).show();

                }else{
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("username", checkuser);
                    query.findInBackground((objects, e) -> {
                        if(objects.size() == 0){
                            Toast.makeText(getApplicationContext(), "User not found!!", Toast.LENGTH_SHORT).show();
                        }else {
                            String tempfirst = objects.get(0).getString("first");
                            String templast = objects.get(0).getString("last");
                            String tempmail = objects.get(0).getString("correo");
                            pass1 = objects.get(0).getString("append").trim();

                            if (tempfirst.equals(checkfirst) & templast.equals(checklast) & tempmail.equals(checkmail)) {
                                recipient = tempmail.trim();
                                sendretmail();
                                Toast.makeText(getApplicationContext(), "Check your Email!!", Toast.LENGTH_SHORT).show();
                                Intent j = new Intent(retrieve.this, Login.class);
                                startActivity(j);

                            } else {
                                Toast.makeText(getApplicationContext(), "Personal Information Mismatch!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });

    }

    private void sendretmail() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GMailSender sender = new GMailSender(MAIL_USER, MAIL_PASS);
                try {
                    Log.d(TAG, "email sent");
                    sender.sendMail("Bubale Password Recovery",
                            "HI "+ senduser +"!!\n" +
                                    "You have requested your password to your Buba'le App account \n" +
                                    "The password, as in our records is = "+ pass1 + " \n" +
                                    "If you DID NOT requested this password please contact us so we can protect your privacy.\n" +
                                    "\n" +
                                    "Respectfully yours,\n" +
                                    "Buba'le MakeUp,\n" +
                                    "Because there's no such thing as too much MakeUp!!!",
                            "Bubaledevelopment@gmail.com",
                            recipient);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        });
        thread.start();
    }
}
