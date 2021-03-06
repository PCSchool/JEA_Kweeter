package dao;

import entities.User;
import entities.Kweet;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
        kweet.setCreateDate(new Date());
        kweet.setUser(user);
        user.addKweet(kweet);
        em.persist(kweet);
    }

    @Override
    public void removeKweet(Long kweetId, Long id) {
        Kweet kweet = this.em.find(Kweet.class, kweetId);
        User user = this.em.find(User.class, id);
        user.removeKweet(kweet);
        this.em.remove(kweet);
    }

    @Override
    public List<Kweet> findByFilter(String message) {
        Query query  = this.em.createQuery("SELECT k FROM Kweet k where k.message LIKE :message ORDER BY k.createDate ASC ");
        return query.getResultList();
    }

    @Override
    public void addReaction(Long id, Long kweetId, Kweet reaction) {
        User user = this.em.find(User.class, id);
        reaction.setCreateDate(new Date());
        reaction.setUser(user);

        Kweet parent = this.em.find(Kweet.class, kweetId);
        reaction.setParent(parent);
        parent.addReaction(reaction);

        em.persist(reaction);
        em.merge(parent);
    }

    @Override
    public List<Kweet> getAllKweets(Long id){
        User user = this.em.find(User.class, id);
        Query query  = this.em.createQuery("SELECT k FROM Kweet k where k.user = :user ORDER BY k.createDate DESC");
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public List<Kweet> getKweets(Long id, int amountOfKweets) {
        User user = this.em.find(User.class, id);
        Query query  = this.em.createQuery("SELECT k FROM Kweet k where k.user = :user ORDER BY k.createDate DESC");
        query.setParameter("user", user);
        List<Kweet> kweetList = query.getResultList();
        if(kweetList.size() > 10){
            return query.getResultList().subList(0, 10);
        }
        return kweetList;
    }


}