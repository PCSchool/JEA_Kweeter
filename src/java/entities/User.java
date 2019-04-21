package entities;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@NamedQueries({@NamedQuery(
        name = "User.getAll",
        query = "SELECT u FROM User u"
), @NamedQuery(
        name = "User.count",
        query = "SELECT k FROM Kweet k INNER JOIN User u ON k.user = u"
), @NamedQuery(
        name = "User.getAllFollowers",
        query = "SELECT u.followers FROM User u WHERE u.id = :id "
),@NamedQuery(
        name = "User.getAllFollowings",
        query = "SELECT u.followings FROM User u WHERE u.id = :id"
), @NamedQuery(
        name = "User.findByUsername",
        query = "SELECT u FROM User u where u.username LIKE :username"
),@NamedQuery(
        name = "User.findBySingleUsername",
        query = "SELECT u FROM User u where u.username = :username"
), @NamedQuery(
        name = "User.findById",
        query = "SELECT u FROM User u where u.id = :id"
),@NamedQuery(
        name = "User.update",
        query = "UPDATE User u set u.name = :name, u.biography = :biography, u.location = :location, u.website = :website, u.role = :role where u.id = :id"
),@NamedQuery(
        name = "User.validate",
        query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password"
)})
@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String name;
    private String password;
    private String biography;
    private String location;
    private String website;
    private boolean active;
    private entities.Roles role;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Kweet> kweets;

    @JsonbTransient
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> followers;

    @ManyToMany(mappedBy = "followers", targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> followings;

    public User() {
    }

    public User(String username, String name, String password, String biography, String location, String role) {
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

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @JsonbTransient
    public List<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(List<Kweet> kweets) {
        this.kweets = kweets;
    }

    public List<User> getFollowers() {
        return followers;
    }

    @JsonbTransient
    public List<User> getFollowings() {
        return followings;
    }

    public void setFollowings(List<User> followings) {
        this.followings = followings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public void addFollowing(User user) {
        followings.add(user);
    }

    public void addKweet(Kweet kweet) {
        kweets.add(kweet);
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
        followers.remove(user);
    }

    /**
     * remove a following from follorings
     * @param user
     */
    public void removeFollowing(User user) {
        followings.remove(user);
    }
}


