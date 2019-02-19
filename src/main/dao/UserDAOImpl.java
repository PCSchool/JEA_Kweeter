package dao;

import entities.User;
import entities.Kweet;

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

    public void deleteUser(User user) {
        this.em.remove(user);
    }

    @Override
    public void updateUser(User user) {
        this.em.merge(user);
        user.updateProfile(user.getName(), user.getBiography(), user.getLocation());
    }

    @Override
    public void addFollowing(User user, User following) {
        user.addFollowing(following);
    }

    @Override
    public void addFollower(User user, User follower) {
        user.addFollower(follower);
    }

    @Override
    public void removeFollower(User user, User follower) {
        user.removeFollower(follower);
    }

    @Override
    public void removeFollowing(User user, User following) {
        user.removeFollowing(following);
    }

    @Override
    public List<Kweet> getAllKweets(User user) {
        return user.getKweets();
    }

    @Override
    public List<User> getAllFollowers(User user) {
        return user.getFollowers();
    }

    @Override
    public List<User> getAllFollowing(User user) {
        return user.getFollowing();
    }

    @Override
    public User getUser(Long id) {
        return null;
    }
}