package websocket;

import javax.websocket.OnMessage;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.nio.ByteBuffer;

//@ServerEndpoint("/receive")
public class KweeterReceiveEndpoint {
    @OnMessage
    public void textMessage(Session session, String msg){
        System.out.println(msg);
    }

    @OnMessage
    public void binaryMessage(Session session, ByteBuffer msg){
        System.out.println(msg);
    }

    @OnMessage
    public void pongMessage(Session session, PongMessage msg){
        System.out.println(msg);
    }
}
