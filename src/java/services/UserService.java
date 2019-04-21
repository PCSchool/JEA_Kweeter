package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.persistence.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import dao.KweetDAO;
import dao.UserDAO;
import dao.UserDAOImpl;
import entities.Kweet;
import entities.Roles;
import entities.User;
import security.Algorithm;
import security.HashGenerator;

@Stateless
public class UserService{

    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,26}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    @Inject
    HashGenerator passwordHash;

    @EJB
    UserDAO userDAO;

    // -    constructor
    public UserService(){
    }

    public User validateUser(String username, String password){
        return userDAO.validateUser(username, hashPassword(password));
    }

    public User findSingleUser(String username){
        return userDAO.findSingleUserByName(username);
    }

    public User createUser(User user){
        user.setActive(true);
        user.setPassword(hashPassword(user.getPassword()));
        return this.userDAO.createUser(user);
    }

    private String hashPassword(String password){
        this.passwordHash = new HashGenerator(Algorithm.SHA512.getAlgorihmName());
        return this.passwordHash.getHashText(password);
    }

    public boolean removeUser(Long id, Long userToRemoveId){
        if(id == userToRemoveId){
            return false;
        }
        User user = findUserById(id);
        if(user.getRole() != Roles.ADMINISTRATOR || user.getRole() != Roles.MODERATOR){
            return false;
        }
        return this.userDAO.removeUser(id, userToRemoveId);
    }

    public User updateUser(User user, Long id) {
        if(user.getBiography().length() > 160 || user.getLocation().length() > 100){
            throw new IllegalArgumentException("Invalid arguments. Maxlength for biography is 160 and for location its 100");
        }
        return this.userDAO.updateUser(user, id);
    }

    public User findUserById(long id){
        return userDAO.findUserById(id);
    }

    public List<User> findUserByUsername(String name){return userDAO.findUserByName(name);}

    public boolean addFollowing(Long user, Long following) {
        if(user == following){
            return false;
        }
        return userDAO.addFollowing(user, following);
    }

    public boolean addFollower(Long user, Long follower) {
        if(user == follower){
            return false;
        }
        return userDAO.addFollower(user, follower);
    }

    public boolean removeFollower(Long user, Long follower) {
        if(user == follower){
            return false;
        }
        return userDAO.removeFollower(user, follower);
    }

    public boolean removeFollowing(Long id, Long following){
        if(id == following){
            return false;
        }
        return userDAO.removeFollowing(id, following);
    }

    public List<User> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public List<User> getAllFollowers(Long id) {
        List<User> returnList = userDAO.getAllFollowers(id);
        if(returnList.size() == 1){
            if(returnList.get(0) == null){
                return new ArrayList<User>();
            }
        }
        return userDAO.getAllFollowers(id);
    }

    public List<User> getAllFollowing(Long id) {
        List<User> returnList = userDAO.getAllFollowing(id);
        if(returnList.size() == 1){
            if(returnList.get(0) == null){
                return new ArrayList<User>();
            }
        }
        return userDAO.getAllFollowing(id);
    }
}