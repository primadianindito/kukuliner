package com.example.kukuliner.kuliner.penjual;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.Model.tambahMakanan;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.user_drawer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class fragmentTambahMenu extends Fragment {
    Button upload,save;
    EditText harga,namaMakanan;
    ImageView previewImage;
    String username,Harga="", NamaMakanan="";
    private ProgressDialog mProgress;
    public final static int GALLERY_INTENT = 2;
    private StorageReference mStorageRef;
    private DatabaseReference mDataf;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.penjual_tambah_pesanan,container,false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDataf = FirebaseDatabase.getInstance().getReference().child("menu");
        final user_drawer activity = (user_drawer) getActivity();
        username = activity.getUsername();
        upload = rootView.findViewById(R.id.button_pesanan_upload);
        save = rootView.findViewById(R.id.button_pesanan_save);
        harga = rootView.findViewById(R.id.penjual_tambah_pesanan_harga);
        namaMakanan = rootView.findViewById(R.id.penjual_tambah_pesanan_nama_makanan);
        previewImage = rootView.findViewById(R.id.penjual_tambah_pesanan_image);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NamaMakanan = namaMakanan.getText().toString();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                if(NamaMakanan.equals("")||NamaMakanan==null){
                    Toast.makeText(activity,"Masukan Nama Makanan Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                }else{
                    startActivityForResult(intent, GALLERY_INTENT);
                    mProgress = new ProgressDialog(getActivity());
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Harga = harga.getText().toString();
                NamaMakanan = namaMakanan.getText().toString();
                if(Harga.equals("") || NamaMakanan.equals("")){
                    Toast.makeText(activity, "Isi Form Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }else{
                    final tambahMakanan TambahMakanan = new tambahMakanan(Harga,NamaMakanan,username);
                    mDataf.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mDataf.push().setValue(TambahMakanan);
                            Toast.makeText(getActivity(), "register sukses", Toast.LENGTH_LONG).show();
                            contentFragmentMenu nextFrag= new contentFragmentMenu();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_main, nextFrag,"tambah pesanan")
                                    .addToBackStack(null)
                                    .commit();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        

        return rootView;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode  == GALLERY_INTENT){
            mProgress.setMessage("UPLOADING.....");
            mProgress.show();
            Uri uri = data.getData();
            mStorageRef = mStorageRef.child("Profile").child("menu").child(username).child(NamaMakanan);
            StorageReference filePath = mStorageRef;
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(),"UPLOAD DONE",Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                    Uri downloadProfilPicture = taskSnapshot.getDownloadUrl();
                    GlideApp.with(getActivity()).load(downloadProfilPicture).apply(RequestOptions.circleCropTransform()).into(previewImage);
                }
            });
        }
    }
}