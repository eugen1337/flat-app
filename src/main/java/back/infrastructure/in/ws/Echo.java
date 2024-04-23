package back.infrastructure.in.ws;

import java.io.IOException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/testEcho")
public class Echo {

    @OnOpen
    public void connectionOpened() {
    }

    @OnClose
    public void connectionClosed() {
    }

    @OnMessage
    public synchronized void processMessage(Session session, String message) {
        System.out.println(message);
        try {
            for (Session sess : session.getOpenSessions()) {
                if (sess.isOpen()) {
                    sess.getBasicRemote().sendText(message);
                }
            }
        } catch (IOException ioe) {
        }
    }

}
