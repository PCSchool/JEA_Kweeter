package dao;

import entities.Kweet;
import entities.User;

import java.util.List;

public interface UserDAO {
    boolean removeUser(Long id, Long userId);

    boolean createUser(User user);

    boolean updateUser(User user, Long id);

    User validateUser(String username, String password);

    List<User> findUserByName(String name);

    User findUserById(long id);

    boolean addFollowing(Long user, Long following);

    boolean addFollower(Long user, Long follower);

    boolean removeFollower(Long id, Long follower);

    boolean removeFollowing(Long id, Long following);

    List<User> getAllUsers();

    List<User> getAllFollowers(Long id);

    List<User> getAllFollowing(Long id);
}
