package com.example.bubaleapp;


import android.content.Context;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bubaleapp.fragments.shopFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class detailAdapter extends RecyclerView.Adapter<detailAdapter.ViewHolder>{

    private static final String TAG = "itemAdapter";
    private Context context;
    private List<item> items;
    private String temporal;
    private static Integer addCheck;


    public detailAdapter(Context context, List<item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public detailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail, parent, false);
        return new detailAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull detailAdapter.ViewHolder holder, int position) {
        item item = items.get(position);
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvPrice;
        private ImageView ivImage;
        private TextView tvDesc2;
        private Button BtnBack;
        private Button BtnAdd;

        public ViewHolder(View view) {
            super(view);
            addCheck = 0;
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDesc = view.findViewById(R.id.tvDesc);
            tvPrice = view.findViewById(R.id.tvPrice);
            ivImage = view.findViewById(R.id.ivPic);
            tvDesc2 = view.findViewById(R.id.tvDesc2);
            tvDesc2.setMovementMethod(new ScrollingMovementMethod());
            BtnBack = view.findViewById(R.id.btnShopB);
            BtnAdd = view.findViewById(R.id.btnAdd);



            tvDesc2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            BtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    querycheckout();
                }
            });

            BtnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new shopFragment();
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.maincont, fragment).commit();
               }
            });

        }

        private void querycheckout() {
            String Title = tvTitle.getText().toString();
            ParseQuery<item> query = ParseQuery.getQuery(item.class).whereEqualTo("title", Title);
            query.setLimit(1);
            query.findInBackground(new FindCallback<item>() {
                @Override
                public void done(List<item> checkout, ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Issue with getting item", e);
                        return;
                    }
                    if(MainActivity.cart == null){MainActivity.cart = new ArrayList<>();
                        MainActivity.checkAdapter = new checkOutAdapter(context, MainActivity.cart);
                    }
                    for(int i=0; i<= MainActivity.cart.size() - 1; i++){
                        String checking = MainActivity.cart.get(i).getTitle().toString();
                        if(checking.trim().equals(Title.trim())){
                            addCheck = 1;
                        }

                    }

                    if(addCheck > 0){
                        Toast.makeText(context, "Item already in the cart!!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Item successfully added!!", Toast.LENGTH_SHORT).show();
                        MainActivity.cart.addAll(checkout);
                        if(MainActivity.total == null){MainActivity.total = 0;}
                        Integer addTot = Integer.parseInt(temporal);
                        MainActivity.total = MainActivity.total + addTot;

                        MainActivity.checkAdapter.notifyDataSetChanged();

                    }

                }
            });

        }

        public void bind(item item) {
            temporal = item.getPrice();
            tvTitle.setText(item.getTitle());
            tvDesc.setText(item.getDescription());
            tvPrice.setText(item.getPrice() + " USD");
            tvDesc2.setText(item.getDes2());
            ParseFile image = item.getImage();
            if(image !=null){
                Glide.with(context).load(item.getImage().getUrl()).into(ivImage);
            }

        }
    }
}
