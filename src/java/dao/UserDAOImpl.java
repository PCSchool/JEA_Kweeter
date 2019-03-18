package dao;

import entities.Kweet;
import entities.Roles;
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
    public boolean removeUser(Long id, Long userId) {
        User user = this.em.find(User.class, id);
        User removeUser = this.em.find(User.class, userId);

        if(user.getRole() == Roles.ADMINISTRATOR || user.getRole() == Roles.MODERATOR){

            for(int i = 0; i< removeUser.getFollowings().size(); i++){
                removeFollowing(removeUser.getId(), removeUser.getFollowings().get(i).getId());
            }
            for(int i = 0; i< removeUser.getFollowers().size(); i++){
                removeFollower(removeUser.getId(), removeUser.getFollowers().get(i).getId());
            }
            this.em.remove(removeUser); return true;
        }
        return true;
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
    public boolean addFollowing(Long user, Long following) {
        User u1 = this.em.find(User.class, user);
        User u2 = this.em.find(User.class, following);
        u2.getFollowings().add(u1);
        u1.getFollowers().add(u2);

        this.em.merge(u2);
        this.em.merge(u1);
        return true;
    }

    @Override
    public boolean addFollower(Long user, Long follower) {
        User u1 = this.em.find(User.class, user);
        User u2 = this.em.find(User.class, follower);

        u1.getFollowers().add(u2);
        u2.getFollowings().add(u1);
        this.em.merge(u2);
        this.em.merge(u1);
        return true;
    }

    @Override
    public boolean updateUser(User user, Long id) {
        Query q = em.createNamedQuery("User.update", User.class);
        q.setParameter("name", user.getName());
        q.setParameter("location", user.getLocation());
        q.setParameter("biography", user.getBiography());
        q.setParameter("id", id);
        q.executeUpdate();
        return true;
    }

    @Override
    public boolean removeFollower(Long id, Long follower) {
        User user = this.em.find(User.class, id);
        User userUnfollower = this.em.find(User.class, follower);
        user.removeFollower(userUnfollower);
        userUnfollower.removeFollowing(user);
        this.em.merge(userUnfollower);
        this.em.merge(user);
        return true;
    }

    @Override
    public boolean removeFollowing(Long id, Long following) {
        User user = this.em.find(User.class, id);
        User userUnfollowing = this.em.find(User.class, following);
        user.removeFollowing(userUnfollowing);
        userUnfollowing.removeFollower(user);
        this.em.merge(userUnfollowing);
        this.em.merge(user);
        return true;
    }

    @Override
    public List<User> getAllUsers(){
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