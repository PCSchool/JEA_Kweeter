package dao;

import entities.Kweet;
import entities.User;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.*;
import java.util.List;

public class UserDAOImpl implements UserDAO{

    @PersistenceContext
    EntityManager em;

    //  empty constructor
    public UserDAOImpl(){

    }

    @Override
    public void removeUser(User user) {
        this.em.remove(user);
    }

    @Override
    public void createUser(User user) {
        this.em.persist(user);
    }

    @Override
    public User findUserByName(String name){
        this.em.createQuery("SELECT u FROM User u where u.name = :name");
        return this.em.find(User.class, name);
    }

    @Override
    public User findUserById(long id){
        this.em.createQuery("SELECT u FROM User u where u.id = :id");
        return this.em.find(User.class, id);
    }

    @Override
    public void addFollowing(User user, User following) {

    }

    @Override
    public void addFollower(User user, User follower) {

    }

    @Override
    public void updateUser(User user) {
        this.em.merge(user);
    }


    @Override
    public void removeFollower(User user, User follower) {
        this.em.merge(user);
    }

    @Override
    public void removeFollowing(User user, User following) {
        //todo check if removeFollower && removeFollowing methods are needed in DAO
        this.em.merge(user);
    }

    @Override
    public List<User> getAllUsers(){
        Query query = this.em.createNamedQuery("User.getAll");
        return query.getResultList();
    }

    @Override
    public List<Kweet> getAllKweets(User user) {
        Query query = this.em.createNamedQuery("User.getAll");
        return query.getResultList();
    }

    @Override
    public List<User> getAllFollowers(User user) {
        //todo UserDAOImpl --> check from which table u get the followers
        Query query = this.em.createNamedQuery("User.getFollowers");
        return query.getResultList();
    }

    @Override
    public List<User> getAllFollowing(User user)
    {
        Query query = this.em.createNamedQuery("User.getFollowing");
        return query.getResultList();
    }
}