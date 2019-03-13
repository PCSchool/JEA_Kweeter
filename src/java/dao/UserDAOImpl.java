package dao;

import entities.Kweet;
import entities.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * implements from interface UserDAO
 */
@Stateless
public class UserDAOImpl implements UserDAO{

    @PersistenceContext
    private EntityManager em;

    /**
     * empty constructor
     */
    public UserDAOImpl(){
    }

    @Override
    public boolean removeUser(User user) {
        this.em.remove(user); return true;
    }

    @Override
    public boolean createUser(User user) {
        this.em.persist(user); return true;
    }

    @Override
    public User findUserByName(String name){
        Query q = em.createNamedQuery("User.findByUsername", User.class);
        q.setParameter("username", name);
        return (User) q.getSingleResult();
    }

    @Override
    public User findUserById(long id){
        return this.em.find(User.class, id);
    }

    @Override
    public void addFollowing(Long user, Long following) {
        this.em.createQuery("SELECT u FROM User u where u.id = :user");
        User u1 = this.em.find(User.class, user);

        this.em.createQuery("SELECT u FROM User u where u.id = :following");
        User u2 = this.em.find(User.class, following);

        u2.getFollowings().add(u1);
        u1.getFollowers().add(u2);
        this.em.merge(u2);
        this.em.merge(u1);
    }

    @Override
    public void addFollower(Long user, Long follower) {
        this.em.createQuery("SELECT u FROM User u where u.id = :user");
        User u1 = this.em.find(User.class, user);

        this.em.createQuery("SELECT u FROM User u where u.id = :following");
        User u2 = this.em.find(User.class, follower);

        u2.getFollowings().add(u1);
        u1.getFollowers().add(u2);
        this.em.merge(u2);
        this.em.merge(u1);
    }

    @Override
    public boolean updateUser(User user) {
        Query q = em.createNamedQuery("User.update", User.class);
        q.setParameter("name", user.getName());
        q.setParameter("location", user.getLocation());
        q.setParameter("biography", user.getBiography());
        this.em.merge(user);
        return true;
    }

    @Override
    public void removeFollower(Long user, Long follower) {
        //DELETE FROM User WHERE
        this.em.merge(user);
    }

    @Override
    public void removeFollowing(Long id, Long following) {
        //int count = em.createQuery("DELETE FROM user_following WHERE 'following_id' = " + following + " AND 'follower_id' =  " + " id ").executeUpdate();
        //System.out.println("UserDAO removeFollowing --> " + count);
    }

    @Override
    public List<User> getAllUsers(){
        Query query = this.em.createNamedQuery("User.getAll");
        return query.getResultList();
    }

    @Override
    public List<Kweet> getAllKweets(Long id) {
        Query query = this.em.createNamedQuery("User.getAll");
        return query.getResultList();
    }

    @Override
    public List<User> getAllFollowers(Long id) {
        Query query = this.em.createNamedQuery("User.getAllFollowers");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<User> getAllFollowing(Long id)
    {
        Query query = this.em.createNamedQuery("User.getAllFollowings");
        query.setParameter("id", id);
        return query.getResultList();
    }
}