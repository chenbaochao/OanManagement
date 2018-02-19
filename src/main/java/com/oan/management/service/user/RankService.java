package com.oan.management.service.user;

import com.oan.management.model.Rank;
import com.oan.management.model.User;

/**
 * Created by Oan on 19/02/2018.
 */
public interface RankService {
    Rank setRank(User user, String title, int rankNumber);
    Rank findByUser(User user);
    Rank updateRankByUser(User user, String title, int rankNumber);
    Rank checkRank(User user);
}
