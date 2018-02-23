package com.oan.management.service.rank;

import com.oan.management.model.Rank;
import com.oan.management.model.User;
import com.oan.management.repository.RankRepository;
import com.oan.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Oan on 19/02/2018.
 */
@Service
public class RankServiceImpl implements RankService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RankRepository rankRepository;

    @Autowired
    RankService rankService;

    @Override
    public Rank setRank(User user, String title, int rankNumber) {
        Rank rank = new Rank();
        rank.setUser(user);
        rank.setTitle(title);
        rank.setRankNumber(rankNumber);
        rank.setImageUrl("/img/ranks/rank"+rankNumber+".png");
        rank.setNextRankUrl("/img/ranks/rank"+rankNumber+1+".png");
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
        if (rank.getRankNumber() < 11) {
            rank.setNextRankUrl("/img/ranks/rank"+(rankNumber+1)+".png");
            rank.setNextRankScore(nextRankList.get(rank.getRankNumber()-1));
        }
        return rankRepository.save(rank);

    }

    public static List<Integer> nextRankList = Arrays.asList(10,21,31,51,71,91,121,301,501,701);

    @Override
    public Rank checkRank(User user) {
        Rank rank = rankRepository.findByUser(user);
        if (user.getTasksCompleted() >= 0 && user.getTasksCompleted() < 10) {
            updateRankByUser(user, "Noob", 1);
        } else if (user.getTasksCompleted() >= nextRankList.get(0) && user.getTasksCompleted() <= 20) {
            updateRankByUser(user, "Junior", 2);
        } else if (user.getTasksCompleted() >= nextRankList.get(1) && user.getTasksCompleted() <= 30){
            updateRankByUser(user, "Apprentice", 3);
        } else if (user.getTasksCompleted() >= nextRankList.get(2) && user.getTasksCompleted() <= 50) {
            updateRankByUser(user, "Motivated", 4);
        } else if (user.getTasksCompleted() >= nextRankList.get(3) && user.getTasksCompleted() <= 70) {
            updateRankByUser(user, "Notable", 5);
        } else if (user.getTasksCompleted() >= nextRankList.get(4) && user.getTasksCompleted() <= 90) {
            updateRankByUser(user, "Veteran", 6);
        } else if (user.getTasksCompleted() >= nextRankList.get(5) && user.getTasksCompleted() <= 120) {
            updateRankByUser(user, "Senior", 7);
        } else if (user.getTasksCompleted() >= nextRankList.get(6) && user.getTasksCompleted() <= 300) {
            updateRankByUser(user, "Elite", 8);
        } else if (user.getTasksCompleted() >= nextRankList.get(7) && user.getTasksCompleted() <= 500) {
            updateRankByUser(user, "Master", 9);
        } else if (user.getTasksCompleted() >= nextRankList.get(8) && user.getTasksCompleted() <= 700) {
            updateRankByUser(user, "Grand Master", 10);
        } else if (user.getTasksCompleted() >= nextRankList.get(9) && user.getTasksCompleted() <= 900) {
            updateRankByUser(user, "Legend Planner", 11);
        }
        return rank;
    }

    @Override
    public Rank getUserRank(User user) {
        Rank rank = rankRepository.findByUser(user);
        if (rank != null) {
            return rankService.checkRank(user);
        } else {
            return rankService.setRank(user, "Noob", 1);
        }
    }

}
