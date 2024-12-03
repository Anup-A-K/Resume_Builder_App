package com.example.app3.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResumeDataManager {
    private static final String PREF_NAME = "ResumeData";
    private final SharedPreferences preferences;

    // Keys for different sections
    private static final String KEY_PERSONAL = "personal_";
    private static final String KEY_EDUCATION = "education_";
    private static final String KEY_PROJECTS = "projects_";
    private static final String KEY_SKILLS = "skills_";
    private static final String KEY_COMPETITIONS = "competitions_";
    private static final String KEY_ACHIEVEMENTS = "achievements_";

    public ResumeDataManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Personal Details
    public void savePersonalDetails(String name, String email, String phone, String address) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_PERSONAL + "name", name);
        editor.putString(KEY_PERSONAL + "email", email);
        editor.putString(KEY_PERSONAL + "phone", phone);
        editor.putString(KEY_PERSONAL + "address", address);
        editor.apply();
    }

    public String getPersonalDetail(String field) {
        return preferences.getString(KEY_PERSONAL + field, "");
    }

    // Education Details
    public void addEducation(String degree, String institution, String year, String grade) {
        Set<String> educationSet = preferences.getStringSet(KEY_EDUCATION + "list", new HashSet<>());
        Set<String> newSet = new HashSet<>(educationSet);
        newSet.add(degree + "|" + institution + "|" + year + "|" + grade);
        preferences.edit().putStringSet(KEY_EDUCATION + "list", newSet).apply();
    }

    public List<String[]> getEducationList() {
        Set<String> educationSet = preferences.getStringSet(KEY_EDUCATION + "list", new HashSet<>());
        List<String[]> educationList = new ArrayList<>();
        for (String education : educationSet) {
            educationList.add(education.split("\\|"));
        }
        return educationList;
    }

    // Projects
    public void addProject(String name, String description, String technologies) {
        Set<String> projectsSet = preferences.getStringSet(KEY_PROJECTS + "list", new HashSet<>());
        Set<String> newSet = new HashSet<>(projectsSet);
        newSet.add(name + "|" + description + "|" + technologies);
        preferences.edit().putStringSet(KEY_PROJECTS + "list", newSet).apply();
    }

    public List<String[]> getProjectsList() {
        Set<String> projectsSet = preferences.getStringSet(KEY_PROJECTS + "list", new HashSet<>());
        List<String[]> projectsList = new ArrayList<>();
        for (String project : projectsSet) {
            projectsList.add(project.split("\\|"));
        }
        return projectsList;
    }

    // Skills
    public void addSkill(String skillName, String proficiency) {
        Set<String> skillsSet = preferences.getStringSet(KEY_SKILLS + "list", new HashSet<>());
        Set<String> newSet = new HashSet<>(skillsSet);
        newSet.add(skillName + "|" + proficiency);
        preferences.edit().putStringSet(KEY_SKILLS + "list", newSet).apply();
    }

    public List<String[]> getSkillsList() {
        Set<String> skillsSet = preferences.getStringSet(KEY_SKILLS + "list", new HashSet<>());
        List<String[]> skillsList = new ArrayList<>();
        for (String skill : skillsSet) {
            skillsList.add(skill.split("\\|"));
        }
        return skillsList;
    }

    // Competitions
    public void addCompetition(String name, String organizer, String year, String achievement) {
        Set<String> competitionsSet = preferences.getStringSet(KEY_COMPETITIONS + "list", new HashSet<>());
        Set<String> newSet = new HashSet<>(competitionsSet);
        newSet.add(name + "|" + organizer + "|" + year + "|" + achievement);
        preferences.edit().putStringSet(KEY_COMPETITIONS + "list", newSet).apply();
    }

    public List<String[]> getCompetitionsList() {
        Set<String> competitionsSet = preferences.getStringSet(KEY_COMPETITIONS + "list", new HashSet<>());
        List<String[]> competitionsList = new ArrayList<>();
        for (String competition : competitionsSet) {
            competitionsList.add(competition.split("\\|"));
        }
        return competitionsList;
    }

    // Achievements
    public void addAchievement(String title, String description, String year) {
        Set<String> achievementsSet = preferences.getStringSet(KEY_ACHIEVEMENTS + "list", new HashSet<>());
        Set<String> newSet = new HashSet<>(achievementsSet);
        newSet.add(title + "|" + description + "|" + year);
        preferences.edit().putStringSet(KEY_ACHIEVEMENTS + "list", newSet).apply();
    }

    public List<String[]> getAchievementsList() {
        Set<String> achievementsSet = preferences.getStringSet(KEY_ACHIEVEMENTS + "list", new HashSet<>());
        List<String[]> achievementsList = new ArrayList<>();
        for (String achievement : achievementsSet) {
            achievementsList.add(achievement.split("\\|"));
        }
        return achievementsList;
    }

    public void clearAllData() {
        preferences.edit().clear().apply();
    }

    public String getFormattedResume() {
        StringBuilder resume = new StringBuilder();
        
        // Personal Details
        resume.append("PERSONAL DETAILS\n\n");
        resume.append("Name: ").append(getPersonalDetail("name")).append("\n");
        resume.append("Email: ").append(getPersonalDetail("email")).append("\n");
        resume.append("Phone: ").append(getPersonalDetail("phone")).append("\n");
        resume.append("Address: ").append(getPersonalDetail("address")).append("\n\n");

        // Education
        resume.append("EDUCATION\n\n");
        for (String[] education : getEducationList()) {
            resume.append("Degree: ").append(education[0]).append("\n");
            resume.append("Institution: ").append(education[1]).append("\n");
            resume.append("Year: ").append(education[2]).append("\n");
            resume.append("Grade: ").append(education[3]).append("\n\n");
        }

        // Projects
        resume.append("PROJECTS\n\n");
        for (String[] project : getProjectsList()) {
            resume.append("Name: ").append(project[0]).append("\n");
            resume.append("Description: ").append(project[1]).append("\n");
            resume.append("Technologies: ").append(project[2]).append("\n\n");
        }

        // Skills
        resume.append("SKILLS\n\n");
        for (String[] skill : getSkillsList()) {
            resume.append("Skill: ").append(skill[0]).append("\n");
            resume.append("Proficiency: ").append(skill[1]).append("\n\n");
        }

        // Competitions
        resume.append("COMPETITIONS\n\n");
        for (String[] competition : getCompetitionsList()) {
            resume.append("Name: ").append(competition[0]).append("\n");
            resume.append("Organizer: ").append(competition[1]).append("\n");
            resume.append("Year: ").append(competition[2]).append("\n");
            resume.append("Achievement: ").append(competition[3]).append("\n\n");
        }

        // Achievements
        resume.append("ACHIEVEMENTS\n\n");
        for (String[] achievement : getAchievementsList()) {
            resume.append("Title: ").append(achievement[0]).append("\n");
            resume.append("Description: ").append(achievement[1]).append("\n");
            resume.append("Year: ").append(achievement[2]).append("\n\n");
        }

        return resume.toString();
    }

    public void clearPersonalDetails() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_PERSONAL + "name");
        editor.remove(KEY_PERSONAL + "email");
        editor.remove(KEY_PERSONAL + "phone");
        editor.remove(KEY_PERSONAL + "address");
        editor.apply();
    }

    public void clearEducation() {
        preferences.edit().remove(KEY_EDUCATION + "list").apply();
    }

    public void clearProjects() {
        preferences.edit().remove(KEY_PROJECTS + "list").apply();
    }

    public void clearSkills() {
        preferences.edit().remove(KEY_SKILLS + "list").apply();
    }

    public void clearCompetitions() {
        preferences.edit().remove(KEY_COMPETITIONS + "list").apply();
    }

    public void clearAchievements() {
        preferences.edit().remove(KEY_ACHIEVEMENTS + "list").apply();
    }
} 