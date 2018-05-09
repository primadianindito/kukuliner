package com.example.kukuliner.kuliner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.Model.adminTab2;
import com.example.kukuliner.kuliner.Model.adminTab3;
import com.example.kukuliner.kuliner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class adminAdapter3 extends RecyclerView.Adapter<adminAdapter3.myHolder> {
    private Context context;
    List<adminTab3> data;

    public adminAdapter3(Context context, List<adminTab3>data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.admin_adapter_tab3,parent,false);
        myHolder holder = new myHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, final int position) {

        StorageReference test = data.get(position).getImg();
        holder.alamat.setText(data.get(position).getAlamat());
        holder.nama.setText(data.get(position).getName());
        holder.email.setText(data.get(position).getEmail());
        holder.noHP.setText(data.get(position).getNoHP());
        GlideApp.with(context).load(test).into(holder.gambar);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myHolder extends RecyclerView.ViewHolder{
        DatabaseReference mDatabase;
        ImageView gambar;
        TextView nama,email,noHP,alamat;
        Button banned;

        public myHolder(View itemView) {
            super(itemView);
            mDatabase = FirebaseDatabase.getInstance().getReference().child("penjual");
            gambar = itemView.findViewById(R.id.admin_tab3_gambar);
            nama = itemView.findViewById(R.id.admin_tab3_name);
            email = itemView.findViewById(R.id.admin_tab3_email);
            noHP = itemView.findViewById(R.id.admin_tab3_noHP);
            alamat = itemView.findViewById(R.id.admin_tab3_alamat);
            banned = itemView.findViewById(R.id.admin_tab3_button);
            banned.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String key = ds.getKey();
                                String email = ds.child("email").getValue().toString();
                                if(email.equals(data.get(getAdapterPosition()).getEmail())){
                                    if(getAdapterPosition()==-1){
                                        return;
                                    }else{
                                        data.remove(getAdapterPosition());
                                        notifyItemRemoved(getAdapterPosition());
                                        mDatabase.child(key).removeValue();
                                        Log.d("test", key);
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