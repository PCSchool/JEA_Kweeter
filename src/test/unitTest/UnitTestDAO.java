package unitTest;

import dao.KweetDAO;
import dao.UserDAO;
import entities.Kweet;
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

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Mock
    MockDatabase mockDatabase;

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
        User user = mock(User.class);
        //when(userDAOMock.createUser(any(User.class))).thenReturn(new User());
        when(userDAOMock.createUser(any(User.class))).thenReturn(user);
        userDAOMock.createUser(any(User.class));

        verify(userDAOMock).createUser(any(User.class));
    }

    @Test
    public void testUpdateUser(){
        User user = mock(User.class);
        when(userDAOMock.updateUser(any(User.class), anyLong())).thenReturn(user);
        userDAOMock.updateUser(any(User.class), anyLong());

        verify(userDAOMock).updateUser(any(User.class), anyLong());
    }

    @Test
    public void testRemoveUser(){
    }

    @Test
    public void testFindByUsername(){
        User user = new User();
        when(userDAOMock.findUserByName(anyString())).thenReturn(anyListOf(User.class));
        assertEquals(anyListOf(User.class), userDAOMock.findUserByName(anyString()));
        verify(userDAOMock, times(1)).findUserByName(anyString());
    }

    @Test
    public void testFindByUserId(){
        User user = new User();
        when(userDAOMock.findUserById(anyLong())).thenReturn(user);
        assertEquals(user, userDAOMock.findUserById(anyLong()));
        verify(userDAOMock, times(1)).findUserById(anyLong());
    }

    @Test
    public void testAddFollower(){
        User u1 = new User("Kat10101", "Lynda Buckley", "8+bq?7kKc,'U", "Now is the winter of our discontent", "Netherlands","STANDARD");
        Long id1 = 1L;
        u1.setId(id1);
        User u2 = new User("DOH1010101", "Renad Buckley", "8+bq?7kKc,'U", "Now is the winter of our discontent", "Netherlands","STANDARD");
        Long id2 = 2L;
        u2.setId(id2);

        when(userDAOMock.addFollower(id1, id2)).thenReturn(true);
        assertEquals(true, userDAOMock.addFollower(id1, id2));
        verify(userDAOMock, times(1)).addFollower(id1, id2);
    }

    @Test
    public void testAddFollowing(){
        User u1 = new User("Kat10101", "Lynda Buckley", "8+bq?7kKc,'U", "Now is the winter of our discontent", "Netherlands","STANDARD");
        Long id1 = 1L;
        u1.setId(id1);
        User u2 = new User("DOH1010101", "Renad Buckley", "8+bq?7kKc,'U", "Now is the winter of our discontent", "Netherlands","STANDARD");
        Long id2 = 2L;
        u2.setId(id2);

        when(userDAOMock.addFollowing(id1, id2)).thenReturn(true);
        assertEquals(true, userDAOMock.addFollowing(id1, id2));
        verify(userDAOMock, times(1)).addFollowing(id1, id2);
    }

    @Test
    public void testRemoveFollower(){
        Long id2 = 2L;
        Long id1 = 1L;
        when(userDAOMock.removeFollowing(id1, id2)).thenReturn(true);
        assertEquals(true, userDAOMock.removeFollowing(id1, id2));
        verify(userDAOMock, times(1)).removeFollowing(id1, id2);
    }

    @Test
    public void testRemoveFollowing(){
        Long id2 = 2L;
        Long id1 = 1L;
        when(userDAOMock.removeFollower(id1, id2)).thenReturn(true);
        assertEquals(true, userDAOMock.removeFollower(id1, id2));
        verify(userDAOMock, times(1)).removeFollower(id1, id2);
    }

    @Test
    public void testGetAllFollowing(){
        List<User> mockList = new ArrayList<User>();
        when(userDAOMock.getAllFollowing(anyLong())).thenReturn(mockList);
        assertEquals(mockList, userDAOMock.getAllFollowing(anyLong()));
        verify(userDAOMock, times(1)).getAllFollowing(anyLong());
    }

    @Test
    public void testGetAllFollowers(){
        List<User> mockList = new ArrayList<User>();
        when(userDAOMock.getAllFollowers(anyLong())).thenReturn(mockList);
        assertEquals(mockList, userDAOMock.getAllFollowers(anyLong()));
        verify(userDAOMock, times(1)).getAllFollowers(anyLong());
    }

    @Test
    public void testGetAllUsers(){
        List<User> mockList = new ArrayList<User>();
        when(userDAOMock.getAllUsers()).thenReturn(mockList);
        assertEquals(mockList, userDAOMock.getAllUsers());
        verify(userDAOMock, times(1)).getAllUsers();
    }

}
