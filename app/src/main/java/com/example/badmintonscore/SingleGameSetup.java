package com.example.badmintonscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.pm.ActivityInfo;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SingleGameSetup extends AppCompatActivity {

    ImageView backButton;
    EditText player1,player2;
    RadioGroup radiogrp;
    RadioButton Right,Left;
    Button btnStartGame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game_setup);

        // Set the screen orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        backButton = findViewById(R.id.backButton);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        btnStartGame = findViewById(R.id.btnStartGame);
        Right = findViewById(R.id.Right);
        Left = findViewById(R.id.Left);
        radiogrp = findViewById(R.id.radiogrp);



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Page = new Intent(SingleGameSetup.this,MainActivity.class);
                Home_Page.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Home_Page);
            }
        });



        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radiogrp.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedSide = selectedRadioButton.getText().toString();

                Intent Home_Page = new Intent(SingleGameSetup.this, SingleGamePage.class);
                String player1Name = player1.getText().toString();
                Home_Page.putExtra("Text1", player1Name);
                String player2Name = player2.getText().toString();
                Home_Page.putExtra("Text2", player2Name);
                Home_Page.putExtra("selectedSide", selectedSide);
                startActivity(Home_Page);
            }
        });


        radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(R.id.Left==i){
                    player1.setBackgroundColor(Color.BLUE);
                    player2.setBackgroundColor(Color.alpha(0));
                } else if(R.id.Right==i){
                    player2.setBackgroundColor(Color.BLUE);
                    player1.setBackgroundColor(Color.alpha(0));
                }
            }
        });



    }
}