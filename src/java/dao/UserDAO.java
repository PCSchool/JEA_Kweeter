package dao;

import entities.Kweet;
import entities.User;

import java.util.List;

public interface UserDAO {
    boolean removeUser(User user);

    boolean createUser(User user);

    boolean updateUser(User user, Long id);

    User findUserByName(String name);

    User findUserById(long id);

    void addFollowing(Long user, Long following);

    void addFollower(Long user, Long follower);

    void removeFollower(Long id, Long follower);

    void removeFollowing(Long id, Long following);

    List<User> getAllUsers();

    List<Kweet> getAllKweets(Long id);

    List<User> getAllFollowers(Long id);

    List<User> getAllFollowing(Long id);
}
