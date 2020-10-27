package com.aca.kktrijumf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.job.JobScheduler;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ListFragment.Callback {
    private static final int MY_PERMISSION_REQ_SMS = 1324;
    DrawerLayout drawerLayout;
    ArrayList<String> listaGodista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActionBarDrawerToggle actionBarDrawerToggle;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String[] grupe = getResources().getStringArray(R.array.godista);
        listaGodista.addAll(Arrays.asList(grupe));



        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_draver_open,R.string.nav_draver_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListFragment lf = new ListFragment();
        MainFragment mf = new MainFragment();

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSION_REQ_SMS);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentFrame, mf).commit();

    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int selectedGroup = 0;
        switch (item.getItemId()){
            case R.id.nav_item_1:{
               selectedGroup = 1;
            }break;
            case R.id.nav_item_2: {
                selectedGroup = 2;
            }break;
            case R.id.nav_item_3:{  selectedGroup = 3;}break;
            case R.id.nav_item_4:{  selectedGroup = 4;}break;
            case R.id.nav_item_5:{  selectedGroup = 5;}break;
            case R.id.nav_item_6:{  selectedGroup = 6;}break;
            case R.id.nav_item_7:{  selectedGroup = 7;}break;
            case R.id.nav_item_8:{  selectedGroup = 8;}break;
            case R.id.nav_item_9:{  selectedGroup = 9;}break;
            case R.id.nav_item_10:{  selectedGroup = 10;}break;
            case R.id.nav_item_11:{  selectedGroup = 11;}break;
            case R.id.nav_item_options:{
                Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);
            }break;
            case R.id.nav_item_feed:{
                Intent intent = new Intent(getApplicationContext(),SelectPlayerSmsActivity.class);
                intent.putExtra("tip","bezPoruka");
                startActivity(intent);
            }break;
        }
        if(selectedGroup!=0) {
            ListFragment lf = new ListFragment(listaGodista.get(selectedGroup-1));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, lf).commit();

            drawerLayout.closeDrawer(Gravity.START);
        }
        return true;
    }

    @Override
    public void exchangeFragment(int position) {

    }
}