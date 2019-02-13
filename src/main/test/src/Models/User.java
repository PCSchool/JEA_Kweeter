package src.Models;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import src.Models.Kweet;
import Domain.Roles;

public class User {

    //Variables
    private Long id;

    private String username;
    private String name;
    private String password;
    private String biography;
    private String location;
    private Roles role;
    private static final String PASSWORD_REGEX="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,26}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private List<Kweet> kweets;

    private List<User> followers;

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

    public User(Long id, String username, String name, String password, String biography, String location, Roles role) {
        if(username.isEmpty() || name.isEmpty() || password.isEmpty()){
            throw new InvalidParameterException("User: parameters username, name and password cant be empty.");
        }

        this.id = id;
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

    public boolean addFollowers(User user){
        if(followers.contains(user)){
            return false;
        }
        followers.add(user);
        return true;
    }

    public void createKweet(Kweet kweet){
        if(kweet.validation()){
            kweets.add(kweet);
        }
    }

    public void updateProfile(String name, String biography, String location){
        this.name = name;
        this.biography = biography;
        this.location = location;
    }

    public void updateUserRole(User user, Roles role){
        if(this.role == Roles.ADMINISTRATOR || this.role == Roles.MODERATOR){
            user.setRole(role);
        }
    }

    public ArrayList<ArrayList<Kweet>> getAllKweetsFollowers(){
        ArrayList<ArrayList<Kweet>> kweetList = new ArrayList<ArrayList<Kweet>>();
        if(this.role == Roles.ADMINISTRATOR || this.role == Roles.MODERATOR){
            for(User f : followers){
                kweetList.add((ArrayList<Kweet>) f.getKweets());
            }
        }
        return kweetList;
    }

    public void removeKweet(Kweet kweet, User user){
        if(this.role == Roles.ADMINISTRATOR || this.role == Roles.MODERATOR){
            System.out.println("User: removeKweet(Kweet kweet) - remove");
        }
    }

    @Override
    public String toString() {
        return username;
    }
}
