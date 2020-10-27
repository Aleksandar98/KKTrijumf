package com.aca.kktrijumf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aca.kktrijumf.Models.Player;
import com.google.android.material.button.MaterialButton;

public class DetailActivity extends AppCompatActivity {

    Player p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

         p = getIntent().getParcelableExtra("izabrani");

        TextView nameTextView = findViewById(R.id.name);
        TextView addressText = findViewById(R.id.addressText);
        nameTextView.setText(p.getName() + " " + p.getSurname());
        addressText.setText(p.getAddress());
        TextView groupTextView = findViewById(R.id.groupText);
        groupTextView.setText(p.getGroup());


        final CardView infoCard = findViewById(R.id.infoCard);
        infoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditUserActivity.class);
                intent.putExtra("izabrani",p);
                startActivity(intent);
            }
        });



        MaterialButton btnCallParent = findViewById(R.id.callParentBtn);
        MaterialButton btnPayment = findViewById(R.id.addPaymentBtn);
        MaterialButton btnViewPayment = findViewById(R.id.viewPaymentBtn);

        btnCallParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);

                intent.setData(Uri.parse("tel:" + p.getParentNumber()));
                getApplicationContext().startActivity(intent);
            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DodajPlacanjeActivity.class);
                intent.putExtra("izabrani",p);
                startActivity(intent);
            }
        });

        btnViewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),VidiPlacanjaActivity.class);
                intent.putExtra("izabrani",p);
                startActivity(intent);
            }
        });

    }
}