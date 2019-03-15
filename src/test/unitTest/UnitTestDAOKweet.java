package unitTest;

import dao.KweetDAO;
import dao.UserDAO;
import entities.Kweet;
import entities.User;
import org.checkerframework.checker.units.qual.K;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import services.KweetService;
import services.UserService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UnitTestDAOKweet {

    @Mock
    private KweetDAO kweetDAOMock;

    @InjectMocks
    private KweetService kweetService;

    public UnitTestDAOKweet(){
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
    public void testCreateKweet(){
        verify(kweetDAOMock, never()).createKweet(any(Kweet.class), anyLong());
        kweetDAOMock.createKweet(any(Kweet.class), anyLong());
        verify(kweetDAOMock, times(1)).createKweet(any(Kweet.class), anyLong());
    }

    @Test
    public void testCreateReaction(){
        verify(kweetDAOMock, never()).addReaction(anyLong(), anyLong(), any(Kweet.class));
        kweetDAOMock.addReaction(anyLong(), anyLong(), any(Kweet.class));
        verify(kweetDAOMock, times(1)).addReaction(anyLong(), anyLong(), any(Kweet.class));
    }

    @Test
    public void testRemoveKweet(){
        verify(kweetDAOMock, never()).removeKweet(anyLong(), anyLong());
        kweetDAOMock.removeKweet(anyLong(), anyLong());
        verify(kweetDAOMock, times(1)).removeKweet(anyLong(), anyLong());
    }

    @Test
    public void testFindByFilter(){
        List<Kweet> mockList = new ArrayList<>();
        when(kweetDAOMock.findByFilter(anyString())).thenReturn(mockList);
        assertEquals(mockList, kweetDAOMock.findByFilter(anyString()));
        verify(kweetDAOMock, times(1)).findByFilter(anyString());
    }

    @Test
    public void testGetAllKweets(){
        List<Kweet> mockList = new ArrayList<>();
        when(kweetDAOMock.getAllKweets(anyLong())).thenReturn(mockList);
        assertEquals(mockList, kweetDAOMock.getAllKweets(anyLong()));
        verify(kweetDAOMock, times(1)).getAllKweets(anyLong());
    }

    @Test
    public void testGetKweets(){
        List<Kweet> mockList = new ArrayList<>();
        when(kweetDAOMock.getKweets(anyLong(), anyInt())).thenReturn(mockList);
        assertEquals(mockList, kweetDAOMock.getKweets(anyLong(), anyInt()));
        verify(kweetDAOMock, times(1)).getKweets(anyLong(), anyInt());
    }
}
