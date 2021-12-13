package com.example.bubaleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class PrevOrAdapter extends RecyclerView.Adapter<PrevOrAdapter.ViewHolder> {

    private static final String TAG = "PrevOrAdapter";
    private Context context;
    private List<orders> items;

    public PrevOrAdapter(Context context, List<orders> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public PrevOrAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_prev, parent, false);
        return new PrevOrAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        orders item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvOrder;
        private TextView tvPrice;
        private ImageView ivImage;
        private TextView tvStatus;
        private String statusF;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDesc = view.findViewById(R.id.tvDesc);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvOrder = view.findViewById(R.id.tvOrder);
            ivImage = view.findViewById(R.id.ivPic);
            tvStatus = view.findViewById(R.id.tvStatus);
        }

        public void bind(orders item) {
            tvTitle.setText(item.getOrderItem());
            tvPrice.setText(item.getPrice() + " USD");
            tvOrder.setText("OREDER " + item.getOrder());
            int state = (int) item.getStatus();
            if(state == 1){ statusF = "PAID";}
            else if(state == 2){statusF = "SHIPPED";}
            else if(state == 3){statusF = "COMPLETED";}
            tvStatus.setText(statusF);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("item");
            query.whereEqualTo("title", item.getOrderItem().toString());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                       ParseFile image = objects.get(0).getParseFile("image");
                       if(image != null){
                           Glide.with(context).load(objects.get(0).getParseFile("image").getUrl()).into(ivImage);
                       }
                }
            });

            }

        }
    }
