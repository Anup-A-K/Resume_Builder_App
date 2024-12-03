package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app3.utils.ResumeDataManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EducationActivity extends BaseActivity {
    private TextInputLayout degreeLayout, institutionLayout, yearLayout, gradeLayout;
    private TextInputEditText degreeInput, institutionInput, yearInput, gradeInput;
    private Button previousButton, nextButton, addMoreButton;
    private ResumeDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_details);
        setActivityTitle("Education");

        dataManager = new ResumeDataManager(this);
        initializeViews();
        setupButtons();
    }

    private void initializeViews() {
        degreeLayout = findViewById(R.id.degreeLayout);
        institutionLayout = findViewById(R.id.institutionLayout);
        yearLayout = findViewById(R.id.yearLayout);
        gradeLayout = findViewById(R.id.gradeLayout);

        degreeInput = findViewById(R.id.degreeInput);
        institutionInput = findViewById(R.id.institutionInput);
        yearInput = findViewById(R.id.yearInput);
        gradeInput = findViewById(R.id.gradeInput);

        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        addMoreButton = findViewById(R.id.addMoreButton);
    }

    private void setupButtons() {
        previousButton.setOnClickListener(v -> finish());
        
        nextButton.setOnClickListener(v -> {
            if (validateInputs()) {
                saveEducationDetails();
                startActivity(new Intent(EducationActivity.this, ProjectsActivity.class));
            }
        });

        addMoreButton.setOnClickListener(v -> {
            if (validateInputs()) {
                saveEducationDetails();
                clearInputs();
                Toast.makeText(this, "Education details added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (TextUtils.isEmpty(degreeInput.getText())) {
            degreeLayout.setError("Degree is required");
            isValid = false;
        } else {
            degreeLayout.setError(null);
        }

        if (TextUtils.isEmpty(institutionInput.getText())) {
            institutionLayout.setError("Institution is required");
            isValid = false;
        } else {
            institutionLayout.setError(null);
        }

        if (TextUtils.isEmpty(yearInput.getText())) {
            yearLayout.setError("Year is required");
            isValid = false;
        } else {
            yearLayout.setError(null);
        }

        if (TextUtils.isEmpty(gradeInput.getText())) {
            gradeLayout.setError("Grade is required");
            isValid = false;
        } else {
            gradeLayout.setError(null);
        }

        return isValid;
    }

    private void saveEducationDetails() {
        dataManager.addEducation(
            degreeInput.getText().toString(),
            institutionInput.getText().toString(),
            yearInput.getText().toString(),
            gradeInput.getText().toString()
        );
    }

    private void clearInputs() {
        degreeInput.setText("");
        institutionInput.setText("");
        yearInput.setText("");
        gradeInput.setText("");
        degreeInput.requestFocus();
    }
} 