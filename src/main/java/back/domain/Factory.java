package back.domain;

import back.domain.api.IFlatCalculator;
import back.domain.api.IRoomCalculator;
import back.domain.implement.FlatCalc;
import back.domain.implement.RoomCalc;

public class Factory {
    public IFlatCalculator createFlatCalculator() {
        return new FlatCalc();
    }

    public IRoomCalculator createRoomCalculator() {
        return new RoomCalc();
    }

}
