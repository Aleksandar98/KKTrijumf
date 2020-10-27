package com.aca.kktrijumf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.aca.kktrijumf.Models.Placanje;
import com.aca.kktrijumf.Models.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class DodajPlacanjeActivity extends AppCompatActivity {


    String datum;
    FirebaseFirestore db;
    Player p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_placanje);

        MaterialButton dodajPlacanjeBtn = findViewById(R.id.dodajPlacanjeBtn);
        final TextInputLayout napomenaInput = findViewById(R.id.napomenaInput);
        CalendarView calendarView = findViewById(R.id.calendar);
        db = FirebaseFirestore.getInstance();
        p = getIntent().getParcelableExtra("izabrani");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String prosireniMesec;
                String prosireniDan;

                if(month<10){
                    prosireniMesec = "0" + (month+1);
                }else{
                    prosireniMesec = String.valueOf(month+1);
                }

                if(day<10){
                    prosireniDan = "0" + (day);
                }else{
                    prosireniDan = String.valueOf(day);
                }

                datum = prosireniDan + "/" + prosireniMesec + "/" + year;
            }
        });

        dodajPlacanjeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Placanje> oldPaymanest;

                if (p.getPayments() != null) {
                    oldPaymanest = p.getPayments();
                } else {

                    oldPaymanest = new ArrayList<>();

                }
                if(datum == null){
                    Toast.makeText(DodajPlacanjeActivity.this, "Izaberite datum", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Map<String,Placanje>> listaMapa = new ArrayList<>();
                oldPaymanest.add(new Placanje(datum, napomenaInput.getEditText().getText().toString()));


                ObjectMapper oMapper = new ObjectMapper();

                for(Placanje p:oldPaymanest)
                    listaMapa.add(oMapper.convertValue(p,Map.class));

                db.collection(p.getGroup()).document(p.getId()).update("payments", listaMapa).addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(DodajPlacanjeActivity.this, "Dodato placanje", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });


    }

    }
