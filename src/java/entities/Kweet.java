package entities;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NamedQueries({@NamedQuery(
        name = "Kweet.getAll",
        query = "SELECT k FROM Kweet k"
)})
@Entity
public class Kweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //AUTO
    private Long id;
    private String message;                 //max 160 charachters

    @Temporal(TemporalType.DATE)
    private Date createDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    private Kweet parent;

    @OneToMany(mappedBy = "parent")
    private List<Kweet> reactions = reactions = new ArrayList<>();

    /**
     * empty constructor
     */
    public Kweet(){
    }

    /**
     * Constructor entities.Kweet
     * @param message value of the tweet, must be between 1 - 160 charachters
     */
    public Kweet(String message, Long inReplyToId, String inReplyToName) {
        this.message = message;
    }

    public Kweet(String message) {
        if(message.isEmpty()){
            throw new InvalidParameterException("entities.Kweet: parameter text cant be null.");
        }

        if(!validation()){
            throw new IllegalArgumentException("entities.Kweet: parameters invalid");
        }
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){this.id = id;}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setReactions(List<Kweet> reactions) {
        this.reactions = reactions;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setParent(Kweet parent) {
        this.parent = parent;
    }

    public Kweet getParent() {
        return parent;
    }

    public List<Kweet> getReactions() {
        return reactions;
    }

    private boolean validation(){
        boolean isValid = true;

        if(message.length() >= 160){
            isValid = false;
        }

        return isValid;
    }

    public void addReaction(Kweet kweet){
        reactions.add(kweet);
    }
}
