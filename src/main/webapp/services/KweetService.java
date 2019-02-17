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
        this.kweetDAO.createKweet(kweet);
    }

    public void updateKweet(Kweet kweet){
        this.kweetDAO.updateKweet(kweet.getText());
    }

    public void deleteKweet(Kweet kweet, User user){
        this.kweetDAO.deleteKweet(kweet, user);
    }

    public List<Kweet> getAllReactions(Kweet kweet){
        return this.kweetDAO.getAll(kweet);
    }

    public void createKweetReaction(Kweet kweet, Kweet reaction){
        kweet.addReaction(reaction);
    }

}