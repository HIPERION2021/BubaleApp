package com.example.bubaleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bubaleapp.fragments.checkoutFragment;
import com.example.bubaleapp.fragments.homeFragment;
import com.example.bubaleapp.fragments.shopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView mainmenu;
    public static List<item> cart;
    public static checkOutAdapter checkAdapter;
    public static Integer total;
    public static final String PAYPAL_CLIENT_ID = "AQCMAx2E27Cek6bKztSkX31N7sJao4g-GMtCOWuzIksdw8Rfvx65pOBDkthsZF6VnW1lQ6nKNWorYG3_";
    private static final String TAG = "Main Activity";
    public static Integer querySelect = 1;
    public static String queryQ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        CheckoutConfig config = new CheckoutConfig(
                getApplication(),
                PAYPAL_CLIENT_ID,
                Environment.SANDBOX,
                String.format("%s://paypalpay", BuildConfig.APPLICATION_ID),
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                new SettingsConfig(
                        true,
                        false
                )
        );
        PayPalCheckout.setConfig(config);


        setContentView(R.layout.activity_main);

        mainmenu = findViewById(R.id.navbar);
        mainmenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new Fragment();
                    switch (item.getItemId()){
                        case R.id.action_home:
                            fragment = new homeFragment();
                            querySelect = 1;
                        break;
                        case R.id.action_shop:
                            fragment = new shopFragment();
                        break;
                        case R.id.action_logout:
                            querySelect = 1;
                             logout();
                        break;
                        case R.id.action_profile:
                            if(cart == null){
                                Toast.makeText(getApplicationContext(), "Your cart is EMPTY!", Toast.LENGTH_SHORT).show();
                                fragment = new shopFragment();
                            }
                            else if(cart.size() == 0){
                                Toast.makeText(getApplicationContext(), "Your cart is EMPTY!", Toast.LENGTH_SHORT).show();
                                fragment = new shopFragment();
                            }else {
                                fragment = new checkoutFragment();
                                querySelect = 1;

                            }
                        break;
                        default:
                            break;
                    }
                fragmentManager.beginTransaction().replace(R.id.maincont, fragment).commit();

                return false;
            }
        });
        mainmenu.setSelectedItemId(R.id.action_shop);

    }




    private void logout() {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        gotologin();
    }

    private void gotologin() {
        Intent log = new Intent(MainActivity.this, Login.class);
        startActivity(log);
        finish();
    }

}