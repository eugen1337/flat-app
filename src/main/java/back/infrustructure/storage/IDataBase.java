package back.infrustructure.storage;

import back.DTO.UserDTO;

public interface IDataBase {
    String checkUser(UserDTO user);

    String addUser(UserDTO user);
}