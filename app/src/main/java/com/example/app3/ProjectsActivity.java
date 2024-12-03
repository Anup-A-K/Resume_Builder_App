package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ProjectsActivity extends BaseActivity {
    private TextInputEditText projectNameInput, projectDescriptionInput, technologiesInput;
    private Button previousButton, nextButton, addMoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projects_details);
        setActivityTitle("Projects");

        initializeViews();
        setupButtons();
    }

    private void initializeViews() {
        projectNameInput = findViewById(R.id.projectNameInput);
        projectDescriptionInput = findViewById(R.id.projectDescriptionInput);
        technologiesInput = findViewById(R.id.technologiesInput);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        addMoreButton = findViewById(R.id.addMoreButton);
    }

    private void setupButtons() {
        previousButton.setOnClickListener(v -> finish());
        
        nextButton.setOnClickListener(v -> {
            saveProjectDetails();
            startActivity(new Intent(ProjectsActivity.this, SkillsActivity.class));
        });

        addMoreButton.setOnClickListener(v -> {
            saveProjectDetails();
            clearInputs();
        });
    }

    private void saveProjectDetails() {
        String projectName = projectNameInput.getText().toString();
        String description = projectDescriptionInput.getText().toString();
        String technologies = technologiesInput.getText().toString();
        
        // TODO: Save to SharedPreferences or Database
    }

    private void clearInputs() {
        projectNameInput.setText("");
        projectDescriptionInput.setText("");
        technologiesInput.setText("");
    }
} 