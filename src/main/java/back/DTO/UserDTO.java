package back.DTO;

public class UserDTO {
    private String login;
    private String password;

    public UserDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
