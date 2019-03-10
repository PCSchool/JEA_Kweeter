package dao;

import entities.Kweet;
import entities.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.io.Console;
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

    /**
     * remove the given user from the database
     * @param user
     */
    @Override
    public void removeUser(User user) {
        this.em.remove(user);
    }

    /**
     * add the given user to the database
     * @param user
     */
    @Override
    public void createUser(User user) {
        this.em.persist(user);
    }

    /**
     * find user in database by name
     * @param name
     * @return
     */
    @Override
    public User findUserByName(String name){
        this.em.createQuery("SELECT u FROM User u where u.name = :name");
        return this.em.find(User.class, name);
    }

    /**
     * find user in database by id
     * @param id
     * @return
     */
    @Override
    public User findUserById(long id){
        this.em.createQuery("SELECT u FROM User u where u.id = :id");
        return this.em.find(User.class, id);
    }

    /**
     * add a follower to the user, user id gets saved in column 'follower_id' and follower id gets saved in 'following_id'
     * @param user
     * @param following
     */
    @Override
    public void addFollowing(Long user, Long following) {
        this.em.createQuery("SELECT u FROM User u where u.id = :user");
        User userAccount = this.em.find(User.class, user);

        this.em.createQuery("SELECT u FROM User u where u.id = :following");
        User userFollowing = this.em.find(User.class, following);

        //todo fix follower - following
        userAccount.addFollowing(userFollowing);
        userFollowing.addFollower(userAccount);

        this.em.merge(userFollowing);
        this.em.merge(userAccount);
        this.em.persist(userFollowing);
        this.em.persist(userAccount);

        //Query query = em.createNativeQuery("INSERT INTO followers (user_id, follows_id) VALUES (?, ?)");
        //query.setParameter(1, following);
        //query.setParameter(2, user);
        //query.executeUpdate();
        //int count = em.createQuery("INSERT INTO user_following VALUES (" + following + ", " + user + ")").executeUpdate();
        //System.out.println("UserDAO addFollowing --> " + count);
    }

    @Override
    public void addFollower(User user, User follower) {

    }

    /**
     * update the current status of the user
     * @param user
     */
    @Override
    public void updateUser(User user) {
        this.em.merge(user);
    }

    /**
     * remove a follower from the user his following
     * @param user
     * @param follower
     */
    @Override
    public void removeFollower(User user, User follower) {
        //DELETE FROM User WHERE
        this.em.merge(user);
    }

    /**
     * remove a user from following an certain account
     * @param id
     * @param following
     */
    @Override
    public void removeFollowing(Long id, Long following) {
        int count = em.createQuery("DELETE FROM user_following WHERE 'following_id' = " + following + " AND 'follower_id' =  " + " id ").executeUpdate();
        System.out.println("UserDAO removeFollowing --> " + count);
    }

    /**
     * returns a list of all users in the kweeter app
     * @return
     */
    @Override
    public List<User> getAllUsers(){
        Query query = this.em.createNamedQuery("User.getAll");
        return query.getResultList();
    }

    /**
     * returns all the kweets from a specific user
     * @param id
     * @return
     */
    @Override
    public List<Kweet> getAllKweets(Long id) {
        Query query = this.em.createNamedQuery("User.getAll");
        return query.getResultList();
    }

    /**
     * returns all the followers of a specific user
     * @param id
     * @return
     */
    @Override
    public List<User> getAllFollowers(Long id) {
        Query query = this.em.createNamedQuery("User.getFollowers");
        return query.getResultList();
    }

    /**
     * returns all the user followed by a specific user
     * @param id
     * @return
     */
    @Override
    public List<User> getAllFollowing(Long id)
    {
        Query query = this.em.createNamedQuery("User.getFollowing");
        return query.getResultList();
    }
}