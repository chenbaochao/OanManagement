package com.oan.management.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Oan on 30/01/2018.
 */

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double budgetAmount;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Income> incomes;

    @OneToMany
    private List<Expense> expenses;

    public Budget() {
    }

    public Budget(String title, Double budgetAmount, User user) {
        this.title = title;
        this.budgetAmount = budgetAmount;
        this.user = user;
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

    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
