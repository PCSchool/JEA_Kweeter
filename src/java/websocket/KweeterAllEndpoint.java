package websocket;

import com.google.gson.Gson;
import entities.Kweet;
import services.KweetService;
import websocket.decoders.KweetDecoder;
import websocket.encoders.KweetEncoder;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

//@ServerEndpoint(value = "/maven/api", ///api/profile/{id}
//    encoders = {KweetEncoder.class},
//    decoders = {KweetDecoder.class})
public class KweeterAllEndpoint {

    private static final Logger logger = Logger.getLogger("KweeterAllEndPoint");
    private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @Inject private KweetService kweetService;

    @OnOpen
    public void open(Session session, EndpointConfig c, @PathParam("username") String username){
        System.out.println("New session added.");
        peers.add(session);
    }

    @OnMessage
    public void message(Session session, Kweet msg){
        try{
            if(msg instanceof Kweet){
                Kweet kweet = (Kweet)msg;
                System.out.println("KweeterAllEndpoint - instance of kweet");
            }

            Gson gson = new Gson();
            String str = gson.toJson(msg);
            for(Session s: session.getOpenSessions()){
                if(s.isOpen()){
                    s.getBasicRemote().sendText(str);
                }
            }
            //for each peer connected to endpoint, send text)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void close(Session session){
        peers.remove(session);
    }

    @OnError
    public void error(Throwable t){
        System.out.println("Error in KweeterAllEndPoint.class.");
    }


}
