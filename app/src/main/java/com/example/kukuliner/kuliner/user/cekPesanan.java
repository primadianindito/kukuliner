package com.example.kukuliner.kuliner.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kukuliner.kuliner.Model.userProgress;
import com.example.kukuliner.kuliner.Model.userTab1;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.adapter.userAdapter1;
import com.example.kukuliner.kuliner.adapter.userAdapter2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class cekPesanan extends Fragment {
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private List<userProgress> data;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    String namaMakanan,hargaMakanan,jmlMakanan,namaToko,alamat;
    userAdapter2 adapter2;
    StorageReference img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_cek_pesanan, container,    false);
        recyclerView  = rootView.findViewById(R.id.recycleview_cek_pesanan_user);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter2= new userAdapter2(getContext(),data);
        recyclerView.setAdapter(adapter2);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profile");
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("pesanan");
        data = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    namaToko = ds.child("namaToko").getValue().toString();
                    alamat = ds.child("alamat").getValue().toString();
                    hargaMakanan = ds.child("hargaMakanan").getValue().toString();
                    namaMakanan = ds.child("namaMakanan").getValue().toString();
                    //jmlMakanan = ds.child()
                    //img = mStorageRef.child(username);
                    Log.d("cek contentFrament","data = "+img.toString());
                    //data.add(new userProgress(namaToko,img,jam,3,"on progress"));
                }
                adapter2.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
}
