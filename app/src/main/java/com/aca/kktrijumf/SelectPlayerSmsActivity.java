package com.aca.kktrijumf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aca.kktrijumf.Adapters.NisuPlatiliAdapter;
import com.aca.kktrijumf.Models.Coach;
import com.aca.kktrijumf.Models.Placanje;
import com.aca.kktrijumf.Models.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SelectPlayerSmsActivity extends AppCompatActivity {
    FirebaseFirestore db;
    List<Player> igraci;
    List<Coach> treneri;
    NisuPlatiliAdapter adapter;
    Map<String, List<Player>> trenerIgracMapa;
    List<Player> listaPoTrenerima;
    int globalniBrojac = 0;
    String formattedDate;
    String poruka;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player_sms);
        String tip  = getIntent().getStringExtra("tip");

        igraci = new ArrayList<Player>();
        trenerIgracMapa = new HashMap<>();
        listaPoTrenerima = new ArrayList<>();
        RelativeLayout sendHolder = findViewById(R.id.sendHolder);
        final String[] grupe = getResources().getStringArray(R.array.godista);
        RecyclerView recyViewNisuPlatili = findViewById(R.id.recyViewNisuPlatili);
        MaterialButton posaljiSmsBtn = findViewById(R.id.posaljiSmsBtn);
        recyViewNisuPlatili.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

         poruka = preferences.getString("porukaZaRoditelja", null);
         if(poruka == null){
             poruka = getResources().getString(R.string.porukaZaRoditelja);
         }

        adapter = new NisuPlatiliAdapter(igraci,trenerIgracMapa);
        if(tip != null)
        if(tip.equals("bezPoruka")){
            adapter.noSendMessage();
            sendHolder.setVisibility(View.GONE);
        }
        recyViewNisuPlatili.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        Date c = Calendar.getInstance().getTime();
        Log.d("TestVreme", "onCreate: current time " + c);
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        formattedDate = df.format(c);

        posaljiSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter.getCheckedPlayers().isEmpty()){
                    Toast.makeText(SelectPlayerSmsActivity.this, "Niste izabrali nikoga", Toast.LENGTH_SHORT).show();
                }else {
                    new MaterialAlertDialogBuilder(SelectPlayerSmsActivity.this)
                            .setTitle("Da li ste sigurni")
                            .setMessage("Da zelite da posaljete poruke")
                            .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (checkPermission(Manifest.permission.SEND_SMS)) {
                                        SmsManager smsManager = SmsManager.getDefault();
                                        for (Player p : adapter.getCheckedPlayers()){

                                            smsManager.sendTextMessage(p.getParentNumber(),null,poruka,null,null);
                                        }


                                    } else {
                                        Toast.makeText(SelectPlayerSmsActivity.this, "Niste dozvolili slanje", Toast.LENGTH_SHORT).show();
                                    }
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();


                }

            }
        });



        treneri = new ArrayList<>();
        db.collection("Coaches").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Coach c = document.toObject(Coach.class);
                            treneri.add(c);
                        }
                    }
                });

        for (int i = 0; i < grupe.length; i++) {

            db.collection(grupe[i]).get().addOnCompleteListener(
                    new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Player p = document.toObject(Player.class);

                                    boolean trebaDaSeIzbaci = false;
                                    for (Placanje placanje : p.getPayments()) {

                                        if (placanje.platioZaMesec(formattedDate)) {
                                            trebaDaSeIzbaci = true;
                                            igraci.remove(p);
                                            break;
                                        }
                                    }
                                    if(!trebaDaSeIzbaci){igraci.add(p);}
                                }

                                Log.d("mapaTest", "onComplete: USPESNO SAM POZVAN a globalni je" + globalniBrojac);
                            } else {
//
                                Log.d("MojTag", "Error getting documents.", task.getException());
                            }
                        }
                    }
            );
        }

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //start your activity here

                popuniMapu();
                adapter.setList2(listaPoTrenerima,treneri);
                adapter.notifyDataSetChanged();
            }

        }, 2000);

    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    void popuniMapu() {

        for (Coach c : treneri) {
            String fullName = c.getName() + " " + c.getSurName();
            for (Player p : igraci) {
                if (p.getCoach().equals(fullName)) {
                    listaPoTrenerima.add(p);
                }

            }
        }

        Log.d("mapaTest", "onCreate: " + listaPoTrenerima.size());
    }
}