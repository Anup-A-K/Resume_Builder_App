package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class AchievementsActivity extends BaseActivity {
    private TextInputEditText achievementTitleInput, descriptionInput, yearInput;
    private Button previousButton, nextButton, addMoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievements_details);
        setActivityTitle("Achievements");

        initializeViews();
        setupButtons();
    }

    private void initializeViews() {
        achievementTitleInput = findViewById(R.id.achievementTitleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        yearInput = findViewById(R.id.yearInput);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        addMoreButton = findViewById(R.id.addMoreButton);
    }

    private void setupButtons() {
        previousButton.setOnClickListener(v -> finish());
        
        nextButton.setOnClickListener(v -> {
            saveAchievementDetails();
            Intent intent = new Intent(AchievementsActivity.this, TemplateSelectionActivity.class);
            intent.putExtra("source", "achievements");
            startActivity(intent);
        });

        addMoreButton.setOnClickListener(v -> {
            saveAchievementDetails();
            clearInputs();
        });
    }

    private void saveAchievementDetails() {
        String title = achievementTitleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        String year = yearInput.getText().toString();
        
        // TODO: Save to SharedPreferences or Database
    }

    private void clearInputs() {
        achievementTitleInput.setText("");
        descriptionInput.setText("");
        yearInput.setText("");
    }
} 