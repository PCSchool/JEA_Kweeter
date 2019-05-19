package services;

import com.auth0.client.mgmt.UsersEntity;
import entities.User;
import entities.Kweet;
import dao.KweetDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless
public class KweetService{
    @EJB
    KweetDAO kweetDAO;

    @Inject
    UserService userService;

    // -    constructor
    public KweetService(){
    }

    public boolean addReaction(Long id, Long kweetid, Kweet kweet){
        if(kweet.getMessage().length() > 160){
            return false;
        }
        this.kweetDAO.addReaction(id, kweetid, kweet);
        return true;
    }

    public List<Kweet> getLastTenKweets(Long id){
        return this.kweetDAO.getKweets(id, 10);
    }

    public List<Kweet> getAllKweets(Long id){
        return this.kweetDAO.getAllKweets(id);
    }

    public boolean createKweet(Kweet kweet, Long id) throws IllegalArgumentException{
        try{
            if(kweet.getMessage().length() > 160){
                return false;
            }
            User user = userService.findUserById(id);
            kweet.setCreateDate(new Date());
            kweet.setUser(user);
            kweet.setUsername(user.getUsername());
            user.addKweet(kweet);
            this.kweetDAO.createKweet(kweet);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean removeKweet(Long kweetId, Long id){
        if(kweetId < -1| id < -1){
            return false;
        }
        this.kweetDAO.removeKweet(kweetId, id);
        return true;
    }

    public List<Kweet> findByFilterKweet(String message){
        return this.kweetDAO.findByFilter(message);
    }

}