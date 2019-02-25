package unitTest;

import org.junit.Before;
import org.junit.Test;
import entities.Kweet;
import entities.User;
import org.junit.Assert;

public class UnitTestGeneric {
    private MockDatabase mockDatabase;

    @Before
    public  void setUp(){
        mockDatabase = new MockDatabase();
    }

    @Test
    public void testUserGetAddFollowing(){
        User user = mockDatabase.userList.get(1);
        Assert.assertEquals(user.getFollowing().size(), 3);
        Assert.assertEquals(user.getFollowers().size(), 6);

        user.addFollowing(mockDatabase.userList.get(6));
        Assert.assertEquals(user.getFollowing().size(), 4);
        Assert.assertEquals(user.getFollowers().size(), 7);

        user.addFollowing(mockDatabase.userList.get(6));
        Assert.assertEquals(user.getFollowing().size(), 4);
        Assert.assertEquals(user.getFollowers().size(), 7);
    }

    @Test
    public void testUserRemoveFollowing(){
        User user = mockDatabase.userList.get(1);
        User follower = mockDatabase.userList.get(3);
        Assert.assertEquals(user.getFollowing().size(), 3);
        Assert.assertEquals(user.getFollowers().size(), 6);
        Assert.assertEquals(follower.getFollowers(), 1);

        user.removeFollowing(follower);
        Assert.assertEquals(user.getFollowing().size(), 2);
        Assert.assertEquals(user.getFollowers().size(), 6);
        Assert.assertEquals(follower.getFollowers(), 0);

        user.removeFollowing(mockDatabase.userList.get(8));
        Assert.assertEquals(user.getFollowing().size(), 2);
        Assert.assertEquals(user.getFollowers().size(), 6);
        Assert.assertEquals(follower.getFollowers(), 0);
    }

    @Test
    public void testUserRemoveFollower(){
        User user = mockDatabase.userList.get(1);
        User follower = mockDatabase.userList.get(8);
        Assert.assertEquals(user.getFollowers().size(), 6);
        Assert.assertEquals(follower.getFollowing().size(), 1);

        follower.removeFollowing(user);
        Assert.assertEquals(user.getFollowers().size(), 5);
        Assert.assertEquals(follower.getFollowing().size(), 0);
    }

    @Test
    public void testUserGetKweets(){
        User user = mockDatabase.userList.get(1);
        Assert.assertEquals(user.getKweets().size(), 3);

        user = mockDatabase.userList.get(5);
        Assert.assertEquals(user.getKweets().size(), 1);
    }

    @Test
    public void testUserAddKweets(){
        User user = mockDatabase.userList.get(5);
        Assert.assertEquals(user.getKweets().size(), 1);

        user.addKweet(new Kweet("Today is a good day :D", null, null, user));
        Assert.assertEquals(user.getKweets().size(), 2);
    }

    @Test
    public void testUserKweetReaction(){
        User user = mockDatabase.userList.get(1);
        Kweet kweet = user.getKweets().get(1);
        kweet.setId(new Long(1001));

        Assert.assertEquals(kweet.getReactions().size(), 0);
        kweet.addReaction(new Kweet("This is a test", kweet.getId(), user.getName(), user));
        Assert.assertEquals(kweet.getReactions().size(), 1);

    }

    @Test
    public void testUserRemoveKweets(){
        User user = mockDatabase.userList.get(1);
        Assert.assertEquals(user.getKweets().size(), 3);

        Kweet kweet = user.getKweets().get(1);
        user.removeKweet(kweet);
        Assert.assertEquals(user.getKweets().size(), 2);

    }
}
