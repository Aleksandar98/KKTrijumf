package com.aca.kktrijumf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.aca.kktrijumf.Adapters.PlayerRecyAdapter;
import com.aca.kktrijumf.Models.Coach;
import com.aca.kktrijumf.Models.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListPlayerActivity extends AppCompatActivity implements PlayerRecyAdapter.ItemClickListener {

    FirebaseFirestore db;
    List<Player> playersList = new ArrayList<>();
    PlayerRecyAdapter adapter;
    String coachFullName;
    String izabranoGodiste;
    Coach choosenCoach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_player);


         choosenCoach = getIntent().getParcelableExtra("izabrani");
        izabranoGodiste = getIntent().getStringExtra("izabranoGodiste");

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Player> filtrirana = new ArrayList<>();
                for(Player p : playersList){
                    if(p.getName().toLowerCase().contains(s) || p.getSurname().toLowerCase().contains(s)){
                        filtrirana.add(p);
                    }
                }
                adapter.setList(filtrirana);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        Toolbar toolbarListActivity = findViewById(R.id.toolbarListActivity);
        setSupportActionBar(toolbarListActivity);
        toolbarListActivity.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.ic_filter));

        final RecyclerView recyclerView = findViewById(R.id.playerRecyView);
        FloatingActionButton fab = findViewById(R.id.addPlayer);
        db = FirebaseFirestore.getInstance();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(view.getContext(), AddUser.class);
                intent.putExtra("izabraniTrener", coachFullName);
                intent.putExtra("izabranoGodiste", izabranoGodiste);
                startActivity(intent);

            }
        });

        adapter = new PlayerRecyAdapter(playersList);
        adapter.setItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if (choosenCoach != null) {
            coachFullName = choosenCoach.getName() + " " + choosenCoach.getSurName();

            db.collection(izabranoGodiste)
                    .whereEqualTo("coach", coachFullName)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Player p = document.toObject(Player.class);
                                    playersList.add(p);

                                }

                                adapter.setList(playersList);
                                adapter.notifyDataSetChanged();

                            } else {
                                Log.d("MojTag", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_delete: {
                new MaterialAlertDialogBuilder(ListPlayerActivity.this)
                        .setTitle("Da li ste sigurni")
                        .setMessage("Zelite da obrisete korisnika")
                        .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.collection("Coaches").document(choosenCoach.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ListPlayerActivity.this, "Korisnik obrisan", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
            break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        intent.putExtra("izabrani", playersList.get(position));
        startActivity(intent);
    }
}