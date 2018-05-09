package com.example.kukuliner.kuliner.adapter;


import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.Model.userTab1;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.user.login;
import com.example.kukuliner.kuliner.user.userPesanMenu;
import com.example.kukuliner.kuliner.user_drawer;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class userAdapter1 extends RecyclerView.Adapter<userAdapter1.myHolder>{
    View mView;
    static String username,NamaToko,email,tipe;
    private Context context;
    List<userTab1> data = new ArrayList<>();

    private StorageReference mStorageRef;

    public userAdapter1(Context context, List<userTab1>data) {
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
    public void onBindViewHolder(final myHolder holder, int position) {

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
            user_drawer drawer = new user_drawer();
            email = drawer.getUsername();
            tipe  = drawer.getTipe();
            Log.d("userAdapter1","data = "+email+tipe);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),userPesanMenu.class);
                intent.putExtra("namaToko",data.get(getAdapterPosition()).getNamaToko());
                intent.putExtra("username",username);
                intent.putExtra("email",email);
                intent.putExtra("tipe",tipe);
                Log.d("test lagi","data = "+NamaToko+username);
                view.getContext().startActivity(intent);

                }
            });
        }
    }
}
