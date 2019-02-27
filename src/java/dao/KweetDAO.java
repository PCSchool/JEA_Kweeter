package dao;

import entities.User;
import entities.Kweet;

import java.util.List;

public interface KweetDAO{
    void createKweet(Kweet kweet);

    void deleteKweet(Kweet kweet);

    List<Kweet> getAllReactions(User user);

    void addReaction(Kweet kweet, Kweet reaction);
}