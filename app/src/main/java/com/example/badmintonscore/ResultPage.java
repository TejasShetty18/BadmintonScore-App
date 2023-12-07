package com.example.badmintonscore;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class ResultPage extends AppCompatActivity {
    Button btnFinish;
    TextView P1name, P1Set1score, P1Set2score, P1Set3score, P2name, P2Set1score, P2Set2score, P2Set3score, CongratesText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        btnFinish = findViewById(R.id.btnFinish);
        P1name = findViewById(R.id.P1name);
        P1Set1score = findViewById(R.id.P1Set1score);
        P1Set2score = findViewById(R.id.P1Set2score);
        P1Set3score = findViewById(R.id.P1Set3score);
        P2name = findViewById(R.id.P2name);
        P2Set1score = findViewById(R.id.P2Set1score);
        P2Set2score = findViewById(R.id.P2Set2score);
        P2Set3score = findViewById(R.id.P2Set3score);
        CongratesText = findViewById(R.id.CongratesText);
        Intent third = getIntent();
        String p1Name = third.getStringExtra("Text1");
        P1name.setText(p1Name);
        String p1Set1Score = third.getStringExtra("Text1score1");
        P1Set1score.setText(p1Set1Score);
        String p1Set2Score = third.getStringExtra("Text1score2");
        P1Set2score.setText(p1Set2Score);
        String p1Set3Score = third.getStringExtra("Text1score3");
        P1Set3score.setText(p1Set3Score);
        String p2Name = third.getStringExtra("Text2");
        P2name.setText(p2Name);
        String p2Set1Score = third.getStringExtra("Text2score1");
        P2Set1score.setText(p2Set1Score);
        String p2Set2Score = third.getStringExtra("Text2score2");
        P2Set2score.setText(p2Set2Score);
        String p2Set3Score = third.getStringExtra("Text2score3");
        P2Set3score.setText(p2Set3Score);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calculate the match result and determine the winner
                int p1Score = Integer.parseInt(p1Set1Score) + Integer.parseInt(p1Set2Score) + Integer.parseInt(p1Set3Score);
                int p2Score = Integer.parseInt(p2Set1Score) + Integer.parseInt(p2Set2Score) + Integer.parseInt(p2Set3Score);
                String winner, loser;
                int winnerScore, loserScore;
                if (p1Score > p2Score) {
                    winner = p1Name;
                    winnerScore = p1Score;
                    loser = p2Name;
                    loserScore = p2Score;
                } else {
                    winner = p2Name;
                    winnerScore = p2Score;
                    loser = p1Name;
                    loserScore = p1Score;
                }
                // Store the match result in SharedPreferences
                saveMatchResultInSharedPreferences(winner, winnerScore, loser, loserScore);
                // Go back to the MainActivity
                Intent Result_Page = new Intent(ResultPage.this, MainActivity.class);
                Result_Page.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Result_Page);
            }
        });
    }
    private void saveMatchResultInSharedPreferences(String winner, int winnerScore, String loser, int loserScore) {
        SharedPreferences sharedPreferences = getSharedPreferences("MatchHistory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String existingMatchHistory = sharedPreferences.getString("MatchResult", "");
        String matchResult = winner + " (" + winnerScore + ") vs " + loser + " (" + loserScore + ")";

        String playerName = "Winner:" + winner + " vs " + "Loser" + loser ;
        if (!existingMatchHistory.isEmpty()) {
            // Append the new match result with a line break
            matchResult = existingMatchHistory + "\n" + matchResult;
        }
        editor.putString("MatchResult", matchResult);
        editor.apply();
    }
}
