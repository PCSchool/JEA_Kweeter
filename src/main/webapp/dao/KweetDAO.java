import Domain.Kweet;
import Domain.User;
import java.util.List;

public interface KweetDAO{
    void createKweet(Kweet kweet);

    void updateKweet(String string);

    void deleteKweet(Kweet kweet, User user);

    List<Kweet> getAll(Kweet kweet);

    void addReaction(Kweet kweet,Kweet reaction, User id);
}