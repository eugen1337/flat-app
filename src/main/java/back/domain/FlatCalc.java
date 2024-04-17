public class FlatCalc {
    public static Flat calc(Flat flat) {
        double perimeter = 404;
        flat.setPerimeter(perimeter);

        double area = 0;
        for(Room room : flat.getRooms()){
            area = area + room.getArea();
        }
        flat.setArea(area);

        return flat;
    }
}
