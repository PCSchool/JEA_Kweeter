import Domain.Kweet;
import Domain.User;

import java.util.List;

//@Stateless
public class UserService{
    //@EJB
    UserDAO UserDAO;

    // -    constructor
    public UserService(){
    }

    public void createUser(User user){
        this.UserDAO.create(user);
    }

    public void updateUser(User user){
        this.UserDAO.update(user);
    }

    public void deleteUser(User user){
        this.UserDAO.delete(user);
    }

    // -    user started following another user
    public void addFollowingUser(User user){
        this.UserDAO.addFollowing(user);
    }

    // -    user has gained a new follower
    public void addFollowerUser(User user){
        this.UserDAO.addFollower(user);
    }

    public List<Kweet> getAllKweets(){
        return this.UserDAO.getAllKweets();
    }

    public List<User> getAllFollowers(){
        return this.UserDAO.getAllFollowers();
    }

    public List<User> getAllFollowing(){
        return this.UserDAO.getAllFollowing();
    }

}