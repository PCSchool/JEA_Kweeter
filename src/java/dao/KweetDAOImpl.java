package dao;

import entities.User;
import entities.Kweet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class KweetDAOImpl implements KweetDAO {

    @PersistenceContext
    private EntityManager em;

    // empty constructor
    public KweetDAOImpl(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        this.em = emf.createEntityManager();
    }

    @Override
    public void createKweet(Kweet kweet, User user) {
        user.addKweet(kweet);
    }

    @Override
    public void deleteKweet(Kweet kweet, User user) {
        user.removeKweet(kweet);
    }

    @Override
    public List<Kweet> getAllReactions(Kweet kweet) {
        return kweet.getReactions();
    }

    @Override
    public void addReaction(Kweet kweet, Kweet reaction, User user) {
        kweet.addReaction(reaction);
    }
}