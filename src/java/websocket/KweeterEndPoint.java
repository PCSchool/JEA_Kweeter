package websocket;

import com.google.gson.Gson;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.faces.bean.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

//sse server sent events
//ws://localhost:8080/maven/echo
@ServerEndpoint(value = "/echo")
public class KweeterEndPoint{
    private static final Logger logger = Logger.getLogger("KweeterEndPoint");
    private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        logger.log(Level.INFO, "Connection opened.");

        try {
            System.out.println(session);
            session.getBasicRemote().sendText("Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.getUserProperties().put("text", "demo test");
        peers.add(session);
    }

    @OnMessage
    public void message(Session session, String msg){
        try{
            System.out.println(msg);

            for(Session s: session.getOpenSessions()){
                System.out.println("Message 1.");
                if(s.isOpen()){
                    s.getBasicRemote().sendText(msg);
                }
            }
        } catch (IOException e) {
                e.printStackTrace();
        }
        final RemoteEndpoint remote = session.getBasicRemote();
        /*session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String text) {
                try {
                    ((RemoteEndpoint.Basic) remote).sendText("Got your message (" + text + "). Thanks !");
                } catch (IOException ioe) {
                    // handle send failure here
                }
            }
        });*/
    }
}

