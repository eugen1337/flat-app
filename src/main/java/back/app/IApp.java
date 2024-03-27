package back.app;

import back.DTO.UserDTO;

public interface IApp {
    public String login(UserDTO user);
    public String register(UserDTO user);
}
