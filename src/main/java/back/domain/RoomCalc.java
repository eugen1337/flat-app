import java.util.Arrays;

public class RoomCalc {
    public static Room calc(Room room) {
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
