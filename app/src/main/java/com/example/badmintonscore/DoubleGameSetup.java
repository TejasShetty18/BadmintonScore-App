package com.example.badmintonscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DoubleGameSetup extends AppCompatActivity {

    ImageView backButton;
    EditText player1,player2,player3,player4;
    RadioGroup radiogrp;
    RadioButton Right,Left;
    Button btnStartGame;
    MyDatabaseHelper myDb=new MyDatabaseHelper(DoubleGameSetup.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_game_setup);

        // Set the screen orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        backButton = findViewById(R.id.backButton);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);
        btnStartGame = findViewById(R.id.btnStartGame);
        Right = findViewById(R.id.Right);
        Left = findViewById(R.id.Left);
        radiogrp = findViewById(R.id.radiogrp);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Page = new Intent(DoubleGameSetup.this,MainActivity.class);
                Home_Page.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Home_Page);
            }
        });


        radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(R.id.Left==i){
                    player2.setBackgroundColor(Color.BLUE);
                    player3.setBackgroundColor(Color.alpha(0));
                    player4.setBackgroundColor(Color.alpha(0));
                } else if(R.id.Right==i){
                    player3.setBackgroundColor(Color.BLUE);
                    player1.setBackgroundColor(Color.alpha(0));
                    player2.setBackgroundColor(Color.alpha(0));
                }
            }
        });

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radiogrp.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedSide = selectedRadioButton.getText().toString();

                Intent Home_Page = new Intent(DoubleGameSetup.this, DoubleGamePage.class);
                String player1Name = player1.getText().toString();
                Home_Page.putExtra("Text1", player1Name);
                String player2Name = player2.getText().toString();
                Home_Page.putExtra("Text2", player2Name);
                String player3Name = player3.getText().toString();
                Home_Page.putExtra("Text3", player3Name);
                String player4Name = player4.getText().toString();
                Home_Page.putExtra("Text4", player4Name);
                Home_Page.putExtra("selectedSide", selectedSide);
                myDb.addHistory(player1.getText().toString(),player2.getText().toString(),player3.getText().toString(),player4.getText().toString());
                myDb.close();
                startActivity(Home_Page);
            }
        });
    }
}