package com.oan.management.model;

import javax.persistence.*;

/**
 * Created by Oan on 26/01/2018.
 */
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String start;
    private String end;
    private String backgroundColor;
    private String borderColor;
    private boolean editable;

    @ManyToOne
    private User user;

    public Event() {
    }

    public Event(String title, String description, String  start, String  end, User user, String backgroundColor, String borderColor, boolean editable) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.user = user;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
