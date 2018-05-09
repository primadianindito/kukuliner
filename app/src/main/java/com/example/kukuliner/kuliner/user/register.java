package com.example.kukuliner.kuliner.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.Model.User;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.penjual.register2;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class register extends AppCompatActivity {
    Button daftar;
    EditText nama;
    EditText username;
    EditText password;
    EditText email;
    EditText alamat;
    EditText noidenditas;
    String tanggalLahir;
    EditText noTelfon;
    EditText pertanyaan;
    EditText jawaban;
    String gender;
    TextView upload;
    String nameId="anonymous";
    Boolean checker = false;
    ProgressDialog progressDialog;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    public final static int GALLERY_INTENT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final DatePicker datePicker = findViewById(R.id.tanggal_register);
        daftar      = findViewById(R.id.signUp_button);
        nama        = findViewById(R.id.nama_register);
        username    = findViewById(R.id.username_register);
        password    = findViewById(R.id.register_password);
        email       = findViewById(R.id.email_register);
        alamat      = findViewById(R.id.alamat);
        noidenditas = findViewById(R.id.noidentitas_register);
        tanggalLahir= datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear();
        noTelfon    = findViewById(R.id.notelepon_register);
        pertanyaan  = findViewById(R.id.pertanyaan_register);
        jawaban     = findViewById(R.id.jawaban_register);
        upload      = findViewById(R.id.upluadKartuId);

        Log.d("cek datepicker",tanggalLahir);
        mStorage = FirebaseStorage.getInstance().getReference().child("Profile").child("imgId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        final String key =  mDatabase.push().getKey();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra("nama","test");
                startActivityForResult(intent, GALLERY_INTENT);
                progressDialog = new ProgressDialog(register.this);
            }
        });
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Daftar,Nama,Username,Password,Email,Alamat,Noidentitas,TanggalLahir,NoTelfon,Pertanyaan,Jawaban;
                Daftar = daftar.getText().toString();
                Nama = nama.getText().toString();
                Username = username.getText().toString();
                Password = password.getText().toString();
                Email = email.getText().toString();
                Alamat = alamat.getText().toString();
                Noidentitas = noidenditas.getText().toString();
                TanggalLahir = tanggalLahir;
                NoTelfon = noTelfon.getText().toString();
                Pertanyaan = pertanyaan.getText().toString();
                Jawaban = jawaban.getText().toString();

                if(Daftar.equals("") || Nama.equals("") || Username.equals("") || Password.equals("") || Email.equals("") || Alamat.equals("") || Noidentitas.equals("") || TanggalLahir.equals("") || NoTelfon .equals("")|| Pertanyaan.equals("")|| Jawaban.equals("")){
                    Toast.makeText(getApplicationContext(), "Data Tidak Boleh Kosong", Toast.LENGTH_LONG).show();

                }else{
                    final User user = new User(Nama, Email,Username,Password,Noidentitas,Alamat,gender,NoTelfon,TanggalLahir,Pertanyaan,Jawaban,"user");


                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                if(ds.child("email").getValue().equals(Email)){
                                    Log.d("cek","EMAIL dah ada");
                                    Toast.makeText(register.this,"Email Sudah Terdaftar",Toast.LENGTH_SHORT).show();
                                    checker=true;
                                    Log.d("cek","checler = "+checker);
                                }
                            }

                            if(checker==false){
                                mDatabase.push().setValue(user);
                                Intent intent = new Intent(register.this, login.class);
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                gender = "male";
                break;
            case R.id.radio_female:
                if (checked)
                gender = "female";
                break;
        }
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
                    Toast.makeText(register.this,"UPLOAD DONE",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            });
        }
    }
}