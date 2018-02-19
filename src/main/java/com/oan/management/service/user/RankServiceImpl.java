package com.oan.management.service.user;

import com.oan.management.model.Rank;
import com.oan.management.model.User;
import com.oan.management.repository.RankRepository;
import com.oan.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Oan on 19/02/2018.
 */
@Service
public class RankServiceImpl implements RankService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RankRepository rankRepository;

    @Override
    public Rank setRank(User user, String title, int rankNumber) {
        Rank rank = new Rank();
        rank.setUser(user);
        rank.setTitle(title);
        rank.setRankNumber(rankNumber);
        rank.setImageUrl("/img/ranks/rank"+rankNumber+".png");
        return rankRepository.save(rank);
    }

    @Override
    public Rank findByUser(User user) {
        return rankRepository.findByUser(user);
    }

    @Override
    public Rank updateRankByUser(User user, String title, int rankNumber) {
        Rank rank = rankRepository.findByUser(user);
        rank.setId(rank.getId());
        rank.setTitle(title);
        rank.setRankNumber(rankNumber);
        rank.setImageUrl("/img/ranks/rank"+rankNumber+".png");
        return rankRepository.save(rank);

    }

    @Override
    public Rank checkRank(User user) {
        Rank rank = rankRepository.findByUser(user);
        if (user.getTasksCompleted() >= 10 && user.getTasksCompleted() <= 20) {
            updateRankByUser(user, "Junior", 2);
        } else if (user.getTasksCompleted() >= 21 && user.getTasksCompleted() <= 30){
            updateRankByUser(user, "Apprentice", 3);
        } else if (user.getTasksCompleted() >= 31 && user.getTasksCompleted() <= 50) {
            updateRankByUser(user, "Motivated", 4);
        } else if (user.getTasksCompleted() >= 51 && user.getTasksCompleted() <= 70) {
            updateRankByUser(user, "Notable", 5);
        } else if (user.getTasksCompleted() >= 71 && user.getTasksCompleted() <= 90) {
            updateRankByUser(user, "Veteran", 6);
        } else if (user.getTasksCompleted() >= 91 && user.getTasksCompleted() <= 120) {
            updateRankByUser(user, "Senior", 7);
        } else if (user.getTasksCompleted() >= 121 && user.getTasksCompleted() <= 300) {
            updateRankByUser(user, "Elite", 8);
        } else if (user.getTasksCompleted() >= 301 && user.getTasksCompleted() <= 500) {
            updateRankByUser(user, "Master", 9);
        } else if (user.getTasksCompleted() >= 501 && user.getTasksCompleted() <= 700) {
            updateRankByUser(user, "Grand Master", 10);
        } else if (user.getTasksCompleted() >= 701 && user.getTasksCompleted() <= 900) {
            updateRankByUser(user, "Legend Planner", 11);
        }
        return rank;
    }









    /*@Override
    public User setRank(User user, int rank) {
        if (rank > 0 && rank <= 11) {
            user.setRank("/img/ranks/rank"+rank+".png");
            return userRepository.save(user);
        } else {
            user.setRank("/img/ranks/rank0.png");
            return userRepository.save(user);
        }

    }

    @Override
    public String automateSetRank(User user) {
        if (user.getTasksCompleted() >= 10 && user.getTasksCompleted() <= 20) {
            setRank(user, 1);
        } else if (user.getTasksCompleted() >= 21 && user.getTasksCompleted() <= 30){
            setRank(user, 2);
        } else if (user.getTasksCompleted() >= 31 && user.getTasksCompleted() <= 40) {
            setRank(user, 3);
        }
        return "success";
    }*/
}
