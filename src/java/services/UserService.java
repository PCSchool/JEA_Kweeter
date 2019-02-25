package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;

import dao.UserDAO;
import entities.Kweet;
import entities.User;

@Stateless
public class UserService{

    //todo userservice <--> userDAO
    //@EJB
    //private UserDAO userDAO;

    @PersistenceContext
    EntityManager em;

    // -    constructor
    public UserService(){
    }

    public void createUser(User user){
        this.em.persist(user);
    }

    public void removeUser(User user){
        this.em.remove(user);
    }

    public void updateUser(User user){
        this.em.merge(user);
    }

    /*public User findUserByName(String name){
        Query q = this.em.createNamedQuery("User.byName");
        q.setParameter("name", name);
        return  (User)q.getSingleResult();
    }*/

    public User findUserById(long id){
        return (User)this.em.find(User.class, id);
    }

    public void addFollowing(User user, User following) {
        user.addFollowing(following);
    }

    public void addFollower(User user, User follower) {
        user.addFollower(follower);
    }

    public void removeFollower(User user, User follower) {
        user.removeFollower(follower);
    }

    public void removeFollowing(User user, User following) {
        user.removeFollowing(following);
    }

    public List<User> getAllUsers(){
        Query q = this.em.createNamedQuery("User.getAll");
        return q.getResultList();
    }

    public List<Kweet> getAllKweets(long id) {
        //return userDAO.getAllKweets(id);
        return null;
}

    public List<User> getAllFollowers(long id) {
        return (List<User>) em.createQuery("SELECT * FROM User WHERE User.id = " + id);
    }

    public List<User> getAllFollowing(long id) {
        return (List<User>) em.createQuery("SELECT * FROM User WHERE User.id = " + id);
    }

    public User getUser(Long id) {
        return null;
    }

}