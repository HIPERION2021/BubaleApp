package com.example.bubaleapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bubaleapp.fragments.logFragment;
import com.example.bubaleapp.fragments.signFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Login extends AppCompatActivity {

    public static final String TAG = "Login";
    final FragmentManager fragmentManager = getSupportFragmentManager();


    public static BottomNavigationView logmenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);

        logmenu =  findViewById(R.id.loginmenu);
        logmenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new Fragment();
                switch (item.getItemId()) {
                    case R.id.action_log:
                        fragment = new logFragment();
                        break;
                    case R.id.action_sign:
                        fragment = new signFragment();
                        break;
                    default:
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit();
                return true;

            }
        });
        logmenu.setSelectedItemId(R.id.action_log);


    }
}
