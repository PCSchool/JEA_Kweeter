package services;

import entities.User;
import entities.Kweet;
import dao.KweetDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class KweetService{
    @EJB
    KweetDAO kweetDAO;

    @PersistenceContext
    private EntityManager em;

    // -    constructor
    public KweetService(){
    }

    public void addReaction(Long id, Long kweetid, Kweet kweet){
        this.kweetDAO.addReaction(id, kweetid, kweet);
    }

    public List<Kweet> getLastTenKweets(Long id){
        return this.kweetDAO.getKweets(id, 10);
    }

    public List<Kweet> getAllKweets(Long id){
        return this.kweetDAO.getAllKweets(id);
    }

    public void createKweet(Kweet kweet, Long id){
        this.kweetDAO.createKweet(kweet, id);
    }

    public void removeKweet(Long kweetId, Long id){
        this.kweetDAO.removeKweet(kweetId, id);
    }

    public List<Kweet> findByFilterKweet(String message){
        return this.kweetDAO.findByFilter(message);
    }

    public List<Kweet> getKweets(Long id, int amount){
        return this.kweetDAO.getKweets(id, amount);
    }

}