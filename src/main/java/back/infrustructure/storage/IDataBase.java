package back.infrustructure.storage;

import back.DTO.UserDTO;

public interface IDataBase {
    boolean checkUser(UserDTO user);

    boolean addUser(UserDTO user);
}