package com.paulosgf.samadhi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class GetAllMessagesActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<MessageModel> MessageModelArrayList;
    private CustomAdapter customAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_messages);

        listView = (ListView) findViewById(R.id.lv);

        databaseHelper = new DatabaseHelper(this);

        MessageModelArrayList = databaseHelper.getAllMsgs();

        customAdapter = new CustomAdapter(this,MessageModelArrayList);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GetAllMessagesActivity.this,UpdateDeleteActivity.class);
                intent.putExtra("user",MessageModelArrayList.get(position));
                startActivity(intent);
            }
        });
    }
}