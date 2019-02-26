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

    void addFollowing(Long user, Long following);

    void addFollower(User user, User follower);

    void removeFollower(User user, User follower);

    void removeFollowing(Long id, Long following);

    List<User> getAllUsers();

    List<Kweet> getAllKweets(Long id);

    List<User> getAllFollowers(Long id);

    List<User> getAllFollowing(Long id);
}
