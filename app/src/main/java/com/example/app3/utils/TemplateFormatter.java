package com.example.app3.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class TemplateFormatter {
    private static final float PAGE_WIDTH = 595f;
    private static final float PAGE_HEIGHT = 842f;
    private static final float MARGIN = 50f;

    public static void applyTemplate(Canvas canvas, String template, String content) {
        switch (template) {
            case "professional":
                applyProfessionalTemplate(canvas, content);
                break;
            case "creative":
                applyCreativeTemplate(canvas, content);
                break;
            case "minimal":
                applyMinimalTemplate(canvas, content);
                break;
        }
    }

    private static void applyProfessionalTemplate(Canvas canvas, String content) {
        // Header background
        Paint headerPaint = new Paint();
        headerPaint.setColor(Color.parseColor("#2196F3"));
        canvas.drawRect(0, 0, PAGE_WIDTH, 120, headerPaint);

        // Content
        Paint contentPaint = new Paint();
        contentPaint.setColor(Color.BLACK);
        contentPaint.setTextSize(12);
        contentPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Title style
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.WHITE);
        titlePaint.setTextSize(24);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        float y = 80;
        String[] lines = content.split("\n");
        boolean isFirstLine = true;

        for (String line : lines) {
            if (line.trim().endsWith("DETAILS") || line.trim().endsWith("EDUCATION") || 
                line.trim().endsWith("PROJECTS") || line.trim().endsWith("SKILLS") || 
                line.trim().endsWith("COMPETITIONS") || line.trim().endsWith("ACHIEVEMENTS")) {
                
                y += 40; // Add spacing before section
                canvas.drawText(line, MARGIN, y, titlePaint);
                y += 20;
            } else {
                if (isFirstLine) {
                    canvas.drawText(line, MARGIN, y, titlePaint);
                    isFirstLine = false;
                } else {
                    canvas.drawText(line, MARGIN, y, contentPaint);
                }
                y += contentPaint.descent() - contentPaint.ascent() + 8;
            }
        }
    }

    private static void applyCreativeTemplate(Canvas canvas, String content) {
        // Sidebar
        Paint sidebarPaint = new Paint();
        sidebarPaint.setColor(Color.parseColor("#FF4081"));
        canvas.drawRect(0, 0, 150, PAGE_HEIGHT, sidebarPaint);

        // Content
        Paint contentPaint = new Paint();
        contentPaint.setColor(Color.BLACK);
        contentPaint.setTextSize(12);

        // Title style
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.WHITE);
        titlePaint.setTextSize(16);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        float y = 50;
        float contentX = 170;
        String[] lines = content.split("\n");
        
        for (String line : lines) {
            if (line.trim().endsWith("DETAILS") || line.trim().endsWith("EDUCATION") || 
                line.trim().endsWith("PROJECTS") || line.trim().endsWith("SKILLS") || 
                line.trim().endsWith("COMPETITIONS") || line.trim().endsWith("ACHIEVEMENTS")) {
                
                y += 30;
                canvas.drawText(line, 20, y, titlePaint);
                y += 20;
            } else {
                canvas.drawText(line, contentX, y, contentPaint);
                y += contentPaint.descent() - contentPaint.ascent() + 8;
            }
        }
    }

    private static void applyMinimalTemplate(Canvas canvas, String content) {
        // Content
        Paint contentPaint = new Paint();
        contentPaint.setColor(Color.BLACK);
        contentPaint.setTextSize(12);

        // Title style
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(18);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Divider
        Paint dividerPaint = new Paint();
        dividerPaint.setColor(Color.parseColor("#BDBDBD"));
        dividerPaint.setStrokeWidth(1);

        float y = 50;
        String[] lines = content.split("\n");
        
        for (String line : lines) {
            if (line.trim().endsWith("DETAILS") || line.trim().endsWith("EDUCATION") || 
                line.trim().endsWith("PROJECTS") || line.trim().endsWith("SKILLS") || 
                line.trim().endsWith("COMPETITIONS") || line.trim().endsWith("ACHIEVEMENTS")) {
                
                y += 30;
                canvas.drawText(line, MARGIN, y, titlePaint);
                y += 10;
                canvas.drawLine(MARGIN, y, PAGE_WIDTH - MARGIN, y, dividerPaint);
                y += 20;
            } else {
                canvas.drawText(line, MARGIN, y, contentPaint);
                y += contentPaint.descent() - contentPaint.ascent() + 8;
            }
        }
    }
} 