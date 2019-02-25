package entities;

import javax.persistence.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({@NamedQuery(
        name = "User.getAll",
        query = "SELECT u FROM User u"
), @NamedQuery(
        name = "User.count",
        query = "SELECT u FROM User u"
), @NamedQuery(
        name = "User.getFollowers",
        query = "SELECT u FROM User u"
),@NamedQuery(
        name = "User.getFollowing",
        query = "SELECT u FROM User u"
)})
@Table(name = "user")
public class User {

    //Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //GenerationType.AUTO //@Column(name = "user_id", updatable = false, nullable=false)
    private Long id;


    private String username;
    private String name;
    private String password;
    private String biography;
    private String location;
    private entities.Roles role;

    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,26}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    @OneToMany
    @JoinColumn(name = "kweet_id", referencedColumnName = "id")
    private List<Kweet> kweets;

    @ManyToMany
    @JoinTable(
        name = "user_following",
        joinColumns = { @JoinColumn(name = "follower_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "following_id", referencedColumnName = "id")}
    )
    private List<User> followings;

    @ManyToMany(mappedBy = "followings")
    private List<User> followers;

    //Constructor
    // empty constructor
    public User() {
    }

    public User(String username, String name, String password, String biography, String location, String role) {
        if (username.isEmpty() || name.isEmpty() || password.isEmpty()) {
            throw new InvalidParameterException("User: parameters username, name and password cant be empty.");
        }

        this.username = username;
        this.name = name;
        this.password = password;
        this.biography = biography;
        this.location = location;
        if(role.equals(Roles.ADMINISTRATOR.toString())){
            this.role = Roles.ADMINISTRATOR;
        }
        else if(role.equals(Roles.MODERATOR.toString())){
            this.role = Roles.MODERATOR;
        }
        else{
            this.role = Roles.STANDARD;
        }
        System.out.println(this.username + " " + this.password);
    }

    private boolean validation() {
        boolean isValid = true;

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            isValid = false;
        }

        if (username.length() < 6 || username.length() > 26) {
            isValid = false;
        }

        return isValid;
    }



    //Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public List<Kweet> getKweets() {
        return kweets;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowing() {
        return followings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Methods CREATE
    public void addUser(User user) {
        if (this.role == Roles.ADMINISTRATOR || this.role == Roles.MODERATOR) {
            System.out.println("addUser: add new user to pm");
        }
    }

    public void addFollowing(User user) {
        if (!followings.contains(user)) {
            followings.add(user);
            user.addFollower(this);
        }
    }

    public void addFollower(User user) {
        if (!followers.contains(user)) {
            followers.add(user);
        }
    }

    public void addKweet(Kweet kweet) {
        if (!kweets.contains(kweet)) {
            kweets.add(kweet);
        }
    }

    //Methods UPDATE
    public void updateProfile(String name, String biography, String location) {
        this.name = name;
        this.biography = biography;
        this.location = location;
    }

    public void updateUserRole(User user, Roles role) {
        if (this.role == Roles.ADMINISTRATOR || this.role == Roles.MODERATOR) {
            user.setRole(role);
        }
    }

    //  Methods GET
    public ArrayList<ArrayList<Kweet>> getAllKweetsFollowers() {
        ArrayList<ArrayList<Kweet>> kweetList = new ArrayList<ArrayList<Kweet>>();
        if (this.role == Roles.ADMINISTRATOR || this.role == Roles.MODERATOR) {
            for (User f : followers) {
                kweetList.add((ArrayList<Kweet>) f.getKweets());
            }
        }
        return kweetList;
    }

    // Methods REMOVE
    public void removeUserKweet(Kweet kweet, User user) {
        if (this.role == Roles.ADMINISTRATOR || this.role == Roles.MODERATOR) {
            user.removeKweet(kweet);
        }
    }

    public void removeKweet(Kweet kweet) {
        kweets.remove(kweet);
    }

    public void removeFollower(User user) {
        if (followers.contains(user)) {
            followers.remove(user);
        }
    }

    public void removeFollowing(User user) {
        if (followings.contains(user)) {
            followings.remove(user);
            user.removeFollower(this);
        }
    }
}


