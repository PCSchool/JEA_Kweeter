package Domain;

import javax.persistence.*;

import Domain.User;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;

//@NamedQuery(name = "Kweet.all", query = "SELECT k FROM Kweet as k")
@Entity
public class Kweet {


    //@Column(name = "id", updatable = false, nullable=false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;                 //max 160 charachters
    private LocalDateTime createDate;    // <-- might chance back to DateTime
    private Long inReplyToId;
    private String inReplyToName;

    @OneToMany
    private List<Kweet> reactions;

    /* empty constructor */
    public Kweet(){
    }

    /**
     * Constructor Kweet
     * @param id id of the Kweet
     * @param text value of the tweet, must be between 1 - 160 charachters
     * @param inReplyToId default null, otherwise contains reply id of Kweet
     * @param inReplyToName default null, otherwise contains name user of Kweet
     */
    public Kweet(Long id, String text, Long inReplyToId, String inReplyToName) {
        if(text.isEmpty()){
            throw new InvalidParameterException("Kweet: parameter text cant be null.");
        }
        this.id = id;
        this.text = text;
        this.inReplyToId = inReplyToId;
        this.inReplyToName = inReplyToName;
        this.createDate = LocalDateTime.now();
        if(!validation()){
            throw new IllegalArgumentException("Kweet: parameters invalid");
        }
    }

    public Long getId() {
            return id;
        }
    public String getText() {
            return text;
        }

    public LocalDateTime getCreateDate() {
            return createDate;
        }

    public Long getInReplyToId() {
            return inReplyToId;
        }
    public String getInReplyToName() {
            return inReplyToName;
        }

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
