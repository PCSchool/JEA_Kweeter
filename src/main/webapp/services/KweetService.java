import Domain.Kweet;
import Domain.User;

import java.util.List;

//@Stateless
public class KweetService{
    //@EJB
    KweetDAO kweetDAO;

    // -    constructor
    public KweetService(){
    }

    public void createKweet(Kweet kweet){
        this.kweetDAO.create(kweet);
    }

    public void updateKweet(Kweet kweet){
        this.kweetDAO.update(kweet);
    }

    public void deleteKweet(Kweet kweet, User user){
        this.kweetDAO.delete(kweet, user);
    }

    public List<Kweet> getAllReactions(Kweet kweet){
        return this.kweetDAO.getAll(kweet);
    }

    public void createKweetReaction(Kweet kweet, User user){
        this.kweetDAO.addReaction(kweet, user);
    }

}