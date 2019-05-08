package websocket.decoders;

import com.google.gson.Gson;
import entities.Kweet;

import javax.jms.Message;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.security.MessageDigest;

/*
The message decoder class converts WebSocket text messages into application messages by parsing JSON text.
 */
public class KweetDecoder implements Decoder.Text<Kweet> {

    public static Gson gson = new Gson();
    /**
     * Decode the given String into an object of type T.
     *
     * @param s string to be decoded.
     * @return the decoded message as an object of type T
     */
    @Override
    public Kweet decode(String s) throws DecodeException {
        Kweet kweet = null;
        if(willDecode(s)){
            kweet = gson.fromJson(s, Kweet.class);
        }
        return kweet;
    }

    /**
     * Answer whether the given String can be decoded into an object of type T.
     *
     * @param s the string being tested for decodability.
     * @return whether this decoder can decoded the supplied string.
     */
    @Override
    public boolean willDecode(String s) {
        try{
            Kweet kweet = gson.fromJson(s, Kweet.class);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    /**
     * This method is called with the endpoint configuration object of the
     * endpoint this decoder is intended for when
     * it is about to be brought into service.
     *
     * @param config the endpoint configuration object when being brought into
     *               service
     */
    @Override
    public void init(EndpointConfig config) {

    }

    /**
     * This method is called when the decoder is about to be removed
     * from service in order that any resources the encoder used may
     * be closed gracefully.
     */
    @Override
    public void destroy() {

    }
}
