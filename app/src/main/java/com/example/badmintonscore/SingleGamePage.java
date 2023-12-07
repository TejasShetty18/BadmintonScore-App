package com.example.badmintonscore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.content.pm.ActivityInfo;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class SingleGamePage extends AppCompatActivity  {

    ImageView backButton,undoButton;
    TextView P1name,P2name,Player1name,Player2name,player1,player2,P1Set1score,P1Set2score,P1Set3score,P2Set1score,P2Set2score,P2Set3score,player1demo,player2demo;
    Button btn1,btn2;
    MyDatabaseHelper myDb=new MyDatabaseHelper(SingleGamePage.this);

    private int player1Score = 0;
    private int player2Score = 0;
    private final int winScore = 21;
    private final int maxSets = 3;
    private int player1SetsWon = 0;
    private int player2SetsWon = 0;

  // Variables to keep track of the current set
    private int currentSet = 0;
    private int lastScoringPlayer = 0; // 0 for Player 1, 1 for Player 2


//    String Name1,Name2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game_page);

        // Set the screen orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        backButton = findViewById(R.id.backButton);
        P1name = findViewById(R.id.P1name);
        P2name = findViewById(R.id.P2name);
        Player1name = findViewById(R.id.Player1name);
        Player2name = findViewById(R.id.Player2name);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player1demo = findViewById(R.id.player1demo);
        player2demo = findViewById(R.id.player2demo);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        P1Set1score = findViewById(R.id.P1Set1score);
        P1Set2score = findViewById(R.id.P1Set2score);
        P1Set3score = findViewById(R.id.P1Set3score);
        P2Set1score = findViewById(R.id.P2Set1score);
        P2Set2score = findViewById(R.id.P2Set2score);
        P2Set3score = findViewById(R.id.P2Set3score);
        undoButton = findViewById(R.id.undoButton);

        currentSet = 0;



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Page = new Intent(SingleGamePage.this, SingleGameSetup.class);
                Home_Page.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Home_Page);
            }
        });


        Intent third = getIntent();
        String player1Name = third.getStringExtra("Text1");
        P1name.setText(player1Name);
        Player1name.setText(player1Name);
        player1.setText(player1Name);

        String player2Name = third.getStringExtra("Text2");
        P2name.setText(player2Name);
        Player2name.setText(player2Name);
        player2.setText(player2Name);
       // myDb.addHistory(Player1name.getText().toString(),Player2name.getText().toString(),null,null);
        String selectedSide = third.getStringExtra("selectedSide");
        if("Left".equals(selectedSide)){
            player1.setBackgroundColor(Color.BLUE);
        }else if ("Right".equals(selectedSide)) {
            player2.setBackgroundColor(Color.BLUE);
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Player1name.setText(player1Name);
                 Player2name.setText(player2Name);
                 increasePlayer1Score();

                 if(player1.getVisibility()== View.VISIBLE || player1demo.getVisibility()== View.VISIBLE){
                    if(player1.getVisibility()==View.VISIBLE){
                        player1.setVisibility(View.INVISIBLE);
                        player1demo.setVisibility(View.VISIBLE);
                        player1demo.setBackgroundColor(Color.BLUE);
                        player1demo.setText(player1.getText().toString());
                        if(player2.getVisibility()== View.VISIBLE){
                            player2.setVisibility(View.INVISIBLE);
                            player2demo.setVisibility(View.VISIBLE);
                            player2demo.setText(player2.getText().toString());
                            //  player1demo.setBackgroundColor(Color.BLUE);
                        } else if(player2demo.getVisibility()== View.VISIBLE){
                            player2demo.setVisibility(View.INVISIBLE);
                            player2.setVisibility(View.VISIBLE);
                        }
                    } else if(player1demo.getVisibility()==View.VISIBLE){
                        player1demo.setVisibility(View.INVISIBLE);
                        player1.setBackgroundColor(Color.BLUE);
                        player1.setVisibility(View.VISIBLE);
                        if(player2.getVisibility()== View.VISIBLE){
                            player2.setVisibility(View.INVISIBLE);
                            player2demo.setVisibility(View.VISIBLE);
                            player2demo.setText(player2.getText().toString());
                            //  player1demo.setBackgroundColor(Color.BLUE);
                        } else if(player2demo.getVisibility()== View.VISIBLE){
                            player2demo.setVisibility(View.INVISIBLE);
                            player2.setVisibility(View.VISIBLE);
                        }
                    }
                } else if (player1.getDrawingCacheBackgroundColor()==Color.BLUE || player1demo.getDrawingCacheBackgroundColor()==Color.BLUE ) {
                     if(player1.getVisibility()==View.VISIBLE)
                         player1.setBackgroundColor(Color.BLUE);
                     else if (player1demo.getVisibility()==View.VISIBLE) {
                         player1demo.setBackgroundColor(Color.BLUE);
                     }
                 }

                player2.setBackgroundColor(Color.alpha(0));
                player2demo.setBackgroundColor(Color.alpha(0));

//                 movePlayer1Up();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player2name.setText(player1Name);
                Player1name.setText(player2Name);
                increasePlayer2Score();

                if(player2.getVisibility()== View.VISIBLE || player2demo.getVisibility()== View.VISIBLE){
                    if(player2.getVisibility()==View.VISIBLE){
                        player2.setVisibility(View.INVISIBLE);
                        player2demo.setVisibility(View.VISIBLE);
                        player2demo.setBackgroundColor(Color.BLUE);
                        player2demo.setText(player2.getText().toString());
                        if(player1.getVisibility()== View.VISIBLE){
                            player1.setVisibility(View.INVISIBLE);
                            player1demo.setVisibility(View.VISIBLE);
                            player1demo.setText(player1.getText().toString());
                            //  player1demo.setBackgroundColor(Color.BLUE);
                        } else if(player1demo.getVisibility()== View.VISIBLE){
                            player1demo.setVisibility(View.INVISIBLE);
                            player1.setVisibility(View.VISIBLE);
                        }
                    } else if(player2demo.getVisibility()==View.VISIBLE){
                        player2demo.setVisibility(View.INVISIBLE);
                        player2.setVisibility(View.VISIBLE);
                        player2.setBackgroundColor(Color.BLUE);
                        if(player1.getVisibility()== View.VISIBLE){
                            player1.setVisibility(View.INVISIBLE);
                            player1demo.setVisibility(View.VISIBLE);
                            player1demo.setText(player1.getText().toString());
                            //  player1demo.setBackgroundColor(Color.BLUE);
                        } else if(player1demo.getVisibility()== View.VISIBLE){
                            player1demo.setVisibility(View.INVISIBLE);
                            player1.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else if (player2.getDrawingCacheBackgroundColor()==Color.BLUE || player2demo.getDrawingCacheBackgroundColor()==Color.BLUE ) {
                    if(player2.getVisibility()==View.VISIBLE)
                        player2.setBackgroundColor(Color.BLUE);
                    else if (player2demo.getVisibility()==View.VISIBLE) {
                        player2demo.setBackgroundColor(Color.BLUE);
                    }
                }

                player1.setBackgroundColor(Color.alpha(0));
                player1demo.setBackgroundColor(Color.alpha(0));
//                movePlayer2Down();
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoLastScore();
            }
        });

    }

    private void increasePlayer1Score() {
        player1Score++;
        lastScoringPlayer = 0; // Player 1 scored last
        Log.d("DebugTag", "Player 1 Score: " + player1Score);

        updateScoreTextViews();
        checkWin();

    }

    private void increasePlayer2Score() {
        player2Score++;
        lastScoringPlayer = 1; // Player 2 scored last

        updateScoreTextViews();
        checkWin();

    }
    private void checkWin() {
        if (player1Score == winScore) {
            player1SetsWon++;
            resetScores();
            Toast.makeText(getApplicationContext(), "Set  Win by: "+ P1name.getText(), Toast.LENGTH_SHORT).show();


            currentSet++; // Move to the next set
//            currentPlayer = 0; // Reset to Player 1 for the new set
//            updateScoreTextViews(); // Update the TextViews for the new set

        } else if (player2Score == winScore) {
            player2SetsWon++;
            resetScores();
            Toast.makeText(getApplicationContext(), "Set  Win by: "+ P2name.getText(), Toast.LENGTH_SHORT).show();

            currentSet++; // Move to the next set
//            currentPlayer = 0; // Reset to Player 1 for the new set
//            updateScoreTextViews(); // Update the TextViews for the new set
        }
        if(currentSet == maxSets){
            if(player1SetsWon > player2SetsWon) {
                // Player 1 wins the match
                // Implement your logic for announcing the winner here
                Intent Res_Page = new Intent(SingleGamePage.this, ResultPage.class);
                String p1Name = P1name.getText().toString();
                Res_Page.putExtra("Text1", p1Name);
                String p1Set1Score = P1Set1score.getText().toString();
                Res_Page.putExtra("Text1score1", p1Set1Score);
                String p1Set2Score = P1Set2score.getText().toString();
                Res_Page.putExtra("Text1score2", p1Set2Score);
                String p1Set3Score = P1Set3score.getText().toString();
                Res_Page.putExtra("Text1score3", p1Set3Score);

                String p2Name = P2name.getText().toString();
                Res_Page.putExtra("Text2", p2Name);
                String p2Set1Score = P2Set1score.getText().toString();
                Res_Page.putExtra("Text2score1", p2Set1Score);
                String p2Set2Score = P2Set2score.getText().toString();
                Res_Page.putExtra("Text2score2", p2Set2Score);
                String p3Set3Score = P2Set3score.getText().toString();
                Res_Page.putExtra("Text2score3", p3Set3Score);
                startActivity(Res_Page);


            } else  {
                // Player 2 wins the match
                // Implement your logic for announcing the winner here
                Intent Res_Page = new Intent(SingleGamePage.this, ResultPage.class);
                String p1Name = P1name.getText().toString();
                Res_Page.putExtra("Text1", p1Name);
                String p1Set1Score = P1Set1score.getText().toString();
                Res_Page.putExtra("Text1score1", p1Set1Score);
                String p1Set2Score = P1Set2score.getText().toString();
                Res_Page.putExtra("Text1score2", p1Set2Score);
                String p1Set3Score = P1Set3score.getText().toString();
                Res_Page.putExtra("Text1score3", p1Set3Score);

                String p2Name = P2name.getText().toString();
                Res_Page.putExtra("Text2", p2Name);
                String p2Set1Score = P2Set1score.getText().toString();
                Res_Page.putExtra("Text2score1", p2Set1Score);
                String p2Set2Score = P2Set2score.getText().toString();
                Res_Page.putExtra("Text2score2", p2Set2Score);
                String p3Set3Score = P2Set3score.getText().toString();
                Res_Page.putExtra("Text2score3", p3Set3Score);
                startActivity(Res_Page);
            }
        }
    }


    private void undoLastScore() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        if (lastScoringPlayer == 0 && player1Score > 0) {
                            player1Score--;
                            if (currentSet == 0) {
                                P1Set1score.setText(String.valueOf(player1Score));
                            } else if (currentSet == 1) {
                                P1Set2score.setText(String.valueOf(player1Score));
                            } else if (currentSet == 2) {
                                P1Set3score.setText(String.valueOf(player1Score));
                            }
                        } else if (lastScoringPlayer == 1 && player2Score > 0) {
                            player2Score--;
                            if (currentSet == 0) {
                                P2Set1score.setText(String.valueOf(player2Score));
                            } else if (currentSet == 1) {
                                P2Set2score.setText(String.valueOf(player2Score));
                            } else if (currentSet == 2) {
                                P2Set3score.setText(String.valueOf(player2Score));
                            }
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Toast.makeText(getApplicationContext(), "NO action choosen", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Undo Last Action");
        alertDialog.show();


    }


//     Modify the updateScoreTextViews method to update the scores for the current set
    private void updateScoreTextViews() {
        if (currentSet == 0) {
            P1Set1score.setText(String.valueOf(player1Score));
            P2Set1score.setText(String.valueOf(player2Score));
        } else if (currentSet == 1) {
            // Update TextViews for the second set
            P1Set2score.setText(String.valueOf(player1Score));
            P2Set2score.setText(String.valueOf(player2Score));
        } else if (currentSet == 2) {
            // Update TextViews for the third set
            P1Set3score.setText(String.valueOf(player1Score));
            P2Set3score.setText(String.valueOf(player2Score));
        }
    }

    private void resetScores() {
        player1Score = 0;
        player2Score = 0;
    }

}