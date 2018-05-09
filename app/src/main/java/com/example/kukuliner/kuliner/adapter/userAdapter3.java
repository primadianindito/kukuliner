package com.example.kukuliner.kuliner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.Model.userTab1;
import com.example.kukuliner.kuliner.Model.userTab2;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.user.userPesanMenu;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class userAdapter3 extends RecyclerView.Adapter<userAdapter3.myHolder> implements View.OnClickListener{
    View mView;
    static String username,NamaToko;
    private Context context;
    List<userTab2> data = new ArrayList<>();

    private StorageReference mStorageRef;

    public userAdapter3(Context context, List<userTab2>data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context).inflate(R.layout.user_adapter_grid_layout,parent,false);

        myHolder holder = new myHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myHolder holder, final int position) {

        StorageReference test = data.get(position).getImage();
        holder.namaToko.setText(data.get(position).getNamaToko());
        holder.JamBuka.setText(data.get(position).getJamBuka());
        holder.rating.setRating(data.get(position).getRatting());
        GlideApp.with(context).load(test).into(holder.gambar);
        NamaToko = data.get(position).getNamaToko();
        username = data.get(position).getUsername();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {
    }

    public class myHolder extends RecyclerView.ViewHolder{
        ImageView gambar;
        TextView namaToko,JamBuka;
        RatingBar rating;
        public LinearLayout linearLayout;
        public myHolder(View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.gridview_adapter_layout_image);
            namaToko   = itemView.findViewById(R.id.gridview_adapter_layout_text);
            rating = itemView.findViewById(R.id.rating_newest_store);
            JamBuka = itemView.findViewById(R.id.jam_buka_newest_store);
            linearLayout = itemView.findViewById(R.id.pesan_linear_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),userPesanMenu.class);
                    intent.putExtra("namaToko",data.get(getAdapterPosition()).getNamaToko());
                    intent.putExtra("username",username);
                    Log.d("test lagi","data = "+NamaToko+username);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}