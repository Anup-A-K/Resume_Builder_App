package com.example.app3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

public class TemplateSelectionActivity extends BaseActivity {
    private MaterialCardView professionalTemplate, creativeTemplate, minimalTemplate;
    private Button previousButton, nextButton;
    private String selectedTemplate = "professional"; // Default template

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_selection);
        setActivityTitle("Select Template");

        initializeViews();
        setupClickListeners();
        loadSavedTemplate();
    }

    private void initializeViews() {
        professionalTemplate = findViewById(R.id.professionalTemplate);
        creativeTemplate = findViewById(R.id.creativeTemplate);
        minimalTemplate = findViewById(R.id.minimalTemplate);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
    }

    private void setupClickListeners() {
        professionalTemplate.setOnClickListener(v -> selectTemplate("professional"));
        creativeTemplate.setOnClickListener(v -> selectTemplate("creative"));
        minimalTemplate.setOnClickListener(v -> selectTemplate("minimal"));

        previousButton.setOnClickListener(v -> {
            // Check where we came from
            String source = getIntent().getStringExtra("source");
            if ("achievements".equals(source)) {
                finish(); // Go back to achievements
            } else {
                startActivity(new Intent(this, AchievementsActivity.class));
                finish();
            }
        });
        
        nextButton.setOnClickListener(v -> {
            if (selectedTemplate == null) {
                Toast.makeText(this, "Please select a template", Toast.LENGTH_SHORT).show();
                return;
            }
            saveSelectedTemplate();
            startActivity(new Intent(this, DownloadResumeActivity.class));
        });
    }

    private void selectTemplate(String template) {
        selectedTemplate = template;
        updateSelectedTemplateUI();
    }

    private void updateSelectedTemplateUI() {
        // Reset all templates to default elevation
        professionalTemplate.setCardElevation(getResources().getDimension(R.dimen.card_elevation_default));
        creativeTemplate.setCardElevation(getResources().getDimension(R.dimen.card_elevation_default));
        minimalTemplate.setCardElevation(getResources().getDimension(R.dimen.card_elevation_default));

        // Highlight selected template
        switch (selectedTemplate) {
            case "professional":
                professionalTemplate.setCardElevation(getResources().getDimension(R.dimen.card_elevation_selected));
                break;
            case "creative":
                creativeTemplate.setCardElevation(getResources().getDimension(R.dimen.card_elevation_selected));
                break;
            case "minimal":
                minimalTemplate.setCardElevation(getResources().getDimension(R.dimen.card_elevation_selected));
                break;
        }
    }

    private void saveSelectedTemplate() {
        SharedPreferences prefs = getSharedPreferences("ResumePrefs", MODE_PRIVATE);
        prefs.edit().putString("selected_template", selectedTemplate).apply();
    }

    private void loadSavedTemplate() {
        SharedPreferences prefs = getSharedPreferences("ResumePrefs", MODE_PRIVATE);
        selectedTemplate = prefs.getString("selected_template", "professional");
        updateSelectedTemplateUI();
    }
} 