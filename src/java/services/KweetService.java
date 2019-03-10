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

    public void createKweet(Kweet kweet, Long id){
        this.kweetDAO.createKweet(kweet, id);
    }

    public void deleteKweet(Kweet kweet, Long id){
        this.kweetDAO.deleteKweet(kweet, id);
    }

    public List<Kweet> getAllReactions(User user){

        return this.kweetDAO.getAllReactions(user.getId());
    }

    public List<Kweet> getKweets(Long id, int amount){
        return this.kweetDAO.getKweets(id, amount);
    }

    public void createKweetReaction(Kweet kweet, Kweet reaction){
        kweet.addReaction(reaction);
    }

}