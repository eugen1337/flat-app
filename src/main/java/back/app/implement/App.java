package back.app.implement;

import java.util.Map;

import back.DTO.UserDTO;
import back.app.api.IApp;
import back.app.api.IDBUsing;
import back.app.api.ITMUsing;
import back.domain.Room;
import back.infrastructure.out.storage.IDataBase;
import back.infrastructure.out.storage.DataBase;
import back.infrastructure.tokenManager.ITokenManager;
import back.infrastructure.tokenManager.TokenManager;

public class App implements IApp, IDBUsing, ITMUsing {

    private IDataBase db = new DataBase();
    private ITokenManager tm = new TokenManager();

    @Override
    public String login(UserDTO user) {
        String token = "bad token";
        String result = db.checkUser(user);

        if (result == "OK")
            token = tm.generateToken(user.getLogin(), user.getPassword());

        System.out.println("db result " + result);
        System.out.println("APP login, token = " + token);

        return "{\"status\":\"" + result + "\", \"token\":\"" + token + "\" }";
    }

    @Override
    public String register(UserDTO user) {
        String token = "bad token";
        String result = db.addUser(user);

        if (result == "OK")
            token = tm.generateToken(user.getLogin(), user.getPassword());
            
        System.out.println("db result " + result);
        System.out.println("APP login, token = " + token);

        return "{\"status\":\"" + result + "\", \"token\":\"" + token + "\" }";
    }

    @Override
    public void useTM(ITokenManager tm) {
        this.tm = tm;
    }

    @Override
    public void useDB(IDataBase db) {
        this.db = db;
    }

    @Override
    public String setRoomPlan(Room room, String login) {
        String result = db.addRoom(room, login);

        System.out.println("db result setRoomPlan: " + result);

        return "{\"status\":\"" + result + "}";
    }

    @Override
    public Map<String, String> getUserInfo(String token) {
        return tm.getTokenInfo(token);
    }

    @Override
    public boolean validateToken(String token) {
        // Map<String, String> tokenInfo = tm.getTokenInfo(token);
        // return db.isRegistredUser(tokenInfo.get("username"),
        // tokenInfo.get("passwd"));
        return true;
    }

}
