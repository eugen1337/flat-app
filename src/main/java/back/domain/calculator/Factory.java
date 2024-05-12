package back.domain.calculator;

import back.domain.calculator.api.IFlatCalculator;
import back.domain.calculator.api.IRoomCalculator;
import back.domain.calculator.implement.FlatCalc;
import back.domain.calculator.implement.RoomCalc;

public class Factory {
    public IFlatCalculator createFlatCalculator() {
        return new FlatCalc();
    }

    public IRoomCalculator createRoomCalculator() {
        return new RoomCalc();
    }

}
