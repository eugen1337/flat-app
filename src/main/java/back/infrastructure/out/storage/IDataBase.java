package back.infrastructure.out.storage;

import java.util.ArrayList;

import back.DTO.FlatDTO;
import back.DTO.UserDTO;
import back.domain.calculator.Room;

public interface IDataBase {
    String checkUser(UserDTO user);

    String addUser(UserDTO user);

    String addRoom(Room room, String login);

    Room getRoom(Room room, String login);

    String addFlat(FlatDTO flat, String login);

    FlatDTO getFlat(String login, int flatId);

    ArrayList<Integer> getFlatList(String login);

}