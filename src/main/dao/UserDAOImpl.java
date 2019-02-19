package dao;

import entities.Kweet;
import entities.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager em;

    //  empty constructor
    public UserDAOImpl(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        this.em = emf.createEntityManager();
    }

    public void removeUser(User user) {
        this.em.remove(user);
    }

    public void createUser(User user) {
        this.em.persist(user);
    }

    public User findUserByName(String name){
        Query q = this.em.createNamedQuery("User.getByName");
        q.setParameter("name", name);
        return  (User)q.getSingleResult();
    }

    public User findUserById(long id){
        return (User)this.em.find(User.class, id);
    }

    public void updateUser(User user) {
        this.em.merge(user);
        user.updateProfile(user.getName(), user.getBiography(), user.getLocation());
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

    public List<Kweet> getAllKweets(User user) {
        return user.getKweets();
    }

    public List<User> getAllFollowers(User user) {
        return user.getFollowers();
    }

    public List<User> getAllFollowing(User user) {
        return user.getFollowing();
    }

    public User getUser(Long id) {
        return null;
    }
}