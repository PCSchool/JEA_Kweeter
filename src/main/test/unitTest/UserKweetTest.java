package unitTest;

import Domain.Roles;
import org.junit.Before;
import src.Models.Kweet;
import src.Models.User;
import org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

//mockito toevoegen voor testen
//integratietesten: framework nodig
//dao specific objects in dal / context
public class UserKweetTest {

    private List<User> users;
    private List<Kweet> kweets;

    @Before
    public void setUp() throws Exception{
        users = new ArrayList<User>();
        kweets = new ArrayList<>(kweets);

        for(int i = 0; i <=8; i++){
            users.add(new User("valentine"+i, "Valen Tine"+i, "PassWord!"+i, "biography", "Netherlands", Roles.STANDARD));
        }
        users.add(new User("NinaHooghuizen", "Nina Hooghuizen", "Fra!nk2", "biography", "Netherlands", Roles.MODERATOR));
        users.add(new User("Henry104", "Henry Hooghuizen", "Nin1$ms", "biography", "Netherlands", Roles.ADMINISTRATOR));
    }





}
