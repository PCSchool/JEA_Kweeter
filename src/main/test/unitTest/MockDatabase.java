package unitTest;

import Domain.Roles;
import src.Models.Kweet;
import src.Models.User;

import java.util.ArrayList;
import java.util.List;

public class MockDatabase {

    public List<User> userList = new ArrayList<>();

    public MockDatabase(){
        //User(Long id, String username, String name, String password, String biography, String location, Roles role) {

        // add 10 users
        userList.add(new User("Kat10101", "Lynda Buckley", "8+bq?7kKc,'U", "Now is the winter of our discontent", "Netherlands",Roles.STANDARD));
        userList.add(new User("ConnorYu", "Connor Yu", "P2VFZ~nQ[>>P", "And all the clouds that lour'd upon our house","Netherlands", Roles.STANDARD));
        userList.add(new User("Lupita12", "Danniella Neville", "8nbU/E/%d~F", "In the deep bosom of the ocean buried.","Netherlands", Roles.STANDARD));
        userList.add(new User("Henrietta2", "Lola-Rose Long", "M<[33z6&Y>g", "Now are our brows bound with victorious wreaths;", "Netherlands",Roles.STANDARD));
        userList.add(new User("PEGSHar21", "Fredrick Gregory", "?J9aQyXs=W]5", "Our bruised arms hung up for monuments", "Netherlands",Roles.STANDARD));
        userList.add(new User("JeffJeff", "Rome Solomon", "Q)^UG9kAg=d+", "Our stern alarums changed to merry meetings","Netherlands", Roles.STANDARD));
        userList.add(new User("LanceLA", "Rowena Stacey", "gaCAEc#)N9#)", "And now, instead of mounting barded steeds", "Netherlands",Roles.STANDARD));
        userList.add(new User("JadAJA", "Enrique Norman", "NbbEpF%RW77~", "I, that am rudely stamp'd, and want love's majesty","Netherlands", Roles.MODERATOR));
        userList.add(new User("ULA_ULA", "Herman Shelton", "k%m3^Q$nZE,g", "To strut before a wanton ambling nymph", "Netherlands",Roles.MODERATOR));
        userList.add(new User("FidelA", "Solomon Betts", "/S(4j5AP/8y=", "I, that am curtail'd of this fair proportion,", "Netherlands",Roles.ADMINISTRATOR));

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

        // add 10 kweets
        //Kweet(Long id, String text, Long inReplyToId, String inReplyToName) {
        userList.get(1).addKweet(new Kweet(new Long(100), "on Can built must no check she pretty going aften otherwise roof old get ", null, null));
        userList.get(2).addKweet(new Kweet(new Long(101), "disposed finished mrs knows ample has once lady but not am hope get by bred Own", null, null));
        userList.get(3).addKweet(new Kweet(new Long(102), "Subjects ham as or would yet to forming to right breakfast do thus we what people only Led juvenile oh newspaper but dine concealed offending when", null, null));
        userList.get(4).addKweet(new Kweet(new Long(103), "Admitting have you any offended nay snug terms excuse do temper though compliment should", null, null));
        userList.get(5).addKweet(new Kweet(new Long(104), "The warmth should why was zealously in He Answer old and Oh is simplicity making begin in barton projection", null, null));
        userList.get(6).addKweet(new Kweet(new Long(105), "His you Entered around out since wholly become Could be An Money be views desirous", null, null));
        userList.get(7).addKweet(new Kweet(new Long(106), "Inquietude next right middleton speedily being can cant merely at met you mrs ya formed", null, null));
        userList.get(8).addKweet(new Kweet(new Long(107), "True felicity here hence why i Collected true an jennings came What few out offices ", null, null));
        userList.get(9).addKweet(new Kweet(new Long(108), "Dear my totally saw does in plan between drawn", null, null));
        userList.get(10).addKweet(new Kweet(new Long(109), "Admitting have you any offended nay snug terms excuse do temper though compliment should", null, null));
    }
}
