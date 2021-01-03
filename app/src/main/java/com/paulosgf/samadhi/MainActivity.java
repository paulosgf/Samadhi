package com.paulosgf.samadhi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {
    private Button btnStore, btnStartAlarm, btnCancelAlarm;
    private EditText etmsg;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // database logic
        TextView textView = findViewById(R.id.textview);
        Typeface typeface = ResourcesCompat.getFont(
                this, R.font.kaushanscript_regular);
        textView.setTypeface(typeface);

        databaseHelper = new DatabaseHelper(this);
        btnStore = (Button) findViewById(R.id.btnstore);
        etmsg = (EditText) findViewById(R.id.etmsg);

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.insertMessage(etmsg.getText().toString());
                etmsg.setText("");
                Toast.makeText(MainActivity.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        // alarm logic
        btnStartAlarm = findViewById(R.id.btnStartAlarm);
        btnCancelAlarm = findViewById(R.id.btnCancelAlarm);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        btnCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);
                Toast.makeText(getApplicationContext(), "Alarm Cancelled", Toast.LENGTH_LONG).show();
            }
        });

        configureReceiver();
    }

    // alarm logic
    private void configureReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.paulosgf.samadhi");
        receiver = new MyReceiver();
        registerReceiver(receiver, filter);
    }

    public void broadcastIntent(View view)
    {
        Intent intent = new Intent();
        intent.setAction("com.paulosgf.samadhi");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}