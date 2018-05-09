package com.example.kukuliner.kuliner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.Model.penjualTab1;
import com.example.kukuliner.kuliner.Model.userPesan;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.user.userPesanMenu;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class userPesanAdapter extends RecyclerView.Adapter<userPesanAdapter.myHolder> {
    private Context context;
    List<userPesan> data  = new ArrayList<>();

    public userPesanAdapter(Context context, List<userPesan>data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context).inflate(R.layout.user_pesan_adapter,parent,false);
        myHolder holder = new myHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myHolder holder, final int position) {
        StorageReference test = data.get(position).getImage();
        holder.namaMakanan.setText(data.get(position).getNamaMakanan());
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.get(position).setJml(data.get(position).getJml()+1);
                holder.pesanan.setText(String.valueOf(data.get(position).getJml()));
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.get(position).setJml(data.get(position).getJml()-1);
                holder.pesanan.setText(String.valueOf(data.get(position).getJml()));
            }
        });
        holder.pesanan.setText(String.valueOf(data.get(position).getJml()));
        holder.harga.setText(data.get(position).getHargaMakanan());
        GlideApp.with(context).load(test).into(holder.gambar);
    }

    @Override
    public int getItemCount() {
            return data.size();
    }

    public static class myHolder extends RecyclerView.ViewHolder{
        ImageView gambar,plus,minus;
        TextView namaMakanan,pesanan,harga;
        public LinearLayout linearLayout;
        public myHolder(View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.penjual_menu_adapter_image2);
            namaMakanan   = itemView.findViewById(R.id.user_pesan_adapter_nama_makanan);
            pesanan = itemView.findViewById(R.id.jumlah_pesanan);
            harga = itemView.findViewById(R.id.user_pesan_adapter_harga);
            plus = itemView.findViewById(R.id.pesanan_plus);
            minus = itemView.findViewById(R.id.pesanan_minus);
        }
    }
}