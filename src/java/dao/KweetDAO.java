package dao;

import entities.User;
import entities.Kweet;

import java.util.List;

public interface KweetDAO{
    void createKweet(Kweet kweet, Long id);

    void removeKweet(Long kweetId, Long id);

    List<Kweet> findByFilter(String message);

    void addReaction(Long id, Long kweetId, Kweet reaction);

    List<Kweet> getAllKweets(Long id);

    List<Kweet> getKweets(Long id, int amountOfKweets);
}