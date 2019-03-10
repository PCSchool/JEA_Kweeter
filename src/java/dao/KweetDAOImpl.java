package dao;

import entities.User;
import entities.Kweet;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class KweetDAOImpl implements KweetDAO {

    @PersistenceContext
    private EntityManager em;

    // empty constructor
    public KweetDAOImpl(){
    }


    //when adding an entity or removing it from an assocatiation you need to perform operation on both ends
    @Override
    public void createKweet(Kweet kweet, Long id) {
        User user = this.em.find(User.class, id);
        java.util.Date now =new Date();
        kweet.setCreateDate(now);
        kweet.setUser(user);
        //user.addKweet(kweet);
        em.persist(kweet);
    }

    @Override
    public void deleteKweet(Kweet kweet, Long id) {
        User user = this.em.find(User.class, id);
        user.removeKweet(kweet);
        this.em.merge(user);
        this.em.remove(kweet);
    }

    @Override
    public List<Kweet> getAllReactions(Long id) {
        Kweet kweet = this.em.find(Kweet.class, id);

        Query query  = this.em.createQuery("SELECT k FROM Kweet k where k.parent = :kweet");
        return query.getResultList();
    }

    @Override
    public void addReaction(Long id, Long kweetid, Kweet reaction) {
        User user = this.em.find(User.class, id);
        java.util.Date now =new Date();
        reaction.setCreateDate(now);
        reaction.setUser(user);

        Kweet parent = this.em.find(Kweet.class, kweetid);
        reaction.setParent(parent);
        //parent.addReaction(reaction);

        em.persist(reaction);
        //em.persist(parent);
    }

    @Override
    public List<Kweet> getKweets(Long id, int amountOfKweets) {
        User user = this.em.find(User.class, id);
        System.out.println(user);
        Query query  = this.em.createQuery("SELECT k FROM Kweet k where k.user = :user");
        return query.getResultList();
    }
}