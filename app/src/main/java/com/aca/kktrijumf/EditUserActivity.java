package com.aca.kktrijumf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aca.kktrijumf.Models.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EditUserActivity extends AppCompatActivity {

    FirebaseFirestore db;
    Player p;

    TextInputLayout imeTextView;
    TextInputLayout prezimeTextView;
    TextInputLayout brojRoditeljaTextView;
    TextInputLayout brojDetetaTextView;
    TextInputLayout adresaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        imeTextView = findViewById(R.id.editIme);
        prezimeTextView = findViewById(R.id.editPrezime);
        brojRoditeljaTextView = findViewById(R.id.editRoditeljBroj);
        brojDetetaTextView = findViewById(R.id.editDeteBroj);
        adresaTextView = findViewById(R.id.editAdresa);

        Button deleteBtn = findViewById(R.id.deleteBtn);
        Button saveChanges = findViewById(R.id.saveChanges);

        db = FirebaseFirestore.getInstance();

        p = getIntent().getParcelableExtra("izabrani");


        imeTextView.getEditText().setText(p.getName(), TextView.BufferType.EDITABLE);
        prezimeTextView.getEditText().setText(p.getSurname(), TextView.BufferType.EDITABLE);
        brojRoditeljaTextView.getEditText().setText(p.getParentNumber(), TextView.BufferType.EDITABLE);
        brojDetetaTextView.getEditText().setText(p.getNumber(), TextView.BufferType.EDITABLE);
        adresaTextView.getEditText().setText(p.getAddress(), TextView.BufferType.EDITABLE);


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MaterialAlertDialogBuilder(EditUserActivity.this)
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
                                db.collection(p.getGroup()).document(p.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(EditUserActivity.this, "Korisnik obrisan", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dialogInterface.dismiss();
                            }
                        })
                        .show();



            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection(p.getGroup()).document(p.getId()).update(
                        "name", imeTextView.getEditText().getText().toString(),
                        "surname", prezimeTextView.getEditText().getText().toString(),
                        "parentNumber", brojRoditeljaTextView.getEditText().getText().toString(),
                        "number", brojDetetaTextView.getEditText().getText().toString(),
                        "address", adresaTextView.getEditText().getText().toString()

                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditUserActivity.this, "Izmene sacuvanje", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}