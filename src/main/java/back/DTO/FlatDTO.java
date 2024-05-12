package back.DTO;

public class FlatDTO {

    private double totalArea;
    private double totalPerimeter;

    private RoomDTO[] rooms;

    public double getArea() {
        return totalArea;
    }

    public void setArea(double area) {
        this.totalArea = area;
    }

    public double getPerimeter() {
        return totalPerimeter;
    }

    public void setPerimeter(double perimeter) {
        this.totalPerimeter = perimeter;
    }

    public RoomDTO[] getRooms() {
        return rooms;
    }

    public void setRooms(RoomDTO[] rooms) {
        this.rooms = rooms;
    }

}
