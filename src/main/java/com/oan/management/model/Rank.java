package com.oan.management.model;

import javax.persistence.*;

/**
 * Created by Oan on 19/02/2018.
 */
@Entity
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private int rankNumber;
    private String title;
    private String imageUrl;
    private String nextRankUrl;
    private int nextRankScore;

    public Rank() {
    }

    public Rank(User user, String title, int rankNumber) {
        this.user = user;
        this.title = title;
        this.rankNumber = rankNumber;
    }

    public int getNextRankScore() {
        return nextRankScore;
    }

    public void setNextRankScore(int nextRankScore) {
        this.nextRankScore = nextRankScore;
    }

    public String getNextRankUrl() {
        return nextRankUrl;
    }

    public void setNextRankUrl(String nextRankUrl) {
        this.nextRankUrl = nextRankUrl;
    }

    public int getRankNumber() {
        return rankNumber;
    }

    public void setRankNumber(int rankNumber) {
        this.rankNumber = rankNumber;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
