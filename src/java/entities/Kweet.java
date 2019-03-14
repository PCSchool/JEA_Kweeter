package entities;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NamedQueries({@NamedQuery(
        name = "Kweet.getAllKweets",
        query = "SELECT k FROM Kweet k JOIN k.user u WHERE k.id = :idUser ORDER BY k.createDate DESC"
), @NamedQuery(
        name = "Kweet.getAllReactions",
        query = "SELECT k FROM Kweet k JOIN Kweet k2 WHERE k.parent = k2 AND k2.id = :idKweet"
)})
@Entity
public class Kweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //AUTO
    private Long id;
    private String message;                 //max 160 charachters
    private Date createDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User user;

    @ManyToOne
    private Kweet parent;

    @JsonbTransient
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
    public Kweet(String message) {
        if(message.length() > 160){
            throw new IllegalArgumentException("entities.Kweet: parameters invalid");
        }
        this.createDate = new Date();
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
        if(message.length() > 160){
            throw new IllegalArgumentException("entities.Kweet: parameters invalid");
        }
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

    @JsonbTransient
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

    @JsonbTransient
    public List<Kweet> getReactions() {
        return reactions;
    }

    public void addReaction(Kweet kweet){
        reactions.add(kweet);
    }
}
