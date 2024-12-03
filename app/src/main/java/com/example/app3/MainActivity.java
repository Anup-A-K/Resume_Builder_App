package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActivityTitle("Resume Builder");

        Button createResumeButton = findViewById(R.id.createResumeButton);
        createResumeButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PersonalDetailsActivity.class));
        });
    }
}