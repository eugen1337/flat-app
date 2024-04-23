package back.infrastructure.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws")
public class WSController implements ITransporter {

    // private final static ConcurrentHashMap<String, List<String>> mapLoginId = new
    // ConcurrentHashMap<>();
    private final static ConcurrentLinkedQueue<Session> queue = new ConcurrentLinkedQueue<>();
    private final static ConcurrentHashMap<String, Session> mapLoginSession = new ConcurrentHashMap<>();
    private final static ConcurrentHashMap<Session, String> mapSessionLogin = new ConcurrentHashMap<>();

    @Override
    public void sendToClient(String login, String message) {
        try {
            Session session = mapLoginSession.get(login);
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            System.out.println("wscontroller error:" + e.getMessage());
        }

    }

    @Override
    public List<String> getActiveClientNames() {
        return new ArrayList<>(mapLoginSession.keySet());
    }

    @OnOpen
    public void connectionOpened(Session session) {
        queue.add(session);
    }

    @OnClose
    public void connectionClosed(Session session) {
        String login = mapSessionLogin.remove(session);
        mapLoginSession.remove(login);
    }

    @OnMessage
    public void addClient(Session session, String login) {
        mapLoginSession.put(login, session);
        mapSessionLogin.put(session, login);
    }

}
