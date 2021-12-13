package com.example.bubaleapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bubaleapp.fragments.ItemDetail;
import com.parse.ParseFile;

import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.ViewHolder> {

    private static final String TAG = "itemAdapter";
    private Context context;
    private List<item> items;


    public itemAdapter(Context context, List<item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        RelativeLayout container;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDesc = view.findViewById(R.id.tvDesc);
            tvPrice = view.findViewById(R.id.tvPrice);
            ivImage = view.findViewById(R.id.ivPic);
            container = view.findViewById(R.id.itemLay);

        }

        public void bind(item item) {
            tvTitle.setText(item.getTitle());
            tvDesc.setText(item.getDescription());
            tvPrice.setText(item.getPrice() + " USD");
            ParseFile image = item.getImage();
            if(image !=null){
                Glide.with(context).load(item.getImage().getUrl()).into(ivImage);
            }
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new ItemDetail();
                    Bundle bundle = new Bundle();
                    String title = tvTitle.getText().toString();
                    bundle.putString("Title", title);
                    fragment.setArguments(bundle);

                   ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.maincont, fragment).commit();
                    Log.d(TAG, "item checked!!");

                }
            });
        }
    }
}
