package dao;

import entities.Kweet;
import entities.Roles;
import entities.User;
import security.Algorithm;
import security.HashGenerator;
import sun.plugin.javascript.navig.Array;

import javax.ejb.Stateless;
import javax.inject.Inject;
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

    @Inject
    HashGenerator passwordHash;

    /**
     * empty constructor
     */
    public UserDAOImpl(){
    }

    @Override
    public boolean removeUser(User user) {
        try{
            user.setActive(false);
            this.em.merge(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public User createUser(User user) {
        try{
            this.em.persist(user);
            this.em.flush();
            return user;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
        public List<User> findUserByName(String name){
        Query q = em.createNamedQuery("User.findByUsername", User.class);
        String like = '%'+name+'%';
        q.setParameter("username", like);
        return q.getResultList();
    }

    @Override
    public User findUserById(long id){

        return this.em.find(User.class, id);
    }

    @Override
    public User findSingleUserByName(String name) {
        Query q = em.createNamedQuery("User.findBySingleUsername", User.class);
        q.setParameter("username", name);
        return (User) q.getSingleResult();
    }

    @Override
    public boolean addFollowing(Long user, Long following) {
        try{
            User u1 = this.em.find(User.class, user);
            User u2 = this.em.find(User.class, following);
            u2.addFollowing(u1);
            u1.addFollower(u2);
            this.em.merge(u2);
            this.em.merge(u1);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addFollower(Long user, Long follower) {
        try{
            User u1 = this.em.find(User.class, user);
            User u2 = this.em.find(User.class, follower);

            u1.addFollower(u2);
            u2.addFollowing(u1);
            this.em.merge(u2);
            this.em.merge(u1);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public User updateUser(User user, Long id) {
        Query q = null;
        try{
            q = em.createNamedQuery("User.update", User.class);
            q.setParameter("name", user.getName());
            q.setParameter("location", user.getLocation());
            q.setParameter("biography", user.getBiography());
            q.setParameter("website", user.getWebsite());
            q.setParameter("role", user.getRole());
            q.setParameter("id", id);
            q.executeUpdate();
            return user;
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public User validateUser(String username, String password) {
        User user = null;
        try{
            Query q = em.createNamedQuery("User.validate", User.class);
            q.setParameter("username", username);
            this.passwordHash = new HashGenerator(Algorithm.SHA512.getAlgorihmName());
            String securedPassword = this.passwordHash.getHashText(password);
            q.setParameter("password", securedPassword);
            user = (User) q.getSingleResult();
            return user;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean removeFollower(Long id, Long follower) {
        try{
            User user = this.em.find(User.class, id);
            User userUnfollower = this.em.find(User.class, follower);
            user.removeFollower(userUnfollower);
            userUnfollower.removeFollowing(user);
            this.em.merge(userUnfollower);
            this.em.merge(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean removeFollowing(Long id, Long following) {
        try{
            User user = this.em.find(User.class, id);
            User userUnfollowing = this.em.find(User.class, following);
            user.removeFollowing(userUnfollowing);
            userUnfollowing.removeFollower(user);
            this.em.merge(userUnfollowing);
            this.em.merge(user);
            return true;
        }catch (Exception e){
            return false;
        }
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
        List<User> returnList = query.getResultList();
        return query.getResultList();
    }

    @Override
    public List<User> getAllFollowing(Long id)
    {
        Query query = this.em.createNamedQuery("User.getAllFollowings");
        query.setParameter("id", id);
        List<User> returnList = query.getResultList();
        return query.getResultList();
    }
}