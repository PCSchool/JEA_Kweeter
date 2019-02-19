package services;

import entities.User;
import entities.Kweet;
import dao.KweetDAO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class KweetService{
    //@EJB
    KweetDAO kweetDAO;

    @PersistenceContext
    private EntityManager em;

    // -    constructor
    public KweetService(){
    }

    public void createKweet(Kweet kweet, User user){
        this.kweetDAO.createKweet(kweet, user);
    }

    public void deleteKweet(Kweet kweet, User user){
        this.kweetDAO.deleteKweet(kweet, user);
    }

    public List<Kweet> getAllReactions(Kweet kweet){

        return this.kweetDAO.getAllReactions(kweet);
    }

    public void createKweetReaction(Kweet kweet, Kweet reaction){
        kweet.addReaction(reaction);
    }

}