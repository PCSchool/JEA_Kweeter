package unitTest;


import Domain.Roles;
import org.junit.Before;
import src.Models.Kweet;
import src.Models.User;
import org.junit.Test;
import org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserCreateTest {

    String username;
    String name;
    String password;
    String biography;
    String location;
    Roles role;

    @Before
    public void setUp() throws Exception{
        String username = "Henry10";
        String name = "Henry Poppel";
        String password = "BikJ!o";
        String biography = "My name is Henry";
        String location = "Netherlands";
        Roles role = Roles.STANDARD;
    }

    @Test
    public void testUserCreate(){
        User user = new User(username, name, password ,biography, location, role);

        assertEquals(user.getUsername(), username);
        assertEquals(user.getName(), name);
        assertEquals(user.getBiography(), biography);
        assertEquals(user.getLocation(), location);
    }

    @Test(expected = InvalidParameterException.class)
    public void testUserInvalidUsername(){
        username = "";

        User user = new User(username, name, password ,biography, location, role);
    }

    @Test(expected = InvalidParameterException.class)
    public void testUserInvalidName(){
        name = "";

        User user = new User(username, name, password ,biography, location, role);
    }

    @Test(expected = InvalidParameterException.class)
    public void testUserInvalidPassword(){
        password = "";

        User user = new User(username, name, password ,biography, location, role);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testUserIllegalPassword(){
        password = "invalid";

        User user = new User(username, name, password ,biography, location, role);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserIllegalPassword1(){
        password = "InvaLID1";

        User user = new User(username, name, password ,biography, location, role);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserIllegalPassword2(){
        password = "innva1$";

        User user = new User(username, name, password ,biography, location, role);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserIllegalUsername(){
        password = "not";

        User user = new User(username, name, password ,biography, location, role);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserIllegalUsername1(){
        password = "whentheusernameistoolonganexceptionwillbecalledforexamplethis";

        User user = new User(username, name, password ,biography, location, role);
    }

}
