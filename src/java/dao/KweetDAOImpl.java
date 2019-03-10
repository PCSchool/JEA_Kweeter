package dao;

import entities.User;
import entities.Kweet;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        kweet.setUser(user);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String datestring = dateFormat.format(new Date());
        try {
            kweet.setCreateDate(dateFormat.parse(datestring));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        em.persist(kweet);

        user.addKweet(kweet);
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
        User user = this.em.find(User.class, id);

        Query query  = this.em.createQuery("SELECT k FROM Kweet k where k.user = :user ORDER BY k.createDate");
        return query.getResultList();


        /*
        List<Kweet> kweets = getSession().createQuery("SELECT k FROM Kweet k where k.user = :user "
         */
    }

    @Override
    public void addReaction(Kweet reaction) {

        this.em.persist(reaction);
    }

    @Override
    public List<Kweet> getKweets(Long id, int amountOfKweets) {
        User user = this.em.find(User.class, id);

        Query query  = this.em.createQuery("SELECT k FROM Kweet k where k.user = :user");
        return query.getResultList();
    }
}