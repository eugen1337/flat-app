package back.domain.implement;

import java.util.Arrays;

import back.domain.Room;
import back.domain.api.IRoomCalculator;

public class RoomCalc implements IRoomCalculator {
    @Override
    public Room calc(Room room) {
        double perimeter = Arrays.stream(room.getWallsLength()).sum();
        room.setPerimeter(perimeter);

        double area = 0;

        switch (room.getType()) {
            case "square":
                area = room.getWallsLength()[0] ^ 2;
                break;

            default:
                break;
        }
        room.setArea(area);

        return room;
    }

}
