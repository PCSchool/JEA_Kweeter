package Domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.List;

//@Table(name = "User")
@NamedQuery(name = "User.all", query = "SELECT u FROM User as u")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable=false)
    private Long id;

    private String username;
    private String name;
    private String password;
    private String biography;
    //private Blob image;
    private String location;
    private String role;   // <-- make enumaration or implement from main class
    private List<Kweet> kweetList;
    private List<User> followers;
    private List<User> following;

    public User(){

    }

    public User(String username, String name, String password, String biography, String location, String role) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.biography = biography;
        this.location = location;
        this.role = role;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Kweet> getKweetList() {
        return kweetList;
    }

    public void addKweetList(Kweet kweet) {
        kweetList.add(kweet);
    }
}
