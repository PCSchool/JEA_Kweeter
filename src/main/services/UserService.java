package services;

import entities.Kweet;
import entities.User;
import dao.UserDAO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserService{
    //@EJB
    UserDAO userDAO;

    @PersistenceContext
    private EntityManager em;

    // -    constructor
    public UserService(){
    }

    public void updateUser(User user){
        this.userDAO.updateUser(user);
    }

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