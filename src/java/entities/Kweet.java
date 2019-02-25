package entities;

import javax.persistence.*;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;

/*@NamedQueries({@NamedQuery(
        name = "Kweet.getAll",
        query = "SELECT k FROM Kweet k"
), @NamedQuery(
        name = "Kweet.count",
        query = "SELECT k FROM Kweet k"
)})*/
@Entity(name = "classes.Kweet")
public class Kweet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "kweet_id", updatable = false, nullable=false)
    private Long id;
    private String text;                 //max 160 charachters
    private LocalDateTime createDate;    // <-- might chance back to DateTime
    private Long inReplyToId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User creator;

    private String inReplyToName;

    @OneToMany
    private List<Kweet> reactions;

    /* empty constructor */
    public Kweet(){
    }

    /**
     * Constructor entities.Kweet
     * @param text value of the tweet, must be between 1 - 160 charachters
     * @param inReplyToId default null, otherwise contains reply id of entities.Kweet
     * @param inReplyToName default null, otherwise contains name user of entities.Kweet
     */
    public Kweet(String text, Long inReplyToId, String inReplyToName, User creator) {
        if(text.isEmpty()){
            throw new InvalidParameterException("entities.Kweet: parameter text cant be null.");
        }

        this.text = text;
        this.inReplyToId = inReplyToId;
        this.inReplyToName = inReplyToName;
        this.createDate = LocalDateTime.now();
        this.creator = creator;

        if(!validation()){
            throw new IllegalArgumentException("entities.Kweet: parameters invalid");
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id){this.id = id;}

    public String getText() {
        return text;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Long getInReplyToId() {
        return inReplyToId;
    }

    public void setInReplyToId(long replyToId){this.inReplyToId = replyToId;}

    public User getCreator(){return creator;}

    public String getInReplyToName() {
        return inReplyToName;
    }

    public void setInReplyToName(String replyToName){this.inReplyToName = replyToName;}

    public List<Kweet> getReactions() {
        return reactions;
    }

    //check validation of kweet
    private boolean validation(){
        boolean isValid = true;

        if(text.length() >= 160){
            isValid = false;
        }

        return isValid;
    }

    public void addReaction(Kweet kweet){
        if(!reactions.contains(kweet)){
            reactions.add(kweet);
        }
    }
}
