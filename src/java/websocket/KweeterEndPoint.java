package websocket;

import com.google.gson.Gson;

import javax.faces.bean.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//ws://localhost:8080/maven/echo
@ServerEndpoint(value = "/echo")
public class KweeterEndPoint{
    private static final Logger logger = Logger.getLogger("KweeterEndPoint");

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        System.out.println("New session added.");
        logger.log(Level.INFO, "Connection opened.");

        try {
            session.getBasicRemote().sendText("Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.getUserProperties().put("text", "demo test");
    }

    @OnMessage
    public void message(Session session, String msg){
        try{
            for(Session s: session.getOpenSessions()){
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


