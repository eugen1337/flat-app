package back.app.api;

import java.util.Map;

import back.DTO.FlatDTO;
import back.DTO.UserDTO;
import back.domain.calculator.Room;

public interface IApp {
    public String login(UserDTO user);

    public String register(UserDTO user);

    public String setRoomPlan(Room room, String login);

    boolean validateToken(String token);

    Map<String, String> getUserInfo(String token);

    public void sendUpdate();

    public String getArea(Room room, String login);
    public String setFlatPlan(FlatDTO flat, String login);
}
