package com.example.app3;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.app3.utils.ResumeDataManager;
import com.example.app3.utils.TemplateFormatter;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DownloadResumeActivity extends BaseActivity {
    private static final int PERMISSION_REQUEST_CODE = 123;
    private TextView previewText;
    private Button previousButton, downloadPdfButton, resetButton;
    private ResumeDataManager dataManager;

    private final String[] sections = {
        "Personal Details",
        "Education",
        "Projects",
        "Skills",
        "Competitions",
        "Achievements"
    };

    private final boolean[] selectedSections = new boolean[sections.length];
    private final List<String> sectionsToDelete = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_resume);
        setActivityTitle("Download Resume");

        dataManager = new ResumeDataManager(this);
        initializeViews();
        setupButtons();
        loadResumePreview();
    }

    private void initializeViews() {
        previewText = findViewById(R.id.previewText);
        previousButton = findViewById(R.id.previousButton);
        downloadPdfButton = findViewById(R.id.downloadPdfButton);
        resetButton = findViewById(R.id.resetButton);
    }

    private void setupButtons() {
        previousButton.setOnClickListener(v -> {
            startActivity(new Intent(this, TemplateSelectionActivity.class));
            finish();
        });
        
        downloadPdfButton.setOnClickListener(v -> {
            if (checkPermission()) {
                generateAndDownloadPDF();
            } else {
                requestPermission();
            }
        });

        resetButton.setOnClickListener(v -> showSectionSelectionDialog());
    }

    private void showSectionSelectionDialog() {
        // Reset selections
        for (int i = 0; i < selectedSections.length; i++) {
            selectedSections[i] = false;
        }
        sectionsToDelete.clear();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select sections to remove")
            .setMultiChoiceItems(sections, selectedSections, (dialog, which, isChecked) -> {
                selectedSections[which] = isChecked;
                String section = sections[which];
                if (isChecked) {
                    sectionsToDelete.add(section);
                } else {
                    sectionsToDelete.remove(section);
                }
            })
            .setPositiveButton("Remove Selected", (dialog, which) -> {
                if (sectionsToDelete.isEmpty()) {
                    Toast.makeText(this, "No sections selected", Toast.LENGTH_SHORT).show();
                    return;
                }
                showConfirmationDialog();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void showConfirmationDialog() {
        StringBuilder message = new StringBuilder("Are you sure you want to remove these sections?\n\n");
        for (String section : sectionsToDelete) {
            message.append("• ").append(section).append("\n");
        }

        new AlertDialog.Builder(this)
            .setTitle("Confirm Removal")
            .setMessage(message.toString())
            .setPositiveButton("Remove", (dialog, which) -> {
                deleteSelectedSections();
                dialog.dismiss();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void deleteSelectedSections() {
        for (String section : sectionsToDelete) {
            switch (section) {
                case "Personal Details":
                    dataManager.clearPersonalDetails();
                    break;
                case "Education":
                    dataManager.clearEducation();
                    break;
                case "Projects":
                    dataManager.clearProjects();
                    break;
                case "Skills":
                    dataManager.clearSkills();
                    break;
                case "Competitions":
                    dataManager.clearCompetitions();
                    break;
                case "Achievements":
                    dataManager.clearAchievements();
                    break;
            }
        }

        Toast.makeText(this, "Selected sections have been removed", Toast.LENGTH_SHORT).show();
        loadResumePreview(); // Refresh the preview
    }

    private void loadResumePreview() {
        String resumeContent = dataManager.getFormattedResume();
        previewText.setText(resumeContent);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generateAndDownloadPDF();
            } else {
                Toast.makeText(this, "Permission denied. Cannot save PDF.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void generateAndDownloadPDF() {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        
        // Get selected template
        SharedPreferences prefs = getSharedPreferences("ResumePrefs", MODE_PRIVATE);
        String selectedTemplate = prefs.getString("selected_template", "professional");
        
        // Apply template formatting
        TemplateFormatter.applyTemplate(canvas, selectedTemplate, dataManager.getFormattedResume());

        document.finishPage(page);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "Resume_" + timeStamp + ".pdf";
        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        try {
            document.writeTo(new FileOutputStream(pdfFile));
            document.close();

            Uri pdfUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "PDF saved to Downloads folder: " + fileName, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
} 