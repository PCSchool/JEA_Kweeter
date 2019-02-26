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

    /**
     * empty constructor
     */
    public User() {
    }

    /**
     * Main constructor for user
     * @param username      must be unique, min 6 charachters, max 26 charachters
     * @param name          cant be empty
     * @param password      must contain 1 special charachter, 1 lowercase, 1 uppercase, min 6 charachters, max 26 charachters
     * @param biography     default "", cant be longer then 160 charachters
     * @param location      default ""
     * @param role          default STANDARD
     */
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

    /**
     * validation for the password and username
     * @return true if validation is sufficient, otherwise false
     */
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

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @return biography
     */
    public String getBiography() {
        return biography;
    }
    /**
     * @param biography, max 160 charachters
     */
    public void setBiography(String biography) {
        if(biography.length() < 160){
            this.biography = biography;
        }
    }
    /**
     * @return location
     */
    public String getLocation() {
        return location;
    }
    /**
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * @return role
     */
    public Roles getRole() {
        return role;
    }
    /**
     * @param role
     */
    public void setRole(Roles role) {
        this.role = role;
    }
    /**
     * @return kweets
     */
    public List<Kweet> getKweets() {
        return kweets;
    }
    /**
     * @return followers
     */
    public List<User> getFollowers() {
        return followers;
    }
    /**
     * @return following
     */
    public List<User> getFollowing() {
        return followings;
    }
    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param user - add user to following
     */
    public void addFollowing(User user) {
        if (!followings.contains(user)) {
            followings.add(user);
            user.addFollower(this);
        }
    }

    /**
     * @param user - add Follower to user
     */
    public void addFollower(User user) {
        if (!followers.contains(user)) {
            followers.add(user);
        }
    }

    /**
     * @param kweet - create kweet
     */
    public void addKweet(Kweet kweet) {
        if (!kweets.contains(kweet)) {
            kweets.add(kweet);
        }
    }

    /**
     * Update the profile
     * @param name
     * @param biography
     * @param location
     */
    public void updateProfile(String name, String biography, String location) {
        this.name = name;
        this.biography = biography;
        this.location = location;
    }

    /**
     * Update the role
     * @param user
     * @param role  only ADMINISTRATOR or MODERATOR can use this function
     */
    public void updateUserRole(User user, Roles role) {
        if (this.role == Roles.ADMINISTRATOR || this.role == Roles.MODERATOR) {
            user.setRole(role);
        }
    }

    /**
     * @return all kweets of the followers of the user
     */
    public ArrayList<ArrayList<Kweet>> getAllKweetsFollowers() {
        ArrayList<ArrayList<Kweet>> kweetList = new ArrayList<ArrayList<Kweet>>();
        if (this.role == Roles.ADMINISTRATOR || this.role == Roles.MODERATOR) {
            for (User f : followers) {
                kweetList.add((ArrayList<Kweet>) f.getKweets());
            }
        }
        return kweetList;
    }

    /**
     * Remove a specific kweet of the user
     * @param kweet
     * @param user
     */
    public void removeUserKweet(Kweet kweet, User user) {
        if (this.role == Roles.ADMINISTRATOR || this.role == Roles.MODERATOR) {
            user.removeKweet(kweet);
        }
    }

    /**
     * Remove a kweet
     * @param kweet
     */
    public void removeKweet(Kweet kweet) {
        kweets.remove(kweet);
    }

    /**
     * remove a follower from followers
     * @param user
     */
    public void removeFollower(User user) {
        if (followers.contains(user)) {
            followers.remove(user);
        }
    }

    /**
     * remove a following from follorings
     * @param user
     */
    public void removeFollowing(User user) {
        if (followings.contains(user)) {
            followings.remove(user);
            user.removeFollower(this);
        }
    }
}


