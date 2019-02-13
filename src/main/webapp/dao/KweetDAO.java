import Domain.Kweet;
import Domain.User;
import java.util.List;

public interface KweetDAO{
    void create(Kweet kweet);

    void update(Kweet kweet);

    void delete(Kweet kweet, User user);

    List<Kweet> getAll(Kweet kweet);

    void addReaction(Kweet kweet, User id);
}