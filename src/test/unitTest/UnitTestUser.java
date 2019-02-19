package unitTest;

import entities.Roles;
import org.junit.Before;
import entities.User;
import org.junit.Test;

import java.security.InvalidParameterException;

import static org.junit.Assert.assertEquals;

public class UnitTestUser {
    private MockDatabase mockDatabase;

    @Before
    public  void setUp(){
        mockDatabase = new MockDatabase();
    }


    @Test(expected = InvalidParameterException.class)
    public void testUserConstructorInvalidParameterExceptionUsername(){
        User user = new User("", "Henry Valk","Min!2ss", "Its a nice day", "Netherlands", Roles.STANDARD);
    }

    @Test(expected = InvalidParameterException.class)
    public void testUserConstructorInvalidParameterExceptionName(){
        User user = new User("HenryValk1", "","Min!2ss", "Its a nice day", "Netherlands", Roles.STANDARD);
    }

    @Test(expected = InvalidParameterException.class)
    public void testUserConstructorInvalidParameterExceptionPassword(){
        User user = new User("HenryValk1", "Henry Valk","", "Its a nice day", "Netherlands", Roles.STANDARD);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserConstructorIllegalArgumentExceptionPassword1(){
        User user = new User("HenryValk1", "Henry Valk","invalidpassword", "Its a nice day", "Netherlands", Roles.STANDARD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserConstructorIllegalArgumentExceptionPassword2(){
        User user = new User("HenryValk1", "Henry Valk","in", "Its a nice day", "Netherlands", Roles.STANDARD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserConstructorIllegalArgumentExceptionPassword3(){
        User user = new User("HenryValk1", "Henry Valk","invalidpasswordinvalidpasswordthatswaytoolongthenisexpectedofapassword", "Its a nice day", "Netherlands", Roles.STANDARD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserConstructorIllegalArgumentExceptionPassword4(){
        User user = new User("HenryValk1", "Henry Valk","!@#$!!!!!", "Its a nice day", "Netherlands", Roles.STANDARD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserConstructorIllegalArgumentExceptionPassword5(){
        User user = new User("HenryValk1", "Henry Valk","!@#$!!!!!", "Its a nice day", "Netherlands", Roles.STANDARD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserConstructorIllegalArgumentExceptionPassword6(){
        User user = new User("HenryValk1", "Henry Valk","1234567890", "Its a nice day", "Netherlands", Roles.STANDARD);
    }
}
