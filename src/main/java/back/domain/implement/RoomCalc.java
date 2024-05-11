package back.domain.implement;

import java.util.Arrays;

import back.domain.Room;
import back.domain.api.IRoomCalculator;

public class RoomCalc implements IRoomCalculator {
    @Override
    public Room calc(Room room) {
        double perimeter = Arrays.stream(room.getWallsLength()).sum();

        double area = 0;
        double[] wallsLength = room.getWallsLength();
        switch (room.getType()) {
            case "square":
                area = Math.pow(room.getWallsLength()[0], 2);
                break;
            case "rectangle":
                area = Math.sqrt(wallsLength[0] * wallsLength[1] * wallsLength[2] * wallsLength[3]);
                break;

            default:
                area = Math.sqrt(wallsLength[0] * wallsLength[1] * wallsLength[2] * wallsLength[3]);
                break;
        }

        room.setPerimeter(perimeter);
        room.setArea(area);

        System.out.println("Domain perimetr = " + perimeter);
        System.out.println("Domain area = " + area);
        return room;
    }

}
