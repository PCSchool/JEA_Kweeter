package entities;

import javax.persistence.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

//@Table(name = "User")

//@Table(name = "User")
//@Table(name = "User", uniqueConstraints = {@UniqueConstraint( columnNames = {"username"})})

@Entity
@NamedQuery(name = "User.getAll", query = "SELECT u FROM User as u")
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
public class User {

    //Variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //GenerationType.AUTO //@Column(name = "user_id", updatable = false, nullable=false)
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
    @JoinColumn(name = "kweet_id", referencedColumnName = "user_id")
    private List<Kweet> kweets;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_following",
        joinColumns = {@JoinColumn(name = "follower_id", referencedColumnName = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "following_id", referencedColumnName = "user_id")}
    )
    private List<User> following;

    @ManyToMany(mappedBy = "follower", cascade = CascadeType.PERSIST)
    private List<User> follower;

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
        return follower;
    }

    public List<User> getFollowing() {
        return following;
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
        if (!following.contains(user)) {
            following.add(user);
            user.addFollower(this);
        }
    }

    public void addFollower(User user) {
        if (!follower.contains(user)) {
            follower.add(user);
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
            for (User f : follower) {
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
        if (follower.contains(user)) {
            follower.remove(user);
        }
    }

    public void removeFollowing(User user) {
        if (following.contains(user)) {
            following.remove(user);
            user.removeFollower(this);
        }
    }
}


