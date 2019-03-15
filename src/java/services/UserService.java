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

    public boolean addFollowing(Long user, Long following) {
        return userDAO.addFollowing(user, following);
    }

    public boolean addFollower(Long user, Long follower) {
        return userDAO.addFollower(user, follower);
    }

    public boolean removeFollower(Long user, Long follower) {
        return userDAO.removeFollower(user, follower);
    }

    public boolean removeFollowing(Long id, Long following){
        return userDAO.removeFollowing(id, following);
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