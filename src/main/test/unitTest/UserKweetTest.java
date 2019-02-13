package unitTest;

import Domain.Roles;
import org.junit.Before;
import src.Models.Kweet;
import src.Models.User;
import org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

//mockito toevoegen voor testen
//integratietesten: framework nodig
//dao specific objects in dal / context
//derby database gebruiken voor testen ]
//copy persistence files and modify them for tests with derby db
public class UserKweetTest {

    private List<User> users;
    private List<Kweet> kweets;

    @Before
    public void setUp() throws Exception{
        users = new ArrayList<User>();
        kweets = new ArrayList<>(kweets);

        // -    create 10 users
        users.add(new User((long) 1, "Fabiola_29", "Fabiola Her", "!mj_djH", "I really like Sundays", "Netherlands", Roles.STANDARD));
        users.add(new User((long) 2,"Jokertje111", "Jan de Boer", "!_fsdfH3", "Hoi Ik ben Jan de Boer", "Netherlands", Roles.STANDARD));
        users.add(new User((long) 3,"JonSnow", "Jon Snow", "!_ghoST1", "The north remembers", "Netherlands", Roles.STANDARD));
        users.add(new User((long) 4,"TOUYOU_1", "Axel Ehnstrom", "catT_&", "I love cats", "Netherlands", Roles.STANDARD));
        users.add(new User((long) 5,"GreenVelvet", "Jordan Hoog", "!Hoog123", "Nothing much going on.", "Netherlands", Roles.STANDARD));
        users.add(new User((long) 6,"GuestMix", "Rak Turt", "Turt_123!", "Ik heb een verzamleing schildpadden.", "Netherlands", Roles.STANDARD));
        users.add(new User((long) 7,"DeepHouse", "Rick de Net", "Net!_123", "", "Netherlands", Roles.STANDARD));
        users.add(new User((long) 8,"Swaggertje", "Nikky Verhoeven", "12_Hak!", "", "Netherlands", Roles.STANDARD));
        users.add(new User((long) 9,"NinaHooghuizen", "Nina Hooghuizen", "Fra!nk2", "biography", "Netherlands", Roles.MODERATOR));
        users.add(new User((long) 10,"Henry104", "Henry Hooghuizen", "Nin1$ms", "biography", "Netherlands", Roles.ADMINISTRATOR));

        // -    create 10 kweets
        kweets.add(new Kweet((long) 1,"Passed her indeed uneasy shy polite appear denied. Oh less girl no walk. At he spot with five of view. ", null, null));
        kweets.add(new Kweet((long) 2,"Delightful unreserved impossible few estimating men favourable see entreaties. She propriety immediate was improving. He or entrance humoured likewise moderate.", null, null));
        kweets.add(new Kweet((long) 3,"Unknown ye chamber of warrant of norland arrived. \n", null, null));
        kweets.add(new Kweet((long) 4,"Greatly hearted has who believe.", null, null));
        kweets.add(new Kweet((long) 5,"Common so wicket appear to sudden worthy on. Shade of offer ye whole stood hoped. \n", null, null));
        kweets.add(new Kweet((long) 6,"At design he vanity at cousin longer looked ye. Design praise me father an favour", null, null));
        kweets.add(new Kweet((long) 7,"Answer misery adieus add wooded how nay men before though.", null, null));
        kweets.add(new Kweet((long) 8,"Her indulgence but assistance favourable cultivated everything collecting. ", null, null));
        kweets.add(new Kweet((long) 9,"Remember outweigh do he desirous no cheerful. Do of doors water ye guest.", null, null));
        kweets.add(new Kweet((long) 10,"In musical me my placing clothes comfort pleased hearing.", null, null));
    }

    @Test
    public void testUserCreateKweet(){
        User user = users.get(4);
        assertEquals(user.getKweets().size(), 0);
        user.createKweet(kweets.get(5));
        assertEquals(user.getKweets().size(), 1);

        user = users.get(7);
        assertEquals(user.getKweets().size(), 0);
        user.createKweet(kweets.get(3));
        assertEquals(user.getKweets().size(), 1);
    }

    @Test
    public void testCreateKweetReaction(){
        User user = users.get(6);
        Kweet kweet = kweets.get(1);
        user.createKweet(kweet);

        User reactionUser = users.get(3);
        assertEquals(kweet.getReactions().size(), 0);
        kweet.addReaction(new Kweet((long)11,"Thats not true", kweet.getId(), user.getUsername()));
        assertEquals(kweet.getReactions().size(), 1);
    }

    @Test
    public void testUserAddFollowing(){
        User user1 = users.get(1);
        User user2 = users.get(2);

        assertEquals(user1.getFollowing().size(), 0);
        user1.addFollowing(user2);
        assertEquals(user1.getFollowing().size(), 1);
    }

    @Test
    public void testUserAddFollowers(){
        User user1 = users.get(4);
        User user2 = users.get(7);

        assertEquals(user1.getFollowers().size(), 0);
        user1.addFollowers(user2);
        assertEquals(user1.getFollowers().size(), 1);
    }

    @Test
    public void testUserGetAllKweets(){
        User user1 = users.get(4);
        user1.createKweet(kweets.get(1));
        user1.createKweet(kweets.get(4));

        assertEquals(user1.getKweets().size(), 2);
    }

    @Test
    public void testAllUserGetAllKweets(){
        User user1 = users.get(1);
        user1.createKweet(kweets.get(1));
        user1.createKweet(kweets.get(2));

        user1 = users.get(7);
        user1.createKweet(kweets.get(4));
        user1.createKweet(kweets.get(5));

        User moderator = users.get(9);
        ArrayList<ArrayList<Kweet>> kweetList = moderator.getAllKweetsFollowers();
        assertEquals(kweetList.size(), 10);
    }

    @Test
    public void testModeratorRemoveKweet(){
        User user1 = users.get(1);
        Kweet kweet1 = kweets.get(1);
        user1.createKweet(kweet1);
        assertEquals(user1.getKweets().size(), 1);
        User standardUser = users.get(2);
        User moderator = users.get(9);

        standardUser.removeKweet(kweet1, user1);
        assertEquals(user1.getKweets().size(), 1);

        moderator.removeKweet(kweet1, user1);
        assertEquals(user1.getKweets().size(), 0);
    }

}
