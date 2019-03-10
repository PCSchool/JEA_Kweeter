package entities;

import javax.persistence.*;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*@NamedQueries({@NamedQuery(
        name = "Kweet.getAll",
        query = "SELECT k FROM Kweet k"
), @NamedQuery(
        name = "Kweet.count",
        query = "SELECT k FROM Kweet k"
)})*/
@Entity
public class Kweet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "kweet_id", updatable = false, nullable=false)
    private Long id;
    private String message;                 //max 160 charachters
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createDate;
    private Long inReplyToId;
    private String inReplyToName;

    //(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinTable(name = "KWEET_ID")
    private List<Kweet> reactions = new ArrayList<>();

    /**
     * empty constructor
     */
    public Kweet(){
    }

    /**
     * Constructor entities.Kweet
     * @param message value of the tweet, must be between 1 - 160 charachters
     * @param inReplyToId default null, otherwise contains reply id of entities.Kweet
     * @param inReplyToName default null, otherwise contains name user of entities.Kweet
     */
    public Kweet(String message, Long inReplyToId, String inReplyToName) {
        /*if(text.isEmpty()){
            throw new InvalidParameterException("entities.Kweet: parameter text cant be null.");
        }*/

        this.message = message;
        this.inReplyToId = inReplyToId;
        this.inReplyToName = inReplyToName;

        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //this.createDate = new Date();

        /*if(!validation()){
            throw new IllegalArgumentException("entities.Kweet: parameters invalid");
        }*/
    }

    public Kweet(String message, Long inReplyToId, String inReplyToName, Date date) {
        /*if(text.isEmpty()){
            throw new InvalidParameterException("entities.Kweet: parameter text cant be null.");
        }*/

        this.message = message;
        this.inReplyToId = inReplyToId;
        this.inReplyToName = inReplyToName;
        this.createDate = date;

        /*if(!validation()){
            throw new IllegalArgumentException("entities.Kweet: parameters invalid");
        }*/
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
    public void setId(Long id){this.id = id;}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return inReplyToId
     */
    public Long getInReplyToId() {
        return inReplyToId;
    }

    /**
     * @param replyToId
     */
    public void setInReplyToId(long replyToId){this.inReplyToId = replyToId;}

    /**
     * @return creator
     */
    public User getCreator(){return user;}

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return inReplyToName
     */
    public String getInReplyToName() {
        return inReplyToName;
    }

    /**
     * @param replyToName
     */
    public void setInReplyToName(String replyToName){this.inReplyToName = replyToName;}

    /**
     * @return reactions
     */
    public List<Kweet> getReactions() {
        return reactions;
    }

    /**
     * @return true if validation is correct
     *          false if validation is incorrect
     */
    private boolean validation(){
        boolean isValid = true;

        if(message.length() >= 160){
            isValid = false;
        }

        return isValid;
    }

    /**
     * @param kweet - add reaction to a kweet
     */
    public void addReaction(Kweet kweet){
        if(!reactions.contains(kweet)){
            reactions.add(kweet);
        }
    }
}
