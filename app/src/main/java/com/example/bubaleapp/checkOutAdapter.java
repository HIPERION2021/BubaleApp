package com.example.bubaleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bubaleapp.fragments.checkoutFragment;
import com.example.bubaleapp.fragments.shopFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.util.List;

public class checkOutAdapter extends RecyclerView.Adapter<checkOutAdapter.ViewHolder>{

    private static final String TAG = "checkoutAdapter";
    private Context context;
    private List<item> items;
    private String RestPrice;
    public Integer minus;


    public checkOutAdapter(Context context, List<item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public checkOutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkout_item, parent, false);
        return new checkOutAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull checkOutAdapter.ViewHolder holder, int position) {
        item item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvPrice;
        private ImageView ivImage;
        private Button btnRemove;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivImage = itemView.findViewById(R.id.ivPic);
            btnRemove = itemView.findViewById(R.id.btnremove);



            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    queryremove();

                }
            });


        }

        private void queryremove() {
            String Title = tvTitle.getText().toString();
            RestPrice = tvPrice.getText().toString();
            RestPrice = RestPrice.substring(0, RestPrice.length()-4);
            minus = Integer.parseInt(RestPrice);
            ParseQuery<item> query = ParseQuery.getQuery(item.class).whereEqualTo("title", Title);
            query.setLimit(1);
            query.findInBackground(new FindCallback<item>() {
                @Override
                public void done(List<item> objects, ParseException e) {

                    for (int i=0; i<= MainActivity.cart.size() -1; i++){
                        String checking = MainActivity.cart.get(i).getTitle().toString();
                        if(checking.trim().equals(Title.trim())){
                            MainActivity.cart.remove(i);
                            MainActivity.total = MainActivity.total - minus;
                            checkoutFragment.checkTot.setText(MainActivity.total + " USD");

                        }

                    }
                    MainActivity.checkAdapter.notifyDataSetChanged();

                    Fragment fragment;
                    if(MainActivity.total == 0){
                        fragment = new shopFragment();
                    }else {
                        fragment = new checkoutFragment();
                    }
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.maincont, fragment).commit();

                }
            });

        }

        public void bind(item item) {
            tvTitle.setText(item.getTitle());
            tvDesc.setText(item.getDescription());
            tvPrice.setText(item.getPrice() + " USD");
            ParseFile image = item.getImage();
            if(image !=null){
                Glide.with(context).load(item.getImage().getUrl()).into(ivImage);
            }
        }
    }
}
