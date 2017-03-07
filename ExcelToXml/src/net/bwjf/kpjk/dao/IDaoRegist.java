package net.bwjf.kpjk.dao;

import net.bwjf.kpjk.domain.entities.User;

public interface IDaoRegist {
     public String zhuce(User user);
     public User login(User user);
     public User judgeUser(String userName);
}
