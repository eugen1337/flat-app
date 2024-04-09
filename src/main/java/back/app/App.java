package back.app;

import back.DTO.UserDTO;
import back.infrustructure.storage.IDataBase;
import back.infrustructure.storage.PostgreDB;
import back.infrustructure.tokenManager.ITokenManager;
import back.infrustructure.tokenManager.TokenManager;

public class App implements IApp {

    private IDataBase db = new PostgreDB();
    private ITokenManager tm = new TokenManager();

    @Override
    public String login(UserDTO user) {
        String token = "bad token";
        String result = db.checkUser(user);

        System.out.println("db result " + result);
        System.out.println("APP login, token = " + token);

        if (result == "OK")
            token = tm.generateToken(user.getLogin(), user.getPassword());

        return "{\"status\":\"" + result + "\", \"token\":\"" + token + "\" }";
    }

    @Override
    public String register(UserDTO user) {
        String token = "bad token";
        String result = db.addUser(user);

        System.out.println("db result register: " + result);
        System.out.println("APP register, token = " + token);

        if (result == "OK")
            token = tm.generateToken(user.getLogin(), user.getPassword());

        return "{\"status\":\"" + result + "\", \"token\":\"" + token + "\" }";
    }
}
