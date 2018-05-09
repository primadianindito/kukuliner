package com.example.kukuliner.kuliner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.Model.penjualTab1;
import com.example.kukuliner.kuliner.Model.penjualTab2;
import com.example.kukuliner.kuliner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class penjualAdapter2 extends RecyclerView.Adapter<penjualAdapter2.myHolder> {
    private Context context;
    List<penjualTab2> data;
    private DatabaseReference mDatabase;

    public penjualAdapter2(Context context, List<penjualTab2>data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context).inflate(R.layout.penjual_adapter_on_proses,parent,false);
        myHolder holder = new myHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, final int position) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("pesanan");
        StorageReference test = data.get(position).getImage();
        holder.namaToko.setText(data.get(position).getNamaToko());
        holder.pesanan.setText(data.get(position).getPesanan());
        holder.harga.setText(data.get(position).getHarga());
        holder.progress.setText(data.get(position).getProgress());
        holder.alamat.setText(data.get(position).getAlamat());
        GlideApp.with(context).load(test).into(holder.gambar);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myHolder extends RecyclerView.ViewHolder{
        ImageView gambar;
        TextView namaToko,pesanan,harga,progress,alamat;
        Button decline;
        public myHolder(View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.penjual_linear_layout_onproses_picture);
            namaToko   = itemView.findViewById(R.id.penjual_linear_layout_onproses_toko);
            pesanan = itemView.findViewById(R.id.penjual_linear_layout_onproses_pesanan);
            harga = itemView.findViewById(R.id.penjual_linear_layout_onproses_total_bayar);
            progress = itemView.findViewById(R.id.penjual_onproses_status);
            alamat = itemView.findViewById(R.id.penjual_linear_layout_onproses_alamat);
            decline = itemView.findViewById(R.id.button_decline2);

            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String status = ds.child("status").getValue().toString();
                                String key = ds.getKey();
                                if(status.equals("on progress")){
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