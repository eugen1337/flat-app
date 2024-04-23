package back.infrastructure.websocket;

import java.util.List;

public interface ITransporter {
    public void sendToClient(String login, String message);
    public List<String> getActiveClientNames();
}
