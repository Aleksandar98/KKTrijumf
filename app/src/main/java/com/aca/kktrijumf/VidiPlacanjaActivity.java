package com.aca.kktrijumf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.aca.kktrijumf.Adapters.PlacanjaAdapter;
import com.aca.kktrijumf.Adapters.PlayerRecyAdapter;
import com.aca.kktrijumf.Models.Placanje;
import com.aca.kktrijumf.Models.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VidiPlacanjaActivity extends AppCompatActivity {

    Player p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidi_placanja);

        p = getIntent().getParcelableExtra("izabrani");

        RecyclerView placanjaRecy = findViewById(R.id.placanjaRecy);
        placanjaRecy.setLayoutManager(new LinearLayoutManager(this));
        List<Placanje> list =  p.getPayments();


        PlacanjaAdapter adapter = new PlacanjaAdapter(list);
        placanjaRecy.setAdapter(adapter);


    }
}