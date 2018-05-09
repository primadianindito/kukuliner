package com.example.kukuliner.kuliner.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kukuliner.kuliner.Model.penjualMenu;
import com.example.kukuliner.kuliner.Model.pesanan;
import com.example.kukuliner.kuliner.Model.userPesan;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.adapter.penjualMenuAdapter;
import com.example.kukuliner.kuliner.adapter.userPesanAdapter;
import com.example.kukuliner.kuliner.penjual.fragmentTambahMenu;
import com.example.kukuliner.kuliner.user_drawer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class userPesanMenu extends AppCompatActivity {
    Context context;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<userPesan> data1 = new ArrayList<>();
    private DatabaseReference mDatabase,mDatabase2;
    private StorageReference mStorageRef;
    userPesanAdapter adapter1;
    String username,hargaMakanan,namaMakanan,namaToko,tokoName,tipe,email;
    StorageReference img;
    Button save;
    EditText alamat;
    int jml = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_pesan);
        final Intent intent= getIntent();
        user_drawer activity = new user_drawer();
        Bundle b = intent.getExtras();
        username = b.getString("username");
        email = b.getString("email");
        tipe = b.getString("tipe");
        namaToko = b.getString("namaToko");
        Log.d("cek nama toko","data = "+username+email+tipe);
        save = findViewById(R.id.user_pesan_button);
        recyclerView  = findViewById(R.id.recycleview_menu_user);
        layoutManager = new LinearLayoutManager(userPesanMenu.this);
        recyclerView.setHasFixedSize(true);
        alamat = findViewById(R.id.pesan_alamat);
        recyclerView.setLayoutManager(layoutManager);
        adapter1 = new userPesanAdapter(this ,data1);
        recyclerView.setAdapter(adapter1);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("menu");
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("pesanan");
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profile").child("menu").child(namaToko);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    hargaMakanan = ds.child("harga").getValue().toString();
                    namaMakanan = ds.child("namaMakanan").getValue().toString();
                    String namaToko2 = ds.child("namaToko").getValue().toString();
                    img = mStorageRef.child(namaMakanan);
                    if(namaToko2.equals(namaToko)){
                        data1.add(new userPesan(img,namaMakanan,hargaMakanan,0,namaToko));
                   }
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Alamat;
                for(int i = 0;i<data1.size();i++){
                    int jml = data1.get(i).getJml();
                    StorageReference gambar = data1.get(i).getImage();
                    Alamat = alamat.getText().toString();
                    String namaMakanan = data1.get(i).getNamaMakanan();
                    String hargaMakanan = data1.get(i).getHargaMakanan();
                    String namaToko = data1.get(i).getNamaToko();
                    if(jml>0){
                        pesanan userpesanan = new pesanan(namaMakanan,hargaMakanan,jml,namaToko,username,"new",Alamat);
                        mDatabase2.push().setValue(userpesanan);
                    }
                }
                Intent intent = new Intent(userPesanMenu.this, user_drawer.class);
                intent.putExtra("user_username",username);
                intent.putExtra("tipe",tipe);
                intent.putExtra("user_email",email);
                startActivity(intent);
            }
        });

    }
}
