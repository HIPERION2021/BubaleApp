package com.example.bubaleapp.fragments;


import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.example.bubaleapp.GMailSender;
import com.example.bubaleapp.MainActivity;
import com.example.bubaleapp.R;
import com.example.bubaleapp.item;
import com.example.bubaleapp.orders;
import com.parse.FindCallback;

import com.parse.ParseException;

import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.Order;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PayPalButton;

import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.List;


public class checkoutFragment extends Fragment {

    private static final String TAG = "checkout_fragment";
    private RecyclerView rvcheck;
    public static TextView checkTot;
    private Button payNow;
    private PayPalButton btnPaypal;
    protected Integer orderNumber;
    private String MAIL_USER;
    private String MAIL_PASS;
    private String recipient;
    protected String orderNumber2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvcheck = view.findViewById(R.id.rvcheckout);
        rvcheck.setAdapter(MainActivity.checkAdapter);
        rvcheck.setLayoutManager(new LinearLayoutManager(getContext()));
        checkTot = view.findViewById(R.id.tvTot);
        String tempoTot = MainActivity.total.toString();
        checkTot.setText(tempoTot + " USD");
        payNow = view.findViewById(R.id.btnPayNow);
        btnPaypal = view.findViewById(R.id.payPalButton);
        orderNumber = 1;
        MAIL_USER = getResources().getString(R.string.MAIL_USER);
        MAIL_PASS = getResources().getString(R.string.MAIL_PASS);
        recipient = ParseUser.getCurrentUser().getEmail().toString();

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Address = ParseUser.getCurrentUser().getString("address");
                if(Address == null || Address.length() < 4){
                    Toast.makeText(getContext(), "Invalid Address", Toast.LENGTH_SHORT).show();
                }else{
                   btnPaypal.performClick();


                   Log.d(TAG, "final order num order is = " + orderNumber2);
                }
            }
        });

        btnPaypal.setup(new CreateOrder() {
                            @Override
                            public void create(@NotNull CreateOrderActions createOrderActions) {
                                ArrayList purchaseUnits = new ArrayList<>();
                                purchaseUnits.add(
                                        new PurchaseUnit.Builder()
                                                .amount(
                                                        new Amount.Builder()
                                                                .currencyCode(CurrencyCode.USD)
                                                                .value(MainActivity.total.toString())
                                                                .build()
                                                )
                                                .build()
                                );
                                Order order = new Order(
                                        OrderIntent.CAPTURE,
                                        new AppContext.Builder()
                                                .userAction(UserAction.PAY_NOW)
                                                .build(),
                                        purchaseUnits
                                );
                                createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                            }
                        },
                new OnApprove() {
                    @Override
                    public void onApprove(@NotNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));
                            }
                        });

                        saveorder();
                        sendMail();
                    }
                }
        );

    }


    private void sendMail() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GMailSender sender = new GMailSender(MAIL_USER, MAIL_PASS);
                try {
                    Log.d(TAG, "email sent");
                    sender.sendMail("Thank you for your business!!!",
                            "Thank you for your business, we are processing your order and we will let you kow when its shipped!!!\n" +
                                    "Please let us know if you have an questions. \n" +
                                    "You can check the status of your orders in our app HOME section!!" +
                                    "\n" +
                                    "Respectfully yours,\n" +
                                    "Buba'le MakeUp\n Because theres no such thing as too much makeup!",
                            "bubalesales@gmail.com",
                            recipient);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        });
        thread.start();

    }



    protected void saveorder(){

        ParseQuery<orders> query =  ParseQuery.getQuery(orders.class);
        query.include(orders.KEY_ORDER);
        query.addDescendingOrder(item.KEY_CRATED_KEY);
        query.findInBackground(new FindCallback<orders>() {
            @Override
            public void done(List<orders> objects, ParseException e) {
                if(e !=null){
                    Log.e(TAG, "Issue with getting orders", e);
                    return;
                }
                if(objects.size() > 0){
                    orderNumber = Integer.parseInt(objects.get(0).getOrder());
                    orderNumber = orderNumber + 1;
                    Log.d(TAG, "order number is = "+ orderNumber);
                    orderNumber2 = orderNumber.toString();
                    Log.d(TAG, "order number string is = "+ orderNumber2);
                    writeOrder();
                }
            }
        });



    }

    private void writeOrder() {
        orders order;
        Log.d(TAG, "The saveorder function executes, size is = " + MainActivity.cart.size());
        Integer i = MainActivity.cart.size() - 1;
        //for(int i=0; i< MainActivity.cart.size() - 1; i++){
        while(i>=0){
            item row = MainActivity.cart.get(i);

            order = new orders();
            order.put("user", ParseUser.getCurrentUser().getUsername());
            order.put("item", row.getTitle());
            order.put("price", row.getPrice());
            order.put("order", orderNumber2);

            order.saveInBackground(e -> {
                if (e==null){
                    //Save was done
                    Log.d(TAG, "working?, check!!");
                }else{
                    //Something went wrong
                    Log.e(TAG, "query error" + e);
                }
            });
            i--;
        }
        MainActivity.total = 0;
        MainActivity.cart = new ArrayList<>();
        Fragment fragment = new shopFragment();
        ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.maincont, fragment).commit();
    }

}