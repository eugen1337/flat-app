package back.infrastructure.out.storage;

import back.DTO.UserDTO;
import back.domain.Room;

public interface IDataBase {
    String checkUser(UserDTO user);

    String addUser(UserDTO user);
    String addRoom(Room room, String login);
    Room getRoom(Room room, String login);

}