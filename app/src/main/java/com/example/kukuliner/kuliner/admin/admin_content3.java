package com.example.kukuliner.kuliner.admin;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kukuliner.kuliner.Model.adminTab2;
import com.example.kukuliner.kuliner.Model.adminTab3;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.adapter.adminAdapter2;
import com.example.kukuliner.kuliner.adapter.adminAdapter3;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class admin_content3 extends Fragment {
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private List<adminTab3> data;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    adminAdapter3 adapter1;
    StorageReference img;
    String nama,email,noHP,alamat;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.admin_content_3,container,false);
        recyclerView  = rootView.findViewById(R.id.admin_tab3);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter1 = new adminAdapter3(getContext(),data);
        recyclerView.setAdapter(adapter1);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profile");
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("penjual");
        data = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (getActivity() == null) {
                    return;
                }else{
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        String aktivasi = ds.child("aktivasi").getValue().toString();
                        nama = ds.child("name").getValue().toString();
                        email = ds.child("email").getValue().toString();
                        noHP = ds.child("noHP").getValue().toString();
                        alamat = ds.child("Alamat").getValue().toString();
                        img = mStorageRef.child(nama);
                        if(aktivasi.equals("yes")){
                            data.add(new adminTab3(img,nama,email,noHP,alamat));
                        }
                    }

                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}