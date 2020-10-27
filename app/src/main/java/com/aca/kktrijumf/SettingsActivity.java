package com.aca.kktrijumf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SettingsActivity extends AppCompatActivity {

    TextInputLayout porukaZaRoditelja;
    SharedPreferences preferences;
    String poruka;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button zakaziObavestenja = findViewById(R.id.zakaziObavestenja);
        Button otkaziObavestenje = findViewById(R.id.otkaziObavestenje);
        porukaZaRoditelja = findViewById(R.id.porukaZaRoditelja);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String porukaKojaPostoji = preferences.getString("porukaZaRoditelja", null);
        Log.d("porukaZaRod", "onCreate: "+porukaKojaPostoji);
        if(porukaKojaPostoji == null){

             poruka = getResources().getString(R.string.porukaZaRoditelja);
             porukaZaRoditelja.getEditText().setText(poruka);
         }else{
             porukaZaRoditelja.getEditText().setText(porukaKojaPostoji);

         }

        Button sacuvajPoruku = findViewById(R.id.sacuvajPoruku);


        sacuvajPoruku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String porukaUkucana = porukaZaRoditelja.getEditText().getText().toString();
                SharedPreferences.Editor edit = preferences.edit();
                Log.d("porukaZaRod", "onCreate:ukucana"+porukaUkucana);
                edit.putString("porukaZaRoditelja",porukaUkucana);
                edit.commit();

                Toast.makeText(SettingsActivity.this, "Poruka sacuvana", Toast.LENGTH_SHORT).show();
            }
        });
        zakaziObavestenja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long currentTime = SystemClock.elapsedRealtime() + 5000;
                long repeating = 30*24 * 60 * 60 * 1000;
                long interval = 10000;

                Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,currentTime,repeating,pendingIntent);

                Toast.makeText(SettingsActivity.this, "POSTAVLJENO", Toast.LENGTH_SHORT).show();
            }
        });

        otkaziObavestenje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

            }
        });
    }
}