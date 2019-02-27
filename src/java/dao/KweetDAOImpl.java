package dao;

import entities.User;
import entities.Kweet;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class KweetDAOImpl implements KweetDAO {

    @PersistenceContext
    private EntityManager em;

    // empty constructor
    public KweetDAOImpl(){
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        //this.em = emf.createEntityManager();
    }


    @Override
    public void createKweet(Kweet kweet) {
        this.em.persist(kweet);
    }

    @Override
    public void deleteKweet(Kweet kweet) {
        this.em.remove(kweet);
    }

    @Override
    public List<Kweet> getAllReactions(User user) {
        Query query  = this.em.createQuery("SELECT k FROM Kweet k where k.creator = :id");
        return query.getResultList();
    }

    @Override
    public void addReaction(Kweet kweet, Kweet reaction) {

    }
}