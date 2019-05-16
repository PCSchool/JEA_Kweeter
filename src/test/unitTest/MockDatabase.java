package unitTest;

import entities.Roles;
import entities.User;
import entities.Kweet;

import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;

public class MockDatabase {

    public List<User> userList = new ArrayList<>();

    public MockDatabase(){
        // add 10 users
        userList.add(new User(1L, "Kat10101", "Lynda Buckley", "8+bq?7kKc,'U", "Now is the winter of our discontent", "Netherlands","STANDARD"));
        userList.add(new User(2L, "ConnorYu", "Connor Yu", "P2VFZ~nQ[>>P", "And all the clouds that lour'd upon our house","Netherlands", "STANDARD"));
        userList.add(new User(3L, "Lupita12", "Danniella Neville", "8nbU/E/%d~F", "In the deep bosom of the ocean buried.","Netherlands", "STANDARD"));
        userList.add(new User(4L, "Henrietta2", "Lola-Rose Long", "M<[33z6&Y>g", "Now are our brows bound with victorious wreaths;", "Netherlands","STANDARD"));
        userList.add(new User(5L, "PEGSHar21", "Fredrick Gregory", "?J9aQyXs=W]5", "Our bruised arms hung up for monuments", "Netherlands","STANDARD"));
        userList.add(new User(6L, "JeffJeff", "Rome Solomon", "Q)^UG9kAg=d+", "Our stern alarums changed to merry meetings","Netherlands", "STANDARD"));
        userList.add(new User(7L, "LanceLA", "Rowena Stacey", "gaCAEc#)N9#)", "And now, instead of mounting barded steeds", "Netherlands","STANDARD"));
        userList.add(new User(8L, "JadAJA", "Enrique Norman", "NbbEpF%RW77~", "I, that am rudely stamp'd, and want love's majesty","Netherlands", "MODERATOR"));
        userList.add(new User(9L, "ULA_ULA", "Herman Shelton", "k%m3^Q$nZE,g", "To strut before a wanton ambling nymph", "Netherlands", "MODERATOR"));
        userList.add(new User(10L, "FidelA", "Solomon Betts", "/S(4j5AP/8y=", "I, that am curtail'd of this fair proportion,", "Netherlands","ADMINISTRATOR"));

        userList.get(1).addFollowing(userList.get(2));
        userList.get(1).addFollowing(userList.get(3));
        userList.get(1).addFollowing(userList.get(4));
        userList.get(2).addFollowing(userList.get(4));
        userList.get(3).addFollowing(userList.get(6));
        userList.get(4).addFollowing(userList.get(8));
        userList.get(5).addFollowing(userList.get(1));
        userList.get(6).addFollowing(userList.get(1));
        userList.get(7).addFollowing(userList.get(1));
        userList.get(8).addFollowing(userList.get(1));
        userList.get(9).addFollowing(userList.get(1));
        userList.get(10).addFollowing(userList.get(1));

        // add 10 kweets .getKweets().add(
        userList.get(1).addKweet(new Kweet( "on Can built must no check she pretty going aften otherwise roof old get "));
        userList.get(2).addKweet(new Kweet( "disposed finished mrs knows ample has once lady but not am hope get by bred Own"));
        userList.get(3).addKweet(new Kweet( "Subjects ham as or would yet to forming to right breakfast do thus we what people only Led juvenile oh newspaper but dine concealed offending when"));
        userList.get(4).addKweet(new Kweet( "Admitting have you any offended nay snug terms excuse do temper though compliment should"));
        userList.get(5).addKweet(new Kweet( "The warmth should why was zealously in He Answer old and Oh is simplicity making begin in barton projection"));
        userList.get(6).addKweet(new Kweet( "His you Entered around out since wholly become Could be An Money be views desirous"));
        userList.get(7).addKweet(new Kweet("Inquietude next right middleton speedily being can cant merely at met you mrs ya formed"));
        userList.get(8).addKweet(new Kweet( "True felicity here hence why i Collected true an jennings came What few out offices "));
        userList.get(9).addKweet(new Kweet("Dear my totally saw does in plan between drawn"));
        userList.get(10).addKweet(new Kweet("Admitting have you any offended nay snug terms excuse do temper though compliment should"));
    }
}
