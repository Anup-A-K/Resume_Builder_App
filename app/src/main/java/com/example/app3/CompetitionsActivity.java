package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class CompetitionsActivity extends BaseActivity {
    private TextInputEditText competitionNameInput, organizerInput, yearInput, achievementInput;
    private Button previousButton, nextButton, addMoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.competitions_details);
        setActivityTitle("Competitions");

        initializeViews();
        setupButtons();
    }

    private void initializeViews() {
        competitionNameInput = findViewById(R.id.competitionNameInput);
        organizerInput = findViewById(R.id.organizerInput);
        yearInput = findViewById(R.id.yearInput);
        achievementInput = findViewById(R.id.achievementInput);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        addMoreButton = findViewById(R.id.addMoreButton);
    }

    private void setupButtons() {
        previousButton.setOnClickListener(v -> finish());
        
        nextButton.setOnClickListener(v -> {
            saveCompetitionDetails();
            startActivity(new Intent(CompetitionsActivity.this, AchievementsActivity.class));
        });

        addMoreButton.setOnClickListener(v -> {
            saveCompetitionDetails();
            clearInputs();
        });
    }

    private void saveCompetitionDetails() {
        String competitionName = competitionNameInput.getText().toString();
        String organizer = organizerInput.getText().toString();
        String year = yearInput.getText().toString();
        String achievement = achievementInput.getText().toString();
        
        // TODO: Save to SharedPreferences or Database
    }

    private void clearInputs() {
        competitionNameInput.setText("");
        organizerInput.setText("");
        yearInput.setText("");
        achievementInput.setText("");
    }
} 