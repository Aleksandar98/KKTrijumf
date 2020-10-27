package com.aca.kktrijumf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aca.kktrijumf.Models.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddUser extends AppCompatActivity {

    FirebaseFirestore db;

    TextInputLayout imeTextView;
    TextInputLayout prezimeTextView;
    TextInputLayout brojRoditeljaTextView;
    TextInputLayout brojDetetaTextView;
    TextInputLayout adresaTextView;
    String izabranoGodiste;
    String izabraniTrener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        db = FirebaseFirestore.getInstance();

        izabraniTrener = getIntent().getStringExtra("izabraniTrener");
        izabranoGodiste = getIntent().getStringExtra("izabranoGodiste");

        imeTextView = findViewById(R.id.imeIgraca);
        prezimeTextView = findViewById(R.id.prezimeIgraca);
        brojRoditeljaTextView = findViewById(R.id.brojRoditelja);
        brojDetetaTextView = findViewById(R.id.brojDeteta);
        adresaTextView = findViewById(R.id.adresaDeteta);
        Button saveChanges = findViewById(R.id.saveChanges);


        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = db.collection(izabranoGodiste).document().getId();

                String ime = imeTextView.getEditText().getText().toString();
                String prezime = prezimeTextView.getEditText().getText().toString();
                String brojRoditelja = brojRoditeljaTextView.getEditText().getText().toString();
                String brojDeteta = brojDetetaTextView.getEditText().getText().toString();
                String adresa = adresaTextView.getEditText().getText().toString();


                Player p = new Player(id, ime, prezime, brojDeteta, brojRoditelja, izabraniTrener, izabranoGodiste, adresa);

                db.collection(izabranoGodiste).document(id).set(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddUser.this, "Uspesno dodato", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}