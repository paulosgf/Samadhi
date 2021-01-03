package com.paulosgf.samadhi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        Intent i = new Intent(context, GetAllMessagesActivity.class);
        context.startActivity(i);
        Toast.makeText(context, "Broadcast Intent Detected.",
                Toast.LENGTH_LONG).show();
    }
}
