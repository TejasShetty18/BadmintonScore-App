package com.example.badmintonscore;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlayerList extends AppCompatActivity{

    ImageView backButton;
    TextView playerListDescription;

    RecyclerView recycleView;
    MyDatabaseHelper myDB;
    CustomAdapter customAdapter;
    ArrayList<String> name1,name2,name3,name4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        backButton = findViewById(R.id.backButton);

        recycleView = findViewById(R.id.recycleView);

        myDB = new MyDatabaseHelper(PlayerList.this);
        name1 = new ArrayList<>();
        name2 = new ArrayList<>();
        name3 = new ArrayList<>();
        name4 = new ArrayList<>();

        recycleView = findViewById(R.id.recycleView);
        storeDataArrays();
        customAdapter = new CustomAdapter(this, name1, name2, name3, name4);
        recycleView.setAdapter(customAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(PlayerList.this));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Page = new Intent(PlayerList.this, SettingsPage.class);
                startActivity(Home_Page);
            }
        });
    }
        void storeDataArrays(){
            Cursor cursor=myDB.readAllData();
            if(cursor.getCount()==0){
                Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            }else{
                while (cursor.moveToNext()){
                    name1.add(cursor.getString(1));
                    name2.add(cursor.getString(2));
                    name3.add(cursor.getString(3));
                    name4.add(cursor.getString(4));

                }
            }
            cursor.close();
        }
    }
