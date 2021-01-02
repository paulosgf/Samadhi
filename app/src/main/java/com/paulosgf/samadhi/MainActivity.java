package com.paulosgf.samadhi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.content.BroadcastReceiver;
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
    private Button btnGetall;
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

        btnStartAlarm = findViewById(R.id.btnStartAlarm);
        btnCancelAlarm = findViewById(R.id.btnCancelAlarm);

        // database logic
        TextView textView = findViewById(R.id.textview);
        Typeface typeface = ResourcesCompat.getFont(
                this, R.font.kaushanscript_regular);
        textView.setTypeface(typeface);

        databaseHelper = new DatabaseHelper(this);
        btnStore = (Button) findViewById(R.id.btnstore);
        btnGetall = (Button) findViewById(R.id.btnget);
        etmsg = (EditText) findViewById(R.id.etmsg);

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.insertMessage(etmsg.getText().toString());
                etmsg.setText("");
                Toast.makeText(MainActivity.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        btnGetall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GetAllMessagesActivity.class);
                startActivity(intent);
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