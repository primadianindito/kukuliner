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
import com.example.kukuliner.kuliner.Model.adminTab3;
import com.example.kukuliner.kuliner.Model.feedback1;
import com.example.kukuliner.kuliner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class adminAdapter4 extends RecyclerView.Adapter<adminAdapter4.myHolder> {
    private Context context;
    List<feedback1> data;

    public adminAdapter4(Context context, List<feedback1>data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.admin_adapter_tab4,parent,false);
        myHolder holder = new myHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, final int position) {
        holder.nama.setText(data.get(position).getUsername());
        holder.feedback.setText(data.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myHolder extends RecyclerView.ViewHolder{
        DatabaseReference mDatabase;
        TextView nama, feedback;

        public myHolder(View itemView) {
            super(itemView);
            mDatabase = FirebaseDatabase.getInstance().getReference().child("feedback");
            nama = itemView.findViewById(R.id.feedback_nama);
            feedback = itemView.findViewById(R.id.feedback_text);
        }
    }
}