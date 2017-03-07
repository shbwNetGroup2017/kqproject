package net.bwjf.kpjk.bo;

import net.bwjf.kpjk.domain.entities.User;

public interface RegistService {
    public String zhuce(User user);
    public User login(User user);
    public boolean judgeUser(String userName);
}
