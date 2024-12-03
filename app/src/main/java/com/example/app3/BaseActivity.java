package com.example.app3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout drawerLayout;
    protected Toolbar toolbar;
    protected FrameLayout contentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main_with_nav);
        
        drawerLayout = findViewById(R.id.drawer_layout);
        contentFrame = findViewById(R.id.content_frame);
        toolbar = findViewById(R.id.toolbar);
        
        setupNavigationDrawer();
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, contentFrame, false);
        contentFrame.removeAllViews();
        contentFrame.addView(view);
    }

    private void setupNavigationDrawer() {
        setSupportActionBar(toolbar);
        
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        int itemId = item.getItemId();

        if (itemId == R.id.nav_personal) {
            intent = new Intent(this, PersonalDetailsActivity.class);
        } else if (itemId == R.id.nav_education) {
            intent = new Intent(this, EducationActivity.class);
        } else if (itemId == R.id.nav_projects) {
            intent = new Intent(this, ProjectsActivity.class);
        } else if (itemId == R.id.nav_skills) {
            intent = new Intent(this, SkillsActivity.class);
        } else if (itemId == R.id.nav_competitions) {
            intent = new Intent(this, CompetitionsActivity.class);
        } else if (itemId == R.id.nav_achievements) {
            intent = new Intent(this, AchievementsActivity.class);
        } else if (itemId == R.id.nav_template) {
            intent = new Intent(this, TemplateSelectionActivity.class);
        } else if (itemId == R.id.nav_download) {
            SharedPreferences prefs = getSharedPreferences("ResumePrefs", MODE_PRIVATE);
            String selectedTemplate = prefs.getString("selected_template", null);
            
            if (selectedTemplate == null) {
                Toast.makeText(this, "Please select a template first", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, TemplateSelectionActivity.class);
            } else {
                intent = new Intent(this, DownloadResumeActivity.class);
            }
        }

        if (intent != null && !this.getClass().equals(intent.getComponent().getClassName())) {
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected void setActivityTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
} 