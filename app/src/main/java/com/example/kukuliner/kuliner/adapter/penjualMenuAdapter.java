package com.example.kukuliner.kuliner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.Model.penjualMenu;
import com.example.kukuliner.kuliner.R;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class penjualMenuAdapter extends RecyclerView.Adapter<penjualMenuAdapter.myHolder> {
    private Context context;
    List<penjualMenu> data;


    public penjualMenuAdapter(Context context, List<penjualMenu>data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context).inflate(R.layout.penjual_menu_adapter,parent,false);
        myHolder holder = new myHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {
        StorageReference test = data.get(position).getmDatabase();
        //holder.gambar.setImageResource(data.get(position).getmDatabase());
        holder.namaMakanan.setText(data.get(position).getNamaMakanan());
        holder.harga.setText(data.get(position).getHargaMakanan());
        GlideApp.with(context).load(test).into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class myHolder extends RecyclerView.ViewHolder{
        ImageView gambar;
        TextView namaMakanan,harga;
        public myHolder(View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.penjual_menu_adapter_image);
            namaMakanan   = itemView.findViewById(R.id.penjual_menu_adapter_nama_makanan);
            harga = itemView.findViewById(R.id.penjual_menu_adapter_harga);
        }
    }
}