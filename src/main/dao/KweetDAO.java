package dao;

import entities.User;
import entities.Kweet;

import java.util.List;

public interface KweetDAO{
    void createKweet(Kweet kweet, User user);

    void deleteKweet(Kweet kweet, User user);

    List<Kweet> getAllReactions(Kweet kweet);

    void addReaction(Kweet kweet, Kweet reaction, User user);
}