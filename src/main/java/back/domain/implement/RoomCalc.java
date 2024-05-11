package back.domain.implement;

import java.util.Arrays;

import back.domain.Room;
import back.domain.api.IRoomCalculator;

public class RoomCalc implements IRoomCalculator {
    @Override
    public Room calc(Room room) {
        double perimeter = Arrays.stream(room.getWallsLength()).sum();
        room.setPerimeter(perimeter);
        System.out.println("Domain perimetr = " + perimeter);

        double area = 0;

        int[] wallsLength = room.getWallsLength();
        System.out.println(wallsLength[0]);
        System.out.println(wallsLength[1]);
        System.out.println(wallsLength[2]);
        System.out.println(wallsLength[3]);

        System.out.println(wallsLength[0] * wallsLength[1] * wallsLength[2] * wallsLength[3]);

        switch (room.getType()) {
            case "square":
                area = room.getWallsLength()[0] ^ 2;
                break;

            default:
                area = Math.sqrt(wallsLength[0] * wallsLength[1] * wallsLength[2] * wallsLength[3]);
                break;
        }
        room.setArea(area);

        System.out.println("Domain area = " + area);
        return room;
    }

}
