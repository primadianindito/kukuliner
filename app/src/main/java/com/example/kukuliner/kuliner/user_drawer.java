package com.example.kukuliner.kuliner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kukuliner.kuliner.adapter.viewPagerAdaper;
import com.example.kukuliner.kuliner.penjual.contentFragmentMenu;
import com.example.kukuliner.kuliner.penjual.fragmentMenu;
import com.example.kukuliner.kuliner.penjual.fragmentProfilPenjual;
import com.example.kukuliner.kuliner.penjual.penjualEditProfil;
import com.example.kukuliner.kuliner.user.cekPesanan;
import com.example.kukuliner.kuliner.user.cekPesananActivity;
import com.example.kukuliner.kuliner.user.contentFragment;
import com.example.kukuliner.kuliner.user.dataFragment;
import com.example.kukuliner.kuliner.user.dataFragment2;
import com.example.kukuliner.kuliner.user.feedback;
import com.example.kukuliner.kuliner.user.login;
import com.example.kukuliner.kuliner.user.profil_fragment;
import com.example.kukuliner.kuliner.user.profil_fragment_static;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.facebook.FacebookSdk.getApplicationContext;

public class user_drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private viewPagerAdaper adapter;
    ImageView userPicture,headerPicture;
    static String username,email,tipe;
    StorageReference mStorage;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout colapsingToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_user_drawer);
        AppEventsLogger.activateApp(user_drawer.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Intent intent= getIntent();
        Bundle b = intent.getExtras();
        userPicture = findViewById(R.id.user_image);
        username = b.getString("user_username");
        email = b.getString("user_email");
        tipe = b.getString("tipe");
        appBarLayout = findViewById(R.id.appbar);
        Log.d("cek intent extra",username+" ,"+email);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView userEmail = (TextView) header.findViewById(R.id.user_email);
        TextView userName = (TextView) header.findViewById(R.id.user_name);
        userPicture = header.findViewById(R.id.user_image);
        userEmail.setText(email);
        userName.setText(username);
        Button buttonEdit = header.findViewById(R.id.edit_profil);
        headerPicture = findViewById(R.id.htab_header);
        mStorage = FirebaseStorage.getInstance().getReference().child("Profile").child(username);
        GlideApp.with(user_drawer.this).load(mStorage).apply(RequestOptions.circleCropTransform()).into(userPicture);
        GlideApp.with(user_drawer.this).load(mStorage).into(headerPicture);
        Log.d("cek glide","path="+mStorage.toString());

        if(tipe.equals("penjual")){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_penjual_drawer_drawer);
            setFragment(new dataFragment2());//init
        }else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_user_drawer_drawer);
            setFragment(new dataFragment());//init
        }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tipe.equals("user")){
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    profil_fragment profil = new profil_fragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, profil).commit();
                    drawer.closeDrawer(GravityCompat.START);
                }else{
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    penjualEditProfil profil = new penjualEditProfil();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, profil).commit();
                    drawer.closeDrawer(GravityCompat.START);
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.user_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if(id == R.id.user_home ||id == R.id.penjual_home){
            Intent intent = new Intent(user_drawer.this, user_drawer.class);
            intent.putExtra("user_email",email);
            intent.putExtra("user_username",username);
            intent.putExtra("tipe",tipe);
            startActivity(intent);
            finish();
        }
        if (id == R.id.user_profil) {
            fragment = new profil_fragment_static();
        } else if (id == R.id.user_cek_pesanan) {
            Intent intent = new Intent(user_drawer.this, cekPesananActivity.class);
            intent.putExtra("user_email",email);
            intent.putExtra("user_username",username);
            intent.putExtra("tipe",tipe);
            startActivity(intent);
            finish();
        } else if (id == R.id.user_orded) {
            fragment = new feedback();
        } else if (id == R.id.user_logout || id==R.id.penjual_logout) {
            Intent intent = new Intent(user_drawer.this, login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if(id == R.id.penjual_menu){
            fragment = new contentFragmentMenu();
        } else if(id ==R.id.penjual_profil){
            fragment = new fragmentProfilPenjual();
        } else if(id == R.id.penjual_feedback){
            fragment = new feedback();
        }

        if(fragment!=null){
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFragment(Fragment fragment){
        if(fragment!=null){
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    public String getUsername(){
        return this.username;
    }
    public String getEmail(){
        return this.email;
    }
    public String getTipe(){
        return this.tipe;
    }
    public interface FragmentChangeListener
    {
        public void replaceFragment(Fragment fragment);
    }
}
