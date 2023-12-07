package com.example.badmintonscore;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button single_button, double_button, clearHistoryButton;
    ImageView settings;
    TextView matchHistoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        single_button = findViewById(R.id.single_button);
        double_button = findViewById(R.id.double_button);
        settings = findViewById(R.id.settings);
        matchHistoryTextView = findViewById(R.id.match_history);
        clearHistoryButton = findViewById(R.id.clear);

        // Load and display the match history
        displayMatchHistory();

        // Set a click listener for the Clear History button
        clearHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the match history
                clearMatchHistory();

                // Refresh the displayed match history
                displayMatchHistory();
            }
        });

        // For setting page
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setting = new Intent(MainActivity.this, SettingsPage.class);
                startActivity(setting);
            }
        });

        // For single player page game
        single_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Hello_world = new Intent(MainActivity.this, SingleGameSetup.class);
                startActivity(Hello_world);
            }
        });

        // For double player page game
        double_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Hello_world = new Intent(MainActivity.this, DoubleGameSetup.class);
                startActivity(Hello_world);
            }
        });
    }

    private void displayMatchHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("MatchHistory", MODE_PRIVATE);
        String matchResult = sharedPreferences.getString("MatchResult", "");
        matchHistoryTextView.setText(matchResult);
    }

    private void clearMatchHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("MatchHistory", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("MatchResult", "");
        editor.apply();
    }
}