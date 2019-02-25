package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;


import dao.UserDAO;
import dao.UserDAOImpl;
import entities.Kweet;
import entities.User;

@Stateless
public class UserService{

    @EJB
    UserDAO userDAO;

    // -    constructor
    public UserService(){
    }

    public void createUser(User user){
        if(user != null){
            this.userDAO.createUser(user);
        }
    }

    public void removeUser(User user){
        this.userDAO.removeUser(user);
    }

    public void updateUser(User user) {
        this.userDAO.updateUser(user);
    }

    /*public User findUserByName(String name){
        Query q = this.em.createNamedQuery("User.byName");
        q.setParameter("name", name);
        return  (User)q.getSingleResult();
    }*/

    public User findUserById(long id){
        return userDAO.findUserById(id);
    }

    public void addFollowing(User user, User following) {
        user.addFollowing(following);
        userDAO.updateUser(user);
    }

    public void addFollower(User user, User follower) {
        user.addFollower(follower);
        userDAO.updateUser(user);
    }

    public void removeFollower(User user, User follower) {
        user.removeFollower(follower);
        userDAO.updateUser(user);
    }

    public void removeFollowing(User user, User following){
        user.removeFollowing(following);
        userDAO.updateUser(user);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public List<User> getAllUsers(){
        return  userDAO.getAllUsers();
    }

    public List<Kweet> getAllKweets(User user) {
        return userDAO.getAllKweets(user);
}

    public List<User> getAllFollowers(User user) {
        return userDAO.getAllFollowers(user);
    }

    public List<User> getAllFollowing(User user) {
        return userDAO.getAllFollowing(user);
    }

    public User getUser(Long id){
        return userDAO.findUserById(id);
    }
}