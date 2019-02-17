import Domain.Kweet;
import Domain.User;

import java.util.List;

public class KweetDAOImpl implements KweetDAO{
    @Override
    public void createKweet(Kweet kweet) {

    }

    @Override
    public void updateKweet(String string) {

    }

    @Override
    public void deleteKweet(Kweet kweet, User user) {

    }

    @Override
    public List<Kweet> getAll(Kweet kweet) {
        return kweet.getReactions();
    }

    @Override
    public void addReaction(Kweet kweet, Kweet reaction, User id) {

    }
}