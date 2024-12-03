package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SkillsActivity extends BaseActivity {
    private TextInputEditText skillNameInput, proficiencyInput;
    private Button previousButton, nextButton, addMoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skills_details);
        setActivityTitle("Skills");

        initializeViews();
        setupButtons();
    }

    private void initializeViews() {
        skillNameInput = findViewById(R.id.skillNameInput);
        proficiencyInput = findViewById(R.id.proficiencyInput);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        addMoreButton = findViewById(R.id.addMoreButton);
    }

    private void setupButtons() {
        previousButton.setOnClickListener(v -> finish());
        
        nextButton.setOnClickListener(v -> {
            saveSkillDetails();
            startActivity(new Intent(SkillsActivity.this, CompetitionsActivity.class));
        });

        addMoreButton.setOnClickListener(v -> {
            saveSkillDetails();
            clearInputs();
        });
    }

    private void saveSkillDetails() {
        String skillName = skillNameInput.getText().toString();
        String proficiency = proficiencyInput.getText().toString();
        
        // TODO: Save to SharedPreferences or Database
    }

    private void clearInputs() {
        skillNameInput.setText("");
        proficiencyInput.setText("");
    }
} 