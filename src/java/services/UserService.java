package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;


import dao.KweetDAO;
import dao.UserDAO;
import dao.UserDAOImpl;
import entities.Kweet;
import entities.User;

@Stateless
public class UserService{

    @EJB
    UserDAO userDAO;

    @EJB
    KweetDAO kweetDAO;

    // -    constructor
    public UserService(){
    }

    public void createUser(User user){
        if(user != null){
            System.out.println(user);
            this.userDAO.createUser(user);
        }
    }

    public void removeUser(User user){
        this.userDAO.removeUser(user);
    }

    public void updateUser(User user) {
        this.userDAO.updateUser(user);
    }

    public User findUserById(long id){
        return userDAO.findUserById(id);
    }

    public void addFollowing(Long user, Long following) {
        //userDAO.addFollowing(user, following);
        User addFollowing = userDAO.findUserById(user);
        User userFollowing = userDAO.findUserById(following);
        addFollowing.addFollower(userFollowing);
        userDAO.updateUser(addFollowing);
        userDAO.updateUser(userFollowing);
    }

    public void addFollower(User user, User follower) {
        user.addFollower(follower);
        userDAO.updateUser(user);
    }

    public void addReactionToKweet(Kweet kweet){
        //kweetDAO.addReaction(kweet);
    }

    public void removeFollower(User user, User follower) {
        user.removeFollower(follower);
        userDAO.updateUser(user);
    }

    public void removeFollowing(Long id, Long following){
        userDAO.removeFollowing(id, following);
    }

    public List<User> getAllUsers(){
        return  userDAO.getAllUsers();
    }

    public List<Kweet> getAllKweets(Long id) {
        return userDAO.getAllKweets(id);
}

    public List<User> getAllFollowers(Long id) {
        return userDAO.getAllFollowers(id);
    }

    public List<User> getAllFollowing(Long id) {
        return userDAO.getAllFollowing(id);
    }


}