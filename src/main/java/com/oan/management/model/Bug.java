package com.oan.management.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Oan on 29/01/2018.
 */
@Entity
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String description;

    private Date date;

    private boolean fixed = false;

    public Bug() {
    }

    public Bug(User user, String description, Date date) {
        this.user = user;
        this.description = description;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
