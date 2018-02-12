package com.oan.management.model;

import javax.persistence.*;

/**
 * Created by Oan on 9/02/2018.
 */

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Long amount;

    @ManyToOne
    private Budget budget;

    public Expense(Budget budget, String description, Long amount) {
        this.budget = budget;
        this.description = description;
        this.amount = amount;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}