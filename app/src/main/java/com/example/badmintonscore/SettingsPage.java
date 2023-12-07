package com.example.badmintonscore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SettingsPage extends AppCompatActivity {

    private CheckBox playSoundCheckBox;
    private SharedPreferences sharedPreferences;
    private MediaPlayer mediaPlayer;

    ImageView backButton;


    public ToggleButton themeToggleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        backButton = findViewById(R.id.backButton);
        TextView playerListHeading;


        themeToggleButton = findViewById(R.id.mode);

        themeToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                setTheme(R.style.AppTheme_Light);
            }
        });





        // Initialize UI elements
        playSoundCheckBox = findViewById(R.id.playSoundCheckBox);

        // Get shared preferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Retrieve and set the state of the checkbox
        playSoundCheckBox.setChecked(sharedPreferences.getBoolean("playSound", true));

        playerListHeading = findViewById(R.id.playerListHeading);



        playerListHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsPage.this, PlayerList.class);
                startActivity(intent);
            }
        });


        // Initialize the MediaPlayer to play a sound
        mediaPlayer = MediaPlayer.create(this, R.raw.your_sound);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Page = new Intent(SettingsPage.this,MainActivity.class);
                Home_Page.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Home_Page);
            }
        });
        // Set a listener for the checkbox state change
        playSoundCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playSoundCheckBox.isChecked()) {
                    // Play the sound
                    mediaPlayer.start();
                } else {
                    // Stop the sound (if it's playing)
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                }
            }
        });


    }




    @Override
    protected void onStop() {
        super.onStop();

        // Save the state of the checkbox in shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("playSound", playSoundCheckBox.isChecked());
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Release the MediaPlayer when the activity is destroyed to free up resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
