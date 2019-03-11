package unitTest;

import dao.KweetDAO;
import dao.UserDAO;
import entities.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Fields;
import org.mockito.runners.MockitoJUnitRunner;
import services.KweetService;
import services.UserService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UnitTestDAO {

    @Mock
    private DataSource mockDataSource;

    @Mock
    private UserDAO userDAOMock;

    @InjectMocks
    private UserService userService;

    @Mock
    private KweetDAO kweetDAOMock;

    @InjectMocks
    private KweetService kweetService;

    @Mock
    Connection mockConn;

    @Mock
    PreparedStatement mockPreparedStmnt;

    @Mock
    ResultSet mockResultSet;

    int userId = 100;

    public UnitTestDAO(){
    }

    @BeforeClass
    public static void setUpClass() throws Exception{

    }

    @AfterClass
    public static void tearDownClass(){

    }

    @Before
    public void setUp() throws SQLException{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser(){
        //when(userDAOMock.createUser(any(User.class))).thenReturn(new User());
        when(userDAOMock.createUser(any(User.class))).thenReturn(true);
        userDAOMock.createUser(any(User.class));

        verify(userDAOMock).createUser(any(User.class));
        verify(userDAOMock, never()).removeUser(any(User.class));
    }

    @Test
    public void testUpdateUser(){
        when(userDAOMock.updateUser(any(User.class))).thenReturn(true);
        userDAOMock.updateUser(any(User.class));

        verify(userDAOMock).updateUser(any(User.class));
        verify(userDAOMock, never()).removeUser(any(User.class));
    }

    @Test
    public void testRemoveUser(){
        when(userDAOMock.removeUser(any(User.class))).thenReturn(true);
        userDAOMock.removeUser(any(User.class));

        verify(userDAOMock).removeUser(any(User.class));
    }

    @Test
    public void testFindByUsername(){
        userDAOMock.findUserByName(anyString());
        verify(userDAOMock).findUserByName(anyString());

    }


}
