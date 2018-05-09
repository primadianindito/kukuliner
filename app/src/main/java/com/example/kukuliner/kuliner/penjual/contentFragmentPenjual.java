package com.example.kukuliner.kuliner.penjual;

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

import com.example.kukuliner.kuliner.Model.penjualTab1;
import com.example.kukuliner.kuliner.Model.userTab1;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.adapter.penjualAdapter1;
import com.example.kukuliner.kuliner.adapter.userAdapter1;
import com.example.kukuliner.kuliner.user_drawer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class contentFragmentPenjual extends Fragment {

    //Deklarasi datanya
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private List<penjualTab1> data;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    String namaMakanan,hargaMakanan,pembeli,username,namaToko,status,alamat;
    String porsi;
    int intHarga,intPorsi;
    penjualAdapter1 adapter1;
    StorageReference img;
    @Nullable
    @Override

    //Untuk mengganti layout yang tadinya kodingan jadi gambar
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.penjual_content, container,    false);

        //inisiasi data-datanya
        recyclerView  = rootView.findViewById(R.id.recycleview_content_penjual);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter1 = new penjualAdapter1(getContext(),data);
        recyclerView.setAdapter(adapter1);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profile");
        return rootView;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inisiasi database untuk firebasenya
        mDatabase = FirebaseDatabase.getInstance().getReference().child("pesanan");
        data = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            // 	Membaca dan mendeteksi perubahan pada seluruh konten di sebuah lokasi
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    user_drawer activity = (user_drawer) getActivity();
                    username = activity.getUsername();
                    namaToko = ds.child("namaToko").getValue().toString();
                    pembeli = ds.child("pembeli").getValue().toString();
                    namaMakanan = ds.child("namaMakanan").getValue().toString();
                    hargaMakanan = ds.child("hargaMakanan").getValue().toString();
                    status = ds.child("status").getValue().toString();
                    porsi = ds.child("jmlMakanan").getValue().toString();
                    alamat = ds.child("alamat").getValue().toString();
                    img = mStorageRef.child(pembeli);
                    intHarga = Integer.parseInt(hargaMakanan);
                    intPorsi = Integer.parseInt(porsi);
                    if(username.equals(namaToko) && status.equals("new")){
                        data.add(new penjualTab1(namaMakanan, img, pembeli,porsi+" porsi / Rp."+(intHarga * intPorsi),status,alamat));
                    }

                }
                //untuk mengambil / melihat data dari adapter
                adapter1.notifyDataSetChanged();
            }
            //Jika database erorr / data erorr maka tidak masuk ke databasenya
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
