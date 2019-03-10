package dao;

import entities.User;
import entities.Kweet;

import java.util.List;

public interface KweetDAO{
    void createKweet(Kweet kweet, Long id);

    void deleteKweet(Kweet kweet, Long id);

    List<Kweet> getAllReactions(Long id);

    void addReaction(Long id, Long kweetid, Kweet reaction);

    List<Kweet> getKweets(Long id, int amountOfKweets);
}