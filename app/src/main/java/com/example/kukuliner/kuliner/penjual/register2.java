package com.example.kukuliner.kuliner.penjual;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kukuliner.kuliner.Model.Penjual;
import com.example.kukuliner.kuliner.Model.User;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.user.login;
import com.example.kukuliner.kuliner.user.register;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class register2 extends AppCompatActivity{
    Button daftar;
    EditText nama,password,email,alamat,noTelfon;
    String gender;
    TextView upload;
    String nameId = "anonymous";
    Boolean checker = false;
    ProgressDialog progressDialog;
    private FirebaseDatabase mFirebaseInstance;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    public final static int GALLERY_INTENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_penjual);
        daftar      = findViewById(R.id.signUp_button_toko);
        nama        = findViewById(R.id.nama_toko_register);
        password    = findViewById(R.id.register_toko_password);
        email       = findViewById(R.id.email_toko_register);
        alamat      = findViewById(R.id.alamat_toko);
        noTelfon    = findViewById(R.id.notelepon_toko_register);
        upload      = findViewById(R.id.userUploadId);
        mStorage = FirebaseStorage.getInstance().getReference().child("Profile").child("imgId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("penjual");
        final String key =  mDatabase.push().getKey();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra("nama","test");
                startActivityForResult(intent, GALLERY_INTENT);
                progressDialog = new ProgressDialog(register2.this);
            }
        });


        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Daftar,Nama,Password,Email,Alamat,NoTelfon;
                Daftar = daftar.getText().toString();
                Nama = nama.getText().toString();
                Password = password.getText().toString();
                Email = email.getText().toString();
                Alamat = alamat.getText().toString();
                NoTelfon = noTelfon.getText().toString();

                if(Daftar.equals("") || Nama.equals("") || Password.equals("") || Email.equals("") || Alamat.equals("") || NoTelfon .equals("")){
                    Toast.makeText(getApplicationContext(), "Data Tidak Boleh Kosong", Toast.LENGTH_LONG).show();

                }else{
                    final Penjual penjual = new Penjual(Nama, Password,Email,Alamat,NoTelfon,"penjual","no",0,0,0,0);


                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                if(ds.child("email").getValue().equals(Email)){
                                    Toast.makeText(register2.this,"Email Sudah Terdaftar",Toast.LENGTH_SHORT).show();
                                    Log.d("cek","EMAIL dah ada");
                                    checker=true;
                                    Log.d("cek","checler = "+checker);
                                }
                            }

                            if(checker==false){
                                mDatabase.push().setValue(penjual);
                                Intent intent = new Intent(register2.this, login.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "register sukses", Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Email Sudah Terdaftar", Toast.LENGTH_LONG).show();
                                checker = false;
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode  == GALLERY_INTENT){
            nameId      = nama.getText().toString();
            Log.d("cek NameId",nameId);
            progressDialog.setMessage("UPLOADING.....");
            progressDialog.show();
            Uri uri = data.getData();
            StorageReference filePath = mStorage.child(nameId);
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(register2.this,"UPLOAD DONE",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            });
        }
    }
}
