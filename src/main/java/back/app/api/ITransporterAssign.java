package back.app.api;

import back.infrastructure.out.websocket.ITransporter;

public interface ITransporterAssign {
    void useTransporter(ITransporter transporter);
    
}
