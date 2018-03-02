package com.oan.management.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by Oan on 18/01/2018.
 */

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    private User user;

    @NotNull
    private Date targetDate;

    private boolean completed;

    private boolean approved;

    @ManyToOne
    private User creator;

    public Task() {
    }

    public Task(User user,String description, Date targetDate, boolean completed) {
        this.user = user;
        this.targetDate = targetDate;
        this.description = description;
        this.completed = completed;
    }

    public Task(User user,String description, Date targetDate, boolean completed, User creator, boolean approved) {
        this.user = user;
        this.targetDate = targetDate;
        this.description = description;
        this.completed = completed;
        this.creator = creator;
        this.approved = approved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
