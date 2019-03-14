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

    // -    constructor
    public UserService(){
    }

    public boolean createUser(User user){
        return this.userDAO.createUser(user);
    }

    public boolean removeUser(User user){
        return this.userDAO.removeUser(user);
    }

    public boolean updateUser(User user, Long id) {

        return this.userDAO.updateUser(user, id);
    }

    public User findUserById(long id){
        return userDAO.findUserById(id);
    }

    public void addFollowing(Long user, Long following) {
        userDAO.addFollowing(user, following);
    }

    public void addFollower(Long user, Long follower) {
        userDAO.addFollower(user, follower);
    }

    public void removeFollower(Long user, Long follower) {
        userDAO.removeFollower(user, follower);
    }

    public void removeFollowing(Long id, Long following){
        userDAO.removeFollowing(id, following);
    }

    public List<User> getAllUsers(){
        return  userDAO.getAllUsers();
    }

    public List<User> getAllFollowers(Long id) {
        return userDAO.getAllFollowers(id);
    }

    public List<User> getAllFollowing(Long id) {
        return userDAO.getAllFollowing(id);
    }


}