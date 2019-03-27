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

    public boolean addReaction(Long id, Long kweetid, Kweet kweet){
        if(kweet.getMessage().length() > 160){
            return false;
        }
        this.kweetDAO.addReaction(id, kweetid, kweet);
        return true;
    }

    public List<Kweet> getLastTenKweets(Long id){
        return this.kweetDAO.getKweets(id, 10);
    }

    public List<Kweet> getAllKweets(Long id){
        return this.kweetDAO.getAllKweets(id);
    }

    public boolean createKweet(Kweet kweet, Long id){
        if(kweet.getMessage().length() > 160){
            return false;
        }
        this.kweetDAO.createKweet(kweet, id);
        return true;
    }

    public boolean removeKweet(Long kweetId, Long id){
        if(kweetId < -1| id < -1){
            return false;
        }
        this.kweetDAO.removeKweet(kweetId, id);
        return true;
    }

    public List<Kweet> findByFilterKweet(String message){
        return this.kweetDAO.findByFilter(message);
    }

}