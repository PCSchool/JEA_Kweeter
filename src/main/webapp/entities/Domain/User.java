package Domain;

import javax.persistence.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import Domain.Kweet;
import Domain.Roles;

//@Table(name = "User")
//@NamedQuery(name = "User.all", query = "SELECT u FROM User as u")
@Entity(name = "User")
public class User {

    //Variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "id", updatable = false, nullable=false)
    private Long id;

    private String username;
    private String name;
    private String password;
    private String biography;
    private String location;
    private Roles role;
    private static final String PASSWORD_REGEX="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,26}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    @OneToMany
    private List<Kweet> kweets;

    @ManyToMany
    private List<User> followers;

    @ManyToMany
    private List<User> following;

    //Constructor
    public User(String username, String name, String password, String biography, String location, Roles role) {
        if(username.isEmpty() || name.isEmpty() || password.isEmpty()){
            throw new InvalidParameterException("User: parameters username, name and password cant be empty.");
        }

        this.username = username;
        this.name = name;
        this.password = password;
        this.biography = biography;
        this.location = location;
        this.role = role;

        if(validation()){
            throw new IllegalArgumentException("User: parameters dont meet the conditions.");
        }
    }

    private boolean validation(){
        boolean isValid = true;

        if(!PASSWORD_PATTERN.matcher(password).matches()){
            isValid = false;
        }

        if(username.length() < 6 || username.length() > 26){
            isValid = false;
        }

        return isValid;
    }

    public User() {
    }

    //Getters & Setters
    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }

    public String getBiography() {
        return biography;
    }

    public String getLocation() {
        return location;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role){
        this.role = role;
    }

    public List<Kweet> getKweets() {
        return kweets;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public String getName() {
        return name;
    }

    // Methods
    public boolean addFollowing(User user){
        if(following.contains(user)){
            return false;
        }
        following.add(user);
        return true;
    }

    public boolean createKweet(String text, Long inReplyToId, String inReplyToName){
        Kweet kweet = new Kweet(text, inReplyToId, inReplyToName);
        if(kweet.validation()){
            kweets.add(kweet);
            return true;
        }
        return false;
    }

    private void updateProfile(String name, String biography, String location){
        this.name = name;
        this.biography = biography;
        this.location = location;
    }

    private void updateUserRole(User user, Roles role){
        if(this.role == Roles.ADMINISTRATOR || this.role == Roles.MODERATOR){
            user.setRole(role);
        }
    }

    private void removeKweet(Kweet kweet){
        if(this.role == Roles.ADMINISTRATOR || this.role == Roles.MODERATOR){
            System.out.println("User: removeKweet(Kweet kweet) - remove");
        }
    }
}
