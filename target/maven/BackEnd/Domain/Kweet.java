package Domain;

import javax.persistence.*;
import java.time.LocalDateTime;

//@NamedQuery(name = "Kweet.all", query = "SELECT k FROM Kweet as k")
@Entity
public class Kweet {


    //@Column(name = "id", updatable = false, nullable=false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;            //max 160 charachters
    //private String[] tags;          //max 5 tags of max 50 words
    private LocalDateTime localDate;    // <-- might chance back to DateTime
    private Long reaction;

    /* empty constructor */
    public Kweet(){
    }

    /**
     * Constructor Kweet
     * @param text
     * @param reaction
     */
    public Kweet(String text, Long reaction) {
        this.text = text;
        this.reaction = reaction;
        this.localDate = LocalDateTime.now();
    }

    /**
     * Constructor Kweet for kweets that are no reaction to another kweet
     * @param text
     */
    public Kweet(String text) {
        this.text = text;
        this.reaction = new Long(-1);
        this.localDate = LocalDateTime.now();
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getLocalDate() {
        return localDate;
    }

    public Long getReaction() {
        return reaction;
    }

    /**
     * @return true if the requirements for a kweet are met
     * else return false
     */
    public boolean validate(String text){
        if(text.length() <= 160){
            return  true;
        }
        return false;
    }
}
