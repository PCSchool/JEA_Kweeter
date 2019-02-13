package src.Models;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;

public class Kweet {

    private Long id;
    private String text;                 //max 160 charachters
    private LocalDateTime createDate;    // <-- might chance back to DateTime
    private Long inReplyToId;
    private String inReplyToName;
    private List<Kweet> reactions;

    /* empty constructor */
    public Kweet(){
    }

    /**
     * Constructor Kweet
     * @param text
     * @param inReplyToId
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
    }

    /**
     * Constructor Kweet for kweets that are no reaction to another kweet
     * @param text
     */
    public Kweet(String text) {
        this.text = text;
        this.inReplyToId = null;
        this.inReplyToName = null;
        this.createDate = LocalDateTime.now();
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
    public boolean validation(){
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
