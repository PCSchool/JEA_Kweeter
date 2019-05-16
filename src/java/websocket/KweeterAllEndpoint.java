package websocket;

import com.google.gson.Gson;
import entities.Kweet;
import services.KweetService;
import websocket.configurator.UserConfigurator;
import websocket.decoders.KweetDecoder;
import websocket.encoders.KweetEncoder;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//ws://localhost:8080/maven/api/profile
@ServerEndpoint(value = "/api/profile", ///api/profile/{id}
    encoders = {KweetEncoder.class},
    decoders = {KweetDecoder.class},
    configurator = UserConfigurator.class)
public class KweeterAllEndpoint {

    private static final Logger logger = Logger.getLogger(KweeterAllEndpoint.class.getName());
    private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    private static final Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<>());

    @Inject private KweetService kweetService;

    @OnOpen
    public void open(Session session, EndpointConfig c){
        try {
            session.getBasicRemote().sendText("Connected");
            System.out.println(c.getUserProperties());

        } catch (IOException e) {
            e.printStackTrace();
        }
        sessions.put(session.getId(), session);
        //peers.add(session);
    }

    @OnMessage
    public void message(Session session, Kweet msg){
        try{
            if(msg instanceof Kweet){
                Kweet kweet = (Kweet)msg;
                System.out.println("KweeterAllEndpoint - instance of kweet");

                Gson gson = new Gson();
                String str = gson.toJson(kweet);
                for(Session s: session.getOpenSessions()){
                    if(s.isOpen()){
                        s.getBasicRemote().sendText(str);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void close(Session session){
        //peers.remove(session);
        sessions.remove(session.getId());
    }

    @OnError
    public void error(Throwable t){
        System.out.println("Error in KweeterAllEndPoint.class.");
    }


}
