package com.example.kukuliner.kuliner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.Model.penjualTab1;
import com.example.kukuliner.kuliner.Model.userTab1;
import com.example.kukuliner.kuliner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class penjualAdapter1 extends RecyclerView.Adapter<penjualAdapter1.myHolder> {
    private Context context;
    List<penjualTab1> data;

    public penjualAdapter1(Context context, List<penjualTab1>data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context).inflate(R.layout.penjual_adapter_new,parent,false);
        myHolder holder = new myHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, final int position) {

        StorageReference test = data.get(position).getImage();
        holder.namaToko.setText(data.get(position).getNamaToko());
        holder.pesanan.setText(data.get(position).getPesanan());
        holder.harga.setText(data.get(position).getHarga());
        holder.alamat.setText(data.get(position).getAlamat());
        GlideApp.with(context).load(test).into(holder.gambar);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myHolder extends RecyclerView.ViewHolder{
        DatabaseReference mDatabase;
        ImageView gambar;
        TextView namaToko,pesanan,harga,alamat;
        Button accept,decline;

        public myHolder(View itemView) {
            super(itemView);
            mDatabase = FirebaseDatabase.getInstance().getReference().child("pesanan");
            gambar = itemView.findViewById(R.id.penjual_linear_layout_new_picture);
            namaToko   = itemView.findViewById(R.id.penjual_linear_layout_new_porsi);
            pesanan = itemView.findViewById(R.id.penjual_linear_layout_new_pesanan);
            harga = itemView.findViewById(R.id.penjual_linear_layout_new_total_bayar);
            alamat = itemView.findViewById(R.id.penjual_linear_layout_new_alamat);
            accept = itemView.findViewById(R.id.button_accept);
            decline = itemView.findViewById(R.id.button_decline);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String status = ds.child("status").getValue().toString();
                                String key = ds.getKey();
                                if(status.equals("new")){
                                    if(getAdapterPosition()==-1){
                                        return;
                                    }else{
                                        data.remove(getAdapterPosition());
                                        notifyItemRemoved(getAdapterPosition());
                                        mDatabase.child(key).child("status").setValue("on progress");
                                    }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            });

            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String status = ds.child("status").getValue().toString();
                                String key = ds.getKey();
                                if(status.equals("new")){
                                    if(getAdapterPosition()==-1){
                                        return;
                                    }else{
                                        data.remove(getAdapterPosition());
                                        notifyItemRemoved(getAdapterPosition());
                                        mDatabase.child(key).child("status").setValue("decline");
                                    }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
    }
}