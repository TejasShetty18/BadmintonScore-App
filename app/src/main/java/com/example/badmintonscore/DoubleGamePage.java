package com.example.badmintonscore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;

public class DoubleGamePage extends AppCompatActivity {

    String LeftServer, RightServer, CurrentServerSide, CurrentServer;

    ImageView backButton, undoButton;
    Button btn1, btn2;
    TextView P1name, P2name, Player1name, Player2name, player1, player2, player3, player4, P1Set1score, P1Set2score, P1Set3score, P2Set1score, P2Set2score, P2Set3score;


//    MyDatabaseHelper myDb=new MyDatabaseHelper(DoubleGamePage.this);

    private int player1Score = 0;
    private int player2Score = 0;
    private final int winScore = 21;
    private final int maxSets = 3;
    private int player1SetsWon = 0;
    private int player2SetsWon = 0;

    // Variables to keep track of the current set
    private int currentSet = 0;
    private int lastScoringPlayer = 0; // 0 for Player 1, 1 for Player 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_game_page);

        // Set the screen orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        backButton = findViewById(R.id.backButton);
        P1name = findViewById(R.id.P1name);
        P2name = findViewById(R.id.P2name);
        Player1name = findViewById(R.id.Player1name);
        Player2name = findViewById(R.id.Player2name);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        P1Set1score = findViewById(R.id.P1Set1score);
        P1Set2score = findViewById(R.id.P1Set2score);
        P1Set3score = findViewById(R.id.P1Set3score);
        P2Set1score = findViewById(R.id.P2Set1score);
        P2Set2score = findViewById(R.id.P2Set2score);
        P2Set3score = findViewById(R.id.P2Set3score);
        undoButton = findViewById(R.id.undoButton);
//        myDb.addHistory(player1.getText().toString(),player2.getText().toString(),player3.getText().toString(),player4.getText().toString());
//        myDb.close();
        currentSet = 0;


        LeftServer = "";
        RightServer = "";
        CurrentServerSide = "";
        MySharedPrefGame.saveString(this, "LeftServer", "");
        MySharedPrefGame.saveString(this, "RightServer", "");
        MySharedPrefGame.saveString(this, "CurrentServerSide", "");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Page = new Intent(DoubleGamePage.this, DoubleGameSetup.class);
                Home_Page.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Home_Page);
            }
        });


        Intent third = getIntent();


        String player1Name = third.getStringExtra("Text1");
        player1.setText(player1Name);
        String player2Name = third.getStringExtra("Text2");
        P1name.setText(player1Name + "/" + player2Name);
        Player1name.setText(player1Name + "/" + player2Name);
        player2.setText(player2Name);

        String player3Name = third.getStringExtra("Text3");
        player3.setText(player3Name);

        String player4Name = third.getStringExtra("Text4");
        P2name.setText(player3Name + "/" + player4Name);
        Player2name.setText(player3Name + "/" + player4Name);
        player4.setText(player4Name);
        String selectedSide = third.getStringExtra("selectedSide");
        if ("Left".equals(selectedSide)) {
            LeftServer = player2.getText().toString().trim();
            MySharedPrefGame.saveString(this, "LeftServer", player2.getText().toString().trim());
            player2.setBackgroundColor(Color.BLUE);
        } else if ("Right".equals(selectedSide)) {
            player3.setBackgroundColor(Color.BLUE);
            RightServer = player3.getText().toString().trim();
            MySharedPrefGame.saveString(this, "RightServer", player3.getText().toString().trim());
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player1name.setText(player1Name + "/" + player2Name);
                Player2name.setText(player3Name + "/" + player4Name);
                increasePlayer1Score();
                CurrentServerSide = "LEFT";
                MySharedPrefGame.saveString(getApplicationContext(), "CurrentServerSide", "LEFT");
                CurrentServer = getCurrentServer();
                swapTeamA();
//                swapCourts();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player2name.setText(player1Name + "/" + player2Name);
                Player1name.setText(player3Name + "/" + player4Name);
                increasePlayer2Score();
                CurrentServerSide = "RIGHT";
                CurrentServer = getCurrentServer();
                swapTeamB();

                //              swapTeamB();
//                swapCourts();

            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoLastScore();
            }
        });

    }

    private boolean isPlayer2Serve = true; // Flag to keep track of the current server

    private boolean isPlayer3Serve = true;

    private void swapTeamA() {
        if (CurrentServer.equals((player1.getText().toString())) || CurrentServer.equals((player2.getText().toString()))) {
            swapPlayerSameCourt(CurrentServerSide);
        } else if (CurrentServer.equals((player3.getText().toString())) || CurrentServer.equals((player4.getText().toString()))) {
            if (LeftServer.equals(player1.getText().toString())) {
                LeftServer = player2.getText().toString();
            } else {
                LeftServer = player1.getText().toString();
            }
            CurrentServer = LeftServer;
            setPalyerColorBlue(LeftServer);
            if (RightServer.equals(player3.getText().toString())) {
                RightServer = player4.getText().toString();
            } else {
                RightServer = player3.getText().toString();
            }
        }




    }

    private void swapTeamB() {
        if (CurrentServer.equals((player3.getText().toString())) || CurrentServer.equals((player4.getText().toString()))) {
            swapPlayerSameCourt(CurrentServerSide);
        } else if (CurrentServer.equals((player1.getText().toString())) || CurrentServer.equals((player2.getText().toString()))) {
            if (LeftServer.equals(player3.getText().toString())) {
                LeftServer = player4.getText().toString();
            } else {
                LeftServer = player3.getText().toString();
            }
            CurrentServer = LeftServer;
            setPalyerColorBlue(LeftServer);
            if (RightServer.equals(player1.getText().toString())) {
                RightServer = player2.getText().toString();
            } else {
                RightServer = player1.getText().toString();
            }
        }
//        else if (CurrentServer.equals((player3.getText().toString())) || CurrentServer.equals((player4.getText().toString()))) {
//            if (LeftServer.equals(player1.getText().toString())) {
//                LeftServer = player2.getText().toString();
//            } else {
//                LeftServer = player1.getText().toString();
//            }
//            setPalyerColorBlue(LeftServer);
//            if (RightServer.equals(player3.getText().toString())) {
//                RightServer = player4.getText().toString();
//            } else {
//                RightServer = player3.getText().toString();
//            }
//        }
    }

    private String getCurrentServer() {
//
        if (getBackgroundColor(player1) != 0) {
            return player1.getText().toString().trim();
        }
        if (getBackgroundColor(player2) != 0) {
            return player2.getText().toString().trim();
        }
        if (getBackgroundColor(player3) != 0) {
            return player3.getText().toString().trim();
        }
        if (getBackgroundColor(player4) != 0) {
            return player4.getText().toString().trim();
        }
        return "";
    }


    private void swapPlayerSameCourt(String courtName) {
        if (courtName.equals("LEFT")) {

            if (!(player1.getDrawingCacheBackgroundColor() == Color.BLUE) && getBackgroundColor(player1)== 0) {
                setPalyerColorBlue(player1.getText().toString());
            }
            if (!(player2.getDrawingCacheBackgroundColor() == Color.BLUE) && getBackgroundColor(player2) == 0) {
                setPalyerColorBlue(player2.getText().toString());
            }
            String tempP1 = player1.getText().toString();
            String tempP2 = player2.getText().toString();
            player1.setText(tempP2);
            player2.setText(tempP1);
        } else if (courtName.equals("RIGHT")) {

            if (!(player3.getDrawingCacheBackgroundColor() == Color.BLUE) && getBackgroundColor(player3) == 0) {
                setPalyerColorBlue(player3.getText().toString());
            }
            if (!(player4.getDrawingCacheBackgroundColor() == Color.BLUE) && getBackgroundColor(player4) == 0) {
                setPalyerColorBlue(player4.getText().toString());
            }
            String tempP3 = player3.getText().toString();
            String tempP4 = player4.getText().toString();
            player3.setText(tempP4);
            player4.setText(tempP3);
        }
    }

    public static int getBackgroundColor(TextView textView) {
        Drawable drawable = textView.getBackground();
        if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            if (Build.VERSION.SDK_INT >= 11) {
                return colorDrawable.getAlpha();
            }
            try {
                Field field = colorDrawable.getClass().getDeclaredField("mState");
                field.setAccessible(true);
                Object object = field.get(colorDrawable);
                field = object.getClass().getDeclaredField("mUseColor");
                field.setAccessible(true);
                return field.getInt(object);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    private void setPalyerColorBlue(String playerName) {
        if (player1.getText().toString().equals(playerName)) {
            player1.setBackgroundColor(Color.BLUE);
            player1.setAlpha(1);
            player2.setAlpha(1);
            player2.setBackgroundColor(Color.alpha(0));
            player3.setAlpha(1);
            player3.setBackgroundColor(Color.alpha(0));
            player4.setAlpha(1);
            player4.setBackgroundColor(Color.alpha(0));
        } else if (player2.getText().toString().equals(playerName)) {
            player1.setAlpha(1);
            player1.setBackgroundColor(Color.alpha(0));
            player2.setBackgroundColor(Color.BLUE);
            player2.setAlpha(1);
            player3.setAlpha(1);
            player3.setBackgroundColor(Color.alpha(0));

            player4.setAlpha(1);
            player4.setBackgroundColor(Color.alpha(0));
        } else if (player3.getText().toString().equals(playerName)) {
            player1.setAlpha(1);
            player1.setBackgroundColor(Color.alpha(0));
            player2.setAlpha(1);
            player2.setBackgroundColor(Color.alpha(0));
            player3.setBackgroundColor(Color.BLUE);
            player3.setAlpha(1);
            player4.setAlpha(1);
            player4.setBackgroundColor(Color.alpha(0));
        } else if (player4.getText().toString().equals(playerName)) {
            player1.setAlpha(1);
            player1.setBackgroundColor(Color.alpha(0));
            player2.setAlpha(1);
            player2.setBackgroundColor(Color.alpha(0));
            player3.setAlpha(1);
            player3.setBackgroundColor(Color.alpha(0));
            player4.setBackgroundColor(Color.BLUE);
            player4.setAlpha(1);

        }
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
            if (player1SetsWon > player2SetsWon) {
                // Player 1 wins the match
                // Implement your logic for announcing the winner here
                Intent Res_Page = new Intent(DoubleGamePage.this, ResultPage.class);
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
                Intent Res_Page = new Intent(DoubleGamePage.this, ResultPage.class);
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