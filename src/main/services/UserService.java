package services;

import dao.UserDAO;
import entities.Kweet;
import entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import dao.UserDAOImpl;

@Stateless
public class UserService{
    @EJB
    private UserDAO userDAO;

    //@PersistenceContext
    //private EntityManager em;

    // -    constructor
    public UserService(){
        userDAO = new UserDAOImpl();
    }

    public void createUser(User user){
        this.userDAO.createUser(user);
    }

    public void removeUser(User user){
        this.userDAO.removeUser(user);
    }

    public void updateUser(User user){
        this.userDAO.updateUser(user);
    }

    public User findUserById(long id) {return this.userDAO.findUserById(id);}

    public User findUserByName(String name){return this.userDAO.findUserByName(name);}

    public void addFollower(User user, User follower){
        this.userDAO.addFollower(user, follower);
    }

    public void addFollowing(User user, User follower){
        this.userDAO.addFollowing(user, follower);
    }

    public void removeFollower(User user, User follower){
        this.userDAO.removeFollower(user, follower);
    }

    public void removeFollowing(User user, User following){
        this.userDAO.removeFollowing(user, following);
    }

    public List<User> getAllUsers() {return this.userDAO.getAllUsers();}

    public List<Kweet> getAllKweets(User user){
        return this.userDAO.getAllKweets(user);
    }

    public List<User> getAllFollowers(User user){
        return this.userDAO.getAllFollowers(user);
    }

    public int getAllFollowersSize(User user){
        return this.userDAO.getAllFollowers(user).size();
    }

    public List<User> getAllFollowing(User user){
        return this.userDAO.getAllFollowing(user);
    }

    public int getAllFollowingSize(User user){
        return this.userDAO.getAllFollowing(user).size();
    }

}