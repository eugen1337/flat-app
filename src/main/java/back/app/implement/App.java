package back.app.implement;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import back.DTO.FlatDTO;
import back.DTO.UserDTO;
import back.app.api.IApp;
import back.app.api.IDBUsing;
import back.app.api.IExecAssign;
import back.app.api.ITMUsing;
import back.app.api.ITransporterAssign;
import back.domain.calculator.Factory;
import back.domain.calculator.Room;
import back.domain.calculator.api.IRoomCalculator;
import back.infrastructure.out.executor.IExecutor;
import back.infrastructure.out.storage.IDataBase;
import back.infrastructure.out.websocket.ITransporter;
import back.infrastructure.utils.tokenManager.ITokenManager;

public class App implements IApp, IDBUsing, ITMUsing, ITransporterAssign, IExecAssign {

    private IDataBase db;
    private ITokenManager tm;
    private ITransporter transporter;
    private IExecutor executor;

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

    @Override
    public void sendUpdate() {
        for (String login : transporter.getActiveClientNames()) {
            System.out.println("active clint: " + login);
            StringBuilder message = new StringBuilder();
            message.append("{\"date\" :\"");

            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String strDate = dateFormat.format(date);

            message.append(strDate);
            message.append("\"}");

            transporter.sendToClient(login, message.toString());
        }
    }

    @Override
    public String getArea(Room room, String login) {
        executor.execute(() -> {
            try {
                System.out.print("Thread.sleep(10); ");
                Thread.sleep(10);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            StringBuilder message = new StringBuilder();

            Factory roomFactory = new Factory();
            IRoomCalculator areaCalculator = roomFactory.createRoomCalculator();
            areaCalculator.calc(room);

            DecimalFormat decimalFormat = new DecimalFormat("#.###");

            String area = decimalFormat.format(room.getArea());
            String perimeter = decimalFormat.format(room.getPerimeter());

            System.out.print("Area = " + area);
            System.out.print("Perimeter = " + perimeter);

            message.append("{\"area\" :\"");
            message.append(area);
            message.append("\"");

            message.append(",\"perimeter\" :\"");
            message.append(perimeter);
            message.append("\"}");

            transporter.sendToClient(login, message.toString());
        });
        return "OK";
    }

    @Override
    public String setFlatPlan(FlatDTO flat, String login) {
        String result = db.addFlat(flat, login);

        System.out.println("db result setFlatPlan: " + result);

        return "{\"status\":\"" + result + "}";
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
    public void useTransporter(ITransporter transporter) {
        this.transporter = transporter;
    }

    @Override
    public void useExecutor(IExecutor executor) {
        this.executor = executor;
    }

}
