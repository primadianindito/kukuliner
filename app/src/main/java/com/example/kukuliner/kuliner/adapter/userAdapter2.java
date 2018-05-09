package com.example.kukuliner.kuliner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.Model.userProgress;
import com.example.kukuliner.kuliner.Model.userTab1;
import com.example.kukuliner.kuliner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class userAdapter2 extends RecyclerView.Adapter<userAdapter2.myHolder> {
    private Context context;
    List<userProgress> data;


    public userAdapter2(Context context, List<userProgress> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context).inflate(R.layout.user_adapter_cek_pesanan,parent,false);
        myHolder holder = new myHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {
        StorageReference test = data.get(position).getImage();
        holder.namaToko.setText(data.get(position).getToko());
        holder.alamat.setText(data.get(position).getAlamat());
        holder.hargaMakanan.setText(data.get(position).getHargaMakanan());
        holder.namaMakanan.setText(data.get(position).getNamaMakanan());
        holder.jmlMakanan.setText(data.get(position).getJmlMakanan());
        holder.progress.setText(data.get(position).getStatus());
        GlideApp.with(context).load(test).into(holder.gambar);
    }

    @Override
    public int getItemCount() {
            return data.size();
    }

    public class myHolder extends RecyclerView.ViewHolder{
        ImageView gambar;
        DatabaseReference mDatabase, mDatabase2;
        TextView namaToko,hargaMakanan,namaMakanan,jmlMakanan,alamat,progress;
        RatingBar rating;
        int ratingValue;
        Button button;
        public myHolder(View itemView) {
            super(itemView);
            mDatabase = FirebaseDatabase.getInstance().getReference().child("pesanan");
            mDatabase2 = FirebaseDatabase.getInstance().getReference().child("penjual");
            gambar = itemView.findViewById(R.id.user_cek_pesanan_gambar);
            hargaMakanan = itemView.findViewById(R.id.user_cek_pesanan_harga_makanan);
            namaMakanan = itemView.findViewById(R.id.user_cek_pesanan_nama_makanan);
            jmlMakanan = itemView.findViewById(R.id.user_cek_pesanan_jml_makanan);
            alamat = itemView.findViewById(R.id.user_cek_pesanan_alamat);
            progress = itemView.findViewById(R.id.progress_makanan);
            rating = itemView.findViewById(R.id.rating_give);
            namaToko = itemView.findViewById(R.id.user_cek_pesanan_toko);
            button = itemView.findViewById(R.id.give_ratting);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()) {
                                String key = ds.getKey().toString();
                                String NamaToko = ds.child("name").getValue().toString();
                                String ratting1 = ds.child("ratting").getValue().toString();
                                String voted = ds.child("voted").getValue().toString();
                                int intRatting = Integer.parseInt(ratting1);
                                ratingValue = Integer.valueOf((int) rating.getRating());
                                int intVoted = Integer.parseInt(voted);
                                final int position = getAdapterPosition();
                                if(position != RecyclerView.NO_POSITION){
                                    if(NamaToko.equals(data.get(getAdapterPosition()).getToko())){
                                        int valuefinal = (int) Math.ceil((double)((ratingValue)+(intRatting*intVoted)) / (intVoted +1));
                                        intVoted = intVoted+1;
                                        String i = String.valueOf(valuefinal);
                                        String ii = String.valueOf(intVoted);
                                        mDatabase2.child(key).child("ratting").setValue(i);
                                        mDatabase2.child(key).child("voted").setValue(ii);
                                    }
                                }


                                }

                                }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()) {
                                String key = ds.getKey().toString();
                                final int position = getAdapterPosition();
                                if(position != RecyclerView.NO_POSITION){
                                    if(key.equals(data.get(getAdapterPosition()).getKey())){
                                        Log.d("key",key+" "+data.get(getAdapterPosition()).getKey());
                                            mDatabase.child(key).child("status").setValue("completed");
                                            data.remove(getAdapterPosition());
                                            notifyItemRemoved(getAdapterPosition());

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