package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import dao.KweetDAO;
import dao.UserDAO;
import dao.UserDAOImpl;
import entities.Kweet;
import entities.User;

@Stateless
public class UserService{

    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,26}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    @EJB
    UserDAO userDAO;

    // -    constructor
    public UserService(){
    }

    public boolean createUser(User user){
        if (user.getUsername().isEmpty() || user.getName().isEmpty() || user.getPassword().isEmpty()) {
            return false;
        }
        return this.userDAO.createUser(user);
    }

    public boolean removeUser(Long id, Long userId){

        return this.userDAO.removeUser(id, userId);
    }

    public boolean updateUser(User user, Long id) {
        if(user.getBiography().length() > 160){
            return false;
        }
        if(user.getLocation().length() > 160){
            return false;
        }
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