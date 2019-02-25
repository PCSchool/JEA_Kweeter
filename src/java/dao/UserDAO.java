package dao;

import entities.Kweet;
import entities.User;

import java.util.List;

public interface UserDAO {
    void removeUser(User user);

    void createUser(User user);

    void updateUser(User user);

    User findUserByName(String name);

    User findUserById(long id);

    void addFollowing(User user, User following);

    void addFollower(User user, User follower);

    void removeFollower(User user, User follower);

    void removeFollowing(User user, User following);

    List<User> getAllUsers();

    List<Kweet> getAllKweets(User user);

    List<User> getAllFollowers(User user);

    List<User> getAllFollowing(User user);
}
