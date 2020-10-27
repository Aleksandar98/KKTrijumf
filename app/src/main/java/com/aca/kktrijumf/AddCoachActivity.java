package com.aca.kktrijumf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.aca.kktrijumf.Models.Coach;
import com.aca.kktrijumf.Models.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddCoachActivity extends AppCompatActivity {

    TextInputLayout imeTreneraTextView;
    TextInputLayout prezimeTreneraTextView;
    TextInputLayout brojTreneraTextView;
    FirebaseFirestore db;
    List<MaterialCheckBox> listaChekova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coach);

        GridLayout holderForCheckBoxes = findViewById(R.id.holderForCheckBoxes);
        Button addCoachBtn = findViewById(R.id.addCoachBtn);
        imeTreneraTextView = findViewById(R.id.imeTreneraTextView);
        prezimeTreneraTextView = findViewById(R.id.prezimeTreneraTextView);
        brojTreneraTextView = findViewById(R.id.brojTreneraTextView);
        String[] grupe = getResources().getStringArray(R.array.godista);
        listaChekova = new ArrayList<>();
        for(String grupa:grupe){


            MaterialCheckBox checkBox = new MaterialCheckBox(this);
            checkBox.setLayoutParams(  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            checkBox.setText(grupa);
            listaChekova.add(checkBox);

            holderForCheckBoxes.addView(checkBox);
        }


        db = FirebaseFirestore.getInstance();

        addCoachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String ime = imeTreneraTextView.getEditText().getText().toString();
                String prezime = prezimeTreneraTextView.getEditText().getText().toString();
                String brojTrenera = brojTreneraTextView.getEditText().getText().toString();

                ArrayList<String> izabranaGodista = new ArrayList<>();
                for( MaterialCheckBox checkBox: listaChekova){
                    if(checkBox.isChecked()){
                        izabranaGodista.add(checkBox.getText().toString());
                    }
                }

                String id = db.collection("Coaches").document().getId();
                Coach c = new Coach(ime, prezime, izabranaGodista,id);

                db.collection("Coaches").document(id).set(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddCoachActivity.this, "Uspesno dodato", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}