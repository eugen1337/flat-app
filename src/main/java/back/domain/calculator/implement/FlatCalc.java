package back.domain.calculator.implement;

import back.domain.calculator.Flat;
import back.domain.calculator.Room;
import back.domain.calculator.api.IFlatCalculator;

public class FlatCalc implements IFlatCalculator {
    @Override
    public Flat calc(Flat flat) {
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
