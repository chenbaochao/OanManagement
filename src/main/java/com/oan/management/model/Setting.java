package com.oan.management.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Oan on 22/02/2018.
 */

@Entity
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String adminEmail;
    private boolean allowRegistrations;

    public Setting() {
    }

    public Setting(String title, String adminEmail, boolean allowRegistrations) {
        this.title = title;
        this.adminEmail = adminEmail;
        this.allowRegistrations = allowRegistrations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public boolean isAllowRegistrations() {
        return allowRegistrations;
    }

    public void setAllowRegistrations(boolean allowRegistrations) {
        this.allowRegistrations = allowRegistrations;
    }
}

