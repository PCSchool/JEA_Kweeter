package Services;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;

import Domain.Kweet;
import Domain.User;

import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager em;

    //get specific user
    public User getUser(Long id){
        return em.find(User.class, id);
    }

    /*get all users
    public List<User> getAllUsers(){
        return null;
    }

    // get all followers
    public List<User> getAllFollowers(Long id){
        return null;
    }

    // get all following
    public List<User> getAllFollowing(Long id){
        return null;
    }*/

    // save user
    public User saveUser(User user){
        em.persist(user);
        return user;
    }

    // save kweet

}
