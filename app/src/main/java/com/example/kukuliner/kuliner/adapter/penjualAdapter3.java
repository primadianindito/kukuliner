package com.example.kukuliner.kuliner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.Model.penjualTab2;
import com.example.kukuliner.kuliner.Model.penjualTab3;
import com.example.kukuliner.kuliner.R;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class penjualAdapter3 extends RecyclerView.Adapter<penjualAdapter3.myHolder> {
    private Context context;
    List<penjualTab3> data;


    public penjualAdapter3(Context context, List<penjualTab3>data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context).inflate(R.layout.penjual_adapter_completed,parent,false);
        myHolder holder = new myHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {
        StorageReference test = data.get(position).getImage();
        holder.namaToko.setText(data.get(position).getNamaToko());
        holder.pesanan.setText(data.get(position).getPesanan());
        holder.harga.setText(data.get(position).getHarga());
        holder.progress.setText(data.get(position).getProgress());
        GlideApp.with(context).load(test).into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class myHolder extends RecyclerView.ViewHolder{
        ImageView gambar;
        TextView namaToko,pesanan,harga,progress;
        public myHolder(View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.penjual_linear_layout_completed_picture);
            namaToko   = itemView.findViewById(R.id.penjual_linear_layout_completed_toko);
            pesanan = itemView.findViewById(R.id.penjual_linear_layout_completed_pesanan);
            harga = itemView.findViewById(R.id.penjual_linear_layout_completed_total_bayar);
            progress = itemView.findViewById(R.id.penjual_completed_status);
        }
    }
}