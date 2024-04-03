package back.app;

import back.DTO.UserDTO;
import back.infrustructure.storage.IDataBase;
import back.infrustructure.storage.PostgreDB;

public class App implements IApp {

    private IDataBase db = new PostgreDB();

    @Override
    public String login(UserDTO user) {
        // String token = tm.generateToken(login, password);
        String token = "OKTOKEN";
        System.out.println("APP login, token = " + token);

        String result = db.checkUser(user);
        System.out.println("db result " + result);
        return "{\"status\":\"" + result + "\", \"token\":\"" + token + "\" }";
    }

    @Override
    public String register(UserDTO user) {
        // TODO Auto-generated method stub
        return null;
    }
}
