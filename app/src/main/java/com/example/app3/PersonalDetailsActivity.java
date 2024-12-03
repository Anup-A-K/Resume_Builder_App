package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.app3.utils.ResumeDataManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PersonalDetailsActivity extends BaseActivity {
    private TextInputLayout nameLayout, emailLayout, phoneLayout, addressLayout;
    private TextInputEditText nameInput, emailInput, phoneInput, addressInput;
    private Button previousButton, nextButton;
    private ResumeDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_details);
        setActivityTitle("Personal Details");

        dataManager = new ResumeDataManager(this);
        initializeViews();
        setupButtons();
        loadSavedData();
    }

    private void initializeViews() {
        nameLayout = findViewById(R.id.nameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        phoneLayout = findViewById(R.id.phoneLayout);
        addressLayout = findViewById(R.id.addressLayout);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);
        addressInput = findViewById(R.id.addressInput);

        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
    }

    private void setupButtons() {
        previousButton.setOnClickListener(v -> finish());
        
        nextButton.setOnClickListener(v -> {
            if (validateInputs()) {
                savePersonalDetails();
                startActivity(new Intent(PersonalDetailsActivity.this, EducationActivity.class));
            }
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (TextUtils.isEmpty(nameInput.getText())) {
            nameLayout.setError("Name is required");
            isValid = false;
        } else {
            nameLayout.setError(null);
        }

        String email = emailInput.getText().toString();
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Valid email is required");
            isValid = false;
        } else {
            emailLayout.setError(null);
        }

        if (TextUtils.isEmpty(phoneInput.getText())) {
            phoneLayout.setError("Phone number is required");
            isValid = false;
        } else {
            phoneLayout.setError(null);
        }

        if (TextUtils.isEmpty(addressInput.getText())) {
            addressLayout.setError("Address is required");
            isValid = false;
        } else {
            addressLayout.setError(null);
        }

        return isValid;
    }

    private void savePersonalDetails() {
        dataManager.savePersonalDetails(
            nameInput.getText().toString(),
            emailInput.getText().toString(),
            phoneInput.getText().toString(),
            addressInput.getText().toString()
        );
        Toast.makeText(this, "Personal details saved", Toast.LENGTH_SHORT).show();
    }

    private void loadSavedData() {
        nameInput.setText(dataManager.getPersonalDetail("name"));
        emailInput.setText(dataManager.getPersonalDetail("email"));
        phoneInput.setText(dataManager.getPersonalDetail("phone"));
        addressInput.setText(dataManager.getPersonalDetail("address"));
    }
} 