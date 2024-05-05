package com.example.task41p;

import java.util.Date;

public class Task {
    private String id;
    private String title;
    private String description;
    private String dueDate; // Added for tracking the due date
    private boolean isExpanded; // To track whether the details are visible

    // Constructor updated to include due date
    public Task(String id, String title, String description, String dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isExpanded = false;
    }
    public Task() {

    }

    // Getters and setters
    public String getId() {
        return id;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isExpanded() { return isExpanded; }
    public void setId(String id) {
        this.id = id;
    }
    public void setExpanded(boolean expanded) { isExpanded = expanded; }
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

}
